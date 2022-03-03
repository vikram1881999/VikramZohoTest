package com.vikram.aem.brands.core.services;

import java.util.List;
import java.util.Map;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

public interface UserService {
	public String createUserNode(String country, SlingHttpServletRequest  request);

	public Resource  getUserContactDetails(String country, String user) throws LoginException, RepositoryException;

	List<Map<String, String>> getUsersContact(String country, ResourceResolver resourceResolver);
}
