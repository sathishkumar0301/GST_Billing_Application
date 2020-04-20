package gst_billing.fresherpro.examly.com;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer productCode;
	
	private String productName;
	
	private Double productPrice;
	
	private Integer productGst;
	
	public void setProductCode(Integer productCode) {
		this.productCode = productCode;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	
	public void setProductGst(Integer productGst) {
		this.productGst = productGst;
	}
	
	public Integer getProductCode() {
		return productCode;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public Double getProductPrice() {
		return productPrice;
	}
	
	public Integer getProductGst() {
		return productGst;
	}
}
