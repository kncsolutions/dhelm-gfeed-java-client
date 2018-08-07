/*
 *StrikePriceResponse.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class StrikePriceResponse{
   @SerializedName("Request")
	public StrikePriceRequest request;
   @SerializedName("Result")
	public List<StrikePrice> prices;
   @SerializedName("MessageType")
	public String message;
}