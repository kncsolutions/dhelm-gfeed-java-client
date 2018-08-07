/*
 *GfeedWSClient.java
 *v1.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed;

import java.io.IOException;

import org.json.JSONObject;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

/**
*
*/
public class GfeedWSClientEndPoint{
    boolean APIConnection = false;
    private WebSocket ws;
    public String endPoint;
    public String accessKey;
    private int no_of_attempts=0;
    public boolean is_disconneted_by_user=false;
    public boolean should_retry_connection=true;
    /**
     * @param endPoint End point for the web socket connection.
     * @param accessKey The access key.
     */
    public GfeedWSClientEndPoint(String endPoint,String accessKey ) {
    	this.endPoint=endPoint;
        this.accessKey=accessKey;
    	doConnect();
    	
    }
    /**
     * 
     */
    private void doConnect() {
    	try {
            ws = new WebSocketFactory().createSocket(endPoint);
            ws.setAutoFlush(true);
            ws.connect();
            autheticate();
    		if(Constants.DEBUG_MODE) {
              System.out.println("Websocket Connected");
    		}
        } catch (IOException e) {
        	if(Constants.DEBUG_MODE) {
        	  e.printStackTrace();
        	}
        } catch (WebSocketException e) {
			if(this.no_of_attempts<Constants.MAX_NO_OF_ATTEMPTS && should_retry_connection) {
				this.no_of_attempts++;
				reconnect();
			}
		}
    	ws.addListener(new WebSocketAdapter() {
   	     @Override
   	     public void onTextMessage(WebSocket websocket, String message) throws Exception {   	         
   	      if (message != null){
              if(message.contains("\"Complete\":true") 
            		  || message.contains("\"AllowVMRunning\":false") 
            		  ||  message.contains("\"AllowServerOSRunning\":false")){
                  APIConnection = true;
                  if(Constants.DEBUG_MODE) {
                    System.out.println(message);
                    System.out.println("Authentication Complete!");  
                  }
              }
          }
   	     }
   	    @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame,
    		  WebSocketFrame clientCloseFrame, boolean closedByServer) {
   		    if(!is_disconneted_by_user && should_retry_connection) {
   		    	if(Constants.DEBUG_MODE) {
   		    		System.out.println("!!!!!!!!!!!!!!!Disconnected!!!!!!!!!!!!!!!!");
   		    	}   		    	  		    	
   		    	reconnect();
   		    }
   		    else if(is_disconneted_by_user) {
	    		disconnected();
	    		return;
	    	}
            
         }
   	 });
    }
    /**
     * 
     */
    private void reconnect() {
    	try {
    		if(Constants.DEBUG_MODE) {
    			System.out.println("<<<<<<<<<<<<<<TRYING TO RECONNECT>>>>>>>>>>>>>>>>");
    			System.out.println("............PLEASE WAIT...............");
    		}
			Thread.sleep(20000);
			APIConnection=false;
			ws=null;
			Thread.sleep(3000);
			doConnect();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    /**
     * 
     */
    public void autheticate() {
    	ws.sendText(getAuthenticate(Constants.AUTHENTICATE,accessKey));
    }
    /**
     * @param msgType
     * @param message
     * @return
     */
    private  String getAuthenticate(String msgType, String message) {
    	JSONObject jobj = new JSONObject();    	
    	jobj.put("MessageType", msgType);
    	jobj.put("Password", message);
    	return jobj.toString();
    }
    /**
     * @return Returns true if connected.
     */
    public boolean isConnected(){
        if(ws != null) {
            if (ws.isOpen()){
                return true;
            }
        }
        return false;
}
    /**
     *@return Returns the web socket instance.
     */
    public WebSocket getSocket() {
    	if(APIConnection==true)
    	    return this.ws;
    	else
    		return null;
    }
    /**
     * 
     */
    private void disconnected() {
    	this.APIConnection=false;
    }
  
}