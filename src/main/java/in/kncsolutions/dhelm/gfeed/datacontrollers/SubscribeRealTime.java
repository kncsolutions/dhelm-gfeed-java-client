/**
*Copyright 2018 Knc Solutions Private Limited
*
*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.
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
   private OnRealtimeDataArrival onRealtimeDataArrival;
   /**
   *@param clientEndPoint  : The clientEndPoint	 
   *@param exchange : The exchange.
   *@param instrumentIdentifier : The instrument identifier for the instrument.
   *@param onRealtimeDataArrival : The callback for real time data message arrival
   */
	public SubscribeRealTime(GfeedWSClientEndPoint clientEndPoint,String exchange,
			String instrumentIdentifier,OnRealtimeDataArrival onRealtimeDataArrival) {
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
            this.onRealtimeDataArrival=onRealtimeDataArrival;
			}
			subscribe();			
	}

   /**
	*@param clientEndPoint  : The clientEndPoint	 
	*@param exchange : The exchange.
	*@param instrumentIdentifier : The instrument identifier for the instrument.
   *@param optionalParameters : The valid optional parameters.
   *@param onRealtimeDataArrival : The callback for real time data message arrival
	*/
	public SubscribeRealTime(GfeedWSClientEndPoint clientEndPoint,
                         String exchange,String instrumentIdentifier,
                         Map<String,Boolean> optionalParameters,
                         OnRealtimeDataArrival onRealtimeDataArrival) {
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
            this.onRealtimeDataArrival=onRealtimeDataArrival;
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
                      responseReceived=false;
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
                      responseReceived=false;
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
      Constants.REAL_TIME_RESPONSE=this.response;
       if(onRealtimeDataArrival != null) {
           onRealtimeDataArrival.onRealtimedataArrival(Constants.REAL_TIME_RESPONSE);
       }
      if(Constants.DEBUG_MODE) {
			System.out.println("Real time data..");
			if(this.response!=null)
			    System.out.println(Constants.REAL_TIME_RESPONSE.toString());		
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
