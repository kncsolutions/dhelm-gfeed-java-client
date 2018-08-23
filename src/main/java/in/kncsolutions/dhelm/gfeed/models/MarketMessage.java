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
