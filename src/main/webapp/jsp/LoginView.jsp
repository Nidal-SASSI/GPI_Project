<%@page import="in.co.fennel.project.ctl.LoginCtl"%>
<%@page import="in.co.fennel.project.util.DataUtility"%>
<%@page import="in.co.fennel.project.ctl.FPSView"%>
<%@page import="in.co.fennel.project.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<div class="container">
		<br>
		<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Login</li>
			</ol>
		</nav>
		<br>
		<div class="row">
			<div class="col-sm-3"></div>
			<div class="col-sm-6">
				<div class="card border-success mb-3" style="max-width: 40rem;">
					<div class="card-header bg-transparent border-success">Login</div>
					<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font></b>
					<div class="card-body text-success">
						<form method="post" action="<%=FPSView.LOGIN_CTL%>">
							<jsp:useBean id="bean" class="in.co.fennel.project.bean.AdminBean"
								scope="request"></jsp:useBean>
							 <input
								type="hidden" name="id" value="<%=bean.getId()%>"> <input
								type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
							<input type="hidden" name="modifiedBy"
								value="<%=bean.getModifiedBy()%>"> <input type="hidden"
								name="createdDatetime"
								value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

							<div class="mb-3">
								<label for="exampleInputEmail1" class="form-label">User Name</label> <input type="text"  class="form-control"
									id="exampleInputEmail1" name="login" aria-describedby="emailHelp" value="<%=DataUtility.getStringData(bean.getLogin())%>" >
								<div class="form-text"><font  color="red"><%=ServletUtility.getErrorMessage("login", request)%></font></div>
							</div>
							<div class="mb-3">
								<label for="exampleInputPassword1" class="form-label">Password</label>
								<input type="password" class="form-control" name="password"
									id="exampleInputPassword1" value="<%=DataUtility.getStringData(bean.getPassword()) %>" >
									<div class="form-text"><font  color="red"><%=ServletUtility.getErrorMessage("password", request)%></font></div>
							</div>
							<input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_IN%>" class="btn btn-primary"  /> or <input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP%>" class="btn btn-primary"  />
						</form>
					</div>
				</div>
			</div>
			<div class="col-sm-3"></div>
		</div>

	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>