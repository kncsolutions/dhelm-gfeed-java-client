/*
 *InstrumentRequest.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
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
