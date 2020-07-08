package com.readit.core.schedulers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;

@Component(service = Runnable.class, property = { "scheduler.expression=*/50 * * * * ?" })
public class ArticleScheduler implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ArticleScheduler.class);

	@Reference
	ResourceResolverFactory factory;

	ResourceResolver resolver;

	@Override
	public void run() {
		Map<String, Object> props = new HashMap<>();
		props.put(ResourceResolverFactory.SUBSERVICE, "writeService");

		try {
			resolver = factory.getServiceResourceResolver(props);
			Resource articleResource = resolver.getResource("/content/readit/index/articles");
			Page articleRootPage = articleResource.adaptTo(Page.class);
			Iterator<Page> listPages = articleRootPage.listChildren();
			while (listPages.hasNext()) {
				Page articlePage = listPages.next();
				String cal = articlePage.getProperties().get("expireDate", String.class);
				if (null == cal) {
					Resource articleRes = resolver.getResource(articlePage.getPath() + "/jcr:content");
					LOG.info("Article Page" + articleRes.getPath());
					Node articleNode = articleRes.adaptTo(Node.class);
					articleNode.setProperty("expireDate", "Scheduler added");
					articleNode.getSession().save();
				}
			}

		} catch (LoginException | RepositoryException e) {

			e.printStackTrace();
		}

	}

}
