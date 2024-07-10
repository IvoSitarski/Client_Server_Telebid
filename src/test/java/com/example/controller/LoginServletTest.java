package com.example.controller;

import org.example.Models.User;
import org.example.repository.UserDatabase;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.example.controller.LoginServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class LoginServletTest {

    @InjectMocks
    private LoginServlet loginServlet;

    @Mock
    private UserDatabase userDatabase;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoPost_ValidUser() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("password")).thenReturn("password");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        when(userDatabase.findByEmail(anyString())).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        loginServlet.doPost(request, response);

        verify(session).setAttribute("user", user);
        verify(response).sendRedirect("html/home.html");
    }

    @Test
    void testDoPost_InvalidUser() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("invalid@example.com");
        when(request.getParameter("password")).thenReturn("password");
        when(userDatabase.findByEmail(anyString())).thenReturn(null);

        loginServlet.doPost(request, response);

        verify(response).sendRedirect("html/login.html");
    }

    @Test
    void testDoPost_WrongPassword() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("password")).thenReturn("wrongpassword");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        when(userDatabase.findByEmail(anyString())).thenReturn(user);

        loginServlet.doPost(request, response);

        verify(response).sendRedirect("html/login.html");
    }
}

