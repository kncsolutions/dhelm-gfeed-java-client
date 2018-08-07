/*
 *ServerInfo.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeed.models;

import com.google.gson.annotations.SerializedName;

public class ServerInfo {
	@SerializedName("ServerID")
	public String serverID;
	@SerializedName("MessageType")
	public String message;
}
