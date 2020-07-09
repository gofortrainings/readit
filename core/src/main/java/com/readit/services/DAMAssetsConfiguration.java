package com.readit.services;


import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "DAM Assets Configuration", description = "DAM Assets configuration for display list of Assets")
public @interface DAMAssetsConfiguration {
	@AttributeDefinition(name = "dam.path", description = "DAM path")
	public String getDamPath() default "/content/dam/readit";

}