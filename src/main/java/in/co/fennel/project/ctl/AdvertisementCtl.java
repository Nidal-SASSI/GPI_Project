package in.co.fennel.project.ctl;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import in.co.fennel.project.bean.AdvertisementBean;
import in.co.fennel.project.bean.BaseBean;
import in.co.fennel.project.exception.ApplicationException;
import in.co.fennel.project.exception.DuplicateRecordException;
import in.co.fennel.project.model.AdvertisementModel;
import in.co.fennel.project.util.DataUtility;
import in.co.fennel.project.util.DataValidator;
import in.co.fennel.project.util.JDBCDataSource;
import in.co.fennel.project.util.PropertyReader;
import in.co.fennel.project.util.ServletUtility;

/**
 * Servlet implementation class AdvertisementCtl
 */
@WebServlet(name = "AdvertisementCtl", urlPatterns = { "/ctl/advertisement" })
@MultipartConfig(maxFileSize = 16177216)
public class AdvertisementCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(AdvertisementCtl.class);

	/**
	 * Validate input Data Entered By User
	 * 
	 * @param request
	 * @return
	 */
	
	
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("AdvertisementCtl validate method start");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("adName"))) {
			request.setAttribute("adName", PropertyReader.getValue("error.require", "Advertisement Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("adPrice"))) {
			request.setAttribute("adPrice", PropertyReader.getValue("error.require", "Advertisement Price"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("recipentAd"))) {
			request.setAttribute("recipentAd", PropertyReader.getValue("error.require", "Recipient Advertisement"));
			pass = false;
		}else if(!DataValidator.isEmail(request.getParameter("recipentAd"))) {
        	request.setAttribute("recipentAd",
                    PropertyReader.getValue("error.invalid", "Recipient Advertisement"));
            pass = false;
        }

		if (DataValidator.isNull(request.getParameter("sender"))) {
			request.setAttribute("sender", PropertyReader.getValue("error.require", "Sender"));
			pass = false;
		}else if(!DataValidator.isEmail(request.getParameter("sender"))) {
        	request.setAttribute("sender",
                    PropertyReader.getValue("error.invalid", "Sender"));
            pass = false;
        }

		if ("-----Select-----".equalsIgnoreCase(request.getParameter("adCategory"))) {
			request.setAttribute("adCategory", PropertyReader.getValue("error.require", "Advertising Category"));
			pass = false;
		}
		log.debug(" AdvertisementCtl validate method end");
		return pass;
	}

	@Override
	protected void preload(HttpServletRequest request) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("Decoration","Decoration");
		map.put("DIY","DIY");
		map.put("Gardening","Gardening");
		
		request.setAttribute("catMap", map);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug(" AdvertisementCtl populateBean method start");
		AdvertisementBean bean = new AdvertisementBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setAdv_name(DataUtility.getString(request.getParameter("adName")));
		bean.setAdv_price(DataUtility.getString(request.getParameter("adPrice")));
		bean.setRecipient_adv(DataUtility.getString(request.getParameter("recipentAd")));
		bean.setSender(DataUtility.getString(request.getParameter("sender")));
		bean.setAdv_category(DataUtility.getString(request.getParameter("adCategory")));
		populateDTO(bean, request);
		log.debug("AdvertisementCtl populateBean method end");
		return bean;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug(" AdvertisementCtl doGet method start");
		String op = DataUtility.getString(request.getParameter("operation"));

		AdvertisementModel model = new AdvertisementModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		ServletUtility.setOpration("Add", request);
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			AdvertisementBean bean;
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
		log.debug(" AdvertisementCtl doGet method end");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug(" AdvertisementCtl doPost method start");
		String op = DataUtility.getString(request.getParameter("operation"));
		AdvertisementModel model = new AdvertisementModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)) {

			AdvertisementBean bean = (AdvertisementBean) populateBean(request);
			Part part = request.getPart("image");
			bean.setImage(part.getInputStream());
			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setOpration("Edit", request);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
					ServletUtility.setBean(bean, request);

				} else {
					long pk = model.addAdvertisement(bean);
					// bean.setId(id);
					ServletUtility.setSuccessMessage("Data is successfully Saved", request);
					ServletUtility.forward(getView(), request, response);
				}

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.forward(FPSView.ERROR_VIEW, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(e.getMessage(), request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			AdvertisementBean bean = (AdvertisementBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(FPSView.ADVERTISEMENT_LIST_CTL, request, response);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				e.printStackTrace();
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(FPSView.ADVERTISEMENT_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(FPSView.ADVERTISEMENT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.debug(" AdvertisementCtl doPost method end");
	}

	@Override
	protected String getView() {
		return FPSView.ADVERTISEMENT_VIEW;
	}
	
	

}
