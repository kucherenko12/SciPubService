<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 04.05.2023
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>View publications</title>
  <style>
    table {
      font-family: arial, sans-serif;
      border-collapse: collapse;
      width: 100%;
    }

    thead { background-color: white; }

    td, th {
      border: 1px solid #dddddd;
      text-align: left;
      padding: 8px;
    }

    tr:nth-child(even) { background-color: #dddddd; }

    tr:nth-child(odd) { background-color: white; }

    h1 { color: white; }

    body { background-color: #00468b; }
  </style>
</head>
<body>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

  <h1>Публікації викладача <c:forEach items="${requestScope.teacher.surnameNamePatronym}" var="nameField">${nameField} </c:forEach></h1>
  <table>
    <thead>
    <tr>
      <th>Назва</th>
      <th>Автори</th>
      <th>Звідки</th>
      <th>Рік</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.publications}" var="publication">
      <tr>
        <td>${publication.name}</td>
        <td><c:forEach items="${publication.authors}" var="author">${author}, </c:forEach></td>
        <td>${publication.establishment}</td>
        <td><c:choose>
          <c:when test="${publication.year == 0}">
          </c:when>
          <c:otherwise>
            ${publication.year}
          </c:otherwise>
        </c:choose>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>


</body>
</html>
