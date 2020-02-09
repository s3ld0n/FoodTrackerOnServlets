package org.training.food.tracker.controller.filter;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private static final Logger LOG = LoggerFactory.getLogger(LocaleFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResp = (HttpServletResponse) servletResponse;

        LOG.debug("inside LocaleFilter");

        String localeName = servletRequest.getParameter("lang");
        LOG.debug("set locale name: {}", localeName);

        if (localeName != null) {
            LOG.debug("setting session attribute 'lang' to locale name");
            httpReq.getSession().setAttribute("lang", localeName);
        }
        filterChain.doFilter(httpReq, httpResp);
    }

    @Override
    public void destroy() {

    }
}
