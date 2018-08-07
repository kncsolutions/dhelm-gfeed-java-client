/*
 *UserAllowedExchanges.java
 *v1.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;

import com.google.gson.annotations.SerializedName;

public class UserAllowedExchanges {
   @SerializedName("AllowedInstruments")
   public int allowedInstruments;
   @SerializedName("DataDelay")
   public long delay;
   @SerializedName("ExchangeName")
   public String exchange;
   @Override
   public String toString() {
	  return  exchange+"\t"+
              Integer.toString(allowedInstruments)+"\t"+
			  Long.toString(delay);
   }
}
