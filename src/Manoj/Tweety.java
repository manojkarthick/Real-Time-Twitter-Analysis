package Manoj;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.FilterQuery;
import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterObjectFactory;

public class Tweety 
{
	public static String[] hashtags = new String[6];
	private static final Logger logger = LoggerFactory.getLogger(Tweety.class);
    
    /** Information necessary for accessing the Twitter API */
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;
    
    /** The actual Twitter stream. It's set up to collect raw JSON data */
    private TwitterStream twitterStream;
        
    private void start(final Context context) throws IOException 
    {
	
	
    	// Producer properties 
    	Properties props = new Properties();
    	props.put("metadata.broker.list", context.getString(TwitterSourceConstant.BROKER_LIST));
    	props.put("serializer.class", context.getString(TwitterSourceConstant.SERIALIZER));
    	props.put("partitioner.class", context.getString(TwitterSourceConstant.PARTITIONER));
    	props.put("request.required.acks", context.getString(TwitterSourceConstant.REQUIRED_ACKS));
	
    	ProducerConfig config = new ProducerConfig(props);
	
    	final Producer<String, String> producer = new Producer<String, String>(config);
 
	
    	/** Twitter properties **/
    	consumerKey = context.getString(TwitterSourceConstant.CONSUMER_KEY_KEY);
    	consumerSecret = context.getString(TwitterSourceConstant.CONSUMER_SECRET_KEY);
    	accessToken = context.getString(TwitterSourceConstant.ACCESS_TOKEN_KEY);
    	accessTokenSecret = context.getString(TwitterSourceConstant.ACCESS_TOKEN_SECRET_KEY);
	
    	
    	
    	ConfigurationBuilder cb = new ConfigurationBuilder();
    	cb.setOAuthConsumerKey(consumerKey);
    	cb.setOAuthConsumerSecret(consumerSecret);
    	cb.setOAuthAccessToken(accessToken);
    	cb.setOAuthAccessTokenSecret(accessTokenSecret);
    	cb.setJSONStoreEnabled(true);
    	cb.setIncludeEntitiesEnabled(true);
    	cb.setHttpProxyHost("proxy.tcs.com");
    	cb.setHttpProxyPort(8080);
    	cb.setHttpProxyUser("876216");
    	cb.setHttpProxyPassword("Apple@123");
    	twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
    	
	    

    	final Map<String, String> headers = new HashMap<String, String>();

    	/** Twitter listener **/
    	StatusListener listener = new StatusListener() 
    	{
    		// The onStatus method is executed every time a new tweet comes
    		// in.
    		public void onStatus(Status status) 
    		{
    			// The EventBuilder is used to build an event using the
    			// the raw JSON of a tweet
		   
    			System.out.println("Listening :");
    			KeyedMessage<String, String> data = new KeyedMessage<String, String>("testing1",TwitterObjectFactory.getRawJSON(status));
    			
    			producer.send(data);
    			System.out.println(data);
       		}
		    
    		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) 
    		{
    			
    		}
		
    		public void onTrackLimitationNotice(int numberOfLimitedStatuses)
    		{
    			
    		}
		
    		public void onScrubGeo(long userId, long upToStatusId) 
    		{
    			
    		}
		
    		public void onException(Exception ex) 
    		{
    			logger.info("ShutDown");
    			twitterStream.shutdown();
    		}
		
    		public void onStallWarning(StallWarning warning) 
    		{
    			
    		}
	    };
   
	    twitterStream.addListener(listener);
	    /** GOGOGO **/
	    twitterStream.sample();
	    FilterQuery query = new FilterQuery().track(Tweety.hashtags[0],Tweety.hashtags[1],Tweety.hashtags[2]
	    		,Tweety.hashtags[3],Tweety.hashtags[4]);
 		twitterStream.filter(query);
	    /** Bind the listener **/
	    
	    
    }
    
    public static void main(String[] args) throws Exception
    {
    	
    	 	Context context = new Context("conf/producer.conf");
    		Tweety tp = new Tweety();
    		
    		try 
    		{
    			Class.forName("com.mysql.jdbc.Driver");
    			String url="jdbc:mysql://172.20.95.137/mongo_test?" + "user=manoj&password=qwerty1!";
    			Connection conn = DriverManager.getConnection(url);
    			System.out.println(url);
    			Statement stmt = conn.createStatement();
    			
    			ConfigurationBuilder cb = new ConfigurationBuilder();
    	    	cb.setOAuthConsumerKey("ujS6V0corQ4v8K4dp6bIlVYsq");
    	    	cb.setOAuthConsumerSecret("XhmTLcS4BI6zy9fZ0xIOy1ZZkAnQYZ1jFrlHojWuWpx2RCWH9H");
    	    	cb.setOAuthAccessToken("30427118-YNLB3WfviD4j7MvNgU4gZ7SPQbCDBXWh4kPZcHXrY");
    	    	cb.setOAuthAccessTokenSecret("LkhcZUJbj33RBc1DTXrxJps4ELgog8WxoRS6gCReemOHc");
    	    	cb.setJSONStoreEnabled(true);
    	    	cb.setIncludeEntitiesEnabled(true);
    	    	cb.setHttpProxyHost("proxy.tcs.com");
    	    	cb.setHttpProxyPort(8080);
    	    	cb.setHttpProxyUser("467098");
    	    	cb.setHttpProxyPassword("See@0615");
    	    	Twitter twitter = new TwitterFactory(cb.build()).getInstance();
    			ResponseList<Location> locations;
    			locations = twitter.getAvailableTrends();
    			System.out.println("Showing available trends");
    			Trends trends = twitter.getPlaceTrends(2295386);
    			stmt.executeUpdate("drop table hashtag");
    			stmt.executeUpdate("create table hashtag(name varchar(50),count int)");
    			for (int i=0;i<=5;i++)
    	        {	
    				  System.out.println(trends.getTrends()[i].getName());
    	        	  hashtags[i]=trends.getTrends()[i].getName();
    	        	  stmt.executeUpdate("insert into hashtag values (\'"+hashtags[i]+"\'"+","+0+")");
    	        }
    	        System.out.println("done.");
    	       
    		}
    		catch (Exception te)
    		{
    			te.printStackTrace();
    			System.out.println("Failed to get trends: " + te.getMessage());
    			System.exit(-1);
    		}
    		tp.start(context);
	
    }
}