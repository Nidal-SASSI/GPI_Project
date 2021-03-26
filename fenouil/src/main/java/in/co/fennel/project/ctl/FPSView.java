package in.co.fennel.project.ctl;

public interface FPSView {
	
	public String APP_CONTEXT = "/Fennel";

	public String LAYOUT_VIEW = "/BaseLayout.jsp";
	public String PAGE_FOLDER = "/jsp";

	public String JAVA_DOC_VIEW = APP_CONTEXT + "/doc/index.html";

	public String ERROR_VIEW = PAGE_FOLDER + "/Error.jsp";

	
	
	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";	
	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	
	public String CUSTOMER_VIEW = PAGE_FOLDER + "/CustomerView.jsp";	
	public String CUSTOMER_LIST_VIEW = PAGE_FOLDER + "/CustomerListView.jsp";
	
	public String ADVERTISEMENT_VIEW = PAGE_FOLDER + "/AdvertisementView.jsp";	
	public String ADVERTISEMENT_LIST_VIEW = PAGE_FOLDER + "/AdvertisementListView.jsp";
	
	public String ITEM_VIEW = PAGE_FOLDER + "/ItemView.jsp";	
	public String ITEM_LIST_VIEW = PAGE_FOLDER + "/ItemListView.jsp";
	
	public String ORDER_VIEW = PAGE_FOLDER + "/OrderView.jsp";	
	public String ORDER_LIST_VIEW = PAGE_FOLDER + "/OrderListView.jsp";
	
	public String USER_ITEM_LIST_VIEW = PAGE_FOLDER + "/UserItemListView.jsp";
		
	public String INDEX_VIEW ="/index.jsp";
	
	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	
	
	public String PAYMENT_VIEW = PAGE_FOLDER + "/PaymentView.jsp";
	
	public String SUCCESS_VIEW = PAGE_FOLDER + "/SuccessView.jsp";
	
	
	public String SEND_MAIL_VIEW = PAGE_FOLDER + "/SendMailView.jsp";
	
	public String GET_IMAGE_VIEW = PAGE_FOLDER + "/getImage.jsp";
	
	public String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";
	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";

	
	

	public String ERROR_CTL = "/ctl/ErrorCtl";

	
	
	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";
	
	public String ORDER_CTL = APP_CONTEXT + "/ctl/order";
	public String ORDER_LIST_CTL = APP_CONTEXT + "/ctl/orderList";
	
	public String CUSTOMER_CTL = APP_CONTEXT + "/ctl/customer";
	public String CUSTOMER_LIST_CTL = APP_CONTEXT + "/ctl/customerList";
	
	public String ADVERTISEMENT_CTL = APP_CONTEXT + "/ctl/advertisement";
	public String ADVERTISEMENT_LIST_CTL = APP_CONTEXT + "/ctl/advertisementList";
	public String ITEM_CTL = APP_CONTEXT + "/ctl/item";
	public String ITEM_LIST_CTL = APP_CONTEXT + "/ctl/itemList";
	public String USER_ITEM_LIST_CTL = APP_CONTEXT + "/ctl/userItemList";
	
	public String SEND_MAIL = APP_CONTEXT + "/ctl/sendMail";
	
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/userRegistration";
	public String LOGIN_CTL = APP_CONTEXT + "/login";
	public String WELCOME_CTL = APP_CONTEXT + "/welcome";
	public String LOGOUT_CTL = APP_CONTEXT + "/login";
	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";



}
