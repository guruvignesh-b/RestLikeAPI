package com.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import redis.clients.jedis.Jedis;

/**
 * @author guru
 *
 * Description:
 * Created an API and when ​/v1/hello-­world returns a JSON with message Hello World.
 * If we give something else other than hello world, the same text is printed as the message as JSON
 * Whenever this end-point is hit, the IP address and Time-stamp are logged using Redis.
 * The values stored in this is used for multiple purposes. 
 * When v1/logs is hit, a JSON displaying the details of all end-points 
 * and when v1/hello-world/logs is hit, it displays the logs are hello-world end-points  
 * 
 * The application was developed using Apache tomcat server. Deploy the project with the server 
 * and when the above end-points are hit, the JSON outputs are displayed. 
 * Index.jsp displays the logged details and the statistics based on every minute.
 */
public class Bean {

	private String ipAddr;
	private String Timestamp;
	private Map<String,String> map=new HashMap<String,String>();
	public String getTimestamp() {
		return Timestamp;
	}
	public void setTimestamp(String timestamp) {
		Timestamp = timestamp;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	/**
	 * @return map. Contains all the hello world Ip address and Timestamps
	 */
	public Map<String,String> mapPut(){
		Jedis j=new Jedis();
		TreeMap<String,String> map1=new TreeMap<String,String>();
		Set<String> list = j.keys("hw,*");
		for(String s:list){

			map1.put(s.split(",")[s.split(",").length-1], j.get(s).split(",")[j.get(s).split(",").length-1]);

		}
		j.close();
		return map1;
	}

	/**
	 * @return map. Contains all the end-point's Ip address and Timestamps
	 */
	public Map<String,String> mapPutAll(){
		Jedis j=new Jedis();
		TreeMap<String,String> map1=new TreeMap<String,String>();
		Set<String> list = j.keys("*");
		for(String s:list){

			map1.put(s.split(",")[s.split(",").length-1], j.get(s).split(",")[j.get(s).split(",").length-1]);

		}
		j.close();
		return map1;
	}

	/**
	 * @return map. Contains all the end-point's Minute aggregates
	 */
	public Map<String,Integer> getResults(){
		Jedis j=new Jedis();
		TreeMap<String,String> map1=new TreeMap<String,String>();
		Map<String,Integer> map2=new TreeMap<String,Integer>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm z");

		Set<String> list = j.keys("*");
		for(String s:list){

			map1.put(s.split(",")[s.split(",").length-1], j.get(s).split(",")[j.get(s).split(",").length-1]);

		}

		for(Map.Entry<String, String> entry: map1.entrySet()){
			Date date1 = new Date(Long.parseLong(entry.getKey())*1000L);
			String formattedDate1= sdf.format(date1);
			if(map2.containsKey(formattedDate1))map2.put(formattedDate1, map2.get(formattedDate1)+1);
			else map2.put(formattedDate1, 1);

		}
		j.close();
		return map2;
	}

	/**
	 * @return map. Contains all the Hello world Minute aggregates
	 */
	public Map<String,Integer> getResultsHW(){
		Jedis j=new Jedis();
		TreeMap<String,String> map1=new TreeMap<String,String>();
		Map<String,Integer> map2=new TreeMap<String,Integer>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm z"); 
		Set<String> list = j.keys("hw*");
		for(String s:list){

			map1.put(s.split(",")[s.split(",").length-1], j.get(s).split(",")[j.get(s).split(",").length-1]);

		}

		for(Map.Entry<String, String> entry: map1.entrySet()){
			Date date1 = new Date(Long.parseLong(entry.getKey())*1000L);
			String formattedDate1= sdf.format(date1);
			if(map2.containsKey(formattedDate1))map2.put(formattedDate1, map2.get(formattedDate1)+1);
			else map2.put(formattedDate1, 1);

		}
		j.close();
		return map2;
	}

}
