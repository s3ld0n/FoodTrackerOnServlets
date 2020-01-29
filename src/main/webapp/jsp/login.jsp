<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="locale" />

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8" >
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel='stylesheet' href='webjars/bootstrap/4.4.1/css/bootstrap.min.css'>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel='stylesheet' href='/static/css/style.css'>


    <title>Login page</title>
</head>
<body>

<%--<t:guest_navbar/>--%>

    <div class="container container mt-5" id="first">

        <div>
            <a href="?lang=en">En</a>
            <a href="?lang=uk">Ua</a>
        </div>

        <div class="row">
            <div class="col-md-12">
                <h1>Hello Guest!</h1>
            </div>
        </div>

        <div class="container mt-4">
            <form action="/login" method="post">
                <div><label>Username<input type="text" name="username"/> </label></div>
                <div><label>Password<input type="password" name="password"/></label></div>
                <input type="submit" value="Log In">
            </form>
            <a href="/registration">Registration</a>
        </div>
    </div>

<script src="webjars/jquery/3.0.0/jquery.min.js"></script>
<script src="webjars/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>