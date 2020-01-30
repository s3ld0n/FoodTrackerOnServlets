package org.training.food_tracker.controller.filters;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food_tracker.controller.Servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private static final Logger log = LogManager.getLogger(LocaleFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResp = (HttpServletResponse) servletResponse;

        log.debug("inside LocaleFilter");

        String localeName = servletRequest.getParameter("lang");
        log.debug("set locale name: {}", localeName);

        if (localeName != null) {
            log.debug("setting session attribute 'lang' to locale name");
            httpReq.getSession().setAttribute("lang", localeName);
        }
        filterChain.doFilter(httpReq, httpResp);
    }

    @Override
    public void destroy() {

    }
}
