/*
 *OptionTypeResponse.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OptionTypeResponse{
   @SerializedName("Request")
	public OptionTypeRequest request;
   @SerializedName("Result")
	public List<OptionType> optionType;
   @SerializedName("MessageType")
	public String message;
}