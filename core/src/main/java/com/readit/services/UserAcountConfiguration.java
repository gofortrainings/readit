package com.readit.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;


@ObjectClassDefinition
public @interface UserAcountConfiguration {
	
	@AttributeDefinition(name = "user.name", description = "User Name")
	public String getUsername() default "";
	
	@AttributeDefinition(name = "account.name", description = "Account Name")
	public String getAccountname() default "Default Account name";
	
	@AttributeDefinition(name = "account.type", description = "Account Type")
	public boolean getAccountType() default true;

}
