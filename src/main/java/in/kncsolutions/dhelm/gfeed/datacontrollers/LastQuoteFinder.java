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



import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

import in.kncsolutions.dhelm.exceptions.DataException;
import in.kncsolutions.dhelm.gfeed.Constants;
import in.kncsolutions.dhelm.gfeed.GfeedWSClientEndPoint;
import in.kncsolutions.dhelm.gfeed.models.LastQuote;
import in.kncsolutions.dhelm.gfeed.models.LastQuoteList;
/**
 *
 */

public class LastQuoteFinder {
	private WebSocket ws;
	private String instance;
	private String exchange;
	private String instrumentIdentifier;
	private String [] instrumentIdentifiers;
	private boolean responseReceived=false;
	private boolean isResultPrepared=false;
    private LastQuote response;
    private LastQuoteList responseList;
  /**
   *@param clientEndPoint  : The clientEndPoint
   *@param instrumentIdentifier : The instrument identifier for the instrument.
   *@param exchange : The exchange.
   */
	public LastQuoteFinder(GfeedWSClientEndPoint clientEndPoint,String exchange,
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
				this.instance=Constants.GET_LAST_QUOTE;
				this.exchange=exchange;
				this.instrumentIdentifier=instrumentIdentifier;
			}
			findLastQuote();			
	}
	/**
	 *@param clientEndPoint  : The clientEndPoint
	 *@param exchange : The exchange.
	 *@param instrumentIdentifiers : The list of instrument identifiers for the instruments.
	 */
	public LastQuoteFinder(GfeedWSClientEndPoint clientEndPoint,String exchange,
				String [] instrumentIdentifiers) {
				if(clientEndPoint.getSocket()==null || instrumentIdentifiers.length>25) {
					try {
						throw new DataException("Data Not Valid.....");
					} catch (DataException e) {
						e.printStackTrace();
					}
				}
				else if(clientEndPoint.getSocket()!=null) {
					ws=clientEndPoint.getSocket();
					this.instance=Constants.GET_LAST_QUOTE_ARRAY;
					this.exchange=exchange;
					this.instrumentIdentifiers=instrumentIdentifiers;
				}
				findLastQuoteList();			
		}
   
   /**
	* 
	*/
	private void findLastQuote() {
		if(ws!=null) {
			ws.sendText(getMessage());
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.GET_LAST_QUOTE)) {
			    		 if(message.contains("\"MessageType\":\"LastQuoteResult\"")) {
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
	private void findLastQuoteList() {
		if(ws!=null) {
			ws.sendText(getMessage());
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.GET_LAST_QUOTE_ARRAY)) {
			    		 if(message.contains("\"MessageType\":\"LastQuoteArrayResult\"")) {
			    			 responseReceived=true;
			    			 if(Constants.DEBUG_MODE) 
			    			     System.out.println(message);
			             parseResponseList(message);
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
		if(this.instrumentIdentifiers==null) {
		  jobj.put("InstrumentIdentifier", this.instrumentIdentifier);
		}
		else {
			 JSONArray ja = new JSONArray();
			 for(int i=0;i<this.instrumentIdentifiers.length;i++) {
				 JSONObject tmp = new JSONObject();
				 tmp.put("Value", this.instrumentIdentifiers[i]);
				 ja.put(tmp);
			 }
			 jobj.put("InstrumentIdentifiers", ja);
		}
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
      this.response=gson.fromJson(message, LastQuote.class);
      if(Constants.DEBUG_MODE) {
			System.out.println("===================LAST QUOTE====================");
			if(this.response!=null)
			    System.out.println(this.response.toString());		
		}
		isResultPrepared=true;

	}
	/**
	* 
	*/
	private void parseResponseList(String message){
	  Gson gson = new GsonBuilder().create();
      this.responseList=gson.fromJson(message, LastQuoteList.class);
      if(Constants.DEBUG_MODE) {
			System.out.println("===================LAST QUOTE LIST====================");
			if(this.responseList.lastQuotes!=null)
			    for(int i=0;i<this.responseList.lastQuotes.size();i++)	{
			    	System.out.println(this.responseList.lastQuotes.get(i).toString());
			    }
		}
		isResultPrepared=true;

	}
  /**
   *@return  Returns the last quote
   */
	public LastQuote getResponse() {
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
	/**
	 *@return Returns the last quote of the specified instruments.
	 */
	public LastQuoteList getResponseList() {
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
			return this.responseList;
		}

}
