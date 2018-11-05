/*
 *DhelmGfeedClientTest.java
 *v1.0.0
 *Copyright (C) 2018 KNC SOLUTIONS PVT. LTD.
 */
package in.kncsolutions.dhelm.gfeedtest;


import in.kncsolutions.dhelm.gfeed.Constants;
import in.kncsolutions.dhelm.gfeed.GfeedClient;
import in.kncsolutions.dhelm.gfeed.models.ExchangeList;
import in.kncsolutions.dhelm.gfeed.models.InstrumentResponse;
import in.kncsolutions.dhelm.gfeed.models.InstrumentTypesResponse;
import in.kncsolutions.dhelm.gfeed.datacontrollers.OnRealtimeDataArrival;
import in.kncsolutions.dhelm.gfeed.datacontrollers.OnSnapshotDataArrival;
import in.kncsolutions.dhelm.gfeed.models.SubscribeRealTimeResponse;
import in.kncsolutions.dhelm.gfeed.models.SubscribeSnapshotResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
/**
*This is a test class for Gfeedclient library.
*/
public class DhelmGfeedClientTest{

 /**
  *
  */
  public DhelmGfeedClientTest(String url,String apikey){
  //Constants.DEBUG_MODE=true;
  //Create an instance of the GfeedClient class.
  GfeedClient gc=new GfeedClient(url,
			apikey);
	try {
		Thread.sleep(5000);
	  //gc.getRealTimeData("NSE","SBIN");
     gc.streamRealTimeData("NSE","SBIN",new OnRealtimeDataArrival(){
         @Override
         public void onRealtimedataArrival(SubscribeRealTimeResponse response){
           System.out.println("-------REALTIME DATA-------");
           System.out.println(response.toString());
         }
     });
	  //gc.getRealTimeSnapshotData("NSE","SBIN",Constants.PERIODICITY_MINUTE);
    /* gc.streamRealTimeSnapshotData("NSE","SBIN",Constants.PERIODICITY_MINUTE,new OnSnapshotDataArrival(){
         @Override
         public void onSnapshotdataArrival(SubscribeSnapshotResponse response){
           System.out.println("-------SNAPSHOT DATA-------");
           System.out.println(response.toString());
         }
     });*/
      //Thread.sleep(10000);
      //gc.closeConnection();
     // gc.getRealTimeData(ir.request.exchange,ir.instruments.get(0).identifier);
      
	} catch (InterruptedException e) {
		e.printStackTrace();
     }

  }
  
  public static void main(String[] args){
    String url="Enter your url endpoint";
    String apikey ="Enter your apikey";
    if(args.length==2){
      url=args[0];
      apikey=args[1];
    }
    new DhelmGfeedClientTest(url,apikey);
  }
}