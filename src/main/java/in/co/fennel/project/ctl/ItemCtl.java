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

import in.co.fennel.project.bean.BaseBean;
import in.co.fennel.project.bean.ItemBean;
import in.co.fennel.project.exception.ApplicationException;
import in.co.fennel.project.exception.DuplicateRecordException;
import in.co.fennel.project.model.ItemModel;
import in.co.fennel.project.util.DataUtility;
import in.co.fennel.project.util.DataValidator;
import in.co.fennel.project.util.PropertyReader;
import in.co.fennel.project.util.ServletUtility;

/**
 * Servlet implementation class ItemCtl
 */
@WebServlet(name="ItemCtl",urlPatterns={"/ctl/item"})
@MultipartConfig(maxFileSize = 16177216)
public class ItemCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log=Logger.getLogger(ItemCtl.class);
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

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name",
                    PropertyReader.getValue("error.require", "Name"));
            pass = false;
        }
        
        if (DataValidator.isNull(request.getParameter("designation"))) {
            request.setAttribute("designation",
                    PropertyReader.getValue("error.require", "Designation"));
            pass = false;
        }
        
        if (DataValidator.isNull(request.getParameter("price"))) {
            request.setAttribute("price",
                    PropertyReader.getValue("error.require", "Price"));
            pass = false;
        }
        
        if (DataValidator.isNull(request.getParameter("stock"))) {
            request.setAttribute("stock",
                    PropertyReader.getValue("error.require", "Stock"));
            pass = false;
        }else if (!DataValidator.isInteger(request.getParameter("stock"))) {
            request.setAttribute("stock",
                    PropertyReader.getValue("error.integer", "Stock"));
            pass = false;
        }
        
        if (DataValidator.isNull(request.getParameter("quantity"))) {
            request.setAttribute("quantity",
                    PropertyReader.getValue("error.require", "Quantities"));
            pass = false;
        }else if (!DataValidator.isInteger(request.getParameter("quantity"))) {
            request.setAttribute("quantity",
                    PropertyReader.getValue("error.integer", "Quantities"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description",
                    PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }
        
        if ("-----Select-----".equalsIgnoreCase(request.getParameter("category"))) {
            request.setAttribute("category",
                    PropertyReader.getValue("error.require", "Category"));
            pass = false;
        }
        

        log.debug("ItemCtl validate method end");
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
		log.debug("ItemCtl populateBean method start");
		ItemBean bean=new ItemBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setDesignation(DataUtility.getString(request.getParameter("designation")));
		bean.setPrice(DataUtility.getString(request.getParameter("price")));
		bean.setStock(DataUtility.getString(request.getParameter("stock")));
		bean.setQuantities(DataUtility.getString(request.getParameter("quantity")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setCategory(DataUtility.getString(request.getParameter("category")));
		populateDTO(bean, request);
		log.debug("ItemCtl populateBean method end");
		return bean;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("ItemCtl doGet method start"); 
		String op = DataUtility.getString(request.getParameter("operation"));
			
		   ItemModel model = new ItemModel();
			long id = DataUtility.getLong(request.getParameter("id"));
			ServletUtility.setOpration("Add", request);
			if (id > 0 || op != null) {
				System.out.println("in id > 0  condition");
				ItemBean bean;
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
			log.debug("ItemCtl doGet method end");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("ItemCtl doPost method start");
		String op=DataUtility.getString(request.getParameter("operation"));
		ItemModel model=new ItemModel();
		long id=DataUtility.getLong(request.getParameter("id"));
		if(OP_SAVE.equalsIgnoreCase(op)){
			
			ItemBean bean=(ItemBean)populateBean(request);
			
				Part part=request.getPart("image");
				bean.setImage(part.getInputStream());
			
				try {
					if(id>0){
						
					model.update(bean);
					ServletUtility.setOpration("Edit", request);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
	                ServletUtility.setBean(bean, request);

					}else {
						long pk=model.addItems(bean);
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
			
		}else if (OP_DELETE.equalsIgnoreCase(op)) {
		ItemBean bean=	(ItemBean)populateBean(request);
		try {
			model.delete(bean);
			ServletUtility.redirect(FPSView.ITEM_LIST_CTL, request, response);
		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			e.printStackTrace();
		}
		}else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(FPSView.ITEM_LIST_CTL, request, response);
			return;
	}else if (OP_RESET.equalsIgnoreCase(op)) {
		ServletUtility.redirect(FPSView.ITEM_CTL, request, response);
		return;
}
				
		
		ServletUtility.forward(getView(), request, response);
		 log.debug("ItemCtl doPost method end");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return FPSView.ITEM_VIEW;
	}

}
