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
<title>Item</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<div class="container">
		<br>
		<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="<%=FPSView.WELCOME_CTL%>">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Item</li>
			</ol>
		</nav>
		<br>

		<div class="card">
			<h5 class="card-header"
				style="background-color: #18334f; color: white;">Item</h5>
			<div class="card-body">
			<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font></b>
				<form class="row g-3" method="post" action="<%=FPSView.ITEM_CTL%>" enctype="multipart/form-data">
				
				<jsp:useBean id="bean"
							class="in.co.fennel.project.bean.ItemBean" scope="request"></jsp:useBean>


						<input type="hidden" name="id" value="<%=bean.getId()%>">
						<input type="hidden" name="createdBy"
							value="<%=bean.getCreatedBy()%>"> <input type="hidden"
							name="modifiedBy" value="<%=bean.getModifiedBy()%>"> <input
							type="hidden" name="createdDatetime"
							value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
				
				<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Name</label>
						 <input type="text" name="name" placeholder="Enter Name" class="form-control" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getName())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font>
					</div>
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Price</label>
						 <input type="text" name="price" placeholder="Enter  Price" class="form-control" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getPrice())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("price", request)%></font>
					</div>
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Stock</label>
						<input type="text" name="stock" class="form-control" placeholder="Enter Stock" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getStock())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("stock", request)%></font>
					</div>
					<div class="col-md-6">
						<label for="inputPassword4" class="form-label">Quantities</label>
						 <input type="text" name="quantity"  placeholder="Enter Quantities" class="form-control" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getQuantities())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("quantity", request)%></font>
					</div>
					
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Designation</label>
						<textarea  name="designation"  rows="4" cols="4" class="form-control" placeholder="Enter Designation" >
						 <%=DataUtility.getStringData(bean.getDesignation())%></textarea>
						 <font color="red"><%=ServletUtility.getErrorMessage("designation", request)%></font>
					</div>
					<div class="col-md-6">
						<label for="inputPassword4" class="form-label">Description</label>
						 <textarea  name="description"  rows="4" cols="4" placeholder="Enter Description" class="form-control">
						 <%=DataUtility.getStringData(bean.getDescription())%></textarea>
						 <font color="red"><%=ServletUtility.getErrorMessage("description", request)%></font>
					</div>
					
					<% HashMap map=(HashMap)request.getAttribute("catMap"); %>
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Category</label>
						<%=HTMLUtility.getList("category",String.valueOf(bean.getCategory()), map) %>
						 <font color="red"><%=ServletUtility.getErrorMessage("category", request)%></font>
					</div>
					<div class="col-md-6">
						<label for="inputPassword4" class="form-label">Item Image</label>
						 <input type="file" required="required" name="image"   class="form-control" id="inputEmail4">
					</div>
					<div class="col-12">
						<input type="submit" name="operation"
									value="<%=ItemCtl.OP_SAVE%>"
									class="btn btn-primary btn btn-info" />
									 or <input type="submit" name="operation" value="<%=ItemCtl.OP_RESET %>" class="btn  btn-primary btn btn-info"  />
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>