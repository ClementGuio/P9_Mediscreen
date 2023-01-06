package com.mediscreen.docnoteapi.configuration;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mediscreen.docnoteapi.util.UrlResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	Logger logger = LoggerFactory.getLogger(WebConfig.class);
	
	@Autowired
	private UrlResolver resolver;
	
	@Value("${host.ui}")
	private String hostUi;
	
	@Value("${port.ui}")
	private String portUi;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
        try {
			registry.addMapping("/**")
			.allowedMethods("GET","POST")
			.allowedOrigins(resolver.buildResolvedUrl(hostUi, portUi));
		} catch (UnknownHostException e) {
			logger.debug(e.getLocalizedMessage());
		}
    }
}