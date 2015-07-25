package Manoj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

public class TwitterExample 
{
	public static String[] hashtags = new String[5];
	public static void main(String[] args) {
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
			for (int i=0;i<5;i++)
	        {	
				  System.out.println(trends.getTrends()[i].getName());
	        	  hashtags[i]=trends.getTrends()[i].getName();
	        }
	        System.out.println("done.");
	       
		}
		catch (Exception te)
		{
			te.printStackTrace();
			System.out.println("Failed to get trends: " + te.getMessage());
			System.exit(-1);
		}
		  TopologyBuilder builder = new TopologyBuilder();
		  
		  GlobalPartitionInformation hosts = new GlobalPartitionInformation();
	      hosts.addPartition(0, new Broker("localhost", 9092));
	      BrokerHosts brokerHosts = new StaticHosts(hosts);
		  
		  SpoutConfig spoutConf = new SpoutConfig(brokerHosts, "testing1", "/kafkastorm", "id");
		  spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
		  builder.setSpout("spout", new KafkaSpout(spoutConf), 2);
		  builder.setBolt("printer", new JSONBolt()).shuffleGrouping("spout");
		  //builder.setBolt("htmlprint", new HTMLBolt()).shuffleGrouping("spout");
		  //builder.setBolt("hbase", new HBaseBolt()).shuffleGrouping("spout");		  
		    spoutConf.zkServers = new ArrayList<String>() {{add("localhost");}};
		    spoutConf.zkPort = 2181;
		  		 
		  Config conf = new Config();
		   conf.setDebug(true);
		 
		  LocalCluster cluster = new LocalCluster();
		  cluster.submitTopology("kafka-test", conf, builder.createTopology());
		 
		  Utils.sleep(600000);
		}
	
}
