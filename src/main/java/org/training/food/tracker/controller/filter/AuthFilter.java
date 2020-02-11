package org.training.food.tracker.controller.filter;

import com.sun.deploy.net.HttpResponse;
import org.training.food.tracker.controller.UserCredentials;
import org.training.food.tracker.dao.Const;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

public class AuthFilter implements Filter {

    @Override public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        setNoCache(response);

        String uri = request.getRequestURI();
        HttpSession session = request.getSession();

        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");

        if (uri.contains("webjars")) {
            chain.doFilter(servletRequest,servletResponse);
            return;
        }

        if (userCredentials == null && authIsNotRequired(uri)) {
            chain.doFilter(servletRequest,servletResponse);
            return;

        } else if (userCredentials == null) {
            response.sendRedirect("/login");
            return;
        } else if (authIsNotRequired(uri)) {
            response.sendRedirect("/logout");
            return;
        } else if (!userCredentials.getRole().equalsIgnoreCase("admin") && uri.contains("admin")) {
            sendErrorPage(request, response);
            return;
        } else if (!userCredentials.getRole().equalsIgnoreCase("user") && uri.contains("user")) {
            sendErrorPage(request, response);
            return;
        }

        chain.doFilter(servletRequest,servletResponse);
    }

    private void sendErrorPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
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
