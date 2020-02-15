package org.training.food.tracker.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.servlet.LogoutServlet;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutServlet.class.getName());

    @Override public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        LOG.trace("Setting content-type, encoding");
        servletResponse.setContentType("text/html");
        servletResponse.setCharacterEncoding("UTF-8");
        servletRequest.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override public void destroy() {
    }
}
