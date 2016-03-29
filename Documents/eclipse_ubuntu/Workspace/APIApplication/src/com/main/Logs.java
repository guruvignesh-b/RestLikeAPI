package com.main;

import java.util.HashSet;
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
@Path("/logs")
public class Logs {

	@SuppressWarnings("unchecked")
	@GET
	@Produces("application/json")

	public Response helloWorld() throws JSONException {
		JSONObject jsonObjectMain = new JSONObject();
		JSONObject jsonObject = new JSONObject();
		JSONArray mainArr=new JSONArray();
		JSONArray jArr=new JSONArray();

		Jedis j=new Jedis("localhost");

		Set<String> list = j.keys("hw*");
		Set<String> o=j.keys("ot,*");
		Set<String> ep=new HashSet<String>();
		for(String i:o){

			ep.add(i.split(",")[1]);

		}
		jsonObject.put("endpoint", "hello-world");

		for(String s:list){



			JSONObject jo = new JSONObject();

			jo.put("ip", j.get(s).split(",")[j.get(s).split(",").length-1]);
			jo.put("timestamp", s.split(",")[s.split(",").length-1]);
			jArr.add(jo);

		}
		jsonObject.put("logs", jArr);
		mainArr.add(jsonObject);
		for(String e:ep){
			jsonObject=new JSONObject();
			jsonObject.put("endpoint", e);

			jArr=new JSONArray();
			Set<String> list2 = j.keys("ot,"+e+",*");
			for(String str:list2){
				JSONObject jo = new JSONObject();

				jo.put("ip", j.get(str).split(",")[j.get(str).split(",").length-1]);
				jo.put("timestamp", str.split(",")[str.split(",").length-1]);
				jArr.add(jo);
			}
			jsonObject.put("logs", jArr);
			mainArr.add(jsonObject);
		}

		jsonObjectMain.put("logset", mainArr);
		j.close();
		String result = ""+jsonObjectMain;
		return Response.status(200).entity(result).build();
	}

}
