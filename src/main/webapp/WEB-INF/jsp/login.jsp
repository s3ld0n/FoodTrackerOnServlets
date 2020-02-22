<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="locale" />

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8" >
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel='stylesheet' href='webjars/bootstrap/4.4.1/css/bootstrap.min.css'>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel='stylesheet' href='/static/css/style.css'>

    <style>
        <%@ include file="/static/css/style.css" %>
        <%@ include file="/static/css/login.css" %>
    </style>

    <title>Login page</title>
</head>
<body>

<c:import url="components/guest-navbar.jsp"/>

<div class="container login-container" id="first">
    <div class="row">
        <div class="col-md-6 login-form-1 col-centered">
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="text-danger">${invalidCredentials}</div>
                <div class="form-group">
                    <input type="text" class="form-control" name="username" placeholder="<fmt:message key="guest.username"/>" value="" />
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" name="password" placeholder="<fmt:message key="guest.password"/>" value="" />
                </div>
                <div class="form-group">
                    <input type="submit" class="btnSubmit" value="<fmt:message key="guest.login"/>" />
                </div>
                <div class="form-group">
                    <a href="${pageContext.request.contextPath}/registration"><fmt:message key="guest.registration"/></a>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>