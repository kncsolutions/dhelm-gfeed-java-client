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
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

import in.kncsolutions.dhelm.exceptions.DataException;
import in.kncsolutions.dhelm.gfeed.Constants;
import in.kncsolutions.dhelm.gfeed.GfeedWSClientEndPoint;
import in.kncsolutions.dhelm.gfeed.models.ExpiryDateResponse;
import java.util.Map;
/**
 *
 */
public class ExpiryDateFinder {
   private WebSocket ws;
	private String instance;
	private String exchange;
	private boolean responseReceived=false;
	private boolean isResultPrepared=false;
   private ExpiryDateResponse response;
   /**
    *@param clientEndPoint  : The clientEndPoint
    *@param exchange : The exchange.
    */
	public ExpiryDateFinder(GfeedWSClientEndPoint clientEndPoint,String exchange) {
			if(clientEndPoint.getSocket()==null) {
				try {
					throw new DataException();
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
			else if(clientEndPoint.getSocket()!=null) {
				ws=clientEndPoint.getSocket();
				this.instance=Constants.GET_EXPIRY_DATES;
				this.exchange=exchange;
			}
			findExpiryDates();			
	}
	/**
	 *@param clientEndPoint : The clientEndPoint
	 *@param exchange : The exchange
	 *@param optionalParameters : The valid optional parameters.
	 */
	public ExpiryDateFinder(GfeedWSClientEndPoint clientEndPoint,String exchange,
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
				this.instance=Constants.GET_EXPIRY_DATES;
				this.exchange=exchange;
			}
			findExpiryDates(optionalParameters);			
	}
  /**
	* 
	*/
	private void findExpiryDates() {
		if(ws!=null) {
			ws.sendText(getMessage());
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.GET_EXPIRY_DATES)) {
			    		 if(message.contains("\"MessageType\":\"ExpiryDatesResult\"")) {
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
	private void findExpiryDates(Map<String,String> optionalParameters) {
		if(ws!=null) {
			ws.sendText(getMessage(optionalParameters));
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.GET_EXPIRY_DATES)) {
			    		 if(message.contains("\"MessageType\":\"ExpiryDatesResult\"")) {
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
      this.response=gson.fromJson(message, ExpiryDateResponse.class);
      if(Constants.DEBUG_MODE) {
			System.out.println("List of  expiry dates for this exchange.."+this.exchange);
			if(this.response.dates!=null)
				for(int i=0;i<this.response.dates.size();i++) {
					System.out.print(this.response.dates.get(i).expirydate+"\t");
				}
		}
		isResultPrepared=true;

	}
  /**
   *@return Returns the expiry dates. 
   */
	public ExpiryDateResponse getExpiryDates() {
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