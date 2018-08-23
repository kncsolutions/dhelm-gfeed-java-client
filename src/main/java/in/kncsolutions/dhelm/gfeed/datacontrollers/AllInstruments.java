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
import in.kncsolutions.dhelm.gfeed.models.InstrumentResponse;
/** 
 *
 */
public class AllInstruments {
	private WebSocket ws;
	private String instance;
	private String exchange;
	private boolean responseReceived=false;
	private boolean isResultPrepared=false;
	private InstrumentResponse instrumentresponse=new InstrumentResponse();
	   /**
	    *@param clientEndPoint  : The clientEndPoint
	    *@param exchange : The exchange.
	    */
		public AllInstruments(GfeedWSClientEndPoint clientEndPoint,String exchange) {
			if(clientEndPoint.getSocket()==null) {
				try {
					throw new DataException();
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
			else if(clientEndPoint.getSocket()!=null) {
				ws=clientEndPoint.getSocket();
				this.instance=Constants.GET_INSTRUMENTS;
				this.exchange=exchange;
			}
			findInstruments();			
		}
		/**
		 *@param clientEndPoint : The clientEndPoint
		 *@param exchange : The exchange
		 *@param optionalParams : The valid optional parameters.
		 */
		public AllInstruments(GfeedWSClientEndPoint clientEndPoint,String exchange,
					Map<String,String> optionalParams) {
				if(clientEndPoint.getSocket()==null) {
					try {
						throw new DataException();
					} catch (DataException e) {
						e.printStackTrace();
					}
				}
				else if(clientEndPoint.getSocket()!=null) {
					ws=clientEndPoint.getSocket();
					this.instance=Constants.GET_INSTRUMENTS;
					this.exchange=exchange;
				}
				findInstruments(optionalParams);			
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
				    	  if(instance.equals(Constants.GET_INSTRUMENTS)) {
				    		 if(message.contains("\"MessageType\":\"InstrumentsResult\"")) {
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
		 * 
		 */
		private void findInstruments(Map<String,String> optionalParams) {
			if(ws!=null) {
				ws.sendText(getInstruments(optionalParams));
				ws.addListener(new WebSocketAdapter() {
				     @Override
				     public void onTextMessage(WebSocket websocket, String message) throws Exception {
				    	 if(!responseReceived) {
				    	  if(instance.equals(Constants.GET_INSTRUMENTS)) {
				    		 if(message.contains("\"MessageType\":\"InstrumentsResult\"")) {
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
		 *@return
		 */
		private  String getInstruments() {
			JSONObject jobj = new JSONObject();
			jobj.put("MessageType", this.instance);
			jobj.put("Exchange", this.exchange);
			if(Constants.DEBUG_MODE) {
			  System.out.println("Request : "+jobj.toString());
			}
			return jobj.toString();
		}
		/**
		 *@return
		 */
		private  String getInstruments(Map<String,String> optionalParams) {
			JSONObject jobj = new JSONObject();
			jobj.put("MessageType", this.instance);
			jobj.put("Exchange", this.exchange);
			if(optionalParams.containsKey(Constants.INSTRUMENT_TYPE_KEY))
		        jobj.put(Constants.INSTRUMENT_TYPE_KEY,optionalParams.get(Constants.INSTRUMENT_TYPE_KEY));
		    if(optionalParams.containsKey(Constants.PRODUCT_KEY))
		        jobj.put(Constants.PRODUCT_KEY,optionalParams.get(Constants.PRODUCT_KEY));
		    if(optionalParams.containsKey(Constants.EXPIRY_DATE_KEY))
		        jobj.put(Constants.EXPIRY_DATE_KEY,optionalParams.get(Constants.EXPIRY_DATE_KEY));
		    if(optionalParams.containsKey(Constants.OPTION_TYPE_KEY))
		        jobj.put(Constants.OPTION_TYPE_KEY,optionalParams.get(Constants.OPTION_TYPE_KEY));
		    if(optionalParams.containsKey(Constants.STRIKE_PRICE_KEY))
		        jobj.put(Constants.STRIKE_PRICE_KEY,optionalParams.get(Constants.STRIKE_PRICE_KEY));
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
				System.out.println("================List of  instruments=======================");
				if(this.instrumentresponse.instruments!=null)
				for(int i=0;i<this.instrumentresponse.instruments.size();i++) {
					System.out.println(
						(i+1)+ "::"+this.instrumentresponse.instruments.get(i).toString());
				}
			}
			isResultPrepared=true;
		
		}
		/**
		 *@return Returns the list of available instruments for the specified exchange. 
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
