package com.appgate.iplocator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

	static Logger logger = LoggerFactory.getLogger(LoggingController.class);
	
	@RequestMapping("/")
	public String index() {
		logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");

        return "Check out the Logs to see the output...";
	}
	
	public static void setError(String msg) {
		logger.error(msg);
	}
	
	public static void setError(String msg, Exception e) {
		logger.error(msg, e);
	}
	
	public static void setInfo(String msg) {
		logger.info(msg);
	}
	
	public static void setInfo(String msg, Object object) {
		logger.info(msg);
	}
	
}
