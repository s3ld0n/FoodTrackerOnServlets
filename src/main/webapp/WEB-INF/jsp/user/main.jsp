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

    <title>Hello, Username</title>
</head>
<body>

<t:user_navbar />

<div class="container" id="first">

    <div class="row justify-content-center">
        <div class="col col-7">
            <div class="card card-body mb-2 bottom-tables">
                <h2>Food Personal</h2>

                <form action="/user/food/add" method="post" class="needs-validation" role="form" novalidate>
                    <div class="form-row">
                        <div class="col-6">
                            <input type="text" id="name" name="name" placeholder="food.name" required="true" class="form-control" required/>
                        </div>

                        <div class="col">
                            <input type="number" step="1" min="1" required="true" name="calories" id="calories"
                                   class="form-control" placeholder="calories" pattern="\d+" required/>
                        </div>

                        <div class="col">
                            <button type="submit" class="btn btn-primary mb-2">Add</button>
                        </div>
                    </div>
                </form>

                <table class="table table-bordered table-responsive-md table-striped text-center">
                    <thead class="border-dark">
                    <col width="130">
                    <col width="150">
                    <tr>
                        <th>Name</th>
                        <th>Calories</th>
                        <th>Amount</th>
                    </tr>
                    </thead>
                    <c:forEach var="userFood" items="${userFoodDTOs}">
                        <tr>
                            <td>${userFood.name}</td>
                            <form class="form-inline needs-validation" role="form" action="${pageContext.request.contextPath}/user/food/consume" method="post">
                                <input type="hidden" name="name" value="${userFood.name}">
                                <input type="hidden" name="totalCalories" value="${userFood.totalCalories}">
                                <td>
                                    <p name="totalCalories">${userFood.totalCalories}</p>
                                </td>

                                <td>
                                    <div class="form-group">
                                        <input type="number" name="amount" required="true" maxlength="2" size="4" value="1" min="1" step="1" class="form-control input-digit">
                                    </div>
                                </td>
                                <td>
                                    <div class="form-group">
                                        <button type="submit" name="consume" class="btn btn-success btn-sm form-control">Consume</button>
                                    </div>
                                </td>
                            </form>
                            <td>
                                <div class="form-group">
                                    <form action="${pageContext.request.contextPath}/user/food/delete" method="post">
                                        <input type="hidden" name="name" value="${userFood.name}" class="form-control">
                                        <button type="submit" name="delete"
                                                class="btn btn-danger btn-sm form-control">Delete</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>

                </table>

            </div>
        </div>
        <div class="col">
            <div class="card card-body" id="consumed">
                <h2>Consumed Today</h2>
                <c:choose>
                    <c:when test="${empty consumedFoodDTOs}">
                        <h3>Nothing here yet</h3>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-striped border-dark">
                            <thead>
                            <tr>
                                <th scope="col">Name</th>
                                <th scope="col">Amount</th>
                                <th scope="col">Calories</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach var="consumedFoodDTO" items="${consumedFoodDTOs}">
                                <tr>
                                    <td>${consumedFoodDTO.name}</td>
                                    <td>${consumedFoodDTO.amount}</td>
                                    <td>${consumedFoodDTO.totalCalories}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col col-6 col-centered bottom-tables">
            <div class="card card-body" id="commonFood">
                <h2>Common</h2>
                <table class="table table-striped border-dark">
                    <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Calories</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="nextFood" items="${allCommonFoodDTOs}">
                        <tr>
                            <td><span>${nextFood.name}</span></td>
                            <td>${nextFood.totalCalories}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/user/food/add" method="post" object="${nextFood}" novalidate>
                                    <input type="hidden" name="name" value="${nextFood.name}">
                                    <input type="hidden" name="calories" value="${nextFood.totalCalories}">
                                    <button type="submit" class="btn btn-primary mb-2">Add</button>
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
<nav class="navbar navbar-expand-sm navbar-light bg-light" id="bottom-navbar">
    <div class="mx-auto d-sm-flex d-block flex-sm-nowrap">
        <a class="collapse navbar-collapse text-center" id="navbarNav">
            <li class="nav-item">
                <a inline="text">User: ${userDTO.username}</a>
            </li>
            <li class="nav-item">
                <a>Daily norm: ${userDTO.dailyNorm}</a>
            </li>
            <li class="nav-item">
                <a>Consumed today: ${dayDTO.caloriesConsumed}</a>
            </li>
            <li class="nav-item">
                <c:if test="${dayDTO.isDailyNormExceeded()}">
                    <a class="card-footer" id="exceeded">Exceeded: ${dayDTO.exceededCalories}</a>
                </c:if>
            </li>
        </a>
    </div>
</nav>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/static/js/form_validator.js"></script>
</body>
</html>