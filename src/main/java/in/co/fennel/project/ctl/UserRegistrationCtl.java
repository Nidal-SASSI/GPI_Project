package in.co.fennel.project.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.fennel.project.bean.AdminBean;
import in.co.fennel.project.bean.BaseBean;
import in.co.fennel.project.bean.CustomerBean;
import in.co.fennel.project.exception.ApplicationException;
import in.co.fennel.project.exception.DuplicateRecordException;
import in.co.fennel.project.model.AdminModel;
import in.co.fennel.project.model.CustomerModel;
import in.co.fennel.project.util.DataUtility;
import in.co.fennel.project.util.DataValidator;
import in.co.fennel.project.util.PropertyReader;
import in.co.fennel.project.util.ServletUtility;

/**
 * Servlet implementation class CustomerCtl
 */
@WebServlet(name="UserRegistrationCtl",urlPatterns={"/userRegistration"})
public class UserRegistrationCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log=Logger.getLogger(UserRegistrationCtl.class);
	/**
	 * Validate input Data Entered By User
	 * 
	 * @param request
	 * @return
	 */
	@Override
    protected boolean validate(HttpServletRequest request) {
		log.debug("CustomerCtl validate method start");
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("firstName"))) {
            request.setAttribute("firstName",
                    PropertyReader.getValue("error.require", "firstName"));
            pass = false;
        }
        
        if (DataValidator.isNull(request.getParameter("lastName"))) {
            request.setAttribute("lastName",
                    PropertyReader.getValue("error.require", "Last Name"));
            pass = false;
        }
        
        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob",
                    PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        }
        
        if (DataValidator.isNull(request.getParameter("PSC"))) {
            request.setAttribute("PSC",
                    PropertyReader.getValue("error.require", "Professional Social Category"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("address"))) {
            request.setAttribute("address",
                    PropertyReader.getValue("error.require", "Address"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("phoneNo"))) {
            request.setAttribute("phoneNo",
                    PropertyReader.getValue("error.require", "Phone No"));
            pass = false;
        }
        
        if (DataValidator.isNull(request.getParameter("email"))) {
            request.setAttribute("email",
                    PropertyReader.getValue("error.require", "Email Id"));
            pass = false;
        }else if(!DataValidator.isEmail(request.getParameter("email"))) {
        	request.setAttribute("email",
                    PropertyReader.getValue("error.invalid", "Email Id"));
            pass = false;
        }
        
        if (DataValidator.isNull(request.getParameter("CC"))) {
            request.setAttribute("CC",
                    PropertyReader.getValue("error.require", "Commercial Categories"));
            pass = false;
        }
        
        if (DataValidator.isNull(request.getParameter("userName"))) {
            request.setAttribute("userName",
                    PropertyReader.getValue("error.require", "User Name"));
            pass = false;
        }
        
        if (DataValidator.isNull(request.getParameter("password"))) {
            request.setAttribute("password",
                    PropertyReader.getValue("error.require", "Password"));
            pass = false;
        }
        
        

        log.debug("CustomerCtl validate method end");
        return pass;
    }
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("CustomerCtl populateBean method start");
		CustomerBean bean=new CustomerBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setSurName(DataUtility.getString(request.getParameter("lastName")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setProfessionalSocialCategory(DataUtility.getString(request.getParameter("PSC")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));
		bean.setEmailID(DataUtility.getString(request.getParameter("email")));
		bean.setCommercialCategories(DataUtility.getString(request.getParameter("CC")));
		bean.setUserName(DataUtility.getString(request.getParameter("userName")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		populateDTO(bean, request);
		log.debug("CustomerCtl populateBean method end");
		return bean;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("CustomerCtl doGet method start"); 
		String op = DataUtility.getString(request.getParameter("operation"));
			
		   CustomerModel model = new CustomerModel();
			long id = DataUtility.getLong(request.getParameter("id"));
			ServletUtility.setOpration("Add", request);
			if (id > 0 || op != null) {
				System.out.println("in id > 0  condition");
				CustomerBean bean;
				try {
					bean = model.getRecordByID(id);
					ServletUtility.setOpration("Edit", request);
					ServletUtility.setBean(bean, request);
				} catch (ApplicationException e) {
					ServletUtility.handleException(e, request, response);
					return;
				}
			}

			ServletUtility.forward(getView(), request, response);
			log.debug("CustomerCtl doGet method end");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("CustomerCtl doPost method start");
		String op=DataUtility.getString(request.getParameter("operation"));
		CustomerModel model=new CustomerModel();
		long id=DataUtility.getLong(request.getParameter("id"));
		if(OP_SAVE.equalsIgnoreCase(op)){
			
			CustomerBean bean=(CustomerBean)populateBean(request);
				try {
					if(id>0){
						
					model.update(bean);
					ServletUtility.setOpration("Edit", request);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
	                ServletUtility.setBean(bean, request);

					}else {
						
						AdminBean aBean=new AdminBean();
						aBean.setLogin(bean.getUserName());
						aBean.setPassword(bean.getPassword());
						aBean.setFirstName(bean.getFirstName());
						aBean.setLastName(bean.getSurName());
						aBean.setRoleId(2L);
						new AdminModel().add(aBean);
						long pk=model.addCustomer(bean);						
						//bean.setId(id);
						ServletUtility.setSuccessMessage("Data is successfully Saved", request);
						ServletUtility.forward(getView(), request, response);
					}
	              
				} catch (ApplicationException e) {
					e.printStackTrace();
					ServletUtility.forward(FPSView.ERROR_VIEW, request, response);
					return;
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(e.getMessage(),
						request);
			}
			
		}else if (OP_RESET.equalsIgnoreCase(op)) {
		ServletUtility.redirect(FPSView.USER_REGISTRATION_CTL, request, response);
		return;
}
				
		
		ServletUtility.forward(getView(), request, response);
		 log.debug("CustomerCtl doPost method end");
	}

	@Override
	protected String getView() {
		return FPSView.USER_REGISTRATION_VIEW;
	}

}
