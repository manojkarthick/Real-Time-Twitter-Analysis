package Manoj;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

//SQLImports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class JSONBolt extends BaseBasicBolt 
{
	private static final long serialVersionUID = 1L;
	
	static int counter0=0;
	static int counter1=0;
	static int counter2=0;
	static int counter3=0;
	static int counter4=0;
	
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) 
	{
		// TODO Auto-generated method stub
		
		//MySQLlogic
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://172.20.95.137/mongo_test?" + "user=manoj&password=qwerty1!";
			Connection conn = DriverManager.getConnection(url);
			System.out.println(url);
			Statement stmt = conn.createStatement();
			for(int i=0;i<TwitterExample.hashtags.length;i++)
			{
				System.out.println(TwitterExample.hashtags[i]);
			}
			
			
			
		String s1 = new String();
		String word=input.getString(0);
		Object s = new Object();
		if(StringUtils.isBlank(word))
		{
			return;
		}
		
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(word);
			s = json.get("text");
			s1 = (String)s;
		String caseins = s1.toLowerCase();
		if(caseins.contains(TwitterExample.hashtags[0].toLowerCase()))
			counter0++;
		if(caseins.contains(TwitterExample.hashtags[1].toLowerCase()))
			counter1++;
		if(caseins.contains(TwitterExample.hashtags[2].toLowerCase()))
			counter2++;
		if(caseins.contains(TwitterExample.hashtags[3].toLowerCase()))
			counter3++;
		if(caseins.contains(TwitterExample.hashtags[4].toLowerCase()))
			counter4++;
		
		
		
		System.out.println("Output: "+s1);
		System.out.println("The counts are: ");
		System.out.println(TwitterExample.hashtags[0] + counter0);
		System.out.println(TwitterExample.hashtags[1] + counter1);
		System.out.println(TwitterExample.hashtags[2] + counter2);
		System.out.println(TwitterExample.hashtags[3] + counter3);
		System.out.println(TwitterExample.hashtags[4] + counter4);
		
		stmt.executeUpdate("update hashtag set count="+counter0+" where name=\""+TwitterExample.hashtags[0]+"\"");
		stmt.executeUpdate("update hashtag set count="+counter1+" where name=\""+TwitterExample.hashtags[1]+"\"");
		stmt.executeUpdate("update hashtag set count="+counter2+" where name=\""+TwitterExample.hashtags[2]+"\"");
		stmt.executeUpdate("update hashtag set count="+counter3+" where name=\""+TwitterExample.hashtags[3]+"\"");
		stmt.executeUpdate("update hashtag set count="+counter4+" where name=\""+TwitterExample.hashtags[4]+"\"");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public void declareOutputFields(OutputFieldsDeclarer declarer) 
	{
		// TODO Auto-generated method stub   
	}
}