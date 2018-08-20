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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

import in.kncsolutions.dhelm.exceptions.DataException;
import in.kncsolutions.dhelm.gfeed.Constants;
import in.kncsolutions.dhelm.gfeed.GfeedWSClientEndPoint;
import in.kncsolutions.dhelm.gfeed.models.HistoricalDataResponse;
/**
 *
 */
public class HistoricalDataExtractor {
	private WebSocket ws;
	private String instance;
	private String exchange;
	private String instrumentIdentifier;
	private boolean responseReceived=false;
	private boolean isResultPrepared=false;
    private HistoricalDataResponse response;
    /**
	 *@param clientEndPoint  : The clientEndPoint	 
	 *@param exchange : The exchange.
	 *@param instrumentIdentifier : The instrument identifier for the instrument.
	 */
	public  HistoricalDataExtractor(GfeedWSClientEndPoint clientEndPoint,String exchange,
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
				this.instance=Constants.GET_HISTORY;
				this.exchange=exchange;
				this.instrumentIdentifier=instrumentIdentifier;
			}
			findHistoricalData();			
	}
	/**
	 *@param clientEndPoint : The clientEndPoint	 
	 *@param exchange : The exchange
	 *@param instrumentIdentifier : The instrument identifier for the instrument.
	 *@param optionalParameters : The valid optional parameters.
	 */
	public  HistoricalDataExtractor(GfeedWSClientEndPoint clientEndPoint,
                         String exchange,String instrumentIdentifier,
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
				this.instance=Constants.GET_HISTORY;
				this.exchange=exchange;
				this.instrumentIdentifier=instrumentIdentifier;
			}
			findHistoricalData(optionalParameters);			
	}
  /**
	* 
	*/
	private void findHistoricalData() {
		if(ws!=null) {
			ws.sendText(getMessage());
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.GET_HISTORY)) {
			    		 if(message.contains("\"MessageType\":\"HistoryTickResult\"") ||
			    				 message.contains("\"MessageType\":\"HistoryOHLCResult\"") ) {
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
	private void findHistoricalData(Map<String,String> optionalParameters) {
		if(ws!=null) {
			ws.sendText(getMessage(optionalParameters));
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.GET_HISTORY)) {
			    		  if(message.contains("\"MessageType\":\"HistoryTickResult\"") ||
				    				 message.contains("\"MessageType\":\"HistoryOHLCResult\"") ) {
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
	private  String getMessage(Map<String,String> optionalParameters) {
		JSONObject jobj = new JSONObject();
		jobj.put("MessageType", this.instance);
		jobj.put("Exchange", this.exchange);
		jobj.put("InstrumentIdentifier", this.instrumentIdentifier);
        if(optionalParameters.containsKey(Constants.PERIODICTY_KEY)) {
           jobj.put(Constants.PERIODICTY_KEY,optionalParameters.get(Constants.PERIODICTY_KEY));
        }
        if(optionalParameters.containsKey(Constants.PERIOD_KEY)) {
            jobj.put(Constants.PERIOD_KEY,Integer.parseInt(optionalParameters.get(Constants.PERIOD_KEY)));
        }
        if(optionalParameters.containsKey(Constants.FROM_KEY)) {
            jobj.put(Constants.FROM_KEY,
            		Long.parseLong(tsToSec8601(optionalParameters.get(Constants.FROM_KEY))));
        }
        if(optionalParameters.containsKey(Constants.TO_KEY)) {
            jobj.put(Constants.TO_KEY,
            		Long.parseLong(tsToSec8601(optionalParameters.get(Constants.TO_KEY))));
        }
        if(optionalParameters.containsKey(Constants.MAX_KEY))
            jobj.put(Constants.MAX_KEY,
            		Integer.parseInt(optionalParameters.get(Constants.MAX_KEY)));
      
		if(Constants.DEBUG_MODE) {
		  System.out.println("Request : "+jobj.toString());
		}
		return jobj.toString();
	}
	/**
	 * @param timestamp
	 * @return
	 */
	private String tsToSec8601(String timestamp){
		  if(timestamp == null) return null;
		  try {
			  SimpleDateFormat sdf;
		      if(timestamp.contains("T"))
			     sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		      else
		    	 sdf=new SimpleDateFormat("yyyy-MM-dd");
		    Date dt = sdf.parse(timestamp);
		    long epoch = dt.getTime();
		    return Integer.toString((int)(epoch/1000));
		  } catch(ParseException e) {
		     return null;
		  }
    }
  /**
	* 
	*/
	private void parseMessage(String message){
		Gson gson = new GsonBuilder().create();
      this.response=gson.fromJson(message, HistoricalDataResponse.class);
      if(Constants.DEBUG_MODE) {
			System.out.println("========================Histotical data======================");
			if(this.response.historicalData!=null)
				for(int i=0;i<this.response.historicalData.size();i++) {
					System.out.println(this.response.historicalData.get(i).toString());
				}
		}
		isResultPrepared=true;

	}
  /**
	*@return Returns the HistoricaldataResponse which contains historical data. 
	*/
  public HistoricalDataResponse getHistoricalData() {
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
