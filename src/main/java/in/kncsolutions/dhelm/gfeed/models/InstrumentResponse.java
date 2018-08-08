/*
 *InstrumentRequest.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class InstrumentResponse {
   /**
    *The request which was sent to the server.
    */
	@SerializedName("Request")
	public InstrumentRequest request;
   /**
    *The reponse containing the list of instruments as per specified request.
    */
	@SerializedName("Result")
	public List<Instrument> instruments;
   /**
    *The specific message for this response.
    */
	@SerializedName("MessageType")
	public String responseMessage;
}
