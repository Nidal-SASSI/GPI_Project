package in.co.fennel.project.ctl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


import in.co.fennel.project.bean.AdminBean;
import in.co.fennel.project.bean.BaseBean;
import in.co.fennel.project.bean.CustomerBean;
import in.co.fennel.project.bean.ItemBean;
import in.co.fennel.project.bean.OrderBean;
import in.co.fennel.project.exception.ApplicationException;
import in.co.fennel.project.exception.DuplicateRecordException;
import in.co.fennel.project.model.CustomerModel;
import in.co.fennel.project.model.ItemModel;
import in.co.fennel.project.model.OrderModel;
import in.co.fennel.project.util.DataUtility;
import in.co.fennel.project.util.DataValidator;
import in.co.fennel.project.util.PropertyReader;
import in.co.fennel.project.util.ServletUtility;

/**
 * Servlet implementation class OrderCtl
 */
@WebServlet(name = "OrderCtl", urlPatterns = { "/ctl/order" })
public class OrderCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(OrderCtl.class);

	/**
	 * Validate input Data Entered By User
	 * 
	 * @param request
	 * @return
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("OrderCtl validate method start");
		boolean pass = true;
		String op=DataUtility.getString(request.getParameter("operation"));
		if(OP_PAYMENT_BOOK.equalsIgnoreCase(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("address1"))) {
			request.setAttribute("address1", PropertyReader.getValue("error.require", "Address1"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("address2"))) {
			request.setAttribute("address2", PropertyReader.getValue("error.require", "Address2"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.invalid", "Email Id"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("quantity"))) {
			request.setAttribute("quantity", PropertyReader.getValue("error.require", "Quantity"));
			pass = false;
		}

		log.debug("OrderCtl validate method end");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("OrderCtl populateBean method start");
		OrderBean bean = new OrderBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		bean.setAddress1(DataUtility.getString(request.getParameter("address1")));
		bean.setAddress2(DataUtility.getString(request.getParameter("address2")));
		bean.setState(DataUtility.getString(request.getParameter("state")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setQuantity(DataUtility.getString(request.getParameter("quantity")));
		populateDTO(bean, request);
		log.debug("OrderCtl populateBean method end");
		return bean;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("OrderCtl doGet method start");

		OrderBean bean=(OrderBean)populateBean(request);
		
		long itemId = DataUtility.getLong(request.getParameter("iId"));
		HttpSession session = request.getSession();
		AdminBean abean = (AdminBean) session.getAttribute("user");
		try {
			CustomerBean cBean=new CustomerModel().findByUserName(abean.getLogin());
			session.setAttribute("item", new ItemModel().getRecordByID(itemId));
			session.setAttribute("customer", cBean);
			bean.setAddress1(cBean.getAddress());
			bean.setName(cBean.getFirstName()+" "+cBean.getSurName());
			bean.setEmail(cBean.getEmailID());
			bean.setMobileNo(cBean.getPhoneNo());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		ServletUtility.setBean(bean, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("OrderCtl doGet method end");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("OrderCtl doPost method start");
		String op = DataUtility.getString(request.getParameter("operation"));
		OrderModel model = new OrderModel();
		HttpSession session = request.getSession();
		long id = DataUtility.getLong(request.getParameter("id"));
		try {
			if (OP_CHECK_OUT.equalsIgnoreCase(op)) {
				OrderBean bean = (OrderBean) populateBean(request);
				ItemBean iBean = (ItemBean) session.getAttribute("item");
				AdminBean aBean = (AdminBean) session.getAttribute("user");
				bean.setItemId(iBean.getId());
				bean.setItemName(iBean.getName());
				bean.setCategory(iBean.getCategory());
				bean.setUserId(aBean.getId());
				bean.setTime_slot(new Date());
				bean.setOrderid(DataUtility.getRandom());
				double price =DataUtility.getDouble(iBean.getPrice())*DataUtility.getDouble(bean.getQuantity());
				bean.setTotal(String.valueOf(price));
				session.setAttribute("order", bean);
				ServletUtility.forward(FPSView.PAYMENT_VIEW, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(FPSView.ORDER_CTL, request, response);
				return;
			} else if (OP_PAYMENT_BOOK.equalsIgnoreCase(op)) {
				OrderBean orderBean = (OrderBean) session.getAttribute("order");
				orderBean.setStatus("Success");
				model.add(orderBean);
				ServletUtility.setSuccessMessage("Order Booked Successfully!!!", request);
				ServletUtility.forward(FPSView.SUCCESS_VIEW, request, response);
				return;
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("OrderCtl doPost method end");
	}

	@Override
	protected String getView() {
		return FPSView.ORDER_VIEW;
	}

}
