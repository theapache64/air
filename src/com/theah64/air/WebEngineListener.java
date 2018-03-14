package com.theah64.air;

import com.theah64.webengine.utils.WebEngineConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by theapache64 on 1/11/17.
 */
@WebListener
public class WebEngineListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        WebEngineConfig.init("jdbc/air", "jdbc/air", false, "http://localhost:8080/air",
                "http://theapache64.com/air");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
