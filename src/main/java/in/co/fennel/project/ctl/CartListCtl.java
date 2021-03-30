package in.co.fennel.project.ctl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.fennel.project.bean.AdminBean;
import in.co.fennel.project.bean.BaseBean;
import in.co.fennel.project.bean.CartBean;
import in.co.fennel.project.exception.ApplicationException;
import in.co.fennel.project.exception.DuplicateRecordException;
import in.co.fennel.project.model.CartModel;
import in.co.fennel.project.util.DataUtility;
import in.co.fennel.project.util.PropertyReader;
import in.co.fennel.project.util.ServletUtility;

/**
 * Servlet implementation class CartListCtl
 */
@WebServlet(name = "CartListCtl", urlPatterns = { "/ctl/cart" })
public class CartListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CartListCtl.class);

	/**
	 * Populates bean object from request parameters
	 * 
	 * @param request
	 * @return
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("CartListCtl populateBean method start");
		CartBean bean = new CartBean();
		log.debug("CartListCtl populateBean method end");
		return bean;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CartListCtl doGet method start");
		List list = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		CartModel model = new CartModel();
		CartBean bean = (CartBean) populateBean(request);

		long cId = DataUtility.getLong(request.getParameter("cId"));

		try {

			if (cId > 0) {
				CartBean cBean = new CartBean();
				cBean.setId(cId);
				model.delete(cBean);
			}

			AdminBean adminBean = (AdminBean) request.getSession().getAttribute("user");
			if (adminBean.getRoleId() == 2) {
				bean.setUserId(adminBean.getId());
			}
			list = model.search(bean, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setSize(model.search(bean).size(), request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			e.printStackTrace();
			return;
		}
		log.debug("CartListCtl doGet method end");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CartListCtl doPost method start");
		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		CartBean bean = (CartBean) populateBean(request);

		CartModel model = new CartModel();
		String[] ids = request.getParameterValues("ids");
		String op = DataUtility.getString(request.getParameter("operation"));

		AdminBean adminBean = (AdminBean) request.getSession().getAttribute("user");
		if (adminBean.getRoleId() == 2) {
			bean.setUserId(adminBean.getId());
		}

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {

					pageNo = 1;

				} else if (OP_NEXT.equalsIgnoreCase(op)) {

					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {

					pageNo--;
				}
			} else if ("Update".equalsIgnoreCase(op)) {
				List<CartBean> cList = model.search(bean);
				Iterator<CartBean> it = cList.iterator();
				int index = 1;
				while (it.hasNext()) {
					CartBean cartBean = (CartBean) it.next();
					String quan = DataUtility.getString(request.getParameter("quantity" + index++));
					System.out.println("Qnatity--------"+quan);
					cartBean.setQuantity(quan);
					cartBean.setTotalPrice(String.valueOf(DataUtility.getDouble(cartBean.getPrice()) * DataUtility.getDouble(quan)));
					model.update(cartBean);
				}
			}else if(OP_CHECK_OUT.equalsIgnoreCase(op)) {
				ServletUtility.redirect(FPSView.ORDER_CTL, request, response);
				return;
			}
			
			list = model.search(bean, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("NO Record Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setSize(model.search(bean).size(), request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			e.printStackTrace();
			return;
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

		log.debug("CartListCtl doPost method end");
	}

	@Override
	protected String getView() {
		return FPSView.CART_VIEW;
	}

}
