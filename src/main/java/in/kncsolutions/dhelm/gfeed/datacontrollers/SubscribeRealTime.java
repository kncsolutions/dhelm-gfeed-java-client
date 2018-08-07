/*
 *SubscribeRealTime.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.datacontrollers;

import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

import in.kncsolutions.dhelm.exceptions.DataException;
import in.kncsolutions.dhelm.gfeed.Constants;
import in.kncsolutions.dhelm.gfeed.GfeedWSClientEndPoint;
import in.kncsolutions.dhelm.gfeed.models.SubscribeRealTimeResponse;
/**
 *
 */
public class SubscribeRealTime {
	private WebSocket ws;
	private String instance;
	private String exchange;
	private String instrumentIdentifier;
	private boolean responseReceived=false;
	private boolean isResultPrepared=false;
    private SubscribeRealTimeResponse response;
  /**
   *@param clientEndPoint  : The clientEndPoint	 
   *@param exchange : The exchange.
   *@param instrumentIdentifier : The instrument identifier for the instrument.
   */
	public SubscribeRealTime(GfeedWSClientEndPoint clientEndPoint,String exchange,
			String instrumentIdentifier) {
			if(clientEndPoint.getSocket()==null) {
				try {
					throw new DataException();
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
			else if(clientEndPoint.getSocket()!=null) {
				ws=clientEndPoint.getSocket();
				this.instance=Constants.SUBSCRIBE_REALTIME;
				this.exchange=exchange;
				this.instrumentIdentifier=instrumentIdentifier;
			}
			subscribe();			
	}
   /**
	*@param clientEndPoint  : The clientEndPoint	 
	*@param exchange : The exchange.
	*@param instrumentIdentifier : The instrument identifier for the instrument.
    *@param optionalParameters : The valid optional parameters.
	*/
	public SubscribeRealTime(GfeedWSClientEndPoint clientEndPoint,
                         String exchange,String instrumentIdentifier,
                         Map<String,Boolean> optionalParameters) {
			if(clientEndPoint.getSocket()==null) {
				try {
					throw new DataException();
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
			else if(clientEndPoint.getSocket()!=null) {
				ws=clientEndPoint.getSocket();
				this.instance=Constants.SUBSCRIBE_REALTIME;
				this.exchange=exchange;
				this.instrumentIdentifier=instrumentIdentifier;
			}
			subscribe(optionalParameters);			
	}
  /**
	* 
	*/
	private void subscribe() {
		if(ws!=null) {
			ws.sendText(getMessage());
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.SUBSCRIBE_REALTIME)) {
			    		 if(message.contains("\"MessageType\":\"RealtimeResult\"")) {
			    			 responseReceived=true;
			    			 if(Constants.DEBUG_MODE)
			    			     System.out.println(message);
			             parseResponse(message);
			    		 }
			    	  }
			    	 }
			     }
		});
		}
	}
    /**
	 * 
	 */
	private void subscribe(Map<String,Boolean> optionalParameters) {
		if(ws!=null) {
			ws.sendText(getMessage(optionalParameters));
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.SUBSCRIBE_REALTIME)) {
			    		 if(message.contains("\"MessageType\":\"RealtimeResult\"")) {
			    			 responseReceived=true;
			    			 if(Constants.DEBUG_MODE)
			    			     System.out.println(message);
			                 parseResponse(message);
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
		jobj.put("Exchange", this.exchange);
		jobj.put("InstrumentIdentifier", this.instrumentIdentifier);
  		if(Constants.DEBUG_MODE) {
		  System.out.println("Request : "+jobj.toString());
		}
		return jobj.toString();
	}
   /**
	 *@param optionalParameters
	 *@return
	 */
	private  String getMessage(Map<String,Boolean> optionalParameters) {
		JSONObject jobj = new JSONObject();
		jobj.put("MessageType", this.instance);
		jobj.put("Exchange", this.exchange);
		jobj.put("InstrumentIdentifier", this.instrumentIdentifier);
      if(optionalParameters.containsKey(Constants.UNSUBSCRIBE_KEY))
        jobj.put(Constants.UNSUBSCRIBE_KEY,optionalParameters.get(Constants.UNSUBSCRIBE_KEY));
		if(Constants.DEBUG_MODE) {
		  System.out.println("Request : "+jobj.toString());
		}
		return jobj.toString();
	}

  /**
	* 
	*/
	private void parseResponse(String message){
		Gson gson = new GsonBuilder().create();
      this.response=gson.fromJson(message, SubscribeRealTimeResponse.class);
      if(Constants.DEBUG_MODE) {
			System.out.println("List of option types this exchange.."+this.exchange);
			if(this.response!=null)
			    System.out.println(this.response.toString());		
		}
		isResultPrepared=true;

	}
  /**
   *@return Returns the  real time subscription response.
   */
	public SubscribeRealTimeResponse getResponse() {
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
