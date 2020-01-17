package BackEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ProductsFromFile {

	private ArrayList<Product> products = new ArrayList();

	public ArrayList<Product> getProductsFromFile(File file) throws Exception {

		if (file == null) {
			throw new Exception();
		}

		String[] productParameters;

		boolean readingDescription = true;

		Scanner sc = new Scanner(file);

		while (sc.hasNextLine()) {
			if (!readingDescription) {
				productParameters = sc.nextLine().split(",");
				createProduct(productParameters);
			} else {
				sc.nextLine();
				readingDescription = false;
			}
		}
		sc.close();
		return products;
	}

	public void createProduct(String[] productParameters) throws ParseException {

		boolean productAlreadyExists = false;

		String product;
		long productCode;
		int productQuantity;
		LocalDate productExDate;

		product = productParameters[0];
		productCode = Long.parseLong(productParameters[1]);
		productQuantity = Integer.parseInt(productParameters[2]);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		productExDate = LocalDate.parse(productParameters[3], formatter);

		productAlreadyExists = checkIfProductAlreadyExists(product, productCode, productExDate);

		if (!productAlreadyExists) {
			Product createProduct = new Product(product, productCode, productQuantity, productExDate);
			products.add(createProduct);
		} else {
			mergeTwoProducts(product, productCode, productQuantity, productExDate);
		}
	}

	public void mergeTwoProducts(String product, long productCode, int productQuantity, LocalDate productExDate) {
		int wholeQuantity;

		for (Product p : products) {
			if (p.getProduct().equals(product) && p.getProductCode() == productCode
					&& p.getProductExpirationDate().isEqual(productExDate)) {
				wholeQuantity = p.getProductQuantity() + productQuantity;
				p.setProductQuantity(wholeQuantity);
			}
		}
	}

	public boolean checkIfProductAlreadyExists(String product, long productCode, LocalDate productExDate) {
		for (Product p : products) {
			if (p.getProduct().equals(product) && p.getProductCode() == productCode
					&& p.getProductExpirationDate().isEqual(productExDate)) {
				return true;
			}
		}

		return false;
	}
}
