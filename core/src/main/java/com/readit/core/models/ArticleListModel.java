package com.readit.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.readit.services.ArticleBean;
import com.readit.services.ArticleService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleListModel {
	
	@OSGiService
	ArticleService articleService;

	public List<ArticleBean> getArticle() {
		return articleService.getListOfArticles();
	}

}
