package com.eladproj.SokoBanServer;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ORM.LevelSolution;
import model.data.Level;

/**
 * Hello world!
 *
 */
public class App 
{
	
	public static String getSolution(String levelData) {
		String url = "http://localhost:8080/SokoBanJerseyWeb/webapi/request/getsolution";
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(url);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.json(levelData));
		if (response.getStatus() == 200) {
			String solution = response.readEntity(new GenericType<String>() {
			});
			return solution;
		} else {
			System.out.println(response.getHeaderString("errorResponse"));
		}
		return null;
	}

	public static void addSolution(LevelSolution solution) {
		String url = "http://localhost:8080/SokoBanJerseyWeb/webapi/request/addsolution";
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(url);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.json(solution));

		if(response == null)
			System.out.println("respone is null");
		if (response.getStatus() == 204) {
			System.out.println("Solution added successfully");
		} else {
			System.out.println(response.getHeaderString("errorResponse"));
		}
	}
	
	public static String levelAsOneString(Level lvl){
		ArrayList<String> list = lvl.getLevelByArrayListOfStrings();
		StringBuilder str = new StringBuilder();
		list.forEach((s)->str.append(s));
		return str.toString();
	}
}
