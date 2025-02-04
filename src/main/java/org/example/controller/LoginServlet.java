package org.example.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import org.example.Models.User;
import org.example.repository.UserDatabase;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet({"/login"})
public class LoginServlet extends HttpServlet {
    private UserDatabase userDatabase=new UserDatabase();

    public LoginServlet() {
    }

    public LoginServlet(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userDatabase.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("html/home.html");
        } else {
            response.sendRedirect("html/login.html");
        }
    }
}