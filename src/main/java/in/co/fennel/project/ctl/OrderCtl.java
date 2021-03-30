package in.co.fennel.project.ctl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.fennel.project.bean.AdminBean;
import in.co.fennel.project.bean.BaseBean;
import in.co.fennel.project.bean.CartBean;
import in.co.fennel.project.bean.CustomerBean;
import in.co.fennel.project.bean.ItemBean;
import in.co.fennel.project.bean.OrderBean;
import in.co.fennel.project.exception.ApplicationException;
import in.co.fennel.project.exception.DuplicateRecordException;
import in.co.fennel.project.model.CartModel;
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

		OrderBean bean = (OrderBean) populateBean(request);

		HttpSession session = request.getSession();
		AdminBean abean = (AdminBean) session.getAttribute("user");
		try {
			CustomerBean cBean = new CustomerModel().findByUserName(abean.getLogin());
			session.setAttribute("customer", cBean);
			bean.setAddress1(cBean.getAddress());
			bean.setName(cBean.getFirstName() + " " + cBean.getSurName());
			bean.setEmail(cBean.getEmailID());
			bean.setMobileNo(cBean.getPhoneNo());
			AdminBean adminBean = (AdminBean) request.getSession().getAttribute("user");
			CartBean crBean = new CartBean();
			if (adminBean.getRoleId() == 2) {
				crBean.setUserId(adminBean.getId());
			}
			List<CustomerBean> list = new CartModel().search(crBean);
			ServletUtility.setList(list, request);
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
			if (OP_PAYMENT.equalsIgnoreCase(op)) {
				ArrayList<OrderBean> list = new ArrayList<OrderBean>();
				AdminBean aBean = (AdminBean) session.getAttribute("user");
				CartBean cBean = new CartBean();
				cBean.setUserId(aBean.getId());
				List<CartBean> cartList = new CartModel().search(cBean);
				ServletUtility.setList(cartList, request);
				long orderId=DataUtility.getRandom();
				session.setAttribute("orderId",orderId);
				OrderBean obean = (OrderBean) populateBean(request);
				session.setAttribute("orderBean",obean);
				for (CartBean crBean : cartList) {
					OrderBean bean = (OrderBean) populateBean(request);
					bean.setOrderid(orderId);
					bean.setUserId(aBean.getId());
					bean.setTime_slot(new Date());
					bean.setStatus("Success");
					bean.setItemId(crBean.getItemId());
					bean.setItemName(crBean.getItemName());
					bean.setCategory(new ItemModel().getRecordByID(crBean.getId()).getCategory());
					bean.setQuantity(crBean.getQuantity());
					bean.setTotal(crBean.getTotalPrice());
					list.add(bean);
					new CartModel().delete(crBean);
				}
				session.setAttribute("orderList", list);
				ServletUtility.forward(FPSView.PAYMENT_VIEW, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(FPSView.ORDER_CTL, request, response);
				return;
			} else if (OP_PAYMENT_BOOK.equalsIgnoreCase(op)) {
				List<OrderBean> orderList = (List<OrderBean>) session.getAttribute("orderList");
				for (OrderBean orBean : orderList) {
					model.add(orBean);
				}
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
