<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
欢迎${subject.principal}登录成功！<a href="${pageContext.request.contextPath}/logout2">退出</a>
<a href="${pageContext.request.contextPath}/">回到主页</a>
</body>
</html>