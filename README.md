Dhelm-gfeed-client is a java client library to access and integrate stock market data from  
[Global Financial Datafeeds LLP](https://globaldatafeeds.in/) with your application.

To use this library you must [subscribe](https://globaldatafeeds.in/api/) to web socket api with Global Financial Datafeeds LLP and get your API key and web socket endpoint URL. Programmatic access to data obviously provides better control over your algorithm. You access the raw data from your data provider, then feed into your own application and do whatever analysis you want.

If java is your favorite language for stock market data analysis, then using Dhelm-gfeed-client you can plug data from web socket api into your application directly.

For detailed integration and usage guidelines, please read through the [wiki](https://github.com/kncsolutions/dhelm-gfeed-client/wiki).

## How to run the test file
Clone the repository.Navigate to the dhelm-gfeed-client/dhelm-gfeed-client-test/target folder.
From the command line run the following command:<br/>
java -jar dhelm-gfeedtest-1.0.0-launcher.jar <WEB_SOCKET_URL> <API_KEY><br/>
where you should replace<br/>
_**<WEB_SOCKET_URL>**_ with the web socket url<br/>
and<br/>
_**<API_KEY>**_ with the api key which you have got at the time of subscription.<br/>

## How to integrate with your project

If you have any query raise an [issue](https://github.com/kncsolutions/dhelm-gfeed-client/issues) or email at developer@kncsolutions.in.

