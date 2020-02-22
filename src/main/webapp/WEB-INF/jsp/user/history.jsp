<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="locale" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8" >
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <style>
        <%@ include file="/static/css/style.css" %>
    </style>

    <title>History of ${userDTO.username}</title>
</head>
<body>

<t:user_navbar/>
<div class="empty">.</div>

<div class="container">
    <div class="row">
        <div class="col col-6 col-centered">

            <div>
                <h1 align="center">${userDTO.username}</h1>
                <hr class="style-seven">
                <div class="card card-body">

                    <table>
                        <c:forEach var="dayDTO" items="${daysDTOs}">
                            <tr class="list-group">

                                <div>
                                    <h5 class="list-group-item text-center">${dayDTO.date}</h5>
                                </div>

                                <table class="table table-striped">
                                    <thead class="border-dark">
                                    <tr>
                                        <th>Time</th>
                                        <th>Food name</th>
                                        <th>Amount</th>
                                        <th>Total calories</th>
                                    </tr>
                                    </thead>
                                    <c:forEach var="consumedFoodDTO" items="${dayDTO.consumedFoodDTOs}">
                                        <tr>
                                            <td>${consumedFoodDTO.time}</td>
                                            <td>${consumedFoodDTO.name}</td>
                                            <td>${consumedFoodDTO.amount}</td>
                                            <td>${consumedFoodDTO.totalCalories}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                <h5>Calories consumed: ${dayDTO.caloriesConsumed}</h5>
                                <h5>Exceeded calories: ${dayDTO.exceededCalories}</h5>
                                <hr class="style-seven">
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

</body>
</html>