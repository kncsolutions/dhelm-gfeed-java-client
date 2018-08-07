/*
 *ServerInfoFinder.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.datacontrollers;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

import in.kncsolutions.dhelm.exceptions.DataException;
import in.kncsolutions.dhelm.gfeed.Constants;
import in.kncsolutions.dhelm.gfeed.GfeedWSClientEndPoint;
import in.kncsolutions.dhelm.gfeed.models.ServerInfo;

public class ServerInfoFinder {
	private WebSocket ws;
	private String instance;
	private boolean responseReceived=false;
	private boolean isResultPrepared=false;
	private  ServerInfo response;
	/**
	 *@param clientEndPoint  : The clientEndPoint
	 */
		public ServerInfoFinder(GfeedWSClientEndPoint clientEndPoint) {
				if(clientEndPoint.getSocket()==null) {
					try {
						throw new DataException();
					} catch (DataException e) {
						e.printStackTrace();
					}
				}
				else if(clientEndPoint.getSocket()!=null) {
					ws=clientEndPoint.getSocket();
					this.instance=Constants.GET_SERVER_INFO;
				}
				findServerInfo();			
		}
	  /**
	   * 
	   */
	  private void findServerInfo() {
			if(ws!=null) {
				ws.sendText(getMessage());
				ws.addListener(new WebSocketAdapter() {
				     @Override
				     public void onTextMessage(WebSocket websocket, String message) throws Exception {
				    	 if(!responseReceived) {
				    	  if(instance.equals(Constants.GET_SERVER_INFO)) {
				    		 if(message.contains("\"MessageType\":\"ServerInfoResult\"")) {
				    			 responseReceived=true;
	                         if(Constants.DEBUG_MODE)
	                             System.out.println(message);
				             parseMessage(message);
				    		 }
				    	  }
				    	 }
				     }
			});
			}
		}
	   
	  /**
		*@return
		*/
		private  String getMessage() {
			JSONObject jobj = new JSONObject();
			jobj.put("MessageType", this.instance);
	  		if(Constants.DEBUG_MODE) {
			  System.out.println("Request : "+jobj.toString());
			}
			return jobj.toString();
		}
	   /**
		* 
		*/
		private void parseMessage(String message){
		   Gson gson = new GsonBuilder().create();
	       this.response=gson.fromJson(message, ServerInfo.class);
	      if(Constants.DEBUG_MODE) {
	        displayServerInfo();
			}
			isResultPrepared=true;

		}
	   /**
	    *
	    */
	    private void displayServerInfo(){
	       System.out.println("=====================Server Info=========================");
	       if(this.response!=null){
	    	   System.out.println("SERVER ID : "+response.serverID);
	       }
	          
	    }
	  /**
	   *@return Returns server information 
	   */
	  public ServerInfo getServerInfo() {
			int count=0;
			while(!isResultPrepared && count<Constants.RETURN_RETRY_NO1) {
				try {
					if(Constants.DEBUG_MODE) 
					 System.out.println((count+1) + "::Result not preapared");
					 Thread.sleep(Constants.RETRY_DURATION);
					 count++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(Constants.DEBUG_MODE) 
			   System.out.println("Returning result set..");
			return this.response;
		}
}
