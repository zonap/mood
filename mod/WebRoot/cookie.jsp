<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'cookie.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<%
		// 创建一个Cookie,包括(key,value).
		Cookie cookie = new Cookie("cookieName", "cookieValue");

		// 设置Cookie的生命周期,如果设置为负值的话,关闭浏览器就失效.
		cookie.setMaxAge(60 * 60 * 24 * 365);

		// 设置Cookie路径,不设置的话为当前路径(对于Servlet来说为request.getContextPath() + web.xml里配置的该Servlet的url-pattern路径部分)
		// cookie.setPath("/");

		// 输出Cookie
		response.addCookie(cookie);
	%>
	已创建Cookie.
	<br>
	<a href="sc.jsp">查看Cookie</a>
</body>
</html>
