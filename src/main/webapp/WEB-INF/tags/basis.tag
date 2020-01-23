<%@tag description="Basic common Tag" pageEncoding="UTF-8"%>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="locale" />

<t:header>
    <title>Test header</title>
</t:header>

<t:body>

</t:body>