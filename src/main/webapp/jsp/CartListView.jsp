<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="in.co.fennel.project.util.DataUtility"%>
<%@page import="in.co.fennel.project.bean.CartBean"%>
<%@page import="in.co.fennel.project.model.ItemModel"%>
<%@page import="in.co.fennel.project.bean.ItemBean"%>
<%@page import="in.co.fennel.project.ctl.ItemListCtl"%>
<%@page import="in.co.fennel.project.model.AdvertisementModel"%>
<%@page import="in.co.fennel.project.bean.AdvertisementBean"%>
<%@page import="in.co.fennel.project.ctl.AdvertisementListCtl"%>
<%@page import="in.co.fennel.project.model.CustomerModel"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.fennel.project.bean.CustomerBean"%>
<%@page import="in.co.fennel.project.ctl.CustomerListCtl"%>
<%@page import="in.co.fennel.project.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cart</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<div class="container">
		<br>
		<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Cart</li>
			</ol>
		</nav>
		<br>
<form action="<%=FPSView.CART_LIST_CTL%>" method="post">
		<div class="card">
			<h5 class="card-header"
				style="background-color: #18334f; color: white;">Cart</h5>
			<div class="card-body">
				<b><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></b>
							<b><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></b>
				<br>
				<table class="table table-bordered border-primary">
					<thead>
						<tr>
						
							<th scope="col">#</th>
							<th scope="col">Image</th>
							<th scope="col">Name</th>
							<th scope="col">Price</th>
							<th scope="col">Quantity</th>
							<th scope="col">Total Price</th>
							<th scope="col">Update</th>
							<th scope="col">Delete</th>
						</tr>
					</thead>
					<tbody>
							<%
							
							Locale locale  = new Locale("en", "UK");
							String pattern = "###.##";

							DecimalFormat decimalFormat = (DecimalFormat)
							        NumberFormat.getNumberInstance(locale);
							decimalFormat.applyPattern(pattern);
							
									int pageNo = ServletUtility.getPageNo(request);
									int pageSize = ServletUtility.getPageSize(request);
									int index = ((pageNo - 1) * pageSize) + 1;
									int size=ServletUtility.getSize(request);
									CartBean bean = null;
									List list = ServletUtility.getList(request);
									Iterator<CartBean> it = list.iterator();
									double total=0.0;
									int i=1;
									while (it.hasNext()) {
										bean = it.next();
								%>
						<tr>
							
							<td scope="row"><%=index++%></td>
							<td scope="row">
							<img src="<%=FPSView.APP_CONTEXT+FPSView.GET_IMAGE_VIEW%>?id=<%=bean.getItemId()%>&table=F_Items" width="100px" height="100px" ></td>
							<td scope="row"><%=bean.getItemName()%></td>
							<td scope="row"><%=bean.getPrice()%></td>
							<td scope="row"><input type="text" name="quantity<%=i++%>" class="form-control" value="<%=bean.getQuantity()%>"></td>
							<td scope="row"><%=decimalFormat.format(DataUtility.getDouble(bean.getTotalPrice()))%></td>
							<td><input type="submit" value="Update" name="operation" class="btn btn-primary btn btn-info" ></td>
																	<td><a href="cart?cId=<%=bean.getId()%>"
										class="btn btn-primary btn btn-info">Remove</a></td>
						</tr>
						<% total += DataUtility.getDouble(bean.getTotalPrice()); %>
						<%} %>
						<tr>
							<td colspan="5">Total</td>
							<td colspan="3"><%=total%></td>
						</tr>
						
					</tbody>
				</table>
				<div class="row">
				<div class="col-11">
				</div>
				<div class="col-1">
						<input type="submit" value="Checkout" name="operation" class="btn  btn-info">
				</div>
				</div>
				<br>	
				<div class="clearfix"></div>
						<ul class="pagination pull-right">
							<li><input type="submit" name="operation"
								class="btn btn-primary btn btn-info"
								value="<%=ItemListCtl.OP_PREVIOUS%>"
								<%=(pageNo == 1) ? "disabled" : ""%>></li>
							
							<li><input type="submit" name="operation"
								class="btn btn-primary btn btn-info"
								value="<%=CustomerListCtl.OP_NEXT%>"
								<%=((list.size() < pageSize) || size==pageNo*pageSize) ? "disabled" : ""%>></li>
						</ul>
			</div>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">
		</div>
		</form>
	</div>
	
	<%@ include file="Footer.jsp" %>
</body>
</html>