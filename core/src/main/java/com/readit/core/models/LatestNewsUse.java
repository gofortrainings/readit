package com.readit.core.models;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import com.adobe.cq.sightly.WCMUsePojo;

public class LatestNewsUse extends WCMUsePojo {

	private String image;
	private String text;
	private String title;
	private String date;
	private String path;
	private String allowedTemplates;


	@Override
	public void activate() throws Exception {
		ValueMap properties = getProperties();
		getResourceResolver();
		ValueMap pageProperties = getPageProperties();
		image = properties.get("image", getCurrentStyle().get("image", String.class));
		text = properties.get("text", "Dummy Text");
		title = properties.get("title", pageProperties.get("jcr:title", String.class));
		date = properties.get("date", String.class);
		Node node = getResource().adaptTo(Node.class);
		
		if(null != node) {
			node.setProperty("title", "Test title 12345");
			node.getSession().save();
		}
		if(getWcmMode().isPreview())
			allowedTemplates = getRequest().getCookie("wcmmode").getValue();
		
		

	
		
	}

	public String getImage() {
		return image;
	}

	public String getText() {
		return text;
	}

	public String getTitle() {
		return title;
	}

	public String getDate() {
		return date;
	}
	

	public String getPath() {
		return path;
	}

	public String getAllowedTemplates() {
		return allowedTemplates;
	}
}
