/*
 *MarketMessage.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class MarketMessage {
	@SerializedName("ServerTime")
	public long serverUnixTimeStamp;
    @SerializedName("MarketType")
	public String marketType;
	@SerializedName("MessageType")
	public boolean messageType;
	/**
	 *@return Returns server date-time. 
	 */
	public String getServerTime() {
		Date date = new Date(this.serverUnixTimeStamp*1000L); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); 
		sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5:30")); 
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	@Override
	public String toString() {
		return "ServerTime : "+getServerTime()+"\n"+
				"MarketType : "+this.marketType+"\n"+
				"MessageType : "+this.messageType;
	}
}
