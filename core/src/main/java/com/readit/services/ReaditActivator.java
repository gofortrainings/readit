package com.readit.services;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReaditActivator implements BundleActivator {

	private Logger Log = LoggerFactory.getLogger(getClass());

	@Override
	public void start(BundleContext context) throws Exception {
		Log.info("Start Method of readit Bundle");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Log.info("Stop Method of readit Bundle");

	}

}