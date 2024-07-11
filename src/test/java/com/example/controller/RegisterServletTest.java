package com.example.controller;

import org.example.Models.User;
import org.example.controller.RegisterServlet;
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
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterServletTest {

    @Mock
    private UserDatabase userDatabase;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private RegisterServlet registerServlet;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));

        registerServlet = new RegisterServlet();
        registerServlet.setUserDatabase(userDatabase);
    }

    @Test
    public void testDoPost_NewUser() throws IOException, ServletException {
        when(request.getParameter("email")).thenReturn("newuser@example.com");
        when(request.getParameter("firstName")).thenReturn("New");
        when(request.getParameter("lastName")).thenReturn("User");
        when(request.getParameter("password")).thenReturn("password");
        when(userDatabase.findByEmail("newuser@example.com")).thenReturn(null);

        registerServlet.doPost(request, response);

        verify(userDatabase).save(any(User.class));
        verify(response).sendRedirect("/html/login.html");
    }

    @Test
    public void testDoPost_ExistingUser() throws IOException, ServletException {
        when(request.getParameter("email")).thenReturn("test@example.com");
        when(request.getParameter("firstName")).thenReturn("Test");
        when(request.getParameter("lastName")).thenReturn("User");
        when(request.getParameter("password")).thenReturn("password");
        when(userDatabase.findByEmail("test@example.com")).thenReturn(user);

        registerServlet.doPost(request, response);

        verify(response).sendRedirect("/html/register.html");
    }
}
