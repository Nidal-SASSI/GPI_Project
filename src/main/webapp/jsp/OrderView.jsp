<%@page import="in.co.fennel.project.bean.ItemBean"%>
<%@page import="in.co.fennel.project.ctl.OrderCtl"%>
<%@page import="in.co.fennel.project.ctl.SendMailCtl"%>
<%@page import="in.co.fennel.project.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Check Out</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<div class="container">
		<br>
		<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Check
					Out</li>
			</ol>
		</nav>
		<br>

		<form class="row g-3" method="post" action="<%=FPSView.ORDER_CTL%>">
			<jsp:useBean id="bean" class="in.co.fennel.project.bean.OrderBean"
				scope="request"></jsp:useBean>
				<% ItemBean item=(ItemBean)session.getAttribute("item"); %>
			<div class="card">
				<h5 class="card-header"
					style="background-color: #18334f; color: white;">Check Out</h5>
				<div class="card-body">
					<div class="row g-3">
						<div class="col-md-5 col-lg-4 order-md-last">
							<h4
								class="d-flex justify-content-between align-items-center mb-3">
								<span class="text-muted">Product Detail</span> 
							</h4>
							<div class="card mb-3" style="max-width: 540px;">
								<div class="row g-0">
									<div class="col-md-4">
										<img src="<%=FPSView.APP_CONTEXT + FPSView.GET_IMAGE_VIEW%>?id=<%=item.getId()%>&table=F_Items" alt="..." width="150px" height="200px">
									</div>
									<div class="col-md-8">
										<div class="card-body">
											<h5 class="card-title"><%=item.getName()%></h5>
											<p class="card-text"><%=item.getDescription()%></p>
											<h6 class="card-title" style="color: orange;"><%=item.getPrice()%></h6>
										</div>
									</div>
								</div>
							</div>

							<div class="input-group">
								<input type="text" class="form-control" name="quantity"
									placeholder="Enter Quantity" required="required">
							</div>

						</div>
						<input type="hidden" name="id" value="<%=bean.getId()%>">
						<input type="hidden" name="createdBy"
							value="<%=bean.getCreatedBy()%>"> <input type="hidden"
							name="modifiedBy" value="<%=bean.getModifiedBy()%>"> <input
							type="hidden" name="createdDatetime"
							value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
						<div class="col-md-7 col-lg-8">
							<h4 class="mb-3">Billing address</h4>
							<div class="row g-3">
								<div class="col-sm-6">
									<label for="firstName" class="form-label">Name</label> <input
										type="text" class="form-control" id="firstName" name="name"
										placeholder="Enter Name"
										value="<%=DataUtility.getStringData(bean.getName())%>"
										required>
								</div>

								<div class="col-sm-6">
									<label for="lastName" class="form-label">Mobile No</label> <input
										type="text" class="form-control" placeholder="Enter Mobile No"
										name="mobileNo"
										value="<%=DataUtility.getStringData(bean.getMobileNo())%>"
										required>

								</div>


								<div class="col-12">
									<label for="email" class="form-label">Email <span
										class="text-muted">(Optional)</span></label> <input type="email"
										class="form-control" id="email" name="email"
										placeholder="Enter Email you@example.com" required="required"
										value="<%=DataUtility.getStringData(bean.getEmail())%>">

								</div>

								<div class="col-md-5">
									<label for="country" class="form-label">State</label> <input
										type="text" class="form-control" placeholder="Enter State"
										name="state"
										value="<%=DataUtility.getStringData(bean.getState())%>"
										required>
								</div>

								<div class="col-md-4">
									<label for="state" class="form-label">City</label> <input
										type="text" class="form-control" placeholder="Enter City"
										name="city"
										value="<%=DataUtility.getStringData(bean.getCity())%>"
										required>
								</div>


								<div class="col-12">
									<label for="address" class="form-label">Address</label> <textarea
										 class="form-control" id="address" rows="3" cols="3" name="address1"
										placeholder="Enter Address " required="required"
										><%=DataUtility.getStringData(bean.getAddress1())%>
										</textarea>
								</div>

								<div class="col-12">
									<label for="address2" class="form-label">Address 2 </label> <textarea
										 class="form-control" id="address" rows="3" cols="3" name="address2"
										placeholder="Enter Address 2 " required="required"
										><%=DataUtility.getStringData(bean.getAddress2())%>
										</textarea>
								</div>


							</div>

							<hr class="my-4">

							<input type="submit" name="operation"
								value="<%=OrderCtl.OP_CHECK_OUT%>"
								class="btn btn-primary btn btn-info" />


						</div>


					</div>

				</div>

			</div>
		</form>
	</div>






	<%@ include file="Footer.jsp"%>
</body>
</html>