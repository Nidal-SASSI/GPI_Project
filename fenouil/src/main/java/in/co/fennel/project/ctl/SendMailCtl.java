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
	protected boolean validate(HttpServletRequest request) {
		log.debug("ItemCtl validate method start");
		boolean pass = true;

		if ("-----Select-----".equalsIgnoreCase(request.getParameter("category"))) {
			request.setAttribute("category", PropertyReader.getValue("error.require", "Category"));
			pass = false;
		}

		log.debug("ItemCtl validate method end");
		return pass;
	}

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
		SendMail bean = new SendMail();
		bean.setCategory(DataUtility.getString(request.getParameter("category")));
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
		ServletUtility.forward(getView(), request, response);
		log.debug("ItemCtl doGet method end");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("ItemCtl doPost method start");
		String op = DataUtility.getString(request.getParameter("operation"));
		if (OP_SEND.equalsIgnoreCase(op)) {
			SendMail bean = (SendMail) populateBean(request);
			List<CustomerBean> list;
			try {
			list = new CustomerModel().list();
			for(CustomerBean cBean : list) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("login", cBean.getUserName());
				map.put("password", cBean.getPassword());
				map.put("firstName",cBean.getFirstName());
				map.put("lastName", cBean.getSurName());
				map.put("link","http://localhost:8080/Fennel/login?category="+bean.getCategory());
				String message = EmailBuilder.getItemsLink(map);
				EmailMessage msg = new EmailMessage();
				msg.setTo(cBean.getEmailID());
				msg.setSubject("Fennel New Items");
				msg.setMessage(message);
				msg.setMessageType(EmailMessage.HTML_MSG);
				EmailUtility.sendMail(msg);
				
			}
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			ServletUtility.setSuccessMessage("Mail has been send successfully", request);
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(FPSView.SEND_MAIL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("ItemCtl doPost method end");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return FPSView.SEND_MAIL_VIEW;
	}

}
