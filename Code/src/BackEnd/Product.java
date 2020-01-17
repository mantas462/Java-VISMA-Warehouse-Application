package BackEnd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Product {

	// Product parameters
	private String product;
	private long productCode;
	private int productQuantity;
	private LocalDate productExDate;

	public Product(String product, long productCode, int productQuantity, LocalDate productExDate)
			throws ParseException {
		this.product = product;
		this.productCode = productCode;
		this.productQuantity = productQuantity;
		this.productExDate = productExDate;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public long getProductCode() {
		return productCode;
	}

	public void setProductCode(long productCode) {
		this.productCode = productCode;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public LocalDate getProductExpirationDate() {
		return productExDate;
	}

	public void setProductExpirationDate(LocalDate productExDate) {
		this.productExDate = productExDate;
	}

}
