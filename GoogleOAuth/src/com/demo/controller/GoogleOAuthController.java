package com.demo.controller;


import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo.utility.Data;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.code.linkedinapi.schema.Person;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

@Controller
@RequestMapping(value="/")
public class GoogleOAuthController {

	private static final String CLIENT_ID = "1014116704783-61uvsdbhvsdku8eustke5hml2jmr7i.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "o37usQ_AWjhbw7JmgvwfEHJcF";
	private static final String CALLBACK_URI = "http://localhost:8080/GoogleOAuth/getBackUrl";

	private static final Iterable<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
	private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
	//private static final String PEOPLE_LIST_URL = "https://www.googleapis.com/plus/v1/people/me/people/visible?key=https://www.googleapis.com/plus/v1/people/me/people/connected?key=AIzaSyDDw25wmpLTl55YXjIwXyiFwQmfsJFbyZE";
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	private String stateToken;
	private GoogleAuthorizationCodeFlow flow;


	@PostConstruct
	public void init()
	{
		
		flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
				JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPE).build();

		generateStateToken();
	}

	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView home(HttpSession session) {
		
		ModelAndView model=new ModelAndView("index");
		model.addObject("url",buildLoginUrl());
		return model;

		
	}
	
	public String  buildLoginUrl() {

		GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
		
		return url.setRedirectUri(CALLBACK_URI).setState(stateToken).build();
	}

	
	
	
	@RequestMapping(value="/getBackUrl",method=RequestMethod.GET)
	public ModelAndView getBackUrl(@RequestParam(value="code",required=true) String code,@RequestParam(value="state",required=true) String state) {
		
		ModelAndView model=new ModelAndView("userdata");
		model.addObject("userdata",getUserInfoJson(code));
		return model;

		
	}
	
	
	
	
	
	public  Data  getUserInfoJson(String authCode)  {
		 String jsonIdentity=null;
		 Data d=null;
		try
		{
			final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI).execute();
			final Credential credential = flow.createAndStoreCredential(response, null);
			final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
			// Make an authenticated request
			final GenericUrl url = new GenericUrl(USER_INFO_URL);
			final HttpRequest request = requestFactory.buildGetRequest(url);
			request.getHeaders().setContentType("application/json");
			 jsonIdentity = request.execute().parseAsString();
			System.out.println(jsonIdentity);
			 ObjectMapper mapper = new ObjectMapper();
			  d = mapper.readValue(jsonIdentity, Data.class);
			  
			
			  
		}
		catch(Exception e)
		{
e.printStackTrace();
		}
		return d;

	}


	private void generateStateToken(){

		SecureRandom sr1 = new SecureRandom();

		stateToken = "google;"+sr1.nextInt();

	}

	
	public String getStateToken(){
		return stateToken;
	}
}
