<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ tag pageEncoding="UTF-8" language="java"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@attribute name="title" required="true" rtexprvalue="true" %>
<%@attribute name="content" fragment="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%--<fmt:setLocale value="${param.lang}" />--%>
<fmt:setBundle basename="locale" />

<html <%--lang="${param.lang}--%>">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8" >
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel='stylesheet' href='webjars/bootstrap/4.4.1/css/bootstrap.min.css'>
    <link rel='stylesheet' href='/static/css/style.css'>

    <title>${title}</title>
</head>
<body>

    <t:guest_navbar />

    <a href="index.jsp">Home</a>
    <jsp:invoke fragment="content" />

<script src="webjars/jquery/3.0.0/jquery.min.js"></script>
<script src="webjars/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
