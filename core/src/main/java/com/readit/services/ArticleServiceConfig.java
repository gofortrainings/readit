package com.readit.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Article List Configuration", description = "Article List configuration for display list of Articles")
public @interface ArticleServiceConfig {
	@AttributeDefinition(name = "article.path", description = "Articles path")
	public String getArticlePath();

}
