<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 21.05.2023
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Admin menu</title>
    <style>
      body
      {
        background-color: #00468b;
        background-repeat: no-repeat;
        background-attachment: fixed;
      }
      .div-2
      {
        height: 312px;
        width: 268px;
        margin: left;
        color: black;
        background-color: white;
        border: 5px solid white;
      }
      .tar{text-align: right;}
      .tal{text-align: left;}
      a{color: black;}
    </style>
</head>
<body>
  <div class="div-2">
    <a href="logoutpost">Log out</a>
    <p>admin menu</p>
    <p></p>
    <form name="addTeacherForm" method="post" action="adminmenu/add">
      <span class="tal">Add teacher<br/></span>
      <div class="tar">
        <div style="width: 260px">
          <select style="width: 150px" name = "type">
            <option value = "aspirant">aspirant</option>
            <option value = "lecturer">lecturer</option>
          </select>
        </div>
        Surname: <input type="text" name="surname"/><br/>
        Name: <input type="text" name="name"/><br/>
        Patronym: <input type="text" name="patronym"/><br/>
        Google Scholar: <input type="text" name="linkgooglescholar"/><br/>
        Scopus: <input type="text" name="linkscopus"/><br/>
        <input type="submit" value="Add" />
      </div>
    </form>
    <p></p>
    <form name="deleteTeacherForm" method="post" action="adminmenu/delete">
      <span class="tal">Delete teacher<br/></span>
        <div class="tar">
          id: <input type="int" name="id"/><br/>
          <input type="submit" value="Delete" />
        </div>
    </form>
  </div>
</body>
</html>
