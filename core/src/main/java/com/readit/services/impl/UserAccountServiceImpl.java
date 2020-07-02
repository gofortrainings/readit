package com.readit.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.readit.services.UserAccountService;
import com.readit.services.UserAcountConfiguration;

@Component(service = UserAccountService.class, immediate = true)
@Designate(ocd=UserAcountConfiguration.class)
public class UserAccountServiceImpl implements UserAccountService {

	private static final Logger LOG = LoggerFactory.getLogger(UserAccountServiceImpl.class);
	
	private String userName;
	private String accountName;
	private boolean accountType;

	@Activate
	private void Activate(final UserAcountConfiguration userAcountConfiguration) {
		userName = userAcountConfiguration.getUsername();
		accountName = userAcountConfiguration.getAccountname();
		accountType = userAcountConfiguration.getAccountType();
		LOG.info("UserAccountService Activate Method");
	}

	@Deactivate
	private void Deactivate() {
		LOG.info("UserAccountService Deactivate Method");
	}
	
	@Reference
	SlingSettingsService slingSetting;
	
	@Reference
	ResourceResolverFactory resolverFactory;
	ResourceResolver resolver;
	
	public String getUserName() {
		Map<String, Object> props = new HashMap<>(); 
		props.put(ResourceResolverFactory.SUBSERVICE, "writeService");
		try {
			resolver = resolverFactory.getServiceResourceResolver(props);
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		if(slingSetting.getRunModes().contains("author")) {
			//return resolver.getResource("/content/readit/index").adaptTo(Page.class).getTitle();
			return userName.concat(accountName);
		}
		return "Not Author GoForTrainings";
	}

}
