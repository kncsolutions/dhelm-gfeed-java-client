/*
 *ExchangeList.java
 *v1.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;
import java.util.List;

import com.google.gson.annotations.SerializedName;
public class ExchangeList {
	@SerializedName("Result")
	public List<Exchange> exchanges;
	@SerializedName("MessageType")
	public String message;
}
