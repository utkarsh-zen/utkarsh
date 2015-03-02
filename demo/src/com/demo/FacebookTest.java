package com.demo;

import java.net.URL;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Page;
import com.restfb.types.User;
import com.visural.common.IOUtil;

public class FacebookTest {

	
	public static void main(String[] args) {
		
		String accessToken="CAACEdEose0cBAETO92UWZCAp1j57Htpz9XgiMCUfjcvW1CuUgTMCPAKVeZBVWZBMd9jEVIYYd6dXjl8WwGZCzbSPfU3L4SUZAwSyjY9W0rcClzZAjCxYQKCIQo1NYIuvZC86qZB1VELgQT2UZBTtVFr1iMIcUypQgNBv2y3V2crgKew9LX3W2X8XYPimQ0eTHx7YQFaJYWHhO9u5ZAtaawWWqX";
		String userId="me";
		JSONObject resp = null;
		try {
			resp = new JSONObject(IOUtil.urlToString(
					new URL(
							"https://graph.facebook.com/v2.2/" + userId + "/?fields=friendlists{id,name}&access_token="+ accessToken)));
		System.out.println(resp);
			resp=(JSONObject) resp.get("friends");
			System.out.println(resp);
		System.out.println(resp.get("data"));
		} 
		catch (Exception e) {
			
			e.printStackTrace();
		}}
}
