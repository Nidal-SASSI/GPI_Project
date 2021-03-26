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
<title>Advertisement</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<div class="container">
		<br>
		<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="<%=FPSView.WELCOME_CTL%>">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Advertisement</li>
			</ol>
		</nav>
		<br>

		<div class="card">
			<h5 class="card-header"
				style="background-color: #18334f; color: white;">Advertisement</h5>
			<div class="card-body">
			<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font></b>
				<form class="row g-3" method="post" action="<%=FPSView.ADVERTISEMENT_CTL%>" enctype="multipart/form-data">
				
				<jsp:useBean id="bean"
							class="in.co.fennel.project.bean.AdvertisementBean" scope="request"></jsp:useBean>


						<input type="hidden" name="id" value="<%=bean.getId()%>">
						<input type="hidden" name="createdBy"
							value="<%=bean.getCreatedBy()%>"> <input type="hidden"
							name="modifiedBy" value="<%=bean.getModifiedBy()%>"> <input
							type="hidden" name="createdDatetime"
							value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
				
				<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Advertisement Name</label>
						 <input type="text" name="adName" placeholder="Enter Advertisement Name" class="form-control" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getAdv_name())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("adName", request)%></font>
					</div>
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Advertisement Price</label>
						 <input type="text" name="adPrice" placeholder="Enter Advertisement Price" class="form-control" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getAdv_price())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("adPrice", request)%></font>
					</div>
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Sender</label>
						<input type="text" name="sender" class="form-control" placeholder="Enter Sender" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getSender())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("sender", request)%></font>
					</div>
					<div class="col-md-6">
						<label for="inputPassword4" class="form-label">Recipient Advertisement</label>
						 <input type="text" name="recipentAd"  placeholder="Enter Recipient Advertisement" class="form-control" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getRecipient_adv())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("recipentAd", request)%></font>
					</div>
					<% HashMap map=(HashMap)request.getAttribute("catMap"); %>
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Advertisement Category</label>
						<%=HTMLUtility.getList("adCategory",String.valueOf(bean.getAdv_category()), map) %>
						 <font color="red"><%=ServletUtility.getErrorMessage("adCategory", request)%></font>
					</div>
					<div class="col-md-6">
						<label for="inputPassword4" class="form-label">Advertisement Image</label>
						 <input type="file" required="required" name="image"   class="form-control" id="inputEmail4">
					</div>
					<div class="col-12">
						<input type="submit" name="operation"
									value="<%=CustomerCtl.OP_SAVE%>"
									class="btn btn-primary btn btn-info" />
									 or <input type="submit" name="operation" value="<%=CustomerCtl.OP_RESET %>" class="btn  btn-primary btn btn-info"  />
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>