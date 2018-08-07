/*
 *InstrumentRequest.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class InstrumentResponse {
	@SerializedName("Request")
	public InstrumentRequest request;
	@SerializedName("Result")
	public List<Instrument> instruments;
	@SerializedName("MessageType")
	public String responseMessage;
}
