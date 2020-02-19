<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ tag pageEncoding="UTF-8" language="java"%>
<head>
    <style>
        <%@ include file="/static/css/style.css" %>
    </style>
</head>
<div class="fixed-top">
    <div class="jumbotron clearfix text-center" style="margin-bottom:0">
        <h1>Food Tracker</h1>
    </div>

    <nav class="navbar navbar-expand-lg navbar-light bg-light" id="upper-navbar">
        <div class="mx-auto d-sm-flex d-block flex-sm-nowrap">
            <div class="collapse navbar-collapse text-center">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/main">Main</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/food-list">Food</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/users">Users</a>
                    </li>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Language
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="?lang=en">En</a>
                            <a class="dropdown-item" href="?lang=uk">Ua</a>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
                <ul class="navbar-nav ml-auto navbar-right">
                    <li class="nav-item">
                        <a class="nav-link">Logged as: ${loggedUsername}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>
