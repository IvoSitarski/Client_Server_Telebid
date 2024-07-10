package com.example.controller;

import org.example.Models.User;
import org.example.repository.UserDatabase;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.example.controller.UpdateServlet;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class UpdateProfileServletTest {

    @InjectMocks
    private UpdateServlet updateProfileServlet;

    @Mock
    private UserDatabase userDatabase;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoPost() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("firstName")).thenReturn("NewFirstName");
        when(request.getParameter("lastName")).thenReturn("NewLastName");
        when(request.getParameter("password")).thenReturn("NewPassword");

        //updateProfileServlet.doPost(request, response);

        verify(user).setFirstName("NewFirstName");
        verify(user).setLastName("NewLastName");
        verify(user).setPassword("NewPassword");
        verify(userDatabase).update(user);
        verify(response).sendRedirect("html/home.html");
    }
}

