<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.fennel.project.bean.ItemBean"%>
<%@page import="in.co.fennel.project.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Success</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<div class="container">
		<br>
		<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Order
					Success</li>
			</ol>
		</nav>

		<br>
		<%
		ItemBean item = (ItemBean) session.getAttribute("item");
		%>
		<div class="card mb-3" style="max-width: 540px;">
			<div class="row g-0">
				<div class="col-md-4">
					<img
						src="<%=FPSView.APP_CONTEXT + FPSView.GET_IMAGE_VIEW%>?id=<%=item.getId()%>&table=F_Items"
						alt="..." width="150px" height="200px">
				</div>
				<div class="col-md-8">
					<div class="card-body">
						<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
						</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
						</font></b>
						<h5 class="card-title"><%=item.getName()%></h5>
						<p class="card-text"><%=item.getDescription()%></p>
						<h6 class="card-title" style="color: orange;"><%=item.getPrice()%></h6>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>