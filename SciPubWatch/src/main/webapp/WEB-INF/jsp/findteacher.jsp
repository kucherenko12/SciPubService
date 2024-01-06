<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Select Teacher</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}button.css">
  <style>


      /* -------------------------------------------------- */
      /* -------------------------------------------------- */

      .changeablebutton
      {
          border-radius: 5px;
          padding: 3px;
          text-decoration: none;
      }
      .changeablebutton.default
      {
          border: 1px solid #000;
          background-color: white;
          color: black;
      }
      .changeablebutton.green
      {
          border: 1px solid green;
          background-color: green;
          color: white;
      }

      /* -------------------------------------------------- */
      /* -------------------------------------------------- */


      body { background-color: #00468b; }

    .whitediv {background-color: white; color:black; font-family: arial, sans-serif; font-size: 16px;}

    table
    {
      background-color: white;
      font-family: arial, sans-serif;
      border: 1px solid #dddddd;
      border-collapse: collapse;
      width: 100%;
    }



    thead { background-color: white; }

    .tbb {border: 1px solid #dddddd;}
    .tbw {border: 1px solid white;}

    td, th
    {
      text-align: left;
      padding: 8px;
    }

    tr:nth-child(even) { background-color: #dddddd; }

    tr:nth-child(odd) { background-color: white; }

    .t2c56 {width: 125px}
    .t2c56u {width: 250px}

    #inputfield{
        width:100%;
        margin:none;
        padding:0;
    }
    #label{
        width:165px;
    }
    #button{
        width:57px;
    }
  </style>
</head>
<body>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <c:if test="${not empty sessionScope.teacher}">
    <div class="whitediv">
      <p>Попередній переглянутий викладач:
        <c:forEach items="${sessionScope.lastteacher.surnameNamePatronym}" var="nameField">${nameField} </c:forEach>
      </p>
    </div>
  </c:if>

  <table class="formtable">
    <tr>
      <td id="label" class="tbw">
        Будь-яке слово з ПІБ:
      </td>
      <form name="teacherSearchForm" onChange="this.form.submit()" method="get" action="teachersearch">
        <td class="tbw">
          <input id="inputfield" type="text" name="name"/>
        </td>
        <td id="button" class="tbw">
          <input type="submit" value="Шукати" />
        </td>
      </form>
    </tr>
  </table>

  <table class="contenttable">
      <thead>
      <tr class="tbb">
          <th class="tbb">Ім'я</th>
          <th class="tbb">Прізвище</th>
          <th class="tbb">Ім'я по батькові</th>
          <th class="tbb t2c56" colspan="2">
              <form method="post" class="myForm" action="changescrapetype">
                  <c:choose>
                      <c:when test="${scrapetype == 'citations'}">
                        <input class="changeablebutton green" name="scrapetype" type="submit" value="citations" />
                        <input class="changeablebutton default" name="scrapetype" type="submit" value="date" />
                      </c:when>
                      <c:when test="${scrapetype == 'date'}">
                        <input class="changeablebutton default" name="scrapetype" type="submit" value="citations" />
                        <input class="changeablebutton green" name="scrapetype" type="submit" value="date" />
                      </c:when>
                  </c:choose>
              </form>
          </th>
      </tr>
      </thead>

      <c:if test="${not empty requestScope.teachers}">
          <tbody>
          <c:forEach items="${requestScope.teachers}" var="teacher">
              <tr class="tbb">
                  <td class="tbb">${teacher.surnameNamePatronym[0]}</td>
                  <td class="tbb">${teacher.surnameNamePatronym[1]}</td>
                  <td class="tbb">${teacher.surnameNamePatronym[2]}</td>
                  <td class="tbb">
                      <c:if test="${teacher.linkGoogleScholar != null}">
                          <a href="viewpublications?id=${teacher.id}&type=fast">шукати швидко</a>
                      </c:if>
                  </td>
                  <td class="tbb">
                      <c:if test="${teacher.linkGoogleScholar != null}">
                          <a href="viewpublications?id=${teacher.id}&type=slow">шукати повільно</a>
                      </c:if>
                  </td>
              </tr>
          </c:forEach>
          </tbody>
      </c:if>

  </table>
</body>
</html>
