<%@page import="com.together.core.service.UserService"%>
<html>
<body>
<h2>Hello World!</h2>
<p>
<% UserService userService=new UserService();
   out.println("hello:"+ userService.getUserInfo()); 
%>
</p>
</body>
</html>
