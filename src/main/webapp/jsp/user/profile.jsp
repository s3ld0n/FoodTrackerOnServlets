<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
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
    <link rel='stylesheet' type="text/css" href="webjars/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <style>
        <%@ include file="/static/css/style.css" %>
    </style>

    <title>Update user</title>
</head>
<body>

<t:user_navbar/>
<div class="empty">.</div>
<div class="container container mt-5">
    <form action="user/update" method="post" novalidate>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Username</label>
            <p>${user.username}</p>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Email</label>
            <div class="col-sm-6">
                <input type="email" name="email" value="${user.email}" class="form-control" placeholder="Email" required/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">First name</label>
            <div class="col-sm-6">
                <input type="text" name="firstName" value="" class="form-control" placeholder="first name" required/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label" >Last name</label>
            <div class="col-sm-6">
                <input type="text" name="nationalName" value="" class="form-control" placeholder="National name" required/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Age</label>
            <div class="col-sm-6">
                <input type="datetime-local" name="age" value="" class="form-control" placeholder="Age" required/>
            </div>
        </div>

        <div class="form-group row">
            <label for="sex" class="col-sm-2 col-form-label">Sex</label>
            <div class="col-sm-6">
                <select id="sex" name="sex">
                    <option value="MALE">Male</option>
                    <option value="FEMALE">Female</option>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Weight</label>
            <div class="col-sm-6">
                <input type="number" name="weight" value="" class="form-control" placeholder="Weight" required/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Height</label>
            <div class="col-sm-6">
                <input type="number" name="height" value="" class="form-control" placeholder="Height" required/>
            </div>
        </div>


        <div class="form-group row">
            <label for="lifestyle" class="col-sm-2 col-form-label">Lifestyle</label>
            <div class="col-sm-6">
                <select id="lifestyle" name="lifestyle">
                    <option value="SEDENTARY">Sedentary</option>
                    <option value="MODERATE">Moderate</option>
                    <option value="VIGOROUS">Vigorous</option>
                </select>
            </div>
        </div>
        <div sec:au>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label" >Password</label>
                <div class="col-sm-6">
                    <input type="password" name="password" class="form-control" placeholder="Password" required/>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Confirm password</label>
                <div class="col-sm-6">
                    <input type="password" name="passwordConfirm" class="form-control" placeholder="Retype Password" required/>
                </div>
            </div>
        </div>
        <button type="submit" class="btn btn-primary mb-2">Update</button>
    </form>
</div>

<%--<script src="webjars/jquery/3.4.1/jquery.min.js"></script>--%>
<%--<script src="webjars/popper.js/1.14.3/popper.min.js"></script>--%>
<%--<script src="webjars/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>--%>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

</body>
</html>