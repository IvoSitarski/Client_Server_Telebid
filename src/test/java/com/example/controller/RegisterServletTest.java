package com.example.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.controller.RegisterServlet;
import org.example.repository.UserDatabase;
import org.example.Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class RegisterServletTest {

    @InjectMocks
    private RegisterServlet registerServlet;

    @Mock
    private UserDatabase userDatabase;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoPost_UserExists() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("firstName")).thenReturn("Test");
        when(request.getParameter("lastName")).thenReturn("User");
        when(request.getParameter("password")).thenReturn("password");

        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        when(userDatabase.findByEmail("test@example.com")).thenReturn(existingUser);

        registerServlet.doPost(request, response);

        verify(userDatabase, never()).save(any(User.class));
        verify(response).sendRedirect("html/register.html");
    }

    @Test
    void testDoPost_NewUser() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("newuser@example.com");
        when(request.getParameter("firstName")).thenReturn("New");
        when(request.getParameter("lastName")).thenReturn("User");
        when(request.getParameter("password")).thenReturn("password");

        when(userDatabase.findByEmail("newuser@example.com")).thenReturn(null);

        registerServlet.doPost(request, response);

        verify(userDatabase, times(1)).save(any(User.class));
        verify(response).sendRedirect("html/home.html");
    }
}


