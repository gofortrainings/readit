package com.readit.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.readit.services.UserAccountService;

@Model(adaptables = Resource.class, resourceType="readit/components/content/latest-news", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = "jackson", extensions = "json", selector = "news", options = {
		@ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "true")
})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
//@JsonIgnoreProperties({"text", "date"})
public class LatestNewsModel {
	@ValueMapValue(via = "resource")
	private String path;
	@ValueMapValue(name = "sling:resourceType")
	private String resourceType;
	@ValueMapValue
	private String text;
	@ValueMapValue
	private String date;
	@ValueMapValue
	private String image;
	/*@ValueMapValue
	@Default(values = "dumy Text")*/
	//private String title;
	
	@OSGiService
	UserAccountService userAccountService;
	
	@JsonGetter("sling:resourceType")
	public String getResourceType() {
		return resourceType;
	}

	public String getPath() {
		return path;
	}

	public String getText() {
		return text;
	}

	public String getDate() {
		return date;
	}

	public String getImage() {
		return image;
	}

	public String getTitle() {
		return userAccountService.getUserName();
	}

}
