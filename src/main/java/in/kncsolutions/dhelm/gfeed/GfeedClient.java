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
package in.kncsolutions.dhelm.gfeed;
import java.util.Map;

import in.kncsolutions.dhelm.gfeed.datacontrollers.ExchangeMessageFinder;
import in.kncsolutions.dhelm.gfeed.datacontrollers.ExpiryDateFinder;
import in.kncsolutions.dhelm.gfeed.datacontrollers.HistoricalDataExtractor;
import in.kncsolutions.dhelm.gfeed.datacontrollers.InstrumentsTypeFinder;
import in.kncsolutions.dhelm.gfeed.datacontrollers.LastQuoteFinder;
import in.kncsolutions.dhelm.gfeed.datacontrollers.MarketMessageFinder;
import in.kncsolutions.dhelm.gfeed.datacontrollers.OptionTypeFinder;
import in.kncsolutions.dhelm.gfeed.datacontrollers.ProductFinder;
import in.kncsolutions.dhelm.gfeed.datacontrollers.QuoteSnapshotFinder;
import in.kncsolutions.dhelm.gfeed.datacontrollers.SearchInstruments;
import in.kncsolutions.dhelm.gfeed.datacontrollers.ServerInfoFinder;
import in.kncsolutions.dhelm.gfeed.datacontrollers.StrikePriceFinder;
import in.kncsolutions.dhelm.gfeed.datacontrollers.SubscribeRealTime;
import in.kncsolutions.dhelm.gfeed.datacontrollers.SubscribeSnapshot;
import in.kncsolutions.dhelm.gfeed.datacontrollers.SubscribedExchanges;
import in.kncsolutions.dhelm.gfeed.datacontrollers.AllInstruments;
import in.kncsolutions.dhelm.gfeed.datacontrollers.UserProfileFinder;
import in.kncsolutions.dhelm.gfeed.datacontrollers.OnRealtimeDataArrival;
import in.kncsolutions.dhelm.gfeed.datacontrollers.OnSnapshotDataArrival;
import in.kncsolutions.dhelm.gfeed.models.ExchangeList;
import in.kncsolutions.dhelm.gfeed.models.ExchangeMessageResponse;
import in.kncsolutions.dhelm.gfeed.models.ExpiryDateResponse;
import in.kncsolutions.dhelm.gfeed.models.HistoricalDataResponse;
import in.kncsolutions.dhelm.gfeed.models.InstrumentResponse;
import in.kncsolutions.dhelm.gfeed.models.InstrumentTypesResponse;
import in.kncsolutions.dhelm.gfeed.models.LastQuote;
import in.kncsolutions.dhelm.gfeed.models.LastQuoteList;
import in.kncsolutions.dhelm.gfeed.models.MarketMessageResponse;
import in.kncsolutions.dhelm.gfeed.models.OptionTypeResponse;
import in.kncsolutions.dhelm.gfeed.models.ProductResponse;
import in.kncsolutions.dhelm.gfeed.models.QuoteSnapshotList;
import in.kncsolutions.dhelm.gfeed.models.ServerInfo;
import in.kncsolutions.dhelm.gfeed.models.StrikePriceResponse;
import in.kncsolutions.dhelm.gfeed.models.SubscribeRealTimeResponse;
import in.kncsolutions.dhelm.gfeed.models.SubscribeSnapshotResponse;
import in.kncsolutions.dhelm.gfeed.models.UserProfile;
/**
*It is a java client library to access and integrate stock market data from Global Financial Datafeeds LLP with your application.
*To use this library you must subscribe to websocket api with Global Financial Datafeeds LLP and get your API key and websocket 
*endpoint url.
*/
public class GfeedClient{
public String endPoint;
public String accessKey;
private GfeedWSClientEndPoint clientEndPoint;
/**
*@param endPoint : the web socket api end  point
*@param accessKey: your access key.
*/
public  GfeedClient(String endPoint,String accessKey ){
  this.endPoint=endPoint;
  this.accessKey=accessKey;
  if(Constants.DEBUG_MODE) {
    System.out.println("End Point : "+this.endPoint);
    System.out.println("Access Key : "+this.accessKey);
  }
  makeConnection();
}
/**
*@param endPoint : the web socket api end point
*/
public void setEndPoint(String endPoint){
  this.endPoint=endPoint;
}
/**
*@return Returns the end point.
*/
public String getEndPoint(){
  return this.endPoint;
}

/**
*@param accessKey : your access key.
*/
public void setAccessKey(String accessKey){
  this.accessKey=accessKey;
}
/**
*@return Returns the accessKey.
*/
public String getAccessKey(){
  return this.accessKey;
}
/**
*
*/
private void makeConnection(){
	clientEndPoint=new GfeedWSClientEndPoint(this.endPoint,this.accessKey);
}
/**
 *@return Returns the subscribed exchange list. For response structure see the 'ExchangeList' model class.
 */
public ExchangeList getSubscribedExchanges() {
	SubscribedExchanges subscribedExchanges
	          =new SubscribedExchanges(this.clientEndPoint);
	return subscribedExchanges.getExchangeList();
}
/**
 *@param exchange : The exchange for which the list of instruments have to be found. Valid values
 *are from the list of exchanges you get by calling the  'getSubscribedExchanges()' function.
 *@return Returns the list of all the instruments listed in the particular exchange. For the structure of
 *the response see the 'InstrumentResponse' model class.
 */
public InstrumentResponse getAllInstruments(String exchange) {
	AllInstruments subscribedInstruments
	          =new AllInstruments(this.clientEndPoint,exchange);
	return subscribedInstruments.getAvailableInstruments();
}
/**
 *@param exchange : The exchange for which the list of instruments have to be found. Valid values
 *are from the list of exchanges you get by calling the  'getSubscribedExchanges()' function.
 *@param optionalParameters : Map of optional parameters.
 *@return Return the list of all the instruments listed in the particular exchange. For the structure of
 *the response see the 'InstrumentResponse' model class.
 */
public InstrumentResponse getAllInstruments(String exchange,Map<String,String> optionalParameters) {
	AllInstruments subscribedInstruments
	          =new AllInstruments(this.clientEndPoint,exchange,optionalParameters);
	return subscribedInstruments.getAvailableInstruments();
}
/**
 *@param exchange : The exchange for which the list of instruments have to be found. Valid values
 *are from the list of exchanges you get by calling the  'getSubscribedExchanges()' function.
 *@param searchKey : The search key word.
 *@return Return the list of all the instruments listed in the particular exchange. For the structure of
 *the response see the 'InstrumentResponse' model class.
 */
public InstrumentResponse getSearchedInstruments(String exchange,String searchKey) {
	SearchInstruments searchInstruments
	          =new SearchInstruments(this.clientEndPoint,exchange,searchKey);
	return searchInstruments.getAvailableInstruments();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@param instrumentIdentifier : The instrument identifier for the particular instrument for which the last quote
 *have to be found.
 *@return Return last quote for the instrument. For the structure of
 *the response see the 'LastQuote' model class.
 */
public LastQuote getLastQuote(String exchange,String instrumentIdentifier) {
	 LastQuoteFinder lastQuote
              =new  LastQuoteFinder(this.clientEndPoint,
  		    exchange,instrumentIdentifier);
    return lastQuote.getResponse();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@param instrumentIdentifiers : The array of instrument identifiers for which the last quote
 *have to be found. The array can contain instrument identifiers for  maximum 25 instruments.
 *@return Returns the list of last quote for the instruments. For the structure of
 *the response see the 'LastQuoteList' model class.
 */
public LastQuoteList getLastQuoteList(String exchange,String [] instrumentIdentifiers) {
	 LastQuoteFinder lastQuoteList
              =new  LastQuoteFinder(this.clientEndPoint,
  		    exchange,instrumentIdentifiers);
    return lastQuoteList.getResponseList();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@param instrumentIdentifiers : The array of instrument identifiers for which the  quote snapshots
 *have to be found. The array can contain instrument identifiers for  maximum 25 instruments.
 *@return Return  quoteSnapshot list for the instruments. For the structure of
 *the response see the 'QuoteSnapshotList' model class.
 */
public QuoteSnapshotList getQutoteSnapshot(String exchange,String [] instrumentIdentifiers) {
	QuoteSnapshotFinder qs
	          =new QuoteSnapshotFinder(this.clientEndPoint,
	        		    exchange,instrumentIdentifiers);
	return qs.getResponse();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@param instrumentIdentifiers : The array of instrument identifiers for which the  quote snapshots
 *have to be found. The array can contain instrument identifiers for  maximum 25 instruments.
 *@param optionalParams : Map of optional parameters.
 *@return Return  quoteSnapshot list for the instruments. For the structure of
 *the response see the 'QuoteSnapshotList' model class.
 */
public QuoteSnapshotList getQutoteSnapshot(String exchange,String [] instrumentIdentifiers,
		 Map<String,String> optionalParams) {
	QuoteSnapshotFinder qs
              =new QuoteSnapshotFinder(this.clientEndPoint,
  		    exchange,instrumentIdentifiers,optionalParams);
    return qs.getResponse();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@param instrumentIdentifier : The instrument identifier for the particular instrument for which the last historical data
 *have to be found.
 *@param params : The parameters to extract historical data.
 *@return Return last historical data for the instrument. For the structure of
 *the response see the 'HistoricalDataResponse' model class.
 */
public HistoricalDataResponse getHistoricalData(String exchange,String instrumentIdentifier,
		Map<String,String> params) {
	 HistoricalDataExtractor hist
              =new  HistoricalDataExtractor(this.clientEndPoint,
  		    exchange,instrumentIdentifier,params);
    return hist.getHistoricalData();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@return Returns the types of instruments available for the exchange. For the structure of
 *the response see the 'InstrumentTypesResponse' model class.
 */
public InstrumentTypesResponse getInstumentTypes(String exchange) {
	InstrumentsTypeFinder instrumentsTypeFinder
	          =new InstrumentsTypeFinder(this.clientEndPoint,exchange);
	return instrumentsTypeFinder.getAvailableInstrumentTypes();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@return Returns the types of products available for the exchange. For the structure of
 *the response see the 'ProductResponse' model class.
 */
public ProductResponse getProducts(String exchange) {
	ProductFinder productFinder
	          =new ProductFinder(this.clientEndPoint,exchange);
	return productFinder.getAvailableProducts();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@param optionalParams : map containing the optional parameters
 *@return Returns the types of products available for the exchange. For the structure of
 *the response see the 'ProductResponse' model class.
 */
public ProductResponse getProducts(String exchange,Map<String,String> optionalParams) {
	ProductFinder productFinder
	          =new ProductFinder(this.clientEndPoint,exchange,optionalParams);
	return productFinder.getAvailableProducts();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@return Returns the expiry dates of the instruments available for the exchange. For the structure of
 *the response see the 'ExpiryDateResponse' model class.
 */
public ExpiryDateResponse getExpiryDates(String exchange) {
	ExpiryDateFinder expFinder
	          =new ExpiryDateFinder(this.clientEndPoint,exchange);
	return expFinder.getExpiryDates();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@param optionalParams : map containing the optional parameters
 *@return Returns the expiry dates of the instruments available for the exchange. For the structure of
 *the response see the 'ExpiryDateResponse' model class.
 */
public ExpiryDateResponse getExpiryDates(String exchange,Map<String,String> optionalParams) {
	ExpiryDateFinder expFinder
	          =new ExpiryDateFinder(this.clientEndPoint,exchange,optionalParams);
	return expFinder.getExpiryDates();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@return Returns the option types available for the exchange. For the structure of
 *the response see the 'OptionTypeResponse' model class.
 */
public OptionTypeResponse getOptionTypes(String exchange) {
	OptionTypeFinder optFinder
	          =new OptionTypeFinder(this.clientEndPoint,exchange);
	return optFinder.getOptionTypes();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@param optionalParams : map containing the optional parameters
 *@return Returns the option types available for the exchange. For the structure of
 *the response see the 'OptionTypeResponse' model class.
 */
public OptionTypeResponse getOptionTypes(String exchange,Map<String,String> optionalParams) {
	OptionTypeFinder optFinder
	          =new OptionTypeFinder(this.clientEndPoint,exchange,optionalParams);
	return optFinder.getOptionTypes();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@return Returns the strike prices for the options  available for the exchange. For the structure of
 *the response see the 'StrikePriceResponse' model class.
 */
public StrikePriceResponse getStrikePrices(String exchange) {
	StrikePriceFinder stkFinder
	          =new StrikePriceFinder(this.clientEndPoint,exchange);
	return stkFinder.getStrikePrices();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@param optionalParams : map containing the optional parameters
 *@return Returns the strike prices for the options  available for the exchange. For the structure of
 *the response see the 'StrikePriceResponse' model class.
 */
public StrikePriceResponse getStrikePrices(String exchange,Map<String,String> optionalParams) {
	StrikePriceFinder stkFinder
	          =new StrikePriceFinder(this.clientEndPoint,exchange,optionalParams);
	return stkFinder.getStrikePrices();
}
/**
 *Call this function to get the account details.
 *@return Returns the subscription details.For the structure of
 *the response see the 'UserProfile' model class.
 */
public UserProfile getUserProfile() {
	UserProfileFinder profile
	          =new UserProfileFinder(this.clientEndPoint);
	return profile.getUserProfile();
}
/**
 *Call this function to get the server information. 
 *@return Returns the server info.
 */
public ServerInfo getServerInfo() {
	ServerInfoFinder serverInfoFinder
	          =new ServerInfoFinder(this.clientEndPoint);
	return serverInfoFinder.getServerInfo();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function.
 *@return Returns the market message.For the structure of
 *the response see the 'MarketMessageResponse' model class.
 */
public MarketMessageResponse getMarketMessage(String exchange) {
	MarketMessageFinder marketMessageFinder
	          =new MarketMessageFinder(this.clientEndPoint,exchange);
	return marketMessageFinder.getMarketMessage();
}
/**
 *@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
 *'getSubscribedExchanges()' function. 
 *@return Returns the exchange message.For the structure of
 *the response see the 'ExchangeMessageResponse' model class.
 */
public ExchangeMessageResponse getExchangeMessage(String exchange) {
	ExchangeMessageFinder exchangeMessageFinder
	          =new ExchangeMessageFinder(this.clientEndPoint,exchange);
	return exchangeMessageFinder.getMarketMessage();
}
/**
*@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
*'getSubscribedExchanges()' function.
*@param instrumentIdentifier : The  instrument identifier for which the  quote
*have to be found.
*@return Returns the real time response for the instruments. For the structure of
*the response see the 'SubscribeRealTimeResponse' model class.
*/
public SubscribeRealTimeResponse getRealTimeData(String exchange,String instrumentIdentifier) {
	SubscribeRealTime sbsr
	          =new SubscribeRealTime(this.clientEndPoint,
	        		    exchange,instrumentIdentifier);
	return sbsr.getResponse();
}

/**
*@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
*'getSubscribedExchanges()' function.
*@param instrumentIdentifier : The  instrument identifier for which the  quote
*have to be found.
*@param optionalParams : the optional parameters.
*@return Returns the real time response for the instruments. For the structure of
*the response see the 'SubscribeRealTimeResponse' model class.
*/
public SubscribeRealTimeResponse getRealTimeData(String exchange,String instrumentIdentifier,
		 Map<String,Boolean> optionalParams) {
	SubscribeRealTime sbsr
              =new SubscribeRealTime(this.clientEndPoint,
  		    exchange,instrumentIdentifier,optionalParams);
    return sbsr.getResponse();
}
/**
*@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
*'getSubscribedExchanges()' function.
*@param instrumentIdentifier : The  instrument identifier for which the  quote
*have to be found.
*@param onRealtimeDataArrival : The callback for real time data message arrival.
*/
public void streamRealTimeData(String exchange,String instrumentIdentifier,OnRealtimeDataArrival onRealtimeDataArrival) {
	SubscribeRealTime sbsr
	          =new SubscribeRealTime(this.clientEndPoint,
	        		    exchange,instrumentIdentifier,onRealtimeDataArrival);
}

/**
*@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
*'getSubscribedExchanges()' function.
*@param instrumentIdentifier : The  instrument identifier for which the  quote
*have to be found.
*@param optionalParams : the optional parameters.
*@param onRealtimeDataArrival : The callback for real time data message arrival.
*/
public void streamRealTimeData(String exchange,String instrumentIdentifier,
		 Map<String,Boolean> optionalParams,OnRealtimeDataArrival onRealtimeDataArrival) {
	SubscribeRealTime sbsr
              =new SubscribeRealTime(this.clientEndPoint,
  		    exchange,instrumentIdentifier,optionalParams,onRealtimeDataArrival);
}

/**
*@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
*'getSubscribedExchanges()' function.
*@param instrumentIdentifier : The  instrument identifier for which the  quote
*have to be found.
*@param periodicity : Valid values are from {"MINUTE","HOUR"}.
*@return Returns the real time snapshot response for the instruments. For the structure of
*the response see the 'SubscribeSnapshotResponse' model class.
*/
public SubscribeSnapshotResponse getRealTimeSnapshotData(String exchange,String instrumentIdentifier,
		String periodicity) {
	SubscribeSnapshot sbsr
	          =new SubscribeSnapshot(this.clientEndPoint,
	        		    exchange,instrumentIdentifier,periodicity);
	return sbsr.getResponse();
}
/**
*@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
*'getSubscribedExchanges()' function.
*@param instrumentIdentifier : The  instrument identifier for which the  quote
*have to be found.
*@param periodicity : Valid values are from {"MINUTE","HOUR"}.
*@param optionalParams : the optional parameters.
*@return Returns the real time snapshot  response for the instruments. For the structure of
*the response see the 'SubscribeSnapshotResponse' model class.
*/
public SubscribeSnapshotResponse getRealTimeSnapshotData(String exchange,String instrumentIdentifier,
		 String periodicity,Map<String,Boolean> optionalParams) {
	SubscribeSnapshot sbsr
              =new SubscribeSnapshot(this.clientEndPoint,
  		    exchange,instrumentIdentifier,periodicity,optionalParams);
    return sbsr.getResponse();
}
/**
*@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
*'getSubscribedExchanges()' function.
*@param instrumentIdentifier : The  instrument identifier for which the  quote
*have to be found.
*@param periodicity : Valid values are from {"MINUTE","HOUR"}.
*@param onSnapshotDataArrival : The callback for snapshot data message arrival
*/
public void streamRealTimeSnapshotData(String exchange,String instrumentIdentifier,
		String periodicity,OnSnapshotDataArrival onSnapshotDataArrival) {
	SubscribeSnapshot sbsr
	          =new SubscribeSnapshot(this.clientEndPoint,
	        		    exchange,instrumentIdentifier,periodicity,onSnapshotDataArrival);
}
/**
*@param exchange : The exchange. Valid values are from the list of exchanges you get by calling the
*'getSubscribedExchanges()' function.
*@param instrumentIdentifier : The  instrument identifier for which the  quote
*have to be found.
*@param periodicity : Valid values are from {"MINUTE","HOUR"}.
*@param optionalParams : the optional parameters.
*@param onSnapshotDataArrival : The callback for snapshot data message arrival
*/
public void streamRealTimeSnapshotData(String exchange,String instrumentIdentifier,
		String periodicity,Map<String,Boolean> optionalParams,OnSnapshotDataArrival onSnapshotDataArrival) {
	SubscribeSnapshot sbsr
	          =new SubscribeSnapshot(this.clientEndPoint,
	        		    exchange,instrumentIdentifier,periodicity,optionalParams,onSnapshotDataArrival);
}

/**
 * 
 */
public void closeConnection() {
	this.clientEndPoint.is_disconneted_by_user=true;
	this.clientEndPoint.getSocket().disconnect();
	this.clientEndPoint=null;
	return;
}

}


