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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

import com.adobe.granite.asset.api.Asset;
import com.readit.services.DAMAssetsConfiguration;

@Component(service = Servlet.class, property = { "sling.servlet.paths=/bin/assets",
		"sling.servlet.resourceTypes=/apps/myapp/component/damasset", "sling.servlet.extensions=json",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET })
@Designate(ocd = DAMAssetsConfiguration.class)
public class DAMAssetsServlet extends SlingSafeMethodsServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String damRootPath;

	@Activate
	public void Activate(final DAMAssetsConfiguration aAMAssetsConfiguration) {
		damRootPath = aAMAssetsConfiguration.getDamPath();
	}

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		ResourceResolver resolver = request.getResourceResolver();
		Resource assetResource = resolver.getResource(damRootPath);
		Iterator<Resource> listAssets = assetResource.listChildren();
		JSONArray array = new JSONArray();
		try {
			while (listAssets.hasNext()) {
				JSONObject jsonObj = new JSONObject();
				Asset asset = listAssets.next().adaptTo(Asset.class);
				if (null != asset) {
					jsonObj.put("name", asset.getName());
					jsonObj.put("altText", asset.getValueMap().get("altText", String.class));
					array.put(jsonObj);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		response.setContentType("applications/json");
		response.getWriter().write(array.toString());
	}

}
