<%@page import="in.co.fennel.project.model.OrderModel"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.fennel.project.bean.OrderBean"%>
<%@page import="in.co.fennel.project.ctl.OrderListCtl"%>
<%@page import="in.co.fennel.project.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order Report</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<div class="container">
		<br>
		<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Order Report</li>
			</ol>
		</nav>
		<br>
<form action="<%=FPSView.ORDER_LIST_CTL%>" method="post">
			<h5 
				>Order Report</h5>
			<div class="card-body">
				<div class="row g-3">
					<div class="col">
						<input type="text" name="name" class="form-control" placeholder="Search By Name"
							aria-label="Search By First Name" value="<%=ServletUtility.getParameter("name", request)%>">
					</div>
					<div class="col">
						<input type="text" name="itemName" class="form-control" placeholder="Search By Item Name"
							aria-label=" Search By Email" value="<%=ServletUtility.getParameter("itemName", request)%>">
					</div>
					
					<div class="col">
						<input type="submit"
									class="btn btn-primary btn btn-info" name="operation"
									value="<%=OrderListCtl.OP_SEARCH%>">or<input type="submit"
									class="btn btn-primary btn btn-info" name="operation"
									value="<%=OrderListCtl.OP_RESET%>">
					</div>
				</div>
				<b><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></b>
							<b><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></b>
				<br>
				<table class="table table-bordered border-primary">
					<thead>
						<tr>
						
							<th scope="col">#</th>
							<th scope="col">Name</th>
							<th scope="col">ItemName</th>
							<th scope="col">Item Category</th>
							<th scope="col">Email</th>
							<th scope="col">MobileNo</th>
							<th scope="col">BookDate</th>
							<th scope="col">City</th>
							<th scope="col">Address</th>
							<th scope="col">Address2</th>
							<th scope="col">Quantity</th>
							<th scope="col">Status</th>
							<th scope="col">Total Price</th>
						</tr>
					</thead>
					<tbody>
							<%
									int pageNo = ServletUtility.getPageNo(request);
									int pageSize = ServletUtility.getPageSize(request);
									int index = ((pageNo - 1) * pageSize) + 1;
									int size=ServletUtility.getSize(request);
									OrderBean bean = null;
									List list = ServletUtility.getList(request);
									Iterator<OrderBean> it = list.iterator();
									while (it.hasNext()) {
										bean = it.next();
								%>
						<tr>
							
							<td scope="row"><%=index++%></td>
							<td scope="row"><%=bean.getName()%></td>
							<td scope="row"><%=bean.getItemName()%></td>
							<td scope="row"><%=bean.getCategory()%></td>
							<td scope="row"><%=bean.getEmail()%></td>
							<td scope="row"><%=bean.getMobileNo()%></td>
							<td scope="row"><%=bean.getTime_slot()%></td>
							<td scope="row"><%=bean.getCity()%></td>
							<td scope="row"><%=bean.getAddress1()%></td>
							<td scope="row"><%=bean.getAddress2()%></td>
							<td scope="row"><%=bean.getQuantity()%></td>
								<td scope="row"><%=bean.getStatus()%></td>
							<td scope="row"><%=bean.getTotal()%></td>
							
						</tr>
						<%} %>
					</tbody>
				</table>
				<div class="clearfix"></div>
						<ul class="pagination pull-right">
							<li><input type="submit" name="operation"
								class="btn btn-primary btn btn-info"
								value="<%=OrderListCtl.OP_PREVIOUS%>"
								<%=(pageNo == 1) ? "disabled" : ""%>></li>
								<li></li>
							
							
							<li><input type="submit" name="operation"
								class="btn btn-primary btn btn-info"
								value="<%=OrderListCtl.OP_NEXT%>"
								<%=((list.size() < pageSize) || size==pageNo*pageSize) ? "disabled" : ""%>></li>
						</ul>
			</div>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">
		</div>
		</form>
	
	<%@ include file="Footer.jsp" %>
</body>
</html>