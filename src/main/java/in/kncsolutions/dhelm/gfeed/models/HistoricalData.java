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
	public String getDateTime() {
		Date date = new Date(this.lastTradeTime*1000L); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 
		//sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5:30")); 
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	@Override
	public String toString() {
		String s="";
		if(this. getDateTime()!=null)s=s+this.getDateTime()+"-";
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
