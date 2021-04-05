<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="in.co.fennel.project.util.DataUtility"%>
<%@page import="in.co.fennel.project.bean.OrderBean"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.fennel.project.bean.ItemBean"%>
<%@page import="in.co.fennel.project.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Success</title>
<style type="text/css">
body {
	background: grey;
	margin-top: 120px;
	margin-bottom: 120px;
}
</style>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<div class="container">
		<br>
		<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Invoice</li>
			</ol>
		</nav>

		<br>

		<div class="container">
			<div class="row">
				<div class="col-12">
					<div class="card">
						<div class="card-body">
							<div class="row" style="margin: 5px">
								<div class="col-md-6 text-right">
									<%
									long orderId = (long) session.getAttribute("orderId");
									OrderBean orderBean = (OrderBean) session.getAttribute("orderBean");
									
									List<OrderBean> list=(List<OrderBean>)session.getAttribute("orderList");
									
									%>
									<p class="font-weight-bold mb-1">
										Invoice #<%=orderId%></p>
									<p class="text-muted"><%=new Date()%></p>
								</div>
							</div>
							<hr class="my-5">
							<div class="row">
								<div class="col-md-6">
									<p class="font-weight-bold mb-4">Customer Information</p>
									<p class="mb-1"><%=orderBean.getName()%></p>
									<p><%=orderBean.getEmail()%></p>
									<p class="mb-1"><%=orderBean.getState() + "," + orderBean.getCity()%>,<%=orderBean.getAddress1()%></p>
									<p class="mb-1"><%=orderBean.getAddress2()%></p>
								</div>
							</div>
							<hr class="my-5">
							<div class="row ">
								<div class="col-md-12">
									<table class="table">
										<thead>
											<tr>
												<th class="border-0 text-uppercase small font-weight-bold">#</th>
												<th class="border-0 text-uppercase small font-weight-bold">Item</th>
												<th class="border-0 text-uppercase small font-weight-bold">Quantity</th>
												<th class="border-0 text-uppercase small font-weight-bold">Unit
													Cost</th>
												<th class="border-0 text-uppercase small font-weight-bold">Total</th>
											</tr>
										</thead>
										<tbody>
										<%Locale locale  = new Locale("en", "UK");
										String pattern = "###.##";

										DecimalFormat decimalFormat = (DecimalFormat)
										        NumberFormat.getNumberInstance(locale);
										decimalFormat.applyPattern(pattern); %>
										
										<% int i=1; 
											
										double total=0.0;
										for(OrderBean bean : list){ %>
											<tr>
												<td><%=i++%></td>
												<td><%=bean.getItemName()%></td>
												<td><%=bean.getQuantity()%></td>
												<td><%=decimalFormat.format(DataUtility.getDouble(bean.getTotal())/DataUtility.getDouble(bean.getQuantity()))%></td>
												<td><%=bean.getTotal()%></td>
											</tr>
											<%
											total +=DataUtility.getDouble(bean.getTotal());
										} %>
										</tbody>
									</table>
								</div>
							</div>

							<div class="d-flex flex-row-reverse bg-dark text-white p-4">
								<div class="py-3 px-5 text-right">
									<div class="mb-2">Grand Total</div>
									<div class="h2 font-weight-light"><%=decimalFormat.format(total)%></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>

	</div>

	<%@ include file="Footer.jsp"%>
</body>
</html>