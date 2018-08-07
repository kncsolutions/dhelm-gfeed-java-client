/*
 *ProductResponse.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ProductResponse{
   @SerializedName("Request")
	public ProductRequest request;
   @SerializedName("Result")
	public List<Products> products;
   @SerializedName("MessageType")
	public String message;
}