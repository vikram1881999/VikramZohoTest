package com.vikram.aem.brands.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Countries Json Configuration", 
		description = "contries json factory configuration"
		)
public @interface CountriesConfig {
	@AttributeDefinition(
			name = "Country Code",
			description = "Add Country Code",
			type = AttributeType.STRING
			)
	public String countryCode() default "us";
	
	@AttributeDefinition(
			name = "JSON Name",
			description = "Name for Json file",
			type = AttributeType.STRING
			)
	public String jsonName() default "us_users.json";
	
	@AttributeDefinition(
			name = "JSON Path",
			description = "Path for json File",
			type = AttributeType.STRING
			)
	public String jsonPath() default "/content/breville/us";
	
	@AttributeDefinition(
			name  = "User Node Path",
			description = "Path for Parent User Node",
			type = AttributeType.STRING
			)
	public String usersPath() default "/content/breville/us";
	
	@AttributeDefinition(
			name = "USER Node Name",
			description = "Parent node name for Users",
			type = AttributeType.STRING
			)
	public String usersNode() default "us-Users";

	
}
