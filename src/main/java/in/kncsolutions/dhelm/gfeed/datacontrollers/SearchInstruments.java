/*
 *SearchInstruments.java
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
import in.kncsolutions.dhelm.gfeed.models.InstrumentResponse;
/**
 *
 */
public class SearchInstruments {
	private WebSocket ws;
	private String instance;
	private String exchange;
	private String searchKey;
	private boolean responseReceived=false;
	private boolean isResultPrepared=false;
	private InstrumentResponse instrumentresponse=new InstrumentResponse();
	   /**
	    *@param clientEndPoint  : The clientEndPoint	 
	    *@param exchange : The exchange.
	    *@param searchKey : The keyword for search
	    */
		public SearchInstruments(GfeedWSClientEndPoint clientEndPoint,
				String exchange,String searchKey) {
			if(clientEndPoint.getSocket()==null) {
				try {
					throw new DataException();
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
			else if(clientEndPoint.getSocket()!=null) {
				ws=clientEndPoint.getSocket();
				this.instance=Constants.GET_INSTRUMENTS_ON_SEARCH;
				this.exchange=exchange;
				this.searchKey=searchKey;
			}
			findInstruments();			
		}
		/**
		 * 
		 */
		private void findInstruments() {
			if(ws!=null) {
				ws.sendText(getInstruments());
				ws.addListener(new WebSocketAdapter() {
				     @Override
				     public void onTextMessage(WebSocket websocket, String message) throws Exception {
				    	 if(!responseReceived) {
				    	  if(instance.equals(Constants.GET_INSTRUMENTS_ON_SEARCH)) {
				    		 if(message.contains("\"MessageType\":\"InstrumentsOnSearchResult\"")) {
				    			responseReceived=true;
				    			if(Constants.DEBUG_MODE)
				    				System.out.println(message);
				                parseInstrumentList(message);
				                
				    		 }
				    	  }
				    	 }
				     }
			});
			}
		}
		/**
		 *@param msgType
		 *@return
		 */
		private  String getInstruments() {
			JSONObject jobj = new JSONObject();
			jobj.put("MessageType", this.instance);
			jobj.put("Exchange", this.exchange);
			jobj.put("Search", this.searchKey);
			if(Constants.DEBUG_MODE) {
			  System.out.println("Request : "+jobj.toString());
			}
			return jobj.toString();
		}
		/**
		 * 
		 */
		private void parseInstrumentList(String message){
			Gson gson = new GsonBuilder().create();
			this.instrumentresponse=gson.fromJson(message, InstrumentResponse.class);
			//Constants.ALL_INSTRUMENTS.add(this.instrumentresponse);
			if(Constants.DEBUG_MODE) {
				System.out.println("===============SEARCH RESULT : List of  instruments=====================");
				if(this.instrumentresponse.instruments!=null)
				for(int i=0;i<this.instrumentresponse.instruments.size();i++) {
					System.out.println(
						(i+1)+ "::"+this.instrumentresponse.instruments.get(i).toString()						
							);
				}
			}
			isResultPrepared=true;
		
		}
		/**
		 *@return Returns the list of instruments matching with the search criteria.Maximum 25 instruments are
		 *returned per search. 
		 */
		public InstrumentResponse getAvailableInstruments() {
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
			return this.instrumentresponse;
		}
}
