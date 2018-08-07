/*
 *StrikePriceRequest.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;
import com.google.gson.annotations.SerializedName;

public class StrikePriceRequest{
   @SerializedName("Exchange")
	public String exchange;
	@SerializedName("MessageType")
	public String requestMessage;
   @SerializedName("InstrumentType")
	public String instrumentType;
   @SerializedName("Product")
	public String product;
   @SerializedName("Expiry")
	public String expiry;
   @SerializedName("OptionType")
	public String optionType;
}