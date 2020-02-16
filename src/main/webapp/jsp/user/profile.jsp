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
<div class="row justify-content-center">
    <div class="col-md-6 bottom-tables">
        <div class="card col-md-8 col-centered">
            <header class="card-header">
                <h4 class="card-title mt-2">Update </h4>
            </header>
            <article class="card-body">

                <form action="/user/profile/" method="post">
                    <input type="hidden" name="id">
                    <div class="form-row">
                        <div class="col form-group">
                            <label>Username</label>
                            <div>
                                <input type="text" class="form-control" value="${userDTO.username}" maxlength="32" id="username" placeholder="username" readonly="readonly"/>
                            </div>
                        </div>

                        <div class="col form-group">
                            <label>Email</label>
                            <div>
                                <input type="email" name="email" value="${userDTO.email}" class="form-control" placeholder="Email" />
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="col form-group">
                            <label>First Name</label>
                            <div>
                                <input type="text" name="firstName" value="${userDTO.firstName}" class="form-control" placeholder="first name" />
                            </div>
                        </div>

                        <div class="col form-group">
                            <label>Last name</label>
                            <div>
                                <input type="text" name="lastName" value="${userDTO.lastName}" class="form-control" placeholder="last name" />
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="sex">Sex</label>
                            <div>
                                <select id="sex" name="sex">
                                    <option value="FEMALE">FEMALE</option>
                                    <option value="MALE">Male</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group col-md-6">
                            <label>Age</label>
                            <div>

                                <input type="number" name="age" value="" class="form-control" placeholder="age"/>
                            </div>
                        </div>
                    </div>


                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label>Weight</label>
                            <div>
                                <input type="number" name="weight" min="1" value="" class="form-control" placeholder="weight" />
                            </div>
                        </div>

                        <div class="form-group col-md-6">
                            <label>Height</label>
                            <div>
                                <input type="number" name="height" min="1" value="" class="form-control" placeholder="height"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <p>Lifestyle</p>
                        <div>
                            <select id="lifestyle" name="lifestyle">
                                <option value="SEDENTARY">Sedentary</option>
                                <option value="MODERATE">Moderate</option>
                                <option value="VIGOROUS">Vigorous</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="form-group col-md-6">
                            <label>Daily Norm</label>
                            <div>
                                <input type="text" class="form-control" value="${userDTO.dailyNorm}" readonly/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="form-group col-md-6">
                            <label>Role: ${userDTO.role}</label>
                        </div>
                    </div>

                    <input type="hidden" name="password" value="${userDTO.password}">

                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-block">Save</button>
                    </div>
                </form>
            </article>
        </div>
    </div>
</div>

<%--<script src="webjars/jquery/3.4.1/jquery.min.js"></script>--%>
<%--<script src="webjars/popper.js/1.14.3/popper.min.js"></script>--%>
<%--<script src="webjars/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>--%>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

</body>
</html>