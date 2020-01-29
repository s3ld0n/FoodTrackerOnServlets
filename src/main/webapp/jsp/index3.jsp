<%--
  Created by IntelliJ IDEA.
  User: Rubezh
  Date: 23.01.2020
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<t:template title="Home Page" >

     <jsp:attribute name="content">
         <h1>Home page</h1>
         <h2>
             <fmt:message key="label.chooseRequestLocale" />
         </h2>

        <p>
            <fmt:message key="label.requestLocaleContent" />
        </p>

        <p>
            <fmt:message key="label.changeLang" />
        </p>
     </jsp:attribute>
</t:template>
