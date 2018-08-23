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
import in.kncsolutions.dhelm.gfeed.models. UserProfile;

public class UserProfileFinder {
   private WebSocket ws;
   private String instance;
   private boolean responseReceived=false;
   private boolean isResultPrepared=false;
   private  UserProfile response;
  /**
   *@param clientEndPoint The clientendPoint
   */
	public UserProfileFinder(GfeedWSClientEndPoint clientEndPoint) {
			if(clientEndPoint.getSocket()==null) {
				try {
					throw new DataException();
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
			else if(clientEndPoint.getSocket()!=null) {
				ws=clientEndPoint.getSocket();
				this.instance=Constants.GET_LIMITATION;
			}
			findUserProfile();			
	}
  /**
	* 
	*/
	private void findUserProfile() {
		if(ws!=null) {
			ws.sendText(getMessage());
			ws.addListener(new WebSocketAdapter() {
			     @Override
			     public void onTextMessage(WebSocket websocket, String message) throws Exception {
			    	 if(!responseReceived) {
			    	  if(instance.equals(Constants.GET_LIMITATION)) {
			    		 if(message.contains("\"MessageType\":\"LimitationResult\"")) {
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
      this.response=gson.fromJson(message, UserProfile.class);
      if(Constants.DEBUG_MODE) {
        displayProfile();
		}
		isResultPrepared=true;

	}
   /**
    *
    */
    private void displayProfile(){
       System.out.println("=====================PROFILE=========================");
       if(this.response.userGeneralParams!=null){
         System.out.println(
              this.response.userGeneralParams.toString()   
              );
       }
       if(this.response.userAllowedExchanges!=null){
    	   System.out.println("+++++++++++++++AllowedExchanges++++++++++++++++++");
    	   System.out.println("ExchangeName"+"\t"+"AllowedInstruments\t"+"DataDelay");
    	   for(int i=0;i<this.response.userAllowedExchanges.size();i++) {
    		   System.out.println(this.response.userAllowedExchanges.get(i).toString());
    	   }
       }
       if(this.response.userAllowedFunctions!=null){
    	   System.out.println("+++++++++++++++AllowedFunstions++++++++++++++++++");
    	   System.out.println("FunctionName"+"\t"+"IsEnabled");
    	   for(int i=0;i<this.response.userAllowedFunctions.size();i++) {
    		   System.out.println(this.response.userAllowedFunctions.get(i).toString());
    	   }
       }
       if(this.response.userHistoryLimitation!=null){
    	   System.out.println("+++++++++++++++HistoryLimitation++++++++++++++++++");
    	   System.out.println(this.response.userHistoryLimitation.toString());
       }
          
    }
  /**
   *@return Returns the user profile and limitations of the account. 
   */
  public UserProfile getUserProfile() {
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