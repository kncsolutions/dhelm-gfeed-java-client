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

import com.google.gson.annotations.SerializedName;

public class Instrument {
	@SerializedName("Identifier")
	public String identifier;
	@SerializedName("Name")
	public String name;
	@SerializedName("Expiry")
	public String expiry;
	@SerializedName("StrikePrice")
	public double strikePrice;
	@SerializedName("Product")
	public String product;
	@SerializedName("PriceQuotationUnit")
	public String priceQuotationUnit;
	@SerializedName("OptionType")
	public String optionType;
	@SerializedName("ProductMonth")
	public String productMonth;
	@SerializedName("UnderlyingAsset")
	public String underlyingAsset;
	@SerializedName("UnderlyingAssetExpiry")
	public String underlyingAssetExpiry;
	@SerializedName("IndexName")
	public String indexName;
	@SerializedName("TradeSymbol")
	public String tradeSymbol;
	@Override
	public String toString() {
		return "Identifier : "+this.identifier+"\t"+
				"Name : "+this.name+"\t"+
				"Expiry : "+this.expiry+"\t"+
				"StrikePrice : "+this.strikePrice+"\t"+
				"Product : "+this.product+"\t"+
				"PriceQuotationUnit  : "+this.priceQuotationUnit+"\t"+
				"OptionType : "+this.optionType+"\t"+
				"ProductMonth : "+this.productMonth+"\t"+
				"UnderlyingAsset : "+this.underlyingAsset+"\t"+
				"UnderlyingAssetExpiry : "+this.underlyingAssetExpiry+"\t"+
				"IndexName : "+this.indexName+"\t"+
				"Trading Symbol : "+this.tradeSymbol;
	}
}
