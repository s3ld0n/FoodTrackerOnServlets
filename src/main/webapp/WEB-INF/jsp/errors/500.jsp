<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="locale" />

<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>500</title>

    <link href="https://fonts.googleapis.com/css?family=Montserrat:200,400,700" rel="stylesheet">

    <style>
        <%@ include file="/static/css/500.css" %>
    </style>

</head>

<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Semi+Condensed:100,200,300,400" rel="stylesheet">
<body class="loading">
<h1>500</h1>
<h2>Unexpected Error <b>:(</b></h2>
    <div class="gears">
        <div class="gear one">
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
        </div>
        <div class="gear two">
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
        </div>
        <div class="gear three">
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
        </div>
    </div>
    <div class="notfound">
        <c:if test="${userCredentials.role.equals('ADMIN')}">
            <a href="${pageContext.request.contextPath}/admin/main">Go TO Homepage</a>
        </c:if>
        <c:if test="${userCredentials.role.equals('USER')}">
            <a href="${pageContext.request.contextPath}/user/main">Go TO Homepage</a>
        </c:if>
    </div>

<script src="https://code.jquery.com/jquery-1.10.2.js"></script>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script>
    $(function() {
        setTimeout(function(){
            $('body').removeClass('loading');
        }, 1000);
    });
</script>
</body>

</html>
