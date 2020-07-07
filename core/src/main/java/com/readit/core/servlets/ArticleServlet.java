package com.readit.core.servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.wcm.api.Page;
import com.readit.services.ArticleBean;
import com.readit.services.ArticleService;

@Component(service = Servlet.class, property = { "sling.servlet.paths=/gofortrainings/servlet/article",
		"sling.servlet.resourceTypes=/apps/readit/component/article", "sling.servlet.selectors=article",
		"sling.servlet.extensions=json", "sling.servlet.methods=" + HttpConstants.METHOD_GET })
public class ArticleServlet extends SlingSafeMethodsServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Reference
	ArticleService articleService;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		ResourceResolver resolver = request.getResourceResolver();
		Resource articleResource = resolver.getResource(articleService.getArticlePath());
		Page articleRootPage = articleResource.adaptTo(Page.class);
		Iterator<Page> listPages = articleRootPage.listChildren();
		JSONArray array = new JSONArray();
		try {
			while (listPages.hasNext()) {
				JSONObject jsonObj = new JSONObject();
				Page articlePage = listPages.next();

				jsonObj.put("title", articlePage.getTitle());

				jsonObj.put("path", articlePage.getPath());
				jsonObj.put("imagePath",
						articlePage.getProperties().get("imagePath", "/content/dam/readit/image_2.jpg"));
				jsonObj.put("text", articlePage.getProperties().get("text", "Dummy Text"));
				// bea
				array.put(jsonObj);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("applications/json");
		response.getWriter().write(array.toString());
	}

}
