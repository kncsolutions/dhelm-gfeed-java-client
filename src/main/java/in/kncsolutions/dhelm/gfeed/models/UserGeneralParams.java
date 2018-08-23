/**
*Copyright 2018 Knc Solutions Private Limited
*
*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.
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
