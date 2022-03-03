package com.vikram.aem.brands.core.servlets;


import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import com.breville.aem.brands.core.constant.LoginServiceConstants;
import com.breville.aem.brands.core.services.UserService;
import com.breville.aem.brands.core.utils.ServiceUtil;

@Component( service = Servlet.class)
@SlingServletResourceTypes(
		methods = {HttpConstants.METHOD_POST},
		resourceTypes = LoginServiceConstants.RESOURCE_TYPE,
		selectors = {LoginServiceConstants.ADDUSER_SELECTORS},
		extensions = {LoginServiceConstants.ADDUSER_EXTENSION}
		)
public class UserServlet extends SlingAllMethodsServlet {
	private static final Logger LOG = LoggerFactory.getLogger(UserServlet.class);
	
	@Reference
	UserService userService;
	
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		try {
			String resp = userService.createUserNode(ServiceUtil.getCountry(request), request);
			response.getWriter().write(resp);
		}
		catch(Exception e) {
			LOG.info("\n Error in request {}, ", e.getMessage());
		}
	}

}
