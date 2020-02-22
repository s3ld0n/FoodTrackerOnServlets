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

    <title><fmt:message key="user.profile"/> ${userDTO.username}</title>
</head>
<body>

<c:import url="../components/user-navbar.jsp"/>
<div class="empty">.</div>
<div class="row justify-content-center">
    <div class="col-md-6 bottom-tables">
        <div class="card col-md-8 col-centered">
            <header class="card-header">
                <h4 class="card-title mt-2"><fmt:message key="user.profile"/></h4>
            </header>
            <article class="card-body">

                <form action="${pageContext.request.contextPath}/user/profile" method="post">
                    <input type="hidden" name="id">
                    <div class="form-row">
                        <div class="col form-group">
                            <label><fmt:message key="guest.username"/></label>
                            <div>
                                <input type="text" class="form-control" value="${userDTO.username}" maxlength="32"
                                       name="username" id="username" placeholder="<fmt:message key='guest.username'/>"
                                       readonly="readonly"/>
                            </div>
                        </div>

                        <div class="col form-group">
                            <label><fmt:message key="guest.email"/></label>
                            <div>
                                <input type="email" name="email" value="${userDTO.email}" class="form-control"
                                       placeholder="<fmt:message key='guest.email'/>" readonly/>
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label><fmt:message key="guest.first-name"/></label>
                            <div>
                                <input type="text" name="firstName" value="${userDTO.firstName}" class="form-control"
                                       placeholder="<fmt:message key='guest.first-name'/>" />
                            </div>
                        </div>

                        <div class="col form-group">
                            <label><fmt:message key="guest.last-name"/></label>
                            <div>
                                <input type="text" name="lastName" value="${userDTO.lastName}" class="form-control"
                                       placeholder="<fmt:message key='guest.last-name'/>" />
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <p><fmt:message key="guest.sex"/></p>
                            <div>
                                <c:if test="${biometricsDTO.sex.equals('FEMALE')}">
                                    <input type="radio" name="sex" value="FEMALE" checked>
                                    <fmt:message key="guest.sex.female"/>
                                </c:if>
                                <c:if test="${!biometricsDTO.sex.equals('FEMALE')}">
                                    <input type="radio" name="sex" value="FEMALE">
                                    <fmt:message key="guest.sex.female"/>
                                </c:if>

                                <c:if test="${biometricsDTO.sex.equals('MALE')}">
                                    <input type="radio" name="sex" value="MALE" checked>
                                    <fmt:message key="guest.sex.male"/>
                                </c:if>
                                <c:if test="${!biometricsDTO.sex.equals('MALE')}">
                                    <input type="radio" name="sex" value="MALE" >
                                    <fmt:message key="guest.sex.male"/>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group col-md-6">
                            <label><fmt:message key="guest.age"/></label>
                            <div>
                                <input type="number" name="age" value="${biometricsDTO.age}" class="form-control"
                                       placeholder="<fmt:message key='guest.age'/>"/>
                            </div>
                        </div>
                    </div>


                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label><fmt:message key="guest.weight"/></label>
                            <div>
                                <input type="number" name="weight" min="1" value="${biometricsDTO.weight}"
                                       class="form-control" placeholder="<fmt:message key='guest.weight'/>" />
                            </div>
                        </div>

                        <div class="form-group col-md-6">
                            <label><fmt:message key="guest.height"/></label>
                            <div>
                                <input type="number" name="height" min="1" value="${biometricsDTO.height}"
                                       class="form-control" placeholder="<fmt:message key='guest.height'/>"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <p><fmt:message key="guest.lifestyle"/></p>
                        <div>
                            <c:if test="${biometricsDTO.lifestyle.equals('SEDENTARY')}">
                                <input type="radio" name="lifestyle" value="SEDENTARY" checked>
                                <fmt:message key="guest.lifestyle.sedentary"/>
                            </c:if>
                            <c:if test="${!biometricsDTO.lifestyle.equals('SEDENTARY')}">
                                <input type="radio" name="lifestyle" value="SEDENTARY" >
                                <fmt:message key="guest.lifestyle.sedentary"/>
                            </c:if>

                            <c:if test="${biometricsDTO.lifestyle.equals('MODERATE')}">
                                <input type="radio" name="lifestyle" value="MODERATE" checked>
                                <fmt:message key="guest.lifestyle.moderate"/>
                            </c:if>
                            <c:if test="${!biometricsDTO.lifestyle.equals('MODERATE')}">
                                <input type="radio" name="lifestyle" value="MODERATE" >
                                <fmt:message key="guest.lifestyle.moderate"/>
                            </c:if>

                            <c:if test="${biometricsDTO.lifestyle.equals('VIGOROUS')}">
                                <input type="radio" name="lifestyle" value="VIGOROUS" checked>
                                <fmt:message key="guest.lifestyle.vigorous"/>
                            </c:if>
                            <c:if test="${!biometricsDTO.lifestyle.equals('VIGOROUS')}">
                                <input type="radio" name="lifestyle" value="VIGOROUS" >
                                <fmt:message key="guest.lifestyle.vigorous"/>
                            </c:if>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="form-group col-md-6">
                            <label><fmt:message key="user.food.daily-norm"/></label>
                            <div>
                                <input type="text" class="form-control" value="${userDTO.dailyNorm}" readonly/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="form-group col-md-6">
                            <label><fmt:message key="user.role"/>: ${userDTO.role}</label>
                            <input type="hidden" name="role" value="${userDTO.role}">
                        </div>
                    </div>

                    <input type="hidden" name="password" value="${userDTO.password}">

                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-block">
                            <fmt:message key="user.save"/>
                        </button>
                    </div>
                </form>
            </article>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

</body>
</html>