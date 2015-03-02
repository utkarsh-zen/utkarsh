package com.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo.scrapper.DataCollectionContext;
import com.demo.scrapper.FacebookDataCollector;
import com.demo.scrapper.ICommunityUser;
import com.demo.scrapper.PrintAllAttributes;
import com.google.gson.Gson;
import com.visural.common.IOUtil;


@Controller
@RequestMapping(value="/")
public class FacebookController {
	public static final String FB_APP_ID = "779705588786457";
	public static final String FB_APP_SECRET = "bca09484508f5c67385037cc4f73403d";
	public static final String REDIRECT_URI = "http://localhost:8080/FacebookOAuth/getBackUrl";
	static String accessToken = "";
	
	public String getFBAuthUrl() {
		String fbLoginUrl = "";
		try {
			fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
					+ FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(REDIRECT_URI, "UTF-8")
					+ "&scope=email";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fbLoginUrl;
	}

	public String getFBGraphUrl(String code) {
		String fbGraphUrl = "";
		try {
			fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
					+ "client_id=" + FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(REDIRECT_URI, "UTF-8")
					+ "&client_secret=" + FB_APP_SECRET + "&code=" + code;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbGraphUrl;
	}

	
	public String getAccessToken(String code) {
		if ("".equals(accessToken)) {
			URL fbGraphURL;
			try {
				fbGraphURL = new URL(getFBGraphUrl(code));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection fbConnection;
			StringBuffer b = null;
			try {
				fbConnection = fbGraphURL.openConnection();
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(
						fbConnection.getInputStream()));
				String inputLine;
				b = new StringBuffer();
				while ((inputLine = in.readLine()) != null)
					b.append(inputLine + "\n");
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Unable to connect with Facebook "
						+ e);
			}

			accessToken = b.toString();
			if (accessToken.startsWith("{")) {
				throw new RuntimeException("ERROR: Access Token Invalid: "
						+ accessToken);
			}
		}
		return accessToken;
	}
	
	@RequestMapping(value="/getBackUrl",method=RequestMethod.GET)
	public ModelAndView getbackUrl(@RequestParam(value="code",required=true) String code)
	{
		
		String accessToken = getAccessToken(code);

		FacebookGraph fbGraph = new FacebookGraph(accessToken);
		String graph = fbGraph.getFBGraph();
		Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
		System.out.println(new Gson().toJson(fbProfileData));
		ModelAndView model=new ModelAndView("userdata");
		return model;
	}
	
	
	@RequestMapping(value="/",method=RequestMethod.GET)
		public ModelAndView getHome(HttpSession session) 
		{
		ModelAndView model=new ModelAndView("index");
		model.addObject("url", getFBAuthUrl());
		
	return model;
			}

	@RequestMapping(value="/home",method=RequestMethod.GET)
	public ModelAndView Home() {
		
		ModelAndView model=new ModelAndView("OAuthJSSample");
	
		return model;

		
	}
	@RequestMapping(value="/submitFb",method=RequestMethod.POST)
	public ModelAndView submitFb(@RequestParam(value="useremail",required=true) String email,
								@RequestParam(value="accessToken",required=true) String token,
								@RequestParam(value="firstName",required=true) String firstName,
								@RequestParam(value="lastName",required=true) String lastName,
								@RequestParam(value="userid",required=true) String userid) {
		
		ModelAndView model=new ModelAndView("userdata");
		
		FacebookDataCollector collector = new FacebookDataCollector();
		DataCollectionContext ctx = new DataCollectionContext();
		ctx.setUsername(userid);
		ctx.setPassword(token);
		ctx.setRIN(12312);
		ctx.setFlc(true);
		ICommunityUser data = collector.collectUserData(ctx);
		try {
			PrintAllAttributes.printAll(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	
		
	
		return model;
		
	}


	
	
	
}
