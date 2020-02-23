<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="locale" />

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8" >
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <style>
        <%@ include file="/static/css/style.css" %>
    </style>

    <title><fmt:message key="admin.food-list"/></title>
</head>
<body>

<c:import url="../components/admin-navbar.jsp"/>

<div class="container-md col-3" id="first">

    <form action="/admin/food-list" method="post" class="needs-validation" role="form" novalidate>
        <c:if test="${invalidInput}" >
            <span class="text-danger">
                <fmt:message key="error.food"/>
            </span>
        </c:if>
        <div class="form-row">
            <div class="col-6">
                <input type="text" name="name" placeholder="<fmt:message key='user.food.food-name'/>" required="true" class="form-control" required/>
            </div>

            <div class="col">
                <input type="number" min="1" required="true" name="calories" value="${food.totalCalories}"
                       class="form-control" placeholder="<fmt:message key='user.food.calories'/>" pattern="\d+" required/>
            </div>

            <div class="col">
                <button type="submit" class="btn btn-primary mb-2"><fmt:message key="user.food.add"/></button>
            </div>
        </div>
    </form>

    <div class="row">
        <div class="col">
            <div class="card card-body">
                <table class="table table-striped border-dark">
                    <thead>
                    <tr>
                        <th scope="col"><fmt:message key="user.food.food-name"/></th>
                        <th scope="col"><fmt:message key="user.food.calories"/></th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="food" items="${allCommonFoodDTOs}">
                            <tr>
                                <td>${food.name}</td>
                                <td>${food.totalCalories}</td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/admin/food-list/delete" method="post">
                                        <input type="hidden" name="name" value="${food.name}" class="form-control">
                                        <button type="submit" name="delete"
                                                class="btn btn-danger btn-sm form-control"><fmt:message key="user.food.delete"/></button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>



<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>
