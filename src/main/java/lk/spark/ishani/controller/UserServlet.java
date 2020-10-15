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

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Boolean ismoh = Boolean.valueOf(req.getParameter("ismoh"));
        Boolean isdoctor = Boolean.valueOf(req.getParameter("isdoctor"));
        Boolean ispatient = Boolean.valueOf(req.getParameter("ispatient"));

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setIsmoh(ismoh);
        user.setIsdoctor(isdoctor);
        user.setIspatient(ispatient);

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
        User user = new User(req.getParameter("email"));
        user.loadUserData();
        System.out.println("user loading success");
    }
}
