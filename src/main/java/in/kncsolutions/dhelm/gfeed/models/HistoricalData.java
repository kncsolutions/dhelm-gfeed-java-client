/*
 *HistoricalData.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

import in.kncsolutions.dhelm.gfeed.Constants;

public class HistoricalData {
	@SerializedName("LastTradeTime")
	public long lastTradeTime;
	@SerializedName("LastTradePrice")
	public double ltp;
	@SerializedName("QuotationLot")
	public int quotationLot;
	@SerializedName("TradedQty")
	public long volume;
	@SerializedName("OpenInterest")
	public double openInterest;
	@SerializedName("BuyPrice")
	public double buyPrice;
	@SerializedName("BuyQty")
	public long buyQty;
	@SerializedName("SellPrice")
	public double sellPrice;
	@SerializedName("SellQty")
	public long sellQty;
	@SerializedName("Open")
	public double open;
	@SerializedName("High")
	public double high;
	@SerializedName("Low")
	public double low;
	@SerializedName("Close")
	public double close;
	/**
	 *@return Returns last traded date-time. 
	 */
	public String getLastTradedTime() {
		Date date = new Date(this.lastTradeTime*1000L); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 
		//sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5:30")); 
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	@Override
	public String toString() {
		String s="";
		if(this.getLastTradedTime()!=null)s=s+this.getLastTradedTime()+"-";
		s=s+"LastTradePrice : "+this.ltp;
		s=s+"QuotationLot : "+this.quotationLot+"-";
		s=s+"TradedQty : "+this.volume+"-";
		s=s+"OpenInterest : "+this.openInterest+"-";
		s=s+"BuyPrice : "+this.buyPrice+"-";				   
		s=s+"BuyQty : "+this.buyQty+"-"+				   
				   "SellPrice : "+this.sellPrice+"-"+				   
				   "SellQty : "+this.sellQty+
		
		
			   "Open : "+this.open+"-"+				   
			   "High : "+this.high+"-"+				   
			   "Low : "+this.low+"-"+				   
			   "Close : "+this.close;
	  return s;
	}

}
