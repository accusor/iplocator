package com.appgate.iplocator.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.appgate.iplocator.persistence.conf.DummyDatabase;
import com.appgate.iplocator.utils.GsonUtils;
import com.google.gson.Gson;

@Configuration
public class MvConfig implements WebMvcConfigurer {

	@Autowired
	private Gson gson;

	private static final Logger log = LoggerFactory.getLogger(DummyDatabase.class);
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		log.info("Setting gson converter");
		gson = GsonUtils.getGsonExt();
		converters.add(new GsonHttpMessageConverter(gson));
	}
	
}
