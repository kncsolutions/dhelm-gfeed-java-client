/*
 *HistoricalDataRequest.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;

import com.google.gson.annotations.SerializedName;

public class HistoricalDataRequest {
	@SerializedName("Exchange")
	public String exchange;
	@SerializedName("InstrumentIdentifier")
	public String instrumentIdentifier;
	@SerializedName("From")
	public long from;
	@SerializedName("To")
	public long to;
	@SerializedName("Periodicity")
	public String periodicity;
	@SerializedName("Period")
	public int period;
	@SerializedName("Max")
	public int max;
	@SerializedName("MessageType")
	public String message;
}
