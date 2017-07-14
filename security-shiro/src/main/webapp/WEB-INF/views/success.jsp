<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title></title>
</head>
<body>
     <input value="${test}">
     <br/>
     <shiro:hasRole name="admin">
         <shiro:principal/>拥有角色admin
     </shiro:hasRole>
</body>
</html>