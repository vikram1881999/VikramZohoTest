package com.vikram.aem.brands.core.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.breville.aem.brands.core.constant.ApplicationConstants;
import com.breville.aem.brands.core.services.SessionAccessor;
import com.breville.aem.brands.core.services.UtilService;

@Component(
		service = UtilService.class,
		immediate  = true,
		name = "UtilService"
		)
public class UtilServiceImpl implements UtilService {
	private static final Logger LOG = LoggerFactory.getLogger(UtilServiceImpl.class);
	
	@Reference
	SessionAccessor accessor;

	@Override
	public String getActionUrl(Resource resource) throws LoginException {
		String actionURl = StringUtils.EMPTY;
		try {
			ResourceResolver resourceResolver = accessor.getServiceResourceResolver(ApplicationConstants.SubService.BREVILLESERVICE);
			String actionType = resource.getValueMap().get("actionType", String.class);
			String selector = resourceResolver.getResource(actionType).getValueMap().get("selector",String.class);
			actionURl = resource.getPath()+ " . "+selector+".json";
		}catch(Exception e) {
			LOG.info("\n Error while getting action URL - {} ", e.getMessage());
		}
		return actionURl;
	}
	
	

}
