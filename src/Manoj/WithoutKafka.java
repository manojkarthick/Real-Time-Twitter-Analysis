package Manoj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
 
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;
 
public class WithoutKafka {
 
    /** The actual Twitter stream. It's set up to collect raw JSON data */
    private TwitterStream twitterStream;
    private String[] keywords;
    Properties prop = new Properties();
    FileOutputStream fos;
 
    public WithoutKafka() {
 
        //load a properties file
      
 
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setOAuthConsumerKey("JBj3CmEXL8PQfRVk8g4WpBSM6");
            cb.setOAuthConsumerSecret("eUXTBDHdIGUVmRMtATUyw42eiEBLeQPegoh91ErGAeXklYyYry");
            cb.setOAuthAccessToken("3241993404-COPiMFGnPcoT4CFQGVfhIbcc7TaWigvXNMwIR34");
            cb.setOAuthAccessTokenSecret("liXLkvh7YlNxuz1nrawSI1xw70cnQzrnPjzvyMbHVBSjO");
            cb.setHttpProxyHost("proxy.tcs.com");
            cb.setHttpProxyPort(8080);
            cb.setHttpProxyUser("467098");
            cb.setHttpProxyPassword("See@0615");
            cb.setJSONStoreEnabled(true);
            cb.setIncludeEntitiesEnabled(true);
 
            twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
 
        
 
    }
 
    public void startTwitter() {
 
        try {
            fos = new FileOutputStream(new File("/home/HadoopUser/Desktop/Banks.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        
        // Set up the stream's listener (defined above),
        twitterStream.addListener(listener);
 
        System.out.println("Starting down Twitter sample stream...");
 
        // Set up a filter to pull out industry-relevant tweets
        FilterQuery query = new FilterQuery().track("HSBC,Bank of America,Santander,Wells Fargo,CitiBank,BNP Paribas,Goldman Sachs,Barclays,State Bank of India,UBS");
        twitterStream.filter(query);
 
    }
 
    public void stopTwitter() {
 
        System.out.println("Shutting down Twitter sample stream...");
        twitterStream.shutdown();
 
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    StatusListener listener = new StatusListener() {
 
        // The onStatus method is executed every time a new tweet comes in.
        public void onStatus(Status status) {
            // The EventBuilder is used to build an event using the headers and
            // the raw JSON of a tweet
            System.out.println(status.getUser().getScreenName() + ": " + status.getText());
 
            System.out.println("timestamp : "+ String.valueOf(status.getCreatedAt().getTime()));
            try {
            	System.out.println("writing");
                fos.write(DataObjectFactory.getRawJSON(status).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
 
        // This listener will ignore everything except for new tweets
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
        public void onScrubGeo(long userId, long upToStatusId) {}
        public void onException(Exception ex) {}
        public void onStallWarning(StallWarning warning) {}
    };
 
    public static void main(String[] args) throws InterruptedException {
 
    	WithoutKafka twitter = new WithoutKafka();
        twitter.startTwitter();
 
    }
 
}