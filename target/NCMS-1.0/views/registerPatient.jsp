<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 1/9/2020
  Time: 9:55 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>National COVID Management System</title>
</head>
<body>
<div align="center">
    <h1>Patient Registration</h1>
    <form action="<%= request.getContextPath() %>/register" method="post">
        <table style="with: 80%">
            <tr>
                <td>First Name</td>
                <td><input type="text" name="first_name" /></td>
            </tr>
            <tr>
                <td>Last Name</td>
                <td><input type="text" name="last_name" /></td>
            </tr>
            <tr>
                <td>UserName</td>
                <td><input type="text" name="username" /></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="password" /></td>
            </tr>
            <tr>
                <td>Contact No</td>
                <td><input type="text" name="contact" /></td>
            </tr>
            <tr>
                <td>Address</td>
                <td><input type="text" name="address" /></td>
            </tr>
            <tr>
                <td>District</td>
                <td><input type="text" name="district" /></td>
            </tr>
            <tr>
                <td>Gender</td>
                <td><input type="text" name="gender" /></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="text" name="email" /></td>
            </tr>
            <tr>
                <td>Age</td>
                <td><input type="text" name="age" /></td>
            </tr>

        </table>
        <input type="submit" value="Submit" />
    </form>
</div>
</body>
</html>
