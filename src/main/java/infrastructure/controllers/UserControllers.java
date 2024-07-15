package infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.models.LoginRequest;
import domain.models.User;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.UserServices;

@WebServlet("/users/*")
public class UserControllers extends HttpServlet {

    private ObjectMapper mapper;
    private UserServices services;

    public UserControllers() {
        this.mapper = new ObjectMapper();
        this.services = new UserServices();
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        configureCorsHeaders(resp);
    }

    private void configureCorsHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*"); // aca colocan la direccion de donde viene la peticion
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "content-type, authorization");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configureCorsHeaders(resp);
        String username = req.getParameter("username");

        if(username != null) {
            User user = services.findUserByEmail(username);
            if(user != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(mapper.writeValueAsString(user));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Usuario no encontrado");
            }
        } else {
            ArrayList<User> users = services.findAllUsers();
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(mapper.writeValueAsString(users));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        configureCorsHeaders(resp);
        String pathInfo = req.getPathInfo();

        if (pathInfo != null && pathInfo.equals("/login")) {
            handleLogin(req, resp);
        } else {
            handleRegister(req, resp);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LoginRequest loginRequest = mapper.readValue(req.getInputStream(), LoginRequest.class);

        boolean isAuthenticated = services.login(loginRequest.getEmail(), loginRequest.getPassword());

        if (isAuthenticated) {
            resp.setStatus(HttpServletResponse.SC_OK);
            User user = services.findUserByEmail(loginRequest.getEmail());
            if(user != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(mapper.writeValueAsString(user));
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"message\": \"Invalid credentials\"}");
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = mapper.readValue(req.getInputStream(), User.class);
        services.saveUser(user);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
