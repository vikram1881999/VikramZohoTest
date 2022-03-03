package com.vikram.aem.brands.core.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.breville.aem.brands.core.constant.LoginServiceConstants;
import com.breville.aem.brands.core.services.UserService;
import com.breville.aem.brands.core.services.UserServiceConfig;
import com.breville.aem.brands.core.utils.ServiceUtil;


@Component(service = UserService.class,  name = "UserSerice", immediate = true)
public class UserServiceImpl implements UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Reference
	UserServiceConfig userServiceConfig;
	
	@Reference
	ResourceResolverFactory resourceResolverFactory;
	
	@Override
	public String createUserNode(String country, SlingHttpServletRequest request) {
		String  nodeCreated = StringUtils.EMPTY;
		try {
			UserServiceConfig config = userServiceConfig.getCountryConfig(country);
			String nodeLocation = config.getNodePath() + "/" + config.getNodeName();
			ResourceResolver resourceResolver = request.getResourceResolver();
			Session session =  resourceResolver.adaptTo(Session.class);
			if(session.nodeExists(nodeLocation)) {
				nodeCreated = addUser(session,request,nodeLocation);
			}
			else {
				addParentNode(session, config);
				nodeCreated = addUser(session, request, nodeLocation);
			}
		} catch(Exception e) {
			LOG.error("\n Error while creating node- > {} ", e.getMessage());
		}
		return nodeCreated;
	}

	private String addParentNode(Session session, UserServiceConfig config) {
		try {
			if(session.nodeExists(config.getNodePath())) {
				Node pParentNode  = session.getNode(config.getNodePath());
				Node parentNode = pParentNode.addNode(config.getNodeName(), LoginServiceConstants.USERNODE_TYPE);
				session.save();
				return parentNode.getName();
			}
		}catch(Exception  e) {
			LOG.error("\n Error while creating parent Node");
		}
		return null;
	}

	private String addUser(Session session, SlingHttpServletRequest request, String nodeLocation) {
		try {
			Node parentNode = session.getNode(nodeLocation);
			if(!parentNode.hasNode(getNodeName(request))) {
				Node userNode = parentNode.addNode(getNodeName(request), LoginServiceConstants.USERNODE_TYPE);
				userNode.setProperty("name", ServiceUtil.getRequestParameter(request,"name"));
				userNode.setProperty("phone", ServiceUtil.getRequestParameter(request, "phone"));
				userNode.setProperty("email", ServiceUtil.getRequestParameter(request, "email"));
				session.save();
				return userNode.getName() + "added. ";
			} else {
				return "this contact already exists";
			}
		} catch(Exception e) {
			LOG.error("Error While Creating User Node");
		}
		return null;
	}

	private String getNodeName(SlingHttpServletRequest request) {
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String userNodeName = name+"-"+phone+"-"+email;
		return userNodeName;
	}
	
	Map<String, Object> authInfo = new HashMap<String, Object>();
	
	@Override
	public Resource getUserContactDetails(String country, String user) throws LoginException, RepositoryException {
		UserServiceConfig config = userServiceConfig.getCountryConfig(country);
		String newLocation = config.getNodePath() + "/" + config.getNodeName();
		Repository repository = JcrUtils.getRepository("http://localhost:4502/crx/de/index.jsp#");
		Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
		try {
			authInfo.put(JcrResourceConstants.AUTHENTICATION_INFO_SESSION, session);
			ResourceResolver resourceResolver = resourceResolverFactory.getResourceResolver(authInfo);
			Resource authorDetails = resourceResolver.getResource(newLocation+"/"+user);
			return authorDetails;
		} catch (Exception e) {
			LOG.error("Ocurred exception -{}" , e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<Map<String,String>> getUsersContact(final String country, ResourceResolver resourceResolver) {
		List<Map<String,String>> contactList =  new ArrayList<Map<String,String>>();
		UserServiceConfig config = userServiceConfig.getCountryConfig(country);
		String nodeLoaction = config.getNodePath() +"/" + config.getNodeName();
		try {
			Iterator<Resource> users = resourceResolver.getResource(nodeLoaction).listChildren();
			while(users.hasNext()) {
				Resource resource = users.next();
				Map<String,String> user = new HashMap<>();
				ValueMap prop =  resource.getValueMap();
				if(StringUtils.isNotBlank(prop.get("name", String.class))) {
					user.put("name", prop.get("name",String.class));
				}
				if(StringUtils.isNotBlank(prop.get("phone", String.class))) {
					user.put("name", prop.get("name",String.class));
				}
				if(StringUtils.isNotBlank(prop.get("email", String.class))) {
					user.put("name", prop.get("eamil",String.class));
				}
				contactList.add(user);
			}
		}catch(Exception e) {
			LOG.error("Exception ->{}", e.getMessage());
		}
		
		return contactList;
	}
	

}
