package org.example.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.example.Models.User;
import org.example.repository.UserDatabase;
import org.mindrot.jbcrypt.BCrypt;


@WebServlet({"/register"})
public class RegisterServlet extends HttpServlet {
    private UserDatabase userDatabase = new UserDatabase();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/html/register.html").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");

        User existingUser = userDatabase.findByEmail(email);
        if (existingUser == null) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            User user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(hashedPassword);
            userDatabase.save(user);
            response.sendRedirect("/html/login.html");
        } else {
            response.sendRedirect("/html/register.html");
        }
    }
}

