<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="locale" />

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8" >
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel='stylesheet' href='webjars/bootstrap/4.4.1/css/bootstrap.min.css'>
    <link rel='stylesheet' href='/static/css/style.css'>

    <title>Hello, Admin</title>
</head>
<body>

<t:admin_navbar />

    <div class="container container mt-5" id="first">
        <div class="row">
            <div class="col-md-12">
                <h1>Greetings</h1>
            </div>
        </div>

    </div>

<script src="webjars/jquery/3.0.0/jquery.min.js"></script>
<script src="webjars/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>