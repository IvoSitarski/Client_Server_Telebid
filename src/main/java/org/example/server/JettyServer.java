package org.example.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.controller.RegisterServlet;
import org.example.controller.LoginServlet;
import org.example.controller.LogoutServlet;
import org.example.controller.UpdateServlet;
import org.eclipse.jetty.servlet.DefaultServlet;


public class JettyServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        server.setHandler(handler);

        handler.addServlet(new ServletHolder(new RegisterServlet()), "/register");
        handler.addServlet(new ServletHolder(new LoginServlet()), "/login");
        handler.addServlet(new ServletHolder(new LogoutServlet()), "/logout");
        handler.addServlet(new ServletHolder(new UpdateServlet()), "/update");

        // Добавяне на DefaultServlet за обслужване на статични файлове
        ServletHolder holderDefault = new ServletHolder("default", DefaultServlet.class);
        holderDefault.setInitParameter("resourceBase", "src/main/webapp/");
        holderDefault.setInitParameter("dirAllowed", "true");
        handler.addServlet(holderDefault, "/");

        server.start();
        server.join();
    }
}


