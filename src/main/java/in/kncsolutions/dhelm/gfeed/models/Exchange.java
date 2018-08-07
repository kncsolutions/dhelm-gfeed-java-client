/*
 *Exchange.java
 *v1.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;

import com.google.gson.annotations.SerializedName;

public class Exchange {
	@SerializedName("Value")
	public String exchange;
	@Override
	public String toString() {
		return "Exchange : "+this.exchange;
	}
}
