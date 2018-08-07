/*
 *UserProfile.java
 *v1.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UserProfile {
   @SerializedName("GeneralParams")
   public UserGeneralParams userGeneralParams;
   @SerializedName("AllowedExchanges")
   public List<UserAllowedExchanges> userAllowedExchanges ;
   @SerializedName("AllowedFunctions")
   public List<UserAllowedFunctions> userAllowedFunctions;
   @SerializedName("HistoryLimitation")
   public UserHistoryLimitation userHistoryLimitation;
}
