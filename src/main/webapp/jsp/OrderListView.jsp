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
<title>Item List</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<div class="container">
		<br>
		<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Item List</li>
			</ol>
		</nav>
		<br>
<form action="<%=FPSView.ITEM_LIST_CTL%>" method="post">
		<div class="card">
			<h5 class="card-header"
				style="background-color: #18334f; color: white;">Item List</h5>
			<div class="card-body">
				<div class="row g-3">
					<div class="col">
						<input type="text" name="name" class="form-control" placeholder=" Search By Name"
							aria-label="Search By First Name" value="<%=ServletUtility.getParameter("name", request)%>">
					</div>
					<div class="col">
						<input type="text" name="category" class="form-control" placeholder="Search By Category"
							aria-label=" Search By Category" value="<%=ServletUtility.getParameter("category", request)%>">
					</div>
					
					<div class="col">
						<input type="submit"
									class="btn btn-primary btn btn-info" name="operation"
									value="<%=ItemListCtl.OP_SEARCH%>">or<input type="submit"
									class="btn btn-primary btn btn-info" name="operation"
									value="<%=ItemListCtl.OP_RESET%>">
					</div>
				</div>
				<b><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></b>
							<b><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></b>
				<br>
				<table class="table table-bordered border-primary">
					<thead>
						<tr>
						<th scope="col"><input type="checkbox" id="selectall">Select
										All</th>
							<th scope="col">#</th>
							<th scope="col">Image</th>
							<th scope="col">Name</th>
							<th scope="col">Price</th>
							<th scope="col">Stock</th>
							<th scope="col">Quantities</th>
							<th scope="col">Category</th>
							<th scope="col">Designation</th>
							<th scope="col">Description</th>
							<th scope="col">Action</th>
						</tr>
					</thead>
					<tbody>
							<%
									int pageNo = ServletUtility.getPageNo(request);
									int pageSize = ServletUtility.getPageSize(request);
									int index = ((pageNo - 1) * pageSize) + 1;
									int size=ServletUtility.getSize(request);
									ItemBean bean = null;
									List list = ServletUtility.getList(request);
									Iterator<ItemBean> it = list.iterator();
									while (it.hasNext()) {
										bean = it.next();
								%>
						<tr>
							<td scope="row"><input type="checkbox" class="case" name="ids"
										value="<%=bean.getId()%>"></td>
							<td scope="row"><%=index++%></td>
							<td scope="row">
							<img src="<%=FPSView.APP_CONTEXT+FPSView.GET_IMAGE_VIEW%>?id=<%=bean.getId()%>&table=F_Items" width="100px" height="100px" ></td>
							<td scope="row"><%=bean.getName()%></td>
							<td scope="row"><%=bean.getPrice()%></td>
							<td scope="row"><%=bean.getStock()%></td>
							<td scope="row"><%=bean.getQuantities()%></td>
							<td scope="row"><%=bean.getCategory()%></td>
							<td scope="row"><%=bean.getDesignation()%></td>
							<td scope="row"><%=bean.getDescription()%></td>
							<td><a href="item?id=<%=bean.getId()%>"
										class="btn btn-primary btn btn-info">Edit</a></td>
						</tr>
						<%} %>
					</tbody>
				</table>
				<div class="clearfix"></div>
						<ul class="pagination pull-right">
							<li><input type="submit" name="operation"
								class="btn btn-primary btn btn-info"
								value="<%=ItemListCtl.OP_PREVIOUS%>"
								<%=(pageNo == 1) ? "disabled" : ""%>></li>
							<li><input type="submit" name="operation"
								class="btn btn-primary btn btn-info"
								value="<%=ItemListCtl.OP_NEW%>"></li>
							<li><input type="submit" name="operation"
								class="btn btn-primary btn btn-info"
								value="<%=ItemListCtl.OP_DELETE%>"
								<%=(list.size() == 0) ? "disabled" : ""%>></li>
							<%
								ItemModel model = new ItemModel();
							%>
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