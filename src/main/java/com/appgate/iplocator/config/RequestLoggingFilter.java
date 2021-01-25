package com.appgate.iplocator.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appgate.iplocator.dto.CallLog;
import com.appgate.iplocator.persistence.CallLogRepository;
import com.appgate.iplocator.rest.dto.Logged;

@Logged
@Component
public class RequestLoggingFilter implements ContainerRequestFilter {

	@Context HttpServletRequest oRequest;
	
	@Autowired
	private CallLogRepository repository;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		CallLog call = new CallLog();
		call.setMethod(requestContext.getUriInfo().getPath());
		call.setDate(new Date());
		call.setRemoteAddress(oRequest.getRemoteAddr());
		repository.save(call);
	}

}
