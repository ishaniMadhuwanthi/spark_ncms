package lk.spark.ishani.controller;

import lk.spark.ishani.dao.UserDao;
import lk.spark.ishani.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet")
public class UserServlet extends HttpServlet {

    //insert user
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        Boolean moh = Boolean.valueOf(req.getParameter("moh"));
        Boolean doctor = Boolean.valueOf(req.getParameter("hospital"));

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setMoh(moh);
        user.setDoctor(doctor);

        UserDao userDao = new UserDao();
        String userRegistered = userDao.regUser(user);

        if(userRegistered.equals("success"))
        {
            System.out.println("Successfully registered");//registered successfully
        }
        else
        {
            System.out.println("registration Failed");//registration failed
        }

        try {
            userDao.regUser(user);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //view user details
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User(req.getParameter("username"));
        user.loadUserData();
        System.out.println("loading success");
    }
}
