package com.vikram.aem.brands.core.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.breville.aem.brands.core.services.UtilService;

@Model(adaptables = { SlingHttpServletRequest.class,  Resource.class },  adapters = AddUser.class,
	resourceType = AddUserImpl.RESOURCE_TYPE,
	defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
		)
public class AddUserImpl implements AddUser {
	
	private static final Logger LOG = LoggerFactory.getLogger(AddUserImpl.class);
	final protected static String  RESOURCE_TYPE = "breville-brands/components/form/ContactADD";
	
	@SlingObject
	Resource resource;
	
	@OSGiService
	UtilService utilService;
	
	@ValueMapValue
	private String actionType;

	@Override
	public String getActionType() {
		return actionType;
	}

	@PostConstruct
	protected void  init() throws LoginException {
		actionType = utilService.getActionUrl(resource);
	}
}
