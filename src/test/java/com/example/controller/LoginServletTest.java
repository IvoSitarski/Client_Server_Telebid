package com.example.controller;

import org.example.Models.User;
import org.example.controller.LoginServlet;
import org.example.repository.UserDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServletTest {

    @Mock
    private UserDatabase userDatabase;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LoginServlet loginServlet;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));

        loginServlet = new LoginServlet(userDatabase);
    }

    @Test
    public void testDoPost_ValidLogin() throws IOException, ServletException {
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("password")).thenReturn("password");
        when(userDatabase.findByEmail("test@example.com")).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        loginServlet.doPost(request, response);

        verify(session).setAttribute("user", user);
        verify(response).sendRedirect("/html/home.html");
    }

    @Test
    public void testDoPost_InvalidLogin() throws IOException, ServletException {
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("password")).thenReturn("wrongpassword");
        when(userDatabase.findByEmail("test@example.com")).thenReturn(user);

        loginServlet.doPost(request, response);

        verify(response).sendRedirect("/html/login.html");
    }

    @Test
    public void testDoPost_UserNotFound() throws IOException, ServletException {
        when(request.getParameter("email")).thenReturn("nonexistent@example.com");
        when(request.getParameter("password")).thenReturn("password");
        when(userDatabase.findByEmail("nonexistent@example.com")).thenReturn(null);

        loginServlet.doPost(request, response);

        verify(response).sendRedirect("/html/login.html");
    }
}
