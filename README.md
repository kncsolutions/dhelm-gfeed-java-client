For smoother streaming of real time data certain changes have been made in version 1.0.1-SNAPSHOT. To integrate that version, add the following dependency 
```xml
<dependency>
  <groupId>in.kncsolutions.dhelm.gfeed</groupId>
  <artifactId>dhelm-gfeed-client</artifactId>
  <version>1.0.1-SNAPSHOT</version>
</dependency>
```
For the souce code clone the ***snapshot branch***.
## Changes
1. In version 1.0.1-SNAPSHOT, to stream real time data `streamRealTimeData(...)` has been introduced. The `getRealTimeData(..)` method has been phased out. See the usage guide [here](https://github.com/kncsolutions/dhelm-gfeed-java-client/wiki/Subscribe-to-realtime-data#changelog).
2. To stream snapshot data `streamRealTimeSnapshotData(..)` has been introduced. The `getRealTimeSnapshotData()` method has been phased out.See the usage guide [here](https://github.com/kncsolutions/dhelm-gfeed-java-client/wiki/Subscribe-to-realtime-snapshot-data#changelog).

To run the corresponding test file, clone the ***snapshot*** branch of the repository to your computer.Navigate to the dhelm-gfeed-client/dhelm-gfeed-client-test/target folder.
From the command line run the following command:<br/>
java -jar dhelm-gfeedtest-1.0.1-launcher.jar <WEB_SOCKET_URL> <API_KEY><br/>
where you should replace<br/>
_**<WEB_SOCKET_URL>**_ with the web socket url<br/>
and<br/>
_**<API_KEY>**_ with the api key which you have got at the time of subscription.<br/>

If you have any query raise an [issue](https://github.com/kncsolutions/dhelm-gfeed-client/issues) or email at developer@kncsolutions.in.
To know about dhelm project visit our project website at https://dhelm.kncsolutions.in
