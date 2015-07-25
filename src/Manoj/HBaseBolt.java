package Manoj;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.thrift.generated.Hbase;
import org.apache.hadoop.hbase.util.Bytes;
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

public class HBaseBolt extends BaseBasicBolt 
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
		
		try
		{
			// Instantiating configuration class
			Configuration hadoopconf = new Configuration();
			hadoopconf.setStrings("hbase.zookeeper.quorum", "172.20.95.110,172.20.95.107,172.20.95.141");
			Configuration conf = HBaseConfiguration.create(hadoopconf);
			HTable htable = new HTable(conf, "tweet");
			Put put0 = new Put(Bytes.toBytes(TwitterExample.hashtags[0]));
			Put put1 = new Put(Bytes.toBytes(TwitterExample.hashtags[1]));
			Put put2 = new Put(Bytes.toBytes(TwitterExample.hashtags[2]));
			Put put3 = new Put(Bytes.toBytes(TwitterExample.hashtags[3]));
			Put put4 = new Put(Bytes.toBytes(TwitterExample.hashtags[4]));
			put0.add(Bytes.toBytes("events"),Bytes.toBytes("count"),Bytes.toBytes(String.valueOf((JSONBolt.counter0))));
			put1.add(Bytes.toBytes("events"),Bytes.toBytes("count"),Bytes.toBytes(String.valueOf((JSONBolt.counter1))));
			put2.add(Bytes.toBytes("events"),Bytes.toBytes("count"),Bytes.toBytes(String.valueOf((JSONBolt.counter2))));
			put3.add(Bytes.toBytes("events"),Bytes.toBytes("count"),Bytes.toBytes(String.valueOf((JSONBolt.counter3))));
			put4.add(Bytes.toBytes("events"),Bytes.toBytes("count"),Bytes.toBytes(String.valueOf((JSONBolt.counter4))));
			htable.put(put0);htable.put(put1);htable.put(put2);htable.put(put3);htable.put(put4);
			htable.flushCommits();
			htable.close();
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