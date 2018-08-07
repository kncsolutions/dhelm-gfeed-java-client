/*
 *LastQuote.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class LastQuote {
    @SerializedName("Exchange")
    public String exchange;
    @SerializedName("InstrumentIdentifier")  
    public String instrumentIdentifier;
    @SerializedName("LastTradeTime")
    public long lastTradeTime;
    @SerializedName("ServerTime")
    public long serverTime;
    @SerializedName("AverageTradedPrice")
    public double averageTradedPrice;
    @SerializedName("BuyPrice")
    public double buyprice;
    @SerializedName("BuyQty")
    public int buyQty;
    @SerializedName("Close")
    public double close;
    @SerializedName("High")
    public double high;
    @SerializedName("Low")
    public double low;
    @SerializedName("LastTradePrice")
    public double ltp;
    @SerializedName("LastTradeQty")
    public int lastTradedQty;
    @SerializedName("Open")
    public double open;
    @SerializedName("OpenInterest")
    public double openInterest;
    @SerializedName("SellPrice")
    public double sellPrice;
    @SerializedName("SellQty")
    public int sellQty;
    @SerializedName("TotalQtyTraded")
    public long totalQtyTraded;
    @SerializedName("Value")
    public double value;
    @SerializedName("PreOpen")
    public boolean preOpen;
    @SerializedName("MessageType")
    public String message;
    /**
	 *@return Returns last traded date-time. 
	 */
	public String getLastTradedTime() {
		Date date = new Date(this.lastTradeTime*1000L); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 
		sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5:30")); 
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	/**
	 *@return Returns server date-time. 
	 */
	public String getserverTime() {
		Date date = new Date(this.serverTime*1000L); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 
		sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5:30")); 
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	@Override
	public String toString() {
		return "Exchange : "+this.exchange+"\n"+
				"InstrumentIdentifier : "+this.instrumentIdentifier+"\n"+
				"LastTradeTime : "+this.getLastTradedTime()+"\n"+
				"ServerTime : "+this.getserverTime()+"\n"+
				"AverageTradedPrice : "+this.averageTradedPrice+"\n"+
				"BuyPrice : "+this.buyprice+"\n"+
				"BuyQty : "+this.buyQty+"\n"+
				"Close : "+this.close+"\n"+
				"High : "+this.high+"\n"+
				"Low : "+this.low+"\n"+
				"LastTradePrice : "+this.ltp+"\n"+
				"LastTradeQty : "+this.lastTradedQty+"\n"+
				"Open : "+this.open+"\n"+
				"OpenInterest : "+this.openInterest+"\n"+
				"SellPrice : "+this.sellPrice+"\n"+
				"SellQty : "+this.sellQty+"\n"+
				"TotalQtyTraded : "+this.totalQtyTraded+"\n"+
				"Value : "+this.value+"\n"+
				"PreOpen : "+this.preOpen+"\n"+
				"MessageType : "+this.message;
	}
}
