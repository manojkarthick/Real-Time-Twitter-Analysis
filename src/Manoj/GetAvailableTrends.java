package Manoj;

/*
* Copyright 2007 Yusuke Yamamoto
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
//SQLImports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
/**
* Shows the locations that Twitter has trending topic information for.
*
* @author Yusuke Yamamoto - yusuke at mac.com
*/
public final class GetAvailableTrends 
{
/**
* Usage: java twitter4j.examples.trends.GetAvailableTrends
*
* @param args message
*/
	public static String[] hashtags = new String[6];
	public static void main(String[] args) 
	{
		
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
	        while(true)
	        {
	        	
	        }
		}
		catch (Exception te)
		{
			te.printStackTrace();
			System.out.println("Failed to get trends: " + te.getMessage());
			System.exit(-1);
		}
	}
}