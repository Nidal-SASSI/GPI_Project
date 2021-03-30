package in.co.fennel.project.ctl;

import java.io.IOException;
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

import in.co.fennel.project.bean.AdminBean;
import in.co.fennel.project.bean.BaseBean;
import in.co.fennel.project.bean.CartBean;
import in.co.fennel.project.bean.ItemBean;
import in.co.fennel.project.exception.ApplicationException;
import in.co.fennel.project.exception.DuplicateRecordException;
import in.co.fennel.project.model.CartModel;
import in.co.fennel.project.model.ItemModel;
import in.co.fennel.project.util.DataUtility;
import in.co.fennel.project.util.DataValidator;
import in.co.fennel.project.util.PropertyReader;
import in.co.fennel.project.util.ServletUtility;

/**
 * Servlet implementation class CartCtl
 */
@WebServlet(name = "CartCtl", urlPatterns = { "/ctl/addCart" })
public class CartCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(CartCtl.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CartCtl doGet method start");
		long itemId = DataUtility.getLong(request.getParameter("iId"));
			CartBean bean=new CartBean();
			AdminBean aBean=(AdminBean)request.getSession().getAttribute("user");
		try {
			if (itemId > 0) {
				ItemBean iBean = new ItemModel().getRecordByID(itemId);
					bean.setItemId(iBean.getId());
					bean.setItemName(iBean.getName());
					bean.setQuantity("1");
					bean.setPrice(iBean.getPrice());
					bean.setTotalPrice(String.valueOf(DataUtility.getDouble(iBean.getPrice())));
					bean.setUserId(aBean.getId());
					new CartModel().add(bean);
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
		ServletUtility.redirect(FPSView.CART_LIST_CTL, request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return FPSView.CART_VIEW;
	}

}
