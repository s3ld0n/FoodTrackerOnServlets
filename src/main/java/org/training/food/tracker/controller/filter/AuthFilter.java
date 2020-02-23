package org.training.food.tracker.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.UserCredentials;
import org.training.food.tracker.controller.servlet.LogoutServlet;
import org.training.food.tracker.dao.Const;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

public class AuthFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutServlet.class.getName());

    @Override public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        LOG.trace("doFilter()");
        LOG.trace("doFilter() :: disabling browsers cache");
        setNoCache(response);

        LOG.trace("doFilter() :: getting uri from request");
        String uri = request.getRequestURI();

        LOG.trace("doFilter() :: getting session request");
        HttpSession session = request.getSession();

        LOG.trace("doFilter() :: getting userCredentials from session");
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");

        if (userCredentials == null && authIsNotRequired(uri)) {
            LOG.trace("doFilter() :: user is not logged and requested page that doesn't need an auth.\nAllowing");
            chain.doFilter(servletRequest,servletResponse);
            return;
        }
        else if (userCredentials == null) {
            LOG.trace("doFilter() :: user is not logged and requested page that needs an auth. Redirecting to /login");
            response.sendRedirect("/login");
            return;
        } else if (authIsNotRequired(uri)) {
            LOG.trace("doFilter() :: user is logged and requested page that doesn't need an auth. Signing him out.");
            response.sendRedirect("/logout");
            return;
        } else if (!userCredentials.getRole().equalsIgnoreCase("admin") && uri.contains("admin")) {
            LOG.trace("doFilter() :: user requested admins page but is not an admin. Giving him 403.");
            send403ErrorPage(request, response);
            return;
        } else if (!userCredentials.getRole().equalsIgnoreCase("user")
                         && uri.contains("user")
                         && !uri.contains("users")) {
            LOG.trace("doFilter() :: user requested user's page but is an admin. Giving him 403.");
            send403ErrorPage(request, response);
            return;
        }

        chain.doFilter(servletRequest,servletResponse);
    }

    private void send403ErrorPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/errors/403.jsp").forward(request, response);
    }

    private boolean authIsNotRequired(String url) {
        if (url.equals("/")) {
            return true;
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

        String[] validNonAuthenticationUrls = resourceBundle.getString(Const.URL_AUTH_NOT_REQUIRED).split(", ");
        for(String validUrl : validNonAuthenticationUrls) {
            if (url.contains(validUrl)) {
                return true;
            }
        }
        return false;
    }

    private void setNoCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
    }

    @Override public void destroy() {

    }
}
