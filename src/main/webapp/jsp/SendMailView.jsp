<%@page import="in.co.fennel.project.ctl.SendMailCtl"%>
<%@page import="in.co.fennel.project.bean.SendMail"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.fennel.project.util.HTMLUtility"%>
<%@page import="java.util.Map"%>
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
<title>Send Mail</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<div class="container">
		<br>
		<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Send
					Mail</li>
			</ol>
		</nav>
		<br>
		<form action="<%=FPSView.SEND_MAIL%>" method="post">
			<div class="card">
				<h5 class="card-header"
					style="background-color: #18334f; color: white;">Send Mail</h5>
				<div class="card-body">
					<div class="row g-3">
						<div class="col">
							<input type="text" name="firstName" class="form-control"
								placeholder="Search By First name"
								aria-label="Search By First Name"
								value="<%=ServletUtility.getParameter("firstName", request)%>">
						</div>
						<div class="col">
							<input type="text" name="email" class="form-control"
								placeholder="Search By Email Id" aria-label=" Search By Email"
								value="<%=ServletUtility.getParameter("email", request)%>">
						</div>

						<div class="col">
							<input type="submit" class="btn btn-primary btn btn-info"
								name="operation" value="<%=CustomerListCtl.OP_SEARCH%>">or<input
								type="submit" class="btn btn-primary btn btn-info"
								name="operation" value="<%=CustomerListCtl.OP_RESET%>">
						</div>
					</div>
				<br>
					<div class="row g-3">
						<div class="col-sm-6">
							<%
							HashMap map = (HashMap) request.getAttribute("catMap");
							%>
							<label>Category</label>
							<%=HTMLUtility.getList("category", ServletUtility.getParameter("category", request), map)%>
							<font color="red"><%=ServletUtility.getErrorMessage("category", request)%></font>
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
								<th scope="col">First Name</th>
								<th scope="col">Last Name</th>
								<th scope="col">Email Id</th>
							</tr>
						</thead>
						<tbody>
							<%
							int pageNo = ServletUtility.getPageNo(request);
							int pageSize = ServletUtility.getPageSize(request);
							int index = ((pageNo - 1) * pageSize) + 1;
							int size = ServletUtility.getSize(request);
							CustomerBean bean = null;
							List list = ServletUtility.getList(request);
							Iterator<CustomerBean> it = list.iterator();
							while (it.hasNext()) {
								bean = it.next();
							%>
							<tr>
								<td scope="row"><input type="checkbox" class="case"
									name="ids" value="<%=bean.getId()%>"></td>
								<td scope="row"><%=index++%></td>
								<td scope="row"><%=bean.getFirstName()%></td>
								<td scope="row"><%=bean.getSurName()%></td>
								<td scope="row"><%=bean.getEmailID()%></td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
					<div class="clearfix"></div>
					<ul class="pagination pull-right">
						<li><input type="submit" name="operation"
							class="btn btn-primary btn btn-info"
							value="<%=CustomerListCtl.OP_PREVIOUS%>"
							<%=(pageNo == 1) ? "disabled" : ""%>></li>
						<li></li>

						<li><input type="submit" name="operation"
							class="btn btn-primary btn btn-info"
							value="<%=SendMailCtl.OP_SEND%>"
							<%=(list.size() == 0) ? "disabled" : ""%>></li>
						<li></li>


						<li><input type="submit" name="operation"
							class="btn btn-primary btn btn-info"
							value="<%=CustomerListCtl.OP_NEXT%>"
							<%=((list.size() < pageSize) || size == pageNo * pageSize) ? "disabled" : ""%>></li>
					</ul>
				</div>
				<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
					type="hidden" name="pageSize" value="<%=pageSize%>">
			</div>
		</form>
	</div>

	<%@ include file="Footer.jsp"%>
</body>
</html>