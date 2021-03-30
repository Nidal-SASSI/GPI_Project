<%@page import="java.util.Iterator"%>
<%@page import="in.co.fennel.project.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.fennel.project.bean.CartBean"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Locale"%>
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
			<div class="card">
				<h5 class="card-header"
					style="background-color: #18334f; color: white;">Check Out</h5>
				<div class="card-body">
					<div class="row g-3">
						<div class="col-md-5 col-lg-4 order-md-last">
							<h4
								class="d-flex justify-content-between align-items-center mb-3">
								<span class="text-muted">Item Detail</span> 
							</h4>

							<table class="table table-bordered border-primary">
					<thead>
						<tr>
						
							<th scope="col">Image</th>
							<th scope="col">Name</th>
							<th scope="col">Price</th>
							<th scope="col">Quantity</th>
						</tr>
					</thead>
					<tbody>
							<%
							
							Locale locale  = new Locale("en", "UK");
							String pattern = "###.##";

							DecimalFormat decimalFormat = (DecimalFormat)
							        NumberFormat.getNumberInstance(locale);
							decimalFormat.applyPattern(pattern);
							
									CartBean cbean = null;
									List list = ServletUtility.getList(request);
									Iterator<CartBean> it = list.iterator();
									double total=0.0;
									int i=1;
									while (it.hasNext()) {
										cbean = it.next();
								%>
						<tr>
							
							<td scope="row">
							<img src="<%=FPSView.APP_CONTEXT+FPSView.GET_IMAGE_VIEW%>?id=<%=cbean.getItemId()%>&table=F_Items" width="100px" height="100px" ></td>
							<td scope="row"><%=cbean.getItemName()%></td>
							<td scope="row"><%=cbean.getPrice()%></td>
							<td scope="row"><%=cbean.getQuantity()%></td>
						</tr>
						<% total += DataUtility.getDouble(cbean.getTotalPrice()); %>
						<%} %>
						<tr>
							<td colspan="3">Total</td>
							<td colspan="1"><%=total%></td>
						</tr>
						
					</tbody>
				</table>

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
										placeholder="Enter Address " required
										><%=DataUtility.getStringData(bean.getAddress1())%>
										</textarea>
								</div>

								<div class="col-12">
									<label for="address2" class="form-label">Address 2 </label> <textarea
										 class="form-control" id="address" rows="3" cols="3" name="address2"
										placeholder="Enter Address 2 " required
										><%=DataUtility.getStringData(bean.getAddress2())%>
										</textarea>
								</div>


							</div>

							<hr class="my-4">

							<input type="submit" name="operation"
								value="<%=OrderCtl.OP_PAYMENT%>"
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