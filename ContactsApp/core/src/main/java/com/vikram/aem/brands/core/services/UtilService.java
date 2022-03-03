package com.vikram.aem.brands.core.services;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;

public interface UtilService {

	public String getActionUrl(Resource resource) throws LoginException;
}
