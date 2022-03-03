package com.vikram.aem.brands.core.models;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;

import com.breville.aem.brands.core.services.UserService;
import com.day.cq.wcm.api.Page;

@Model(
		adaptables = {SlingHttpServletRequest.class, Resource.class},
		adapters =  ContactList.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContactListImpl implements ContactList {
	
	@OSGiService
	UserService userService;
	
	@Inject
	ResourceResolver resolver;
	
	@ScriptVariable
	Page page;
	
	private String country;

	@Override
	public List<Map<String, String>> getContactsList() {
		// TODO Auto-generated method stub
		List<Map<String, String>> users = userService.getUsersContact(country, resolver);
		return users;
	}
	
	@PostConstruct
	public void init() {
		country =  page.getPath().split("/")[3];
	}

}
