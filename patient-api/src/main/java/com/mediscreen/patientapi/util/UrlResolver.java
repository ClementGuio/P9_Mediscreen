package com.mediscreen.patientapi.util;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UrlResolver {

	Logger logger = LoggerFactory.getLogger(UrlResolver.class);
	
	public String resolveHostname(String hostname, String port) throws UnknownHostException {
		
		String url = "http://"+hostname+":"+port;
		String strIp = "";
		try{
			InetAddress ip = InetAddress.getByName(new URL(url).getHost());
			strIp = ip.toString().split("/")[1];
		}catch (MalformedURLException e) {
			logger.debug("Invalid URL :"+url);
		}
		return strIp;
	}
	
	public String buildResolvedUrl(String hostname, String port) throws UnknownHostException {
		
		String resolvedHost = resolveHostname(hostname, port);
		
		return "http://"+resolvedHost+":"+port;
	}
}