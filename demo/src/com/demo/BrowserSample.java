package com.demo;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class BrowserSample {
	  public static void main(String[] args) {
		  URL oracle = null;
		try {
			oracle = new URL("https://www.linkedin.com/contacts/?filter=recent&trk=nav_responsive_tab_network#?filter=recent&trk=nav_responsive_tab_network");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	        BufferedReader in = null;
			try {
				in = new BufferedReader(
				new InputStreamReader(oracle.openStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        String inputLine;
	        try {
				while ((inputLine = in.readLine()) != null)
				    System.out.println(inputLine);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        	try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }
}
