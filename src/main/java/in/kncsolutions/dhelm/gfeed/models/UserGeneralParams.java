/*
 *UserGeneralParams.java
 *v1.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class UserGeneralParams {
   @SerializedName("AllowedBandwidthPerHour")
   public double allowedBandwidthPerHour;
   @SerializedName("AllowedCallsPerHour")
   public int allowedCallsPerHour;
   @SerializedName("AllowedCallsPerMonth")
   public int allowedCallsPerMonth;
   @SerializedName("AllowedBandwidthPerMonth")
   public double allowedBandwidthPerMonth;
   @SerializedName("ExpirationDate")
   public long unixTimeStamp;
   @SerializedName("Enabled")
   public boolean isEnabled;
   /**
    *@return Returns the expiration date. 
    */
   public String getExpirationDate() {
	   Date date = new Date ();
	   date.setTime((long)this.unixTimeStamp*1000);
	   return date.toString();
   }
   /**
    * 
    */
   @Override
   public String toString() {
	   return "AllowedBandwidthPerHour : "+this.allowedBandwidthPerHour+"\n"+
			   "AllowedCallsPerHour : "+this.allowedCallsPerHour+"\n"+
			   "AllowedCallsPerMonth : "+this.allowedCallsPerMonth+"\n"+
			   "AllowedBandwidthPerMonth : "+this.allowedBandwidthPerMonth+"\n"+
			   "ExpirationDate : "+this.getExpirationDate()+"\n"+
			   "IS Enabled? : "+this.isEnabled;		   
   }
}
