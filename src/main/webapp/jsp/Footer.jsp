<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<br>
<footer class="footer mt-auto py-3" style="background-color: #18334f">
  <div class="container">
    <span class="text-muted">Copyright &copy <%= new SimpleDateFormat("yyyy").format(new Date()) %> Fennel. All Rights Reserved.</span>
  </div>
</footer>

</body>
</html>