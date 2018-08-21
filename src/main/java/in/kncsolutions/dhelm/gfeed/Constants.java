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

import java.util.ArrayList;
import java.util.List;

import in.kncsolutions.dhelm.gfeed.models.ExchangeList;
import in.kncsolutions.dhelm.gfeed.models.InstrumentResponse;
/**
 *
 */
public class Constants {
   public static long RETRY_DURATION=2000;
   public static int RETURN_RETRY_NO1=30;
   public static int MAX_NO_OF_ATTEMPTS=10;
   /**
    *End point to do the authentication. 
    */
   public static String AUTHENTICATE="Authenticate";
   /**
    *End point to get the subscribe exchanges. 
    */
   public static String GET_EXCHANGES="GetExchanges";
   /**
    *End point to get the instruments for an exchange. 
    */
   public static String GET_INSTRUMENTS="GetInstruments";
   /**
    *End point to get the instruments for an exchange on search with a search key word. 
    */
   public static String GET_INSTRUMENTS_ON_SEARCH="GetInstrumentsOnSearch";
   /**
    *End point to get the last quote of an instrument. 
    */
   public static String GET_LAST_QUOTE="GetLastQuote";
   /**
    *End point to get the last quote of a set of instruments in a single call. 
    */
   public static final String GET_LAST_QUOTE_ARRAY="GetLastQuoteArray";
   /**
    *End point to get the snapshot of a set of instruments in a single call. 
    */
   public static final String GET_SNAPSHOT="GetSnapshot";
   /**
    *End point to get the historical data. 
    */
   public static final String GET_HISTORY="GetHistory";
   /**
    *End point to get the instrument types. 
    */
   public static final String GET_INSTRUMENT_TYPES="GetInstrumentTypes";
   /**
    *End point to get the product types. 
    */
   public static final String GET_PRODUCTS="GetProducts";
   /**
    *End point to get  expiry dates. 
    */
   public static final String GET_EXPIRY_DATES="GetExpiryDates";
   /**
    *End point to get option types. 
    */
   public static final String GET_OPTION_TYPES="GetOptionTypes";
   /**
    *End point to get the strike types. 
    */
   public static final String GET_STRIKE_PRICES="GetStrikePrices";
   /**
    *End point to get the account details. 
    */
   public static final String GET_LIMITATION="GetLimitation";
   /**
    *End point to get the server information. 
    */
   public static final String GET_SERVER_INFO="GetServerInfo";
   /**
    *End point to get the market message. 
    */
   public static final String GET_MARKET_MESSAGES="GetMarketMessages";
   /**
    *End point to get the exchange message. 
    */
   public static final String GET_EXCHANGE_MESSAGES="GetExchangeMessages";
   /**
    *End point for real time subscription. 
    */
   public static final String SUBSCRIBE_REALTIME="SubscribeRealtime";
   /**
    *End point for snapshot subscription. 
    */
   public static final String SUBSCRIBE_SNAPSHOT="SubscribeSnapshot";
 
   /*The keys to be used to pass optional parameters in certain cases*/
   /**
    * 
    */
   public static final String EXCHANGE_KEY="Exchange";
   public static final String INSTRUMENT_TYPE_KEY="InstrumentType";
   public static final String PRODUCT_KEY="Product";
   public static final String EXPIRY_DATE_KEY="Expiry";
   public static final String OPTION_TYPE_KEY="OptionType";
   public static final String STRIKE_PRICE_KEY="StrikePrice";
   public static final String PERIODICITY_KEY="Periodicity";
   public static final String UNSUBSCRIBE_KEY="Unsubscribe";
   public static final String PERIOD_KEY="Period";
   public static final String FROM_KEY="From";
   public static final String TO_KEY="To";
   public static final String MAX_KEY="Max";
   public static final String SEARCH_KEY="Search";
   /**
    *
    */
   public static final String PERIODICITY_MONTH="MONTH";
   public static final String PERIODICITY_WEEK="WEEK";
   public static final String PERIODICITY_DAY="DAY";
   public static final String PERIODICITY_HOUR="HOUR";
   public static final String PERIODICITY_MINUTE="MINUTE";
   public static final String PERIODICITY_TICK="TICK";
  /**
   * 
   */
   public static final String HISTORY_OHLC_RESULT_TYPE="HistoryOHLCResult"; 
   public static final String HISTORY_TICK_RESULT_TYPE="HistoryTickResult";
   public static final String EXCHANGE_RESULT_MESSAGE="ExchangesResult";
   public static final String INSTRUMENT_RESULT_RESPONSE="InstrumentsResult";
   public static final String INSTRUMENT_SEARCH_RESULT_RESPONSE="InstrumentsOnSearchResult";
   public static final String LAST_QUOTE_RESULT_MESSAGE="LastQuoteResult";
   public static final String LAST_QUOTE_ARRAY_RESULT_MESSAGE="LastQuoteArrayResult";
   public static final String SNAPSHOT_RESULT_MESSAGE="SnapshotResult";
   public static final String INSTRUMENT_TYPES_RESULT_MESSAGE="InstrumentTypesResult";
   public static final String PRODUCT_RESULT_MESSAGE="ProductsResult";
   public static final String EXPIRY_DATE_RESULT_MESSAGE="ExpiryDatesResult";
   public static final String OPTION_TYPE_RESULT_MESSAGE="OptionTypesResult";
   public static final String STRIKE_PRICES_RESULT_MESSAGE="StrikePricesResult";
   public static final String USER_PROFILE_LIMITATION_MESSAGE="LimitationResult";
   public static final String MARKET_MESSAGES_ITEM_RESULT_MESSAGE="MarketMessagesItemResult";

   /**
   *Set it false to stop debug mode.
   */
	public static boolean DEBUG_MODE=true;
	
}
