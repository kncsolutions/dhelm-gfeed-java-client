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
  Constants.DEBUG_MODE=true;
  //Create an instance of the GfeedClient class.
  GfeedClient gc=new GfeedClient(url,
			apikey);
	try {
		Thread.sleep(5000);
      //Get list of subscribed exchanges.
		ExchangeList l=gc.getSubscribedExchanges();
		Thread.sleep(3000);
      for(int i=0;i<l.exchanges.size();i++) {
        System.out.println(l.exchanges.get(i).exchange);
      }
		for(int i=0;i<l.exchanges.size();i++) {
		   Thread.sleep(5000);
		   InstrumentTypesResponse response=gc.getInstumentTypes(l.exchanges.get(i).exchange);
		   if(response.instrumentTypes!=null)
				for(int j=0;j<response.instrumentTypes.size();j++) {
					System.out.println(response.instrumentTypes.get(j).instrumentType);
				}
		}
      Thread.sleep(3000);
      gc.getUserProfile();
      Thread.sleep(3000);
      gc.getServerInfo();
      Thread.sleep(3000);
      gc.getMarketMessage("NSE");
      Thread.sleep(3000);
      gc.getExchangeMessage("MCX");
      Thread.sleep(3000);
      gc.getSearchedInstruments("NSE", "SBIN");
      Thread.sleep(3000);
      Map<String,String> ophist=new HashMap<String,String>();
      ophist.put(Constants.PERIODICITY_KEY, Constants.PERIODICITY_DAY);
      ophist.put(Constants.FROM_KEY, "2017-06-04");
      ophist.put(Constants.TO_KEY, "2018-08-04");
      gc.getHistoricalData("NSE", "SBIN", ophist);
      Thread.sleep(3000);
      gc.getHistoricalData("NSE", "SBIN.N2", ophist);
      Thread.sleep(3000);
      gc.getHistoricalData("NSE", "SBIN.N5", ophist);
      Thread.sleep(3000);
      gc.getHistoricalData("NSE", "SBIN.N6", ophist);
      Thread.sleep(3000);
      gc.getHistoricalData("NSE", "SBIN.TT", ophist);
      Thread.sleep(3000);
      gc.getHistoricalData("NSE", "SBIN.U1", ophist);
      Thread.sleep(3000);
      gc.getLastQuote("NSE", "SBIN");
      Thread.sleep(3000);
      String [] scrp=new String[] {"SBIN","BEPL","INFY"};
      gc.getLastQuoteList("NSE", scrp);
      Thread.sleep(3000);
      gc.getQutoteSnapshot("NSE", scrp);
      Thread.sleep(3000);
      Map<String,String> opq=new HashMap<String,String>();
      opq.put(Constants.PERIODICITY_KEY, Constants.PERIODICITY_HOUR);
      opq.put(Constants.PERIOD_KEY, "1");
      gc.getQutoteSnapshot("NSE", scrp,opq);
      InstrumentResponse ir=gc.getAllInstruments(l.exchanges.get(3).exchange);
		if(ir.instruments!=null){
			for(int i=0;i<ir.instruments.size();i++) {
				System.out.println(
					(i+1)+ "::Name : "+	ir.instruments.get(i).name+
					"  Symbol : "+	ir.instruments.get(i).tradeSymbol+
               " Identifier : "+	ir.instruments.get(i).identifier+
					"  Exchange : "+	ir.request.exchange							
						);
			}
         int count=1;
         for(int i=0;i<ir.instruments.size();i++) {
          if(ir.instruments.get(i).identifier.contains(".EQ")||ir.instruments.get(i).identifier.contains(".EQ")){
				System.out.println(
					(count+1)+ "::Name : "+	ir.instruments.get(i).name+
					"  Symbol : "+	ir.instruments.get(i).tradeSymbol+
               " Identifier : "+	ir.instruments.get(i).identifier+
					"  Exchange : "+	ir.request.exchange							
						);
             count++;
           }
			}

       }
		Thread.sleep(10000);
		/*int s=0;
		for(int i=0;i<Constants.ALL_INSTRUMENTS.size();i++) {
		 s=s+Constants.ALL_INSTRUMENTS.get(i).instruments.size();
		}
		System.out.println("Total data size : "+s);*/
      Thread.sleep(5000);
      Map<String,String> op=new HashMap<String,String>();
      op.put(Constants.INSTRUMENT_TYPE_KEY,"FUTIDX");
      for(int i=0;i<l.exchanges.size();i++) {
        gc.getProducts(l.exchanges.get(i).exchange);
         Thread.sleep(5000);
         gc.getProducts(l.exchanges.get(i).exchange,op);
          Thread.sleep(5000);
      }
      op.put(Constants.PRODUCT_KEY,"BANKNIFTY");
      for(int i=0;i<l.exchanges.size();i++) {
        gc.getExpiryDates(l.exchanges.get(i).exchange);
         Thread.sleep(5000);
         gc.getExpiryDates(l.exchanges.get(i).exchange,op);
          Thread.sleep(5000);
      }
      op.put(Constants.EXPIRY_DATE_KEY,"30AUG2018");
      for(int i=0;i<l.exchanges.size();i++) {
        gc.getOptionTypes(l.exchanges.get(i).exchange);
         Thread.sleep(5000);
         gc.getOptionTypes(l.exchanges.get(i).exchange,op);
          Thread.sleep(5000);
      }
      op.put(Constants.OPTION_TYPE_KEY,"CE");
      for(int i=0;i<l.exchanges.size();i++) {
        gc.getStrikePrices(l.exchanges.get(i).exchange);
         Thread.sleep(5000);
         gc.getStrikePrices(l.exchanges.get(i).exchange,op);
          Thread.sleep(5000);
      }
      //InstrumentResponse ir=gc.getSubscribedInstruments(l.exchanges.get(3).exchange);
      Thread.sleep(10000);
      gc.closeConnection();
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