package com.example.controller;

import org.example.controller.LogoutServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogoutServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LogoutServlet logoutServlet;

    @BeforeEach
    public void setUp() {
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoGet() throws Exception {
        Method doGet = LogoutServlet.class.getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
        doGet.setAccessible(true);

        doGet.invoke(logoutServlet, request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("/html/login.html");
    }

    @Test
    public void testDoPost() throws Exception {
        Method doPost = LogoutServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doPost.setAccessible(true);

        doPost.invoke(logoutServlet, request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("/html/login.html");
    }
}
