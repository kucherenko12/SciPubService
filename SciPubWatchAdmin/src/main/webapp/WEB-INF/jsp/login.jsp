<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 21.05.2023
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Log in</title>
    <style>
      body
      {
          background-image: url('/Ukraine_NewsletterRechnungslegung_0.5.jpeg');
          background-repeat: no-repeat;
          background-attachment: fixed;
          background-position: calc(50% - 75px) calc(50% + 150px);
      }
      .div-1
      {
        height: 115px;
        width: 221px;
        background-color: #00468b;
        margin: auto;
        color: white;
        border: 5px solid #00468b;
      }
      .tar{text-align: right;}
      .tal{text-align: left;}
      .tac{text-align: center;}
      a{color: white;}
    </style>
</head>
<body>
<div class="div-1">
  <div class="tal"><a href="/SciPubWatch">back</a></div>
  <form name="loginForm" method="post" action="loginpost">
    <div class="tar">Username: <input type="text" name="username"/><br/></div>
    <div class="tar">Password: <input type="password" name="password"/><br/></div>
    <div class="tac"><input type="submit" value="Login" /></div>
  </form>
</div>
</body>
</html>
