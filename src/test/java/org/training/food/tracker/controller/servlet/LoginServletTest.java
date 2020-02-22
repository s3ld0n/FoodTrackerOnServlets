package org.training.food.tracker.controller.servlet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.training.food.tracker.model.User;
import org.training.food.tracker.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest {

    private static final String USER_LOGIN = "user login";
    private static final String USER_PASSWORD = "user password";

    @InjectMocks
    private LoginServlet loginServlet;

    @Mock private UserService userService;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private User user;

    @Before
    public void setUp() {
        when(request.getParameter("login")).thenReturn(USER_LOGIN);
        when(request.getParameter("password")).thenReturn(USER_PASSWORD);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void valid() {}

}
