package in.co.fennel.project.bean;

import java.io.InputStream;
import java.util.List;

public class AdvertisementBean extends BaseBean {

	private List recipient_list;
	private String adv_price;
	private String adv_name;
	private String recipient_adv;
	private String sender;
	private String adv_category;
	private InputStream image;
//	private String order;
	
	
	
	public List getRecipient_list() {
		return recipient_list;
	}
	public InputStream getImage() {
		return image;
	}
	public void setImage(InputStream image) {
		this.image = image;
	}
	public void setRecipient_list(List recipient_list) {
		this.recipient_list = recipient_list;
	}
	public String getAdv_price() {
		return adv_price;
	}
	public void setAdv_price(String adv_price) {
		this.adv_price = adv_price;
	}
	public String getAdv_name() {
		return adv_name;
	}
	public void setAdv_name(String adv_name) {
		this.adv_name = adv_name;
	}
	public String getRecipient_adv() {
		return recipient_adv;
	}
	public void setRecipient_adv(String recipient_adv) {
		this.recipient_adv = recipient_adv;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getAdv_category() {
		return adv_category;
	}
	public void setAdv_category(String adv_category) {
		this.adv_category = adv_category;
	}
	/*public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}*/
	
	
}
