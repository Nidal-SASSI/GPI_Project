package in.co.fennel.project.ctl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.fennel.project.bean.BaseBean;
import in.co.fennel.project.bean.CustomerBean;
import in.co.fennel.project.bean.SendMail;
import in.co.fennel.project.exception.ApplicationException;
import in.co.fennel.project.model.CustomerModel;
import in.co.fennel.project.util.DataUtility;
import in.co.fennel.project.util.EmailBuilder;
import in.co.fennel.project.util.EmailMessage;
import in.co.fennel.project.util.EmailUtility;
import in.co.fennel.project.util.PropertyReader;
import in.co.fennel.project.util.ServletUtility;

/**
 * Servlet implementation class ItemCtl
 */
@WebServlet(name = "SendMailCtl", urlPatterns = { "/ctl/sendMail" })
public class SendMailCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(SendMailCtl.class);

	/**
	 * Validate input Data Entered By User
	 * 
	 * @param request
	 * @return
	 */


	@Override
	protected void preload(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Decoration", "Decoration");
		map.put("DIY", "DIY");
		map.put("Gardening", "Gardening");
		request.setAttribute("catMap", map);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("ItemCtl populateBean method start");
		CustomerBean bean = new CustomerBean();
		bean.setCategory(DataUtility.getString(request.getParameter("category")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setEmailID(DataUtility.getString(request.getParameter("email")));
		log.debug("ItemCtl populateBean method end");
		return bean;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("ItemCtl doGet method start");
		List list = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		CustomerModel model = new CustomerModel();
		CustomerBean bean = (CustomerBean) populateBean(request);
		try {
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
		log.debug("ItemCtl doGet method end");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("ItemCtl doPost method start");
		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		CustomerBean bean = (CustomerBean) populateBean(request);

		CustomerModel model = new CustomerModel();
		String[] ids = request.getParameterValues("ids");
		String op = DataUtility.getString(request.getParameter("operation"));
		
		
		

		if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

			if (OP_SEARCH.equalsIgnoreCase(op)) {

				pageNo = 1;

			} else if (OP_NEXT.equalsIgnoreCase(op)) {

				pageNo++;
			} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {

				pageNo--;
			}
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(FPSView.CUSTOMER_CTL, request, response);
			return;
		} else if (OP_SEND.equalsIgnoreCase(op)) {
			
			if ("-----Select-----".equalsIgnoreCase(request.getParameter("category"))) {
				request.setAttribute("category", PropertyReader.getValue("error.require", "Category"));
				doGet(request, response);
				return;
			}
			
			pageNo = 1;
			if (ids != null && ids.length >= 5) {
				CustomerBean cbean = new CustomerBean();
				for (String id : ids) {
					cbean.setId(DataUtility.getInt(id));
					try {
						cbean=model.getRecordByID(cbean.getId());
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("login", cbean.getUserName());
						map.put("password", cbean.getPassword());
						map.put("firstName",cbean.getFirstName());
						map.put("lastName", cbean.getSurName());
						map.put("link","http://localhost:8080/Fennel/login?category="+bean.getCategory());
						String message = EmailBuilder.getItemsLink(map);
						EmailMessage msg = new EmailMessage();
						msg.setTo(cbean.getEmailID());
						msg.setSubject("Fennel New Items");
						msg.setMessage(message);
						msg.setMessageType(EmailMessage.HTML_MSG);
						EmailUtility.sendMail(msg);
					} catch (ApplicationException e) {
						ServletUtility.handleException(e, request, response);
						e.printStackTrace();
						return;
					}
				}
				ServletUtility.setSuccessMessage("Mail Send Successfully !!!", request);
			} else {
				ServletUtility.setErrorMessage("Select at least five record", request);
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(FPSView.SEND_MAIL, request, response);
			return;

		}

		try {

			list = model.search(bean, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("NO Record Found", request);
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

		ServletUtility.forward(getView(), request, response);
		log.debug("ItemCtl doPost method end");
		return;
	}

	@Override
	protected String getView() {
		return FPSView.SEND_MAIL_VIEW;
	}

}
