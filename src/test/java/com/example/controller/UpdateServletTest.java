package com.example.controller;

import org.example.Models.User;
import org.example.repository.UserDatabase;
import org.example.controller.UpdateServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateServletTest {

    @Mock
    private UserDatabase userDatabase;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private UpdateServlet updateServlet;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));
    }

    @Test
    public void testDoGet_UserLoggedIn() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getRequestDispatcher("/html/updateProfile.html")).thenReturn(requestDispatcher);

        Method doGet = UpdateServlet.class.getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
        doGet.setAccessible(true);
        doGet.invoke(updateServlet, request, response);

        verify(request).setAttribute("firstName", "Test");
        verify(request).setAttribute("lastName", "User");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_UserNotLoggedIn() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        Method doGet = UpdateServlet.class.getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
        doGet.setAccessible(true);
        doGet.invoke(updateServlet, request, response);

        verify(response).sendRedirect("/html/login.html");
    }

    @Test
    public void testDoPost_UserLoggedIn() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("firstName")).thenReturn("NewFirstName");
        when(request.getParameter("lastName")).thenReturn("NewLastName");
        when(request.getParameter("password")).thenReturn("newpassword");

        Method doPost = UpdateServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doPost.setAccessible(true);
        doPost.invoke(updateServlet, request, response);

        verify(userDatabase).update(user);
        verify(response).sendRedirect("/html/home.html");
        assertEquals("NewFirstName", user.getFirstName());
        assertEquals("NewLastName", user.getLastName());
        assertTrue(BCrypt.checkpw("newpassword", user.getPassword()));
    }

    @Test
    public void testDoPost_UserNotLoggedIn() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        Method doPost = UpdateServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        doPost.setAccessible(true);
        doPost.invoke(updateServlet, request, response);

        verify(response).sendRedirect("/html/login.html");
    }
}
