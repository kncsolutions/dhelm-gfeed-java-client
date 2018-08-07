/*
 *LastQuoteList.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class LastQuoteList {
	@SerializedName("Result")
	public List<LastQuote> lastQuotes;
	@SerializedName("MessageType")
	public String message;
}
