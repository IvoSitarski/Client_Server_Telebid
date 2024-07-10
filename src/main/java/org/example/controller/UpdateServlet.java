package org.example.controller;

import org.example.Models.User;
import org.example.repository.UserDatabase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/updateProfile")
public class UpdateServlet extends HttpServlet {
    private UserDatabase userDatabase = new UserDatabase();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            request.setAttribute("firstName", user.getFirstName());
            request.setAttribute("lastName", user.getLastName());
            request.getRequestDispatcher("/html/updateProfile.html").forward(request, response);
        } else {
            response.sendRedirect("/html/login.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);

        userDatabase.update(user);
        response.sendRedirect("/html/home.html");
    }
}
