
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Register</title>
</head>
<body>
<div align="center">
    <h1>Patient Register Form</h1>
    <form name="form" action="PatientServlet" method="post">
        <table style="with: 80%">
            <tr>
                <td>First Name</td>
                <td><input type="text" name="firstName" /></td>
            </tr>
            <tr>
                <td>Last Name</td>
                <td><input type="text" name="lastName" /></td>
            </tr>
            <tr>
                <td>District</td>
                <td><input type="text" name="district" /></td>
            </tr>
            <tr>
                <td>Location X</td>
                <td><input type="text" name="locationX" /></td>
            </tr>
            <tr>
                <td>Location Y</td>
                <td><input type="text" name="locationY" /></td>
            </tr>
            <tr>
                <td>Severity Level</td>
                <td><input type="text" name="severityLevel" /></td>
            </tr>
            <tr>
                <td>Gender</td>
                <td><input type="text" name="gender" /></td>
            </tr>
            <tr>
                <td>Contact</td>
                <td><input type="text" name="contact" /></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="text" name="email" /></td>
            </tr>
            <tr>
                <td>Age</td>
                <td><input type="text" name="age" /></td>
            </tr>
            <tr>
                <td>Admit Date</td>
                <td><input type="Date" name="admitDate" /></td>
            </tr>
            <tr>
                <td>Admitted By</td>
                <td><input type="text" name="admittedBy" /></td>
            </tr>
            <tr>
                <td>Discharge Date</td>
                <td><input type="Date" name="dischargeDate" /></td>
            </tr>
            <tr>
                <td>Discharged By</td>
                <td><input type="text" name="dischargedBy" /></td>
            </tr>

        </table>
        <input type="submit" value="Submit" />
    </form>
</div>
</body>
</html>