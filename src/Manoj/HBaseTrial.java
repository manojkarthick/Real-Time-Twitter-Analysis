package Manoj;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseTrial {

	public static void main(String args[])
	{
		try{
		// Instantiating configuration class
		
		Configuration hadoopconf = new Configuration();
		hadoopconf.setStrings("hbase.zookeeper.quorum", "172.20.95.110,172.20.95.107,172.20.95.141");
	    Configuration conf = HBaseConfiguration.create(hadoopconf);
	    HTable htable = new HTable(conf, "twitter_manoj");
	    System.out.println("Table - "+htable.isTableEnabled("twitter_manoj"));
	    htable.close();
	    
	    
	      // Instantiating HBaseAdmin class
	      /*HBaseAdmin admin = new HBaseAdmin(conf);
	      // Verifying weather the table is disabled
	      Boolean bool = admin.isTableDisabled("twitter_manoj");
	      System.out.println(bool);
	      // Disabling the table using HBaseAdmin object
	      if(!bool){
	         admin.disableTable("twitter_manoj");
	         System.out.println("Table disabled");
	      }*/
	     	         
	      /*Result g = htable.get(new Get(Bytes.toBytes("")));
	      
	     for(int i=0;i<g.size();i++){
	    	 System.out.println(g.getRow());
	     }*/
	        
	      /*HBaseAdmin hbase = new HBaseAdmin(conf);
	      HTableDescriptor desc = new HTableDescriptor("twitter_manoj");
	      HColumnDescriptor meta = new HColumnDescriptor("events".getBytes());
	      desc.addFamily(meta);
	      hbase.createTable(desc);
	      */
	      }
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
