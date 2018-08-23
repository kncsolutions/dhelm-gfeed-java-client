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
import in.kncsolutions.dhelm.gfeed.models.MarketMessageResponse;
/**
 *
 */
public class MarketMessageFinder {
	private WebSocket ws;
	private String instance;
	private String exchange;
	private boolean responseReceived=false;
	private boolean isResultPrepared=false;
	private MarketMessageResponse response=new MarketMessageResponse();
	/**
	 *@param clientEndPoint  : The clientEndPoint
	 *@param exchange : The exchange.
	 */
	public MarketMessageFinder(GfeedWSClientEndPoint clientEndPoint,String exchange) {
			if(clientEndPoint.getSocket()==null) {
				try {
					throw new DataException();
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
			else if(clientEndPoint.getSocket()!=null) {
				ws=clientEndPoint.getSocket();
				this.instance=Constants.GET_MARKET_MESSAGES;
				this.exchange=exchange;
			}
			findMarketMessage();			
	}
	/**
	 * 
	 */
	private void findMarketMessage() {
		if(ws!=null) {
			ws.sendText(getRequestMessage());
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.GET_MARKET_MESSAGES)) {
			    		 if(message.contains("\"MessageType\":\"MarketMessagesResult\"")) {
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
	 *@param msgType
	 *@return
	 */
	private  String getRequestMessage() {
		JSONObject jobj = new JSONObject();
		jobj.put("MessageType", this.instance);
		jobj.put("Exchange", this.exchange);
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
		this.response=gson.fromJson(message, MarketMessageResponse.class);
		if(Constants.DEBUG_MODE) {
			System.out.println("============================MARKET MESSAGE==========================");
			if(this.response.marketMessage!=null)
				for(int i=0;i<this.response.marketMessage.size();i++) {
					System.out.println(this.response.marketMessage.get(i).toString());
				}
		}
		isResultPrepared=true;
	}
	/**
	 *@return Returns the market message. 
	 */
	public MarketMessageResponse getMarketMessage() {
		int count=0;
		while(!isResultPrepared && count<7) {
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
