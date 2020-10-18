package lk.spark.ishani.ncms.controller;

import lk.spark.ishani.ncms.dao.UserDao;
import lk.spark.ishani.ncms.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet")
public class UserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Boolean moh = Boolean.valueOf(request.getParameter("moh"));
        Boolean hospital = Boolean.valueOf(request.getParameter("hospital"));

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setMoh(moh);
        user.setHospital(hospital);

        UserDao userDao = new UserDao();
        String userRegistered = userDao.viewStatistics(user);

        if(userRegistered.equals("SUCCESS"))
        {
            System.out.println("Success");
        }
        else
        {
            System.out.println("Failed");
        }

        try {
            userDao.viewStatistics(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.getModel();
        System.out.println("doGet user success");
    }
}
