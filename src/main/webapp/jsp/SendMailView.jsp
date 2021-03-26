<%@page import="in.co.fennel.project.ctl.SendMailCtl"%>
<%@page import="in.co.fennel.project.bean.SendMail"%>
<%@page import="in.co.fennel.project.ctl.ItemCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="in.co.fennel.project.util.HTMLUtility"%>
<%@page import="javax.swing.text.html.HTML"%>
<%@page import="in.co.fennel.project.ctl.CustomerCtl"%>
<%@page import="in.co.fennel.project.util.DataUtility"%>
<%@page import="in.co.fennel.project.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Send Mail</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<div class="container">
		<br>
		<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="<%=FPSView.SEND_MAIL%>">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Send Mail</li>
			</ol>
		</nav>
		<br>

		<div class="card">
			<h5 class="card-header"
				style="background-color: #18334f; color: white;">SendMail</h5>
			<div class="card-body">
			<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font></b>
				<form class="row g-3" method="post" action="<%=FPSView.SEND_MAIL%>" >
				
				<jsp:useBean id="bean"
							class="in.co.fennel.project.bean.SendMail" scope="request"></jsp:useBean>
					
					<% HashMap map=(HashMap)request.getAttribute("catMap"); %>
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Category</label>
						<%=HTMLUtility.getList("category",String.valueOf(bean.getCategory()), map) %>
						 <font color="red"><%=ServletUtility.getErrorMessage("category", request)%></font>
					</div>
					
					<div class="col-12">
						<input type="submit" name="operation"
									value="<%=SendMailCtl.OP_SEND%>"
									class="btn btn-primary btn btn-info" />
									 or <input type="submit" name="operation" value="<%=SendMailCtl.OP_RESET %>" class="btn  btn-primary btn btn-info"  />
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>