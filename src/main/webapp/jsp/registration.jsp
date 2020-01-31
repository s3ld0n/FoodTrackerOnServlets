<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:requestEncoding value="UTF-8"/>
<%--<fmt:setLocale value="${lang}"/>--%>
<fmt:setBundle basename="locale" />


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body>

<div class="empty">.</div>
<div class="container container mt-5">
    <div class="row">
        <div class="col-md-12">
            <h1>Registration</h1>
        </div>
    </div>

    <form action="${pageContext.request.contextPath}/registration" method="post" class="needs-validation" novalidate>

        <div class="form-group row field">
            <label class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-6 field">
                <input type="text" class="form-control" maxlength="32" placeholder="username"/>
            </div>
        </div>

        <div class="form-group row field">
            <label class="col-sm-2 col-form-label">Email</label>
            <div class="col-sm-6 field">
                <input type="email" name="email" value="" class="form-control" placeholder="email"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Full name</label>
            <div class="col-sm-6 field" >
                <input type="text" name="fullName" value="" class="form-control" placeholder="full name}" />
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">National name</label>
            <div class="col-sm-6 field">
                <input type="text" name="nationalName" value="" class="form-control" placeholder="national_name" />
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Age</label>
            <div class="col-sm-6 field">
                <input type="number" name="age" value="" class="form-control" placeholder="age" />
            </div>
        </div>

        <div class="form-group row">
            <label for="sex" class="col-sm-2 col-form-label">Sex</label>
            <div class="col-sm-6 field">
                <select id="sex" name="sex">
                    <option value="MALE">Male</option>
                    <option value="FEMALE">Female</option>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Weight</label>
            <div class="col-sm-6 field">
                <input type="number" name="weight" min="1" value="" class="form-control" placeholder="weight" />
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Height</label>
            <div class="col-sm-6 field">
                <input type="number" name="height" min="1" value="" class="form-control" placeholder="height" />
            </div>
        </div>


        <div class="form-group row field">
            <label for="lifestyle" class="col-sm-2 col-form-label" text="user.lifestyle"></label>
            <div class="col-sm-6 field">
                <select id="lifestyle" name="lifestyle">
                    <option value="SEDENTARY">Sedentary</option>
                    <option value="MODERATE">Moderate</option>
                    <option value="VIGOROUS">Vigorous</option>
                </select>
            </div>
        </div>

        <div class="form-group row field">
            <label class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-6 field">
                <input type="password" name="password" class="form-control" placeholder="password"/>
            </div>
        </div>

        <div class="form-group row field">
            <label class="col-sm-2 col-form-label">Confirm Password</label>
            <div class="col-sm-6 field">
                <input type="password" name="passwordConfirm" class="form-control" placeholder="сonfirm_password}" required/>
            </div>
        </div>

        <button type="submit" class="btn btn-primary mb-2">Add</button>
    </form>
</div>

<!--<script th:src="@{/js/form_validator.js}"></script>-->
</body>
</html>