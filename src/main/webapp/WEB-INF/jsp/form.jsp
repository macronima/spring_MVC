<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Student form</title>
</head>
<body>
    <form method="POST">
        <label>First name</label>
        <input name="firstName" placeholder="firstName" value="${student.firstName}">
        <br>
        <label>Last name</label>
        <input name="lastName" placeholder="lastName" value="${student.lastName}">
        <br>
        <label>Sex</label>
        <select name="sex">
            <option value="m" <c:if test="${student.sex == 'm'}">selected="selected"</c:if> >male</option>
            <option value="f" <c:if test="${student.sex == 'f'}">selected="selected"</c:if> >female</option>
        </select>
        <br>
        <input name="birth" placeholder="dd-mm-yyyy" value="${student.birth}"><br>
        <input type="submit" value="${student.id>0?'Update':'Add'}">
        <a href="/list">Back to list</a>
    </form>
</body>
</html>
