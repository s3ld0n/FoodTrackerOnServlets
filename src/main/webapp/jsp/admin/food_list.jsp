<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="locale" />

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8" >
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <%--    <link rel='stylesheet' href='webjars/bootstrap/4.4.1/css/bootstrap.min.css'>--%>
    <%--    <link rel='stylesheet' href='/static/css/style.css'>--%>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">


    <style>
        <%@ include file="/static/css/style.css" %>
    </style>

    <title>Food list</title>
</head>
<body>

<t:admin_navbar />

<div class="container-md col-3" id="first">

    <form action="/admin/food/add" method="post" class="needs-validation" role="form" novalidate>
        <div class="form-row">


            <div class="col-6">
                <input type="text" name="name" placeholder="food name" required="true" class="form-control" required/>
            </div>

            <div class="col">
                <input type="number" min="1" required="true" name="totalCalories" value="${food.totalCalories}"
                       class="form-control" placeholder="Calories" pattern="\d+" required/>
            </div>

            <div class="col">
                <button type="submit" class="btn btn-primary mb-2">Add</button>
            </div>
        </div>
    </form>

    <div class="row">
        <div class="col">
            <div class="card card-body">
                <table class="table table-striped border-dark">
                    <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Calories</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="food" items="${allCommonFoodDTOs}">
                            <tr>
                                <td>${food.name}</td>
                                <td>${food.totalCalories}</td>
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
