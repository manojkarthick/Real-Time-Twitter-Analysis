	package Manoj;

import java.io.File;
import java.io.FileOutputStream;

import scala.sys.process.ProcessBuilderImpl.FileOutput;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

	//SQLImports


	public class HTMLBolt extends BaseBasicBolt 
	{
		private static final long serialVersionUID = 1L;
		

		@Override
		public void execute(Tuple input, BasicOutputCollector collector) 
		{
			StringBuffer htmlcode = new StringBuffer();
			htmlcode.append("<!DOCTYPE html><html<head><meta http-equiv=\"refresh\" content=\"5\" >");
			htmlcode.append("<!-- Latest compiled and minified CSS --><link rel=\"stylesheet\"");
			htmlcode.append("href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\">");
			htmlcode.append("</head><body><div class=\"container\"><div class=\"page-header\"><h3>Twitter Streaming data</h3></div>");
			htmlcode.append("<table class=\"table table-hover table-bordered\" ><th >Hashtag-Name</th><th>Count</th>");
			htmlcode.append("<tr><td>"+TwitterExample.hashtags[0]+"</td><td>"+JSONBolt.counter0+"</td></tr>");
			htmlcode.append("<tr><td>"+TwitterExample.hashtags[1]+"</td><td>"+JSONBolt.counter1+"</td></tr>");
			htmlcode.append("<tr><td>"+TwitterExample.hashtags[2]+"</td><td>"+JSONBolt.counter2+"</td></tr>");
			htmlcode.append("<tr><td>"+TwitterExample.hashtags[3]+"</td><td>"+JSONBolt.counter3+"</td></tr>");
			htmlcode.append("<tr><td>"+TwitterExample.hashtags[4]+"</td><td>"+JSONBolt.counter4+"</td></tr>");
			htmlcode.append("</table></div></body></html>");
			try{
				String s = new String(htmlcode);
			FileOutputStream fos = new FileOutputStream("/home/HadoopUser/Desktop/display.html");
			fos.write(s.getBytes());
			fos.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		public void declareOutputFields(OutputFieldsDeclarer declarer) 
		{
			// TODO Auto-generated method stub   
		}
	}


