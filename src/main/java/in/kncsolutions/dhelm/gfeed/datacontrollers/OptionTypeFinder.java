/*
 *OptionTypeFinder.java
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
import in.kncsolutions.dhelm.gfeed.models.OptionTypeResponse;
import java.util.Map;
/**
 * @author pallav
 */
public class OptionTypeFinder {
    private WebSocket ws;
	private String instance;
	private String exchange;
	private boolean responseReceived=false;
	private boolean isResultPrepared=false;
    private OptionTypeResponse response;
  /**
   *@param clientEndPoint  : The clientEndPoint
   *@param exchange : The exchange.
   */
	public OptionTypeFinder(GfeedWSClientEndPoint clientEndPoint,String exchange) {
			if(clientEndPoint.getSocket()==null) {
				try {
					throw new DataException();
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
			else if(clientEndPoint.getSocket()!=null) {
				ws=clientEndPoint.getSocket();
				this.instance=Constants.GET_OPTION_TYPES;
				this.exchange=exchange;
			}
			findOptionTypes();			
	}
    /**
	 *@param clientEndPoint  : The clientEndPoint
	 *@param exchange : The exchange.
     *@param optionalParameters : The valid optional parameters
	 */
	public OptionTypeFinder(GfeedWSClientEndPoint clientEndPoint,String exchange,
                         Map<String,String> optionalParameters) {
			if(clientEndPoint.getSocket()==null) {
				try {
					throw new DataException();
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
			else if(clientEndPoint.getSocket()!=null) {
				ws=clientEndPoint.getSocket();
				this.instance=Constants.GET_OPTION_TYPES;
				this.exchange=exchange;
			}
			findOptionTypes(optionalParameters);			
	}
  /**
	* 
	*/
	private void findOptionTypes() {
		if(ws!=null) {
			ws.sendText(getMessage());
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.GET_OPTION_TYPES)) {
			    		 if(message.contains("\"MessageType\":\"OptionTypesResult\"")) {
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
	 * 
	 */
	private void findOptionTypes(Map<String,String> optionalParameters) {
		if(ws!=null) {
			ws.sendText(getMessage(optionalParameters));
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.GET_OPTION_TYPES)) {
			    		 if(message.contains("\"MessageType\":\"OptionTypesResult\"")) {
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
		jobj.put("Exchange", this.exchange);
  		if(Constants.DEBUG_MODE) {
		  System.out.println("Request : "+jobj.toString());
		}
		return jobj.toString();
	}
   /**
	 *@param optionalParameters
	 *@return
	 */
	private  String getMessage(Map<String,String> optionalParameters) {
		JSONObject jobj = new JSONObject();
		jobj.put("MessageType", this.instance);
		jobj.put("Exchange", this.exchange);
      if(optionalParameters.containsKey(Constants.INSTRUMENT_TYPE_KEY))
        jobj.put(Constants.INSTRUMENT_TYPE_KEY,optionalParameters.get(Constants.INSTRUMENT_TYPE_KEY));
      if(optionalParameters.containsKey(Constants.PRODUCT_KEY))
        jobj.put(Constants.PRODUCT_KEY,optionalParameters.get(Constants.PRODUCT_KEY));
      if(optionalParameters.containsKey(Constants.EXPIRY_DATE_KEY))
        jobj.put(Constants.EXPIRY_DATE_KEY,optionalParameters.get(Constants.EXPIRY_DATE_KEY));
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
      this.response=gson.fromJson(message, OptionTypeResponse.class);
      if(Constants.DEBUG_MODE) {
			System.out.println("List of option types this exchange.."+this.exchange);
			if(this.response.optionType!=null)
				for(int i=0;i<this.response.optionType.size();i++) {
					System.out.println(this.response.optionType.get(i).type);
				}
		}
		isResultPrepared=true;

	}
  /**
	*@return Returns the types of options available for the exchange/setup. 
	*/
	public OptionTypeResponse getOptionTypes() {
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