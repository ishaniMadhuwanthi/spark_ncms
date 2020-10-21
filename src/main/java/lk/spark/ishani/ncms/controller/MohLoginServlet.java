package lk.spark.ishani.ncms.controller;

import lk.spark.ishani.ncms.dao.DoctorDao;
import lk.spark.ishani.ncms.dao.UserDao;
import lk.spark.ishani.ncms.models.Doctor;
import lk.spark.ishani.ncms.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MohLoginServlet")
public class MohLoginServlet  extends HttpServlet {

    /*
    ------------moh login control----------------
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String password = req.getParameter("moh_id");
        String email = req.getParameter("email");

        User user = new User();
        user.setMoh_id(password);
        user.setEmail(email);

        UserDao userDao = new UserDao();
        String userLogin = userDao.loginMoh(user);


        if (userLogin.equals("SUCCESS")) {
            System.out.println("Success");

            User user2 = new User(password);
            user2.getModel();

            PrintWriter printWriter = resp.getWriter();
            printWriter.println(user2.toString());

        } else {
            System.out.println("Failed");
        }
    }

}
