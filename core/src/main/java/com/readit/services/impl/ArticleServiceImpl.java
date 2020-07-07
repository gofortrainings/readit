package com.readit.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;

import com.day.cq.wcm.api.Page;
import com.readit.services.ArticleBean;
import com.readit.services.ArticleService;
import com.readit.services.ArticleServiceConfig;

@Component(service = ArticleService.class, immediate = true)
@Designate(ocd = ArticleServiceConfig.class)
public class ArticleServiceImpl implements ArticleService {

	private String articlePath;

	@Reference
	ResourceResolverFactory resolverFactory;

	private ResourceResolver resolver;

	@Activate
	public void Activate(final ArticleServiceConfig articleServiceConfig) {
		articlePath = articleServiceConfig.getArticlePath();
	}

	List<ArticleBean> articleList = new ArrayList<ArticleBean>();

	@Override
	public List<ArticleBean> getListOfArticles() {
		Map<String, Object> props = new HashMap<>();
		props.put(ResourceResolverFactory.SUBSERVICE, "writeService");

		try {
			resolver = resolverFactory.getServiceResourceResolver(props);
			Resource articleResource = resolver.getResource(articlePath);
			Page articleRootPage = articleResource.adaptTo(Page.class);
			Iterator<Page> listPages = articleRootPage.listChildren();
			while (listPages.hasNext()) {
				ArticleBean bean = new ArticleBean();
				Page articlePage = listPages.next();
				bean.setTitle(articlePage.getTitle());
				bean.setPath(articlePage.getPath());
				bean.setImagePath(articlePage.getProperties().get("imagePath", "/content/dam/readit/image_2.jpg"));
				bean.setText(articlePage.getProperties().get("text", "Dummy Text"));
				//bea
				articleList.add(bean);
			}
		} catch (LoginException e) {

			e.printStackTrace();
		}

		return articleList;
	}
	
	@Override
	public String getArticlePath() {
		return articlePath;
	}
}
