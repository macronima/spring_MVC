<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:choose>
  <c:when test="${empty listDTO.nameFilter}">
    <c:set var="suffix" value="offset=${listDTO.offset}"/>
  </c:when>
  <c:otherwise>
    <c:set var="suffix" value="offset=${listDTO.offset}&nameFilter=${listDTO.nameFilter}"/>
  </c:otherwise>
</c:choose>

<html>
  <head>
    <title>Students list</title>
  </head>
  <body>
  <a href="/student/0">Add new</a>
  <form action="/list" method="GET">
    <input type="hidden" name="orderField" value="${listDTO.orderField}">
    <input type="hidden" name="orderDirection" value="${listDTO.orderDirection}">
    <input type="hidden" name="offset" value="${listDTO.offset}">
    <input type="text" name="nameFilter" value="${listDTO.nameFilter}" placeholder="Filter by first name">
    <input type="submit" value="Filter">
  </form>
  <a href="/list?orderField=${listDTO.orderField}&orderDirection=${listDTO.orderDirection}&offset=${listDTO.offset}">Reset filter</a>
  <table>
    <thead>
    <td><t:orderControl key="id" name="Id" currentDirection="${listDTO.orderDirection}" currentField="${listDTO.orderField}" suffix="${suffix}"/></td>
    <td><t:orderControl key="first_name" name="First name" currentDirection="${listDTO.orderDirection}" currentField="${listDTO.orderField}"  suffix="${suffix}"/></td>
    <td><t:orderControl key="last_name" name="Last name" currentDirection="${listDTO.orderDirection}" currentField="${listDTO.orderField}"  suffix="${suffix}"/></td>
    <td><t:orderControl key="sex" name="Sex" currentDirection="${listDTO.orderDirection}" currentField="${listDTO.orderField}"  suffix="${suffix}"/></td>
    <td><t:orderControl key="birth" name="Birth" currentDirection="${listDTO.orderDirection}" currentField="${listDTO.orderField}"  suffix="${suffix}"/></td>
    <td>Controls</td>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="item">
      <tr>
        <td>${item.id}</td>
        <td>${item.firstName}</td>
        <td>${item.lastName}</td>
        <td>${item.sex}</td>
        <td>${item.birth}</td>
        <td>
          <a href="/student/${item.id}">view</a>
          <a href="/student/${item.id}/del">delete</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  </body>
</html>
