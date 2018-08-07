/*
 *UserHistoryLimitation.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;

import com.google.gson.annotations.SerializedName;

public class UserHistoryLimitation {
   @SerializedName("TickEnabled")
   public boolean isTickEnabled;
   @SerializedName("DayEnabled")
   public boolean isDayEnabled;
   @SerializedName("WeekEnabled")
   public boolean isWeekEnabled;
   @SerializedName("MonthEnabled")
   public boolean isMonthEnabled;
   @SerializedName("MaxEOD")
   public int maxEOD;
   @SerializedName("MaxIntraday")
   public int maxIntraday;
   @SerializedName("Hour_1Enabled")
   public boolean isHour_1Enabled;
   @SerializedName("Hour_2Enabled")
   public boolean isHour_2Enabled;
   @SerializedName("Hour_3Enabled")
   public boolean isHour_3Enabled;
   @SerializedName("Hour_4Enabled")
   public boolean isHour_4Enabled;
   @SerializedName("Hour_6Enabled")
   public boolean isHour_6Enabled;
   @SerializedName("Hour_8Enabled")
   public boolean isHour_8Enabled;
   @SerializedName("Hour_12Enabled")
   public boolean isHour_12Enabled;
   @SerializedName("Minute_1Enabled")
   public boolean isMinute_1Enabled;
   @SerializedName("Minute_2Enabled")
   public boolean isMinute_2Enabled;
   @SerializedName("Minute_3Enabled")
   public boolean isMinute_3Enabled;
   @SerializedName("Minute_4Enabled")
   public boolean isMinute_4Enabled;
   @SerializedName("Minute_5Enabled")
   public boolean isMinute_5Enabled;
   @SerializedName("Minute_6Enabled")
   public boolean isMinute_6Enabled;
   @SerializedName("Minute_10Enabled")
   public boolean isMinute_10Enabled;
   @SerializedName("Minute_12Enabled")
   public boolean isMinute_12Enabled;
   @SerializedName("Minute_15Enabled")
   public boolean isMinute_15Enabled;
   @SerializedName("Minute_20Enabled")
   public boolean isMinute_20Enabled;
   @SerializedName("Minute_30Enabled")
   public boolean isMinute_30Enabled;
   @SerializedName("MaxTicks")
   public int maxTicks;
   @Override
   public String toString() {
	   return "TickEnabled  : " +isTickEnabled+"\n"+
			   "DayEnabled  : " +isDayEnabled +"\n"+
			   "WeekEnabled  : " +isWeekEnabled+"\n"+
			   "MonthEnabled  : " +isMonthEnabled+"\n"+
			   "MaxEOd  : " +maxEOD+"\n"+
			   "MaxIntraday"+maxIntraday+"\n"+
			   "Hour_1Enabled  : " +isHour_1Enabled+"\n"+
			   "Hour_2Enabled  : " +isHour_2Enabled+"\n"+
			   "Hour_3Enabled  : " +isHour_3Enabled+"\n"+
			   "Hour_4Enabled  : " +isHour_4Enabled+"\n"+
			   "Hour_6Enabled  : " +isHour_6Enabled +"\n"+
			   "Hour_8Enabled  : " +isHour_8Enabled+"\n"+
			   "Hour_12Enabled  : " +isHour_12Enabled+"\n"+
			   "Minute_1Enabled  : " +isMinute_1Enabled+"\n"+
			   "Minute_2Enabled  : " +isMinute_2Enabled+"\n"+
			   "Minute_3Enabled  : " +isMinute_3Enabled+"\n"+
			   "Minute_4Enabled  : " +isMinute_4Enabled +"\n"+
			   "Minute_5Enabled  : " +isMinute_5Enabled +"\n"+
			   "Minute_6Enabled  : " +isMinute_6Enabled+"\n"+
			   "Minute_10Enabled  : " +isMinute_10Enabled+"\n"+
			   "Minute_12Enabled  : " +isMinute_12Enabled+"\n"+
			   "Minute_15Enabled  : " +isMinute_15Enabled+"\n"+
			   "Minute_20Enabled  : " +isMinute_20Enabled+"\n"+
			   "Minute_30Enabled  : " +isMinute_30Enabled+"\n"+
			   "MaxTicks : "+maxTicks;
   }
}
