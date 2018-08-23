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
import in.kncsolutions.dhelm.gfeed.models.ProductResponse;
import java.util.Map;

public class ProductFinder {
   private WebSocket ws;
	private String instance;
	private String exchange;
	private boolean responseReceived=false;
	private boolean isResultPrepared=false;
   private ProductResponse response;
   /**
    *@param clientEndPoint  : The clientEndPoint
    *@param exchange : The exchange.
    */
	public ProductFinder(GfeedWSClientEndPoint clientEndPoint,String exchange) {
			if(clientEndPoint.getSocket()==null) {
				try {
					throw new DataException();
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
			else if(clientEndPoint.getSocket()!=null) {
				ws=clientEndPoint.getSocket();
				this.instance=Constants.GET_PRODUCTS;
				this.exchange=exchange;
			}
			findProducts();			
	}
	/**
	 *@param clientEndPoint  : The clientEndPoint
	 *@param exchange : The exchange.
     *@param optionalParameters : The valid optional parameters
	 */
	public ProductFinder(GfeedWSClientEndPoint clientEndPoint,String exchange,
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
				this.instance=Constants.GET_PRODUCTS;
				this.exchange=exchange;
			}
			findProducts(optionalParameters);			
	}
   /**
	 * 
	 */
	private void findProducts() {
		if(ws!=null) {
			ws.sendText(getProducts());
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.GET_PRODUCTS)) {
			    		 if(message.contains("\"MessageType\":\"ProductsResult\"")) {
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
	private void findProducts(Map<String,String> optionalParameters) {
		if(ws!=null) {
			ws.sendText(getProducts(optionalParameters));
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.GET_PRODUCTS)) {
			    		 if(message.contains("\"MessageType\":\"ProductsResult\"")) {
			    			 responseReceived=true;
			    			 if(Constants.DEBUG_MODE)
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
	 *@return
	 */
	private  String getProducts() {
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
	private  String getProducts(Map<String,String> optionalParameters) {
		JSONObject jobj = new JSONObject();
		jobj.put("MessageType", this.instance);
		jobj.put("Exchange", this.exchange);
      if(optionalParameters.containsKey(Constants.INSTRUMENT_TYPE_KEY))
        jobj.put(Constants.INSTRUMENT_TYPE_KEY,optionalParameters.get(Constants.INSTRUMENT_TYPE_KEY));
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
      this.response=gson.fromJson(message, ProductResponse.class);
      if(Constants.DEBUG_MODE) {
			System.out.println("List of  Product types for.."+this.exchange);
			if(this.response.products!=null)
				for(int i=0;i<this.response.products.size();i++) {
					System.out.println(this.response.products.get(i).product);
				}
		}
		isResultPrepared=true;
	}


  /**
   *@return Returns the product types.
   */
	public ProductResponse getAvailableProducts() {
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
