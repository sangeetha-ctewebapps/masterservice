package com.lic.epgs.mst.usermgmt.exceptionhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUtil {

	private static final Logger LOG = LoggerFactory.getLogger(LoggingUtil.class);

	public static final String LOG_Basic_MSG = "class={} method={} logmessage={}";

	public static void logInfo(final String className, final String methodName, final String logmessage) {
		LOG.info(LoggingUtil.LOG_Basic_MSG, className, methodName, logmessage);
	}

}
