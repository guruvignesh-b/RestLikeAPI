package com.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

@Path("/hello-world/logs")
public class HelloLogs {

	@SuppressWarnings("unchecked")
	@GET
	@Produces("application/json")

	public Response helloWorld() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		JSONArray jArr=new JSONArray();
		Bean b=new Bean();

		Jedis j=new Jedis("localhost");

		Set<String> list = j.keys("hw,*");
		for(String s:list){
			System.out.println(s+"--"+j.get(s));

			JSONObject jo = new JSONObject();
			Map<String,String> map=new HashMap<String,String>();
			jo.put("ip", j.get(s).split(",")[j.get(s).split(",").length-1]);
			jo.put("timestamp", s.split(",")[s.split(",").length-1]);
			jArr.add(jo);
			map.put(s.split(",")[s.split(",").length-1], j.get(s).split(",")[j.get(s).split(",").length-1]);
			b.setMap(map);
		}


		jsonObject.put("logs", jArr);
		j.close();
		String result = ""+jsonObject;
		return Response.status(200).entity(result).build();
	}

}
