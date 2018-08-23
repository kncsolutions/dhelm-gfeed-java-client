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
import in.kncsolutions.dhelm.gfeed.models.ExchangeList;
/**
 *To get the list of subscribed exchanges, this class have to be used.
 */
public class SubscribedExchanges {
    private WebSocket ws;
    private String instance;
    private ExchangeList exchangeList=new ExchangeList();
    private boolean responseReceived=false;
    private boolean isResultPrepared=false;
   /**
    *@param clientEndPoint  : The GfeedWSClientEndpoint.
    */
	public SubscribedExchanges(GfeedWSClientEndPoint clientEndPoint) {
		if(clientEndPoint.getSocket()==null) {
			try {
				throw new DataException();
			} catch (DataException e) {
				e.printStackTrace();
			}
		}
		else if(clientEndPoint.getSocket()!=null) {
			ws=clientEndPoint.getSocket();
			this.instance=Constants.GET_EXCHANGES;
		}
		findExchanges();			
	}
	/**
	 * 
	 */
	private void findExchanges() {
		if(ws!=null) {
			ws.sendText(getExchanges(instance));
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			       if(!responseReceived) {
			    	 if(instance.equals(Constants.GET_EXCHANGES)) {
			    		 if(message.contains("\"MessageType\":\"ExchangesResult\"")) {
			    			 responseReceived=true;
			                parseExchangeList(message);
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
	private  String getExchanges(String msgType) {
		JSONObject jobj = new JSONObject();
		jobj.put("MessageType", msgType);
		if(Constants.DEBUG_MODE) {
			  System.out.println("Request : "+jobj.toString());
		}
		return jobj.toString();
	}
	/**
	 * 
	 */
	private void parseExchangeList(String message){
		Gson gson = new GsonBuilder().create();
		this.exchangeList=gson.fromJson(message, ExchangeList.class);
		if(Constants.DEBUG_MODE) {
		  System.out.println("Message : "+exchangeList.message);
		  if(exchangeList.exchanges!=null) {
			System.out.println("List of subscribed exchanges:");
			for(int i=0; i<exchangeList.exchanges.size();i++) {
				System.out.println("Exchange Identifier : "+ exchangeList.exchanges.get(i).toString());
			}
		  }
		}
		isResultPrepared=true;
	}
	/**
	 *@return Returns the subscribed exchange list.
	 */
	public ExchangeList getExchangeList() {
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
		return this.exchangeList;
	}
}
