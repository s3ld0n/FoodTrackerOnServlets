package org.training.food_tracker.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food_tracker.controller.command.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(Servlet.class.getName());

    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig) {

        servletConfig.getServletContext().setAttribute("loggedUsers", new HashSet<String>());

        commands.put("logout", new LogOutCommand());
        commands.put("login", new LoginCommand());
        commands.put("login_page", new LoginPageCommand());
        commands.put("exception", new ExceptionCommand());
        commands.put("registration_page", new RegistrationPageCommand());
        commands.put("registration", new RegistrationCommand());

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("inside doGet()");
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("inside doPost()");
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("---> processRequest()");

        log.trace("getting path from request url");

        String path = request.getRequestURI();
        log.trace("path: ", path);

        log.trace("cutting out the command name from path");
        path = path.replaceAll(".*/", "");

        log.trace("command name: ", path);
        log.trace("getting corresponding command or redirecting to index");
        Command command = commands.getOrDefault(path, (r) -> "jsp/index.jsp");


        log.trace("command was found: " + command.getClass().getName() + "/Getting url");
        String url = command.execute(request);

        log.trace("url: ", url);
        log.trace("getting requestDispatcher and forwarding url");


        if (url.contains("redirect:")) {
            url = url.replace("redirect:", "");
        }
        request.getRequestDispatcher(url).forward(request, response);
        log.debug("processRequest() --->");
    }
}
