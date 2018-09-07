package com.sims.websecurityconfig;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

@WebListener
public class SessionListener implements HttpSessionListener{

	private static final Logger LOGGER = Logger.getLogger(SessionListener.class);
	private static final int INACTIVE_INTERVAL = 10;
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
		LOGGER.info("HttpSessionListener.sessionCreated(): invoked");
		HttpSession session = se.getSession();
		session.setMaxInactiveInterval(INACTIVE_INTERVAL);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		LOGGER.info("HttpSessionListener.sessionDestroyed(): invoked");
	}

}
