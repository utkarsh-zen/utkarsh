<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table>
 <tr>
            <td>Id :</td><td>${userdata.id}</td></tr>
         <tr>   <td>Email :</td> <td>${userdata.email}</td></tr>
            <tr>   <td>Verified Email :</td><td>${userdata.verified_email}</td></tr>
            <tr>  <td>Name :</td> <td>${userdata.name}</td></tr>
            <tr>   <td>Given Name :</td><td>${userdata.given_name}</td></tr>
            <tr>  <td>Family Name :</td> <td>${userdata.family_name}</td></tr>
            <tr>   <td>Link :</td><td>${userdata.link}</td></tr>
           <tr>   <td>Picture :</td> <td><img src="${userdata.picture}" width="100" height="100" /></td></tr>
            <tr>   <td>Gender :</td><td>${userdata.gender}</td></tr>
           <tr>   <td>Locale :</td> <td>${userdata.locale}</td></tr>
            <tr>   <td>HD :</td><td>${userdata.hd}</td></tr>
            
       

</table>
       
   
</body>
</html>