<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="locale" />

<div class="fixed-top">
    <div class="jumbotron clearfix text-center" style="margin-bottom:0">
        <h1>Food Tracker</h1>
    </div>
    <nav class="navbar navbar-expand-sm navbar-light bg-light" id="upper-navbar">
        <div class="mx-auto d-sm-flex d-block flex-sm-nowrap">
            <div class="collapse navbar-collapse text-center" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/main">
                            <fmt:message key="label.main"/>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/profile">
                            <fmt:message key="user.profile"/>
                        </a>
                    </li>

                    <li class="nav-item" >
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/history">
                            <fmt:message key="user.history"/>
                        </a>
                    </li>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <fmt:message key="label.lang"/>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="?lang=en"><fmt:message key="label.lang.en"/></a>
                            <a class="dropdown-item" href="?lang=uk"><fmt:message key="label.lang.ua"/></a>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
                <ul class="navbar-nav ml-auto navbar-right">
                    <li class="nav-item">
                        <a class="nav-link">
                            <fmt:message key="label.logged-as"/>: ${userCredentials.username}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                            <fmt:message key="label.logout"/>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>
