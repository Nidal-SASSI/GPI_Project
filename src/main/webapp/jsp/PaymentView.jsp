<%@page import="in.co.fennel.project.ctl.OrderCtl"%>
<%@page import="in.co.fennel.project.ctl.CustomerCtl"%>
<%@page import="in.co.fennel.project.util.DataUtility"%>
<%@page import="in.co.fennel.project.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<div class="container">
		<br>
		<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="<%=FPSView.WELCOME_CTL%>">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Payment</li>
			</ol>
		</nav>
		<br>

		<div class="card">
			<h5 class="card-header"
				style="background-color: #18334f; color: white;">Payment</h5>
			<div class="card-body">
			<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font></b>
				<form class="row g-3" method="post" action="<%=FPSView.ORDER_CTL%>">
				


						
					<div class="col-md-12">
						<label for="inputEmail4" class="form-label">Card No</label>
						 <input type="text" name="" placeholder="Enter Card No" class="form-control" id="inputEmail4"
						 value="" required="required">
					</div>
					
					<div class="col-md-12">
						<label for="inputEmail4" class="form-label">Card Holder Name</label>
						 <input type="text" name="" placeholder="Enter Card Holder" class="form-control" id="inputEmail4"
						 value="" required="required">
					</div>
					
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Month</label>
						 <input type="text" name="" placeholder="Enter Card Holder" class="form-control" id="inputEmail4"
						 value="" required="required">
					</div>
					
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Year</label>
						 <input type="text" name="" placeholder="Enter Year" class="form-control" id="inputEmail4"
						 value="" required="required">
					</div>
					
					<div class="col-md-12">
						<label for="inputEmail4" class="form-label">CVV</label>
						 <input type="text" name="" placeholder="Enter CVV" class="form-control" id="inputEmail4"
						 value="" required="required">
					</div>
					
					
					<div class="col-12">
						<input type="submit" name="operation"
									value="<%=OrderCtl.OP_PAYMENT_BOOK%>"
									class="btn btn-primary btn btn-info" />
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>