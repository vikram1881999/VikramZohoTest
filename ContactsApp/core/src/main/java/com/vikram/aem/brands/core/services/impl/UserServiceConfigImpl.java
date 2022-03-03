package com.vikram.aem.brands.core.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.breville.aem.brands.core.services.CountriesConfig;
import com.breville.aem.brands.core.services.UserServiceConfig;

@Component( service = UserServiceConfig.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = CountriesConfig.class, factory = true)
public class UserServiceConfigImpl implements UserServiceConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceConfigImpl.class);
	
	
	private String countryCode;
	private String jsonName;
	private String jsonPath;
	private String usersNodeName;
	private String usersNodePath;
	private List<UserServiceConfig> configs;
	
	@Activate
	@Modified
	protected void activate(final CountriesConfig config) {
		countryCode = config.countryCode();
		jsonName = config.jsonName();
		jsonPath = config.jsonPath();
		usersNodeName = config.usersNode();
		usersNodePath = config.usersPath();
	}
	
	@Reference(service = UserServiceConfig.class, cardinality =  ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void bindUserServiceConfig(final UserServiceConfig config) {
		if(configs ==  null) {
			configs = new ArrayList<>();
		}
		configs.add(config);
	}
	
	public void unbindUserServiceConfig(final UserServiceConfig config) {
		configs.remove(config);
	}
	
	@Override
	public String getCountryCode() {
		
		return countryCode;
	}

	@Override
	public String getJsonName() {
		
		return jsonName;
	}

	@Override
	public String getJsonPath() {
		
		return jsonPath;
	}

	@Override
	public String getNodeName() {
		
		return usersNodeName;
	}

	@Override
	public String getNodePath() {
		
		return usersNodePath;
	}

	@Override
	public UserServiceConfig getCountryConfig(String countryCode) {
		for(UserServiceConfig conf : configs) {
			if(StringUtils.equalsIgnoreCase(countryCode, conf.getCountryCode())) {
				LOG.info("\n Config Service -> {}", conf.getCountryCode());
				return conf;
			}
		}
		
		return null;
	}

	@Override
	public List<UserServiceConfig> getAllConfigs() {
		
		return configs;
	}

}
