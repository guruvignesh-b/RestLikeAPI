package com.main;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

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
@Path("/{name}")
public class OtherPages extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GET
	@Produces("application/json")

	public Response helloWorld(@Context HttpServletRequest request,@PathParam ("name") String userName) throws JSONException {

		String ip= request.getRemoteAddr();

		long unixTime = System.currentTimeMillis() / 1000L;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", userName);

		Jedis j=new Jedis("localhost");
		j.set("ot,"+userName+","+unixTime,"ot,"+userName+","+ip);

		j.close();
		String result = ""+jsonObject;
		return Response.status(200).entity(result).build();
	}

}