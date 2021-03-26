package in.co.fennel.project.bean;

import java.io.InputStream;
import java.sql.Blob;

public class ItemBean extends BaseBean {

	private String name;
	private String designation;
	private String price;
	private String stock;
	private String quantities;
	private String description;
	private Blob photo;
	private String category;
	private InputStream image;
	
	
	public InputStream getImage() {
		return image;
	}
	public void setImage(InputStream image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getQuantities() {
		return quantities;
	}
	public void setQuantities(String quantities) {
		this.quantities = quantities;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Blob getPhoto() {
		return photo;
	}
	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
	
}
