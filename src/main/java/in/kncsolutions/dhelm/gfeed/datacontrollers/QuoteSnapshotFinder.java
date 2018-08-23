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

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

import in.kncsolutions.dhelm.exceptions.DataException;
import in.kncsolutions.dhelm.gfeed.Constants;
import in.kncsolutions.dhelm.gfeed.GfeedWSClientEndPoint;
import in.kncsolutions.dhelm.gfeed.models.QuoteSnapshotList;

public class QuoteSnapshotFinder {
	private WebSocket ws;
	private String instance;
	private String exchange;
	private String [] instrumentIdentifiers;
	private boolean responseReceived=false;
	private boolean isResultPrepared=false;
    private QuoteSnapshotList responseList;
    /**
	 *@param clientEndPoint  : The clientEndPoint	 
	 *@param exchange : The exchange.
	 *@param instrumentIdentifiers : The list of instrument identifiers for the instruments.
	 */
	public QuoteSnapshotFinder(GfeedWSClientEndPoint clientEndPoint,String exchange,
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
					this.instance=Constants.GET_SNAPSHOT;
					this.exchange=exchange;
					this.instrumentIdentifiers=instrumentIdentifiers;
				}
				findLastQuoteList();			
	}
	/**
	 *@param clientEndPoint : The clientEndPoint	 
	 *@param exchange : The exchange
	 *@param instrumentIdentifiers : The list of instrument identifiers for the instruments.
	 *@param optionalParams : The valid optional parameters.
	 */
		public QuoteSnapshotFinder(GfeedWSClientEndPoint clientEndPoint,String exchange,
					String [] instrumentIdentifiers,Map<String,String> optionalParams) {
					if(clientEndPoint.getSocket()==null || instrumentIdentifiers.length>25) {
						try {
							throw new DataException("Data Not Valid.....");
						} catch (DataException e) {
							e.printStackTrace();
						}
					}
					else if(clientEndPoint.getSocket()!=null) {
						ws=clientEndPoint.getSocket();
						this.instance=Constants.GET_SNAPSHOT;
						this.exchange=exchange;
						this.instrumentIdentifiers=instrumentIdentifiers;
					}
					findLastQuoteList(optionalParams);			
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
			    	  if(instance.equals(Constants.GET_SNAPSHOT)) {
			    		 if(message.contains("\"MessageType\":\"SnapshotResult\"")) {
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
	private void findLastQuoteList(Map<String,String> optionalParams) {
		if(ws!=null) {
			ws.sendText(getMessage(optionalParams));
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.GET_SNAPSHOT)) {
			    		 if(message.contains("\"MessageType\":\"SnapshotResult\"")) {
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
	private  String getMessage(Map<String,String> optionalParams) {
		JSONObject jobj = new JSONObject();
		jobj.put("MessageType", this.instance);
		jobj.put("Exchange", this.exchange);
		JSONArray ja = new JSONArray();
	    for(int i=0;i<this.instrumentIdentifiers.length;i++) {
			JSONObject tmp = new JSONObject();
		    tmp.put("Value", this.instrumentIdentifiers[i]);
			ja.put(tmp);
		}
		jobj.put("InstrumentIdentifiers", ja);
		if(optionalParams.containsKey(Constants.PERIODICITY_KEY))
			 jobj.put(Constants.PERIODICITY_KEY, optionalParams.get(Constants.PERIODICITY_KEY));
		if(optionalParams.containsKey(Constants.PERIOD_KEY))
			 jobj.put(Constants.PERIOD_KEY, Integer.parseInt(optionalParams.get(Constants.PERIOD_KEY)));
  		if(Constants.DEBUG_MODE) {
		  System.out.println("Request : "+jobj.toString());
		}
		return jobj.toString();
	}
	/**
	*@return
	*/
	private  String getMessage() {
		JSONObject jobj = new JSONObject();
		jobj.put("MessageType", this.instance);
		jobj.put("Exchange", this.exchange);
		JSONArray ja = new JSONArray();
	    for(int i=0;i<this.instrumentIdentifiers.length;i++) {
			JSONObject tmp = new JSONObject();
		    tmp.put("Value", this.instrumentIdentifiers[i]);
			ja.put(tmp);
		}
		jobj.put("InstrumentIdentifiers", ja);
		
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
      this.responseList=gson.fromJson(message, QuoteSnapshotList.class);
      if(Constants.DEBUG_MODE) {
			System.out.println("===================QUOTE SNAPSHOT====================");
			if(this.responseList.quoteSnapshotList!=null)
				for(int i=0;i<this.responseList.quoteSnapshotList.size();i++)
			    System.out.println(this.responseList.quoteSnapshotList.get(i).toString());		
		}
		isResultPrepared=true;

	}
	
  /**
   *@return Returns the quote snapshots for the asked instruments. 
   */
	public QuoteSnapshotList getResponse() {
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
