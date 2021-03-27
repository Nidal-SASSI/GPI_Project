<%@page import="in.co.fennel.project.ctl.LoginCtl"%>
<%@page import="in.co.fennel.project.bean.AdminBean"%>
<%@page import="in.co.fennel.project.ctl.FPSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
	crossorigin="anonymous"></script>
<link rel="canonical"
	href="https://getbootstrap.com/docs/5.0/examples/offcanvas/">

<title>Fennel Project</title>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : 'mm/dd/yy',
			changeMonth : true,
			changeYear : true,
			defaultDate : '01/01/1990'
		});
	});
</script>

<script language="javascript">
	$(function() {
		$("#selectall").click(function() {
			$('.case').attr('checked', this.checked);
		});
		$(".case").click(function() {

			if ($(".case").length == $(".case:checked").length) {
				$("#selectall").attr("checked", "checked");
			} else {
				$("#selectall").removeAttr("checked");
			}

		});
	});
</script>

<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}
</style>
</head>
<body>

	<%
	AdminBean userBean = (AdminBean) session.getAttribute("user");

	boolean userLoggedIn = userBean != null;

	String welcomeMsg = "Hi, ";

	if (userLoggedIn) {
		String role = (String) session.getAttribute("role");
		welcomeMsg += userBean.getFirstName() + " (Admin)";
	} else {
		welcomeMsg += "Guest";
	}
	%>

	<nav class="navbar navbar-expand-lg fixed-top navbar-dark"
		style="background-color: #18334f" aria-label="Main navigation">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">Fenouil</a>
			<button class="navbar-toggler p-0 border-0" type="button"
				data-bs-toggle="offcanvas" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="navbar-collapse offcanvas-collapse"
				id="navbarsExampleDefault">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">

					<%
					if (userLoggedIn) {
					%>
					<%
					if (userBean.getRoleId() == 1) {
					%>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="dropdown01"
						data-bs-toggle="dropdown" aria-expanded="false">Customer</a>
						<ul class="dropdown-menu" aria-labelledby="dropdown01">
							<li><a class="dropdown-item" href="<%=FPSView.CUSTOMER_CTL%>">Add Customer</a></li>
							<li><a class="dropdown-item" href="<%=FPSView.CUSTOMER_LIST_CTL%>">Customer List</a></li>
							
						</ul></li>
						
						<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="dropdown01"
						data-bs-toggle="dropdown" aria-expanded="false">Advertisement</a>
						<ul class="dropdown-menu" aria-labelledby="dropdown01">
							<li><a class="dropdown-item" href="<%=FPSView.ADVERTISEMENT_CTL%>">Add Advertisement</a></li>
							<li><a class="dropdown-item" href="<%=FPSView.ADVERTISEMENT_LIST_CTL%>">Advertisement
									List</a>
						</ul></li>
						
						<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="dropdown01"
						data-bs-toggle="dropdown" aria-expanded="false">Item</a>
						<ul class="dropdown-menu" aria-labelledby="dropdown01">
							<li><a class="dropdown-item" href="<%=FPSView.ITEM_CTL%>">Item</a></li>
							<li><a class="dropdown-item" href="<%=FPSView.ITEM_LIST_CTL%>">Item
									List</a>
						</ul></li>
						<li class="nav-item"><a class="nav-link"
						href="<%=FPSView.SEND_MAIL%>">Send Mail</a></li>
						<li class="nav-item"><a class="nav-link"
						href="<%=FPSView.ORDER_LIST_CTL%>">Order Report</a></li>
					<%
					}else if(userBean.getRoleId()==2){
					%>
							<li class="nav-item"><a class="nav-link"
						href="<%=FPSView.USER_ITEM_LIST_CTL%>">Items</a></li>
						
						<li class="nav-item"><a class="nav-link"
						href="<%=FPSView.ORDER_LIST_CTL%>">Order Report</a></li>
					<%
					}} else {
					%>

					<li class="nav-item"><a class="nav-link"
						href="<%=FPSView.WELCOME_CTL%>">Home</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<%=FPSView.LOGIN_CTL%>">Login</a></li>
					<%
					}
					%>

				</ul>
				<%if(userLoggedIn){ %>
				<form class="d-flex">
				<a class="nav-link"
						href="<%=FPSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">Logout</a>
				</form>
				<%} %>
			</div>
		</div>
	</nav>
	<div style="margin-top: 57px"></div>
</body>
</html>