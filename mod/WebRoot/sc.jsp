<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'sc.jsp' starting page</title>
    
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
     读取客户端的Cookie信息. <br>
   <%
     // 获得当前路径以及"直接父路径"的所有Cookie对象,如果没有任何Cookie的话,则返回null  
     Cookie[] cookies = request.getCookies();
    
     // 遍历数组,获得具体的Cookie
     if(cookies == null) {
        out.print("没有Cookie信息");
     } else {
         for(int i=0; i<cookies.length; i++) {
            // 获得具体的Cookie
            Cookie cookie = cookies[i];
            // 获得Cookie的名称
            String name = cookie.getName();
            String value = cookie.getValue();
            out.print("Cookie名:"+name+" &nbsp; Cookie值:"+value+"<br>");
         }
     }
    %>
  </body>
</html>
