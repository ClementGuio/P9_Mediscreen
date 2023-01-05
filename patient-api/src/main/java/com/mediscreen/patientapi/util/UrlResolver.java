package com.mediscreen.patientapi.util;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import org.springframework.stereotype.Component;

@Component
public class UrlResolver {

	public String resolveHostname(String hostname, String port) throws UnknownHostException {
		
		String url = "http://"+hostname+":"+port;
		String strIp = "";
		try{
			// Fetch IP address by getByName()
			InetAddress ip = InetAddress.getByName(new URL(url).getHost());
			// Print the IP address
			strIp = ip.toString().split("/")[1];
			System.out.println("Public IP Address of: " + strIp);
		}catch (MalformedURLException e) {
			// It means the URL is invalid
			System.out.println("Invalid URL");
		}
		return strIp;
	}
	
	public String buildResolvedUrl(String hostname, String port) throws UnknownHostException {
		
		String resolvedHost = resolveHostname(hostname, port);
		
		return "http://"+resolvedHost+":"+port;
	}
}