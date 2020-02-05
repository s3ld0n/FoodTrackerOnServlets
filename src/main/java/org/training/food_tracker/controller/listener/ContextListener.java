package org.training.food_tracker.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;

public class ContextListener implements ServletContextListener {

    @Override public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute("loggedUsers", new HashSet<String>());
    }
}
