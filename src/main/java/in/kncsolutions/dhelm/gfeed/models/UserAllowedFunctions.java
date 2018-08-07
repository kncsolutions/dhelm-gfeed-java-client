/*
 *UserAllowedFunctions.java
 *v1.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;

import com.google.gson.annotations.SerializedName;

public class UserAllowedFunctions {
   @SerializedName("FunctionName")
   public String functionName;
   @SerializedName("IsEnabled")
   public boolean isEnabled;
   @Override
   public String toString() {
	  return  functionName+"\t"+
			  Boolean.toString(isEnabled);
   }
}
