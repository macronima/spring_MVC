<%@tag description="Render row order control" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="key" required="true" %>
<%@attribute name="name" required="true" %>
<%@attribute name="currentField" required="true" %>
<%@attribute name="currentDirection" required="true" %>
<%@attribute name="suffix" required="true" %>
<c:choose>
    <c:when test="${key == currentField}">
        ${name}
        <c:choose>
            <c:when test="${currentDirection =='asc'}">
                <b>▲</b>
            </c:when>
            <c:otherwise>
                <a href="?orderField=${key}&orderDirection=asc&${suffix}">▲</a>
            </c:otherwise>
        </c:choose>
        /
        <c:choose>
            <c:when test="${currentDirection =='desc'}">
                <b>▼</b>
            </c:when>
            <c:otherwise>
                <a href="?orderField=${key}&orderDirection=desc&${suffix}">▼</a>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <a href="?orderField=${key}&${suffix}">${name}</a>
    </c:otherwise>
</c:choose>