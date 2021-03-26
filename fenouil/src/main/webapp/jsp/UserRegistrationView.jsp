<%@page import="in.co.fennel.project.ctl.UserRegistrationCtl"%>
<%@page import="in.co.fennel.project.ctl.CustomerCtl"%>
<%@page import="in.co.fennel.project.util.DataUtility"%>
<%@page import="in.co.fennel.project.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Registration</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<div class="container">
		<br>
		<nav style="-bs-breadcrumb-divider: '&gt;';" aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="<%=FPSView.WELCOME_CTL%>">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">User Registration</li>
			</ol>
		</nav>
		<br>

		<div class="card">
			<h5 class="card-header"
				style="background-color: #18334f; color: white;">User Registration</h5>
			<div class="card-body">
			<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font></b>
				<form class="row g-3" method="post" action="<%=FPSView.USER_REGISTRATION_CTL%>">
				
				<jsp:useBean id="bean"
							class="in.co.fennel.project.bean.CustomerBean" scope="request"></jsp:useBean>


						<input type="hidden" name="id" value="<%=bean.getId()%>">
						<input type="hidden" name="createdBy"
							value="<%=bean.getCreatedBy()%>"> <input type="hidden"
							name="modifiedBy" value="<%=bean.getModifiedBy()%>"> <input
							type="hidden" name="createdDatetime"
							value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
				
				<div class="col-md-6">
						<label for="inputEmail4" class="form-label">First Name</label>
						 <input type="text" name="firstName" placeholder="Enter FirstName" class="form-control" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getFirstName())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</div>
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Last Name</label>
						 <input type="text" name="lastName" placeholder="Enter Last Name" class="form-control" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getSurName())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font>
					</div>
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Email</label>
						<input type="text" name="email" class="form-control" placeholder="Enter Email" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getEmailID())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("email", request)%></font>
					</div>
					<div class="col-md-6">
						<label for="inputPassword4" class="form-label">Date Of Birth</label>
						 <input type="text" name="dob" id="datepicker" placeholder="Enter Date Of Birth" class="form-control" id="inputEmail4"
						 value="<%=DataUtility.getDateString(bean.getDob())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font>
					</div>
					
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Professional Social Category</label>
						<input type="text" name="PSC" placeholder="Enter Professional Social Category" class="form-control" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getProfessionalSocialCategory())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("PSC", request)%></font>
					</div>
					<div class="col-md-6">
						<label for="inputPassword4" class="form-label">Commercial Categories</label>
						 <input type="text" name="CC"  class="form-control" placeholder="Enter Commercial Categories" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getCommercialCategories())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("CC", request)%></font>
					</div>
					
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">UserName</label>
						<input type="text" name="userName" placeholder="Enter User Name" class="form-control" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getPhoneNo())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("userName", request)%></font>
					</div>
					
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Password</label>
						<input type="password" name="password" placeholder="Enter Password" class="form-control" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getPhoneNo())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("password", request)%></font>
					</div>
					
					<div class="col-md-6">
						<label for="inputEmail4" class="form-label">Phone No</label>
						<input type="text" name="phoneNo" placeholder="Enter Phone No" class="form-control" id="inputEmail4"
						 value="<%=DataUtility.getStringData(bean.getPhoneNo())%>" >
						 <font color="red"><%=ServletUtility.getErrorMessage("phoneNo", request)%></font>
					</div>
					<div class="col-md-6">
						<label for="inputPassword4" class="form-label">Address</label>
						 <textarea  name="address" rows="4" cols="4" placeholder="Enter Address" class="form-control" id="inputEmail4">
						 <%=DataUtility.getStringData(bean.getCommercialCategories())%></textarea>
						 <font color="red"><%=ServletUtility.getErrorMessage("address", request)%></font>
					</div>
					
					
					<div class="col-12">
						<input type="submit" name="operation"
									value="<%=UserRegistrationCtl.OP_SAVE%>"
									class="btn btn-primary btn btn-info" />
									 or <input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_RESET %>" class="btn  btn-primary btn btn-info"  />
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>