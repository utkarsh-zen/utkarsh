package com.demo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.visural.common.IOUtil;

public class Demo {
public static void main(String[] args) {
	JSONObject resp = null;
	String userid="me";
	String accessToken="CAACEdEose0cBAIslmUv1yVBZBDDKqdhSZCFJCMwFZA8twdtAPnsenpprf2grAmPgSRa3A5HOZBJmx3KDxjimkerN1g9CQiymeyE05ILqGJrTkZATtILhIVc0GlrZCH2pgum5eU9bJUh0AI9ZALMyockbeO1aeYebijhKHm8BPBY8rOhxiGnFj4tcukU9pFGHw7xX3ybbb4sZC08nYFxDB7zw";
	try {
		/*resp = new JSONObject(IOUtil.urlToString(
				new URL("https://graph.facebook.com/v2.2/" + userid + "?access_token="+accessToken+"&fields=id,name,friends.limit(100){id,first_name}")));
	*/
		resp = new JSONObject(IOUtil.urlToString(new URL(
				"https://graph.facebook.com/" + userid + "/friends?limit=1000"+"&access_token="+ accessToken)));
		
		
		System.out.println(new Gson().toJson(resp));
	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
}
}
