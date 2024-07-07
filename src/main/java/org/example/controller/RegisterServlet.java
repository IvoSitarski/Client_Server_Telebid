package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Models.User;
import org.example.repository.UserDatabase;


import java.io.IOException;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDatabase userDatabase = new UserDatabase();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");

        User existingUser = userDatabase.findByEmail(email);
        if (existingUser == null) {
            User user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            userDatabase.save(user);
            response.sendRedirect("html/home.html");
        } else {
            response.sendRedirect("html/register.html");
        }
    }
}

