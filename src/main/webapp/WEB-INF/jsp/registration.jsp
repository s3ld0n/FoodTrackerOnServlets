<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="locale" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <style type="text/css">
        <%@ include file="/static/css/style.css" %>
    </style>
</head>
<body>
<c:import url="components/guest-navbar.jsp"/>
<div class="empty">.</div>
<div class="container container mt-5">
    <div class="row">
        <div class="col-md-12">
            <h1><fmt:message key="guest.registration"/></h1>
        </div>
    </div>

    <form action="${pageContext.request.contextPath}/registration" method="post" class="needs-validation" novalidate>

        <div class="form-group row field">
            <label class="col-sm-2 col-form-label"><fmt:message key="guest.username"/></label>
            <div class="col-sm-6 field">
                <c:if test="${userExists}">
                    <span class="text-danger"><fmt:message key="error.registration.user-exists"/></span>
                </c:if>
                <input type="text" name="username" class="form-control" maxlength="32" value="${userDTO.username}" placeholder="<fmt:message key='guest.username'/>"/>
                <c:if test="${not empty userErrors['username']}">
                    <span class="text-danger"><fmt:message key="error.registration.username"/></span>
                </c:if>
            </div>
        </div>

        <div class="form-group row field">
            <label class="col-sm-2 col-form-label"><fmt:message key="guest.email"/></label>
            <div class="col-sm-6 field">
                <input type="email" name="email" value="${userDTO.email}" class="form-control" placeholder="<fmt:message key='guest.email'/>"/>
                <c:if test="${not empty userErrors['email']}">
                    <span class="text-danger"><fmt:message key="error.email"/></span>
                </c:if>

            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="guest.first-name"/></label>
            <div class="col-sm-6 field" >
                <input type="text" name="firstName" value="${userDTO.firstName}" class="form-control" placeholder="<fmt:message key='guest.first-name'/>" />
                <c:if test="${not empty userErrors['firstName']}">
                    <span class="text-danger"><fmt:message key="error.firstName"/></span>
                </c:if>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="guest.last-name"/></label>
            <div class="col-sm-6 field">
                <input type="text" name="lastName" value="${userDTO.lastName}" class="form-control" placeholder="<fmt:message key='guest.last-name'/>" />
                <c:if test="${not empty userErrors['lastName']}">
                    <span class="text-danger"><fmt:message key="error.lastName"/></span>
                </c:if>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="guest.age"/></label>
            <div class="col-sm-6 field">
                <input type="number" name="age" value="${biometricsDTO.age}" class="form-control" placeholder="<fmt:message key='guest.age'/>" />
                <c:if test="${not empty biometricsErrors['age']}">
                    <span class="text-danger"><fmt:message key="error.age"/></span>
                </c:if>
            </div>
        </div>

        <div class="form-group row">
            <label for="sex" class="col-sm-2 col-form-label"><fmt:message key="guest.sex"/></label>
            <div class="col-sm-6 field">
                <select id="sex" name="sex">
                    <option value="MALE"><fmt:message key="guest.sex.male"/></option>
                    <option value="FEMALE"><fmt:message key="guest.sex.female"/></option>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="guest.weight"/></label>
            <div class="col-sm-6 field">
                <input type="number" name="weight" min="1" value="${biometricsDTO.weight}" class="form-control" placeholder="<fmt:message key='guest.weight'/>" />
                <c:if test="${not empty biometricsErrors['weight']}">
                    <span class="text-danger"><fmt:message key="error.weight"/></span>
                </c:if>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="guest.height"/></label>
            <div class="col-sm-6 field">
                <input type="number" name="height" min="1" value="${biometricsDTO.height}" class="form-control" placeholder="<fmt:message key='guest.height'/>" />
                <c:if test="${not empty biometricsErrors['height']}">
                    <span class="text-danger"><fmt:message key="error.height"/></span>
                </c:if>
            </div>
        </div>

        <div class="form-group row field">
            <label for="lifestyle" class="col-sm-2 col-form-label"><fmt:message key="guest.lifestyle"/></label>
            <div class="col-sm-6 field">
                <select id="lifestyle" name="lifestyle">
                    <option value="SEDENTARY"><fmt:message key="guest.lifestyle.sedentary"/></option>
                    <option value="MODERATE"><fmt:message key="guest.lifestyle.moderate"/></option>
                    <option value="VIGOROUS"><fmt:message key="guest.lifestyle.vigorous"/></option>
                </select>
            </div>
        </div>

        <div class="form-group row field">
            <label class="col-sm-2 col-form-label"><fmt:message key="guest.password"/></label>
            <div class="col-sm-6 field">
                <input type="password" name="password" class="form-control" placeholder="<fmt:message key='guest.password'/>"/>
                <c:if test="${not empty userErrors['password']}">
                    <span class="text-danger"><fmt:message key="error.password"/></span>
                </c:if>
            </div>
        </div>

        <div class="form-group row field">
            <label class="col-sm-2 col-form-label"><fmt:message key="guest.password-confirm"/></label>
            <div class="col-sm-6 field">
                <input type="password" name="passwordConfirm" class="form-control" placeholder="<fmt:message key='guest.password-confirm'/>" required/>
                <c:if test="${not empty passwordsDontMatch}">
                    <span class="text-danger"><fmt:message key="error.password.dont-match"/></span>
                </c:if>

            </div>
        </div>

        <button type="submit" class="btn btn-primary mb-2"><fmt:message key="guest.sign-up"/></button>
    </form>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>