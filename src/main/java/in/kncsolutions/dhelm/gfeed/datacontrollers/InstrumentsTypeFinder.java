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
import in.kncsolutions.dhelm.gfeed.models.InstrumentTypesResponse;
/**
 *
 */
public class InstrumentsTypeFinder {
	private WebSocket ws;
	private String instance;
	private String exchange;
	private boolean responseReceived=false;
	private boolean isResultPrepared=false;
	private InstrumentTypesResponse response=new InstrumentTypesResponse();
	/**
	 *@param clientEndPoint  : The clientEndPoint
	 *@param exchange : The exchange.
	 */
	public InstrumentsTypeFinder(GfeedWSClientEndPoint clientEndPoint,String exchange) {
			if(clientEndPoint.getSocket()==null) {
				try {
					throw new DataException();
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
			else if(clientEndPoint.getSocket()!=null) {
				ws=clientEndPoint.getSocket();
				this.instance=Constants.GET_INSTRUMENT_TYPES;
				this.exchange=exchange;
			}
			findInstrumentTypes();			
	}
	/**
	 * 
	 */
	private void findInstrumentTypes() {
		if(ws!=null) {
			ws.sendText(getInstrumentTypes());
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.GET_INSTRUMENT_TYPES)) {
			    		 if(message.contains("\"MessageType\":\"InstrumentTypesResult\"")) {
			    			 responseReceived=true;
			    			 System.out.println(message);
			                parseInstrumentTypes(message);
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
	private  String getInstrumentTypes() {
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
	private void parseInstrumentTypes(String message){
		Gson gson = new GsonBuilder().create();
		this.response=gson.fromJson(message, InstrumentTypesResponse.class);
		if(Constants.DEBUG_MODE) {
			System.out.println("===========List of  instrument types for "+this.exchange+"====================");
			if(this.response.instrumentTypes!=null)
				for(int i=0;i<this.response.instrumentTypes.size();i++) {
					System.out.println(this.response.instrumentTypes.get(i).instrumentType);
				}
		}
		isResultPrepared=true;
	}
	/**
	 *@return Returns the instrument types available for the specified exchange. 
	 */
	public InstrumentTypesResponse getAvailableInstrumentTypes() {
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
