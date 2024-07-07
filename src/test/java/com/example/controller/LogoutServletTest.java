package com.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.example.controller.LogoutServlet;


import java.io.IOException;

import static org.mockito.Mockito.*;

public class LogoutServletTest {

    @InjectMocks
    private LogoutServlet logoutServlet;

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
    void testDoGet() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);

        logoutServlet.doGet(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("html/login.html");
    }
}

