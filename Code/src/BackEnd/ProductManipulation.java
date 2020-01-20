package BackEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class ProductManipulation {

	private ArrayList<Product> products = new ArrayList();
	String uploadedFileLocation = null;

	public void uploadProducts(File file) throws Exception {
		ProductsFromFile uploadFile = new ProductsFromFile();
		products = uploadFile.getProductsFromFile(file);
	}

	public void createProduct(String product, long productCode, int productQuantity, LocalDate productExDate)
			throws ParseException {

		boolean productAlreadyExists;

		productAlreadyExists = checkIfProductAlreadyExists(product, productCode, productExDate);

		if (!productAlreadyExists) {
			Product createProduct = new Product(product, productCode, productQuantity, productExDate);
			products.add(createProduct);
		} else {
			mergeTwoProducts(product, productCode, productQuantity, productExDate);
		}
	}

	public void saveFileFromProgram() throws FileNotFoundException, UnsupportedEncodingException {
		ProductsToFile file = new ProductsToFile();
		file.saveFile(products, uploadedFileLocation);
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


	public ArrayList<Product> getProducts() {
		return products;
	}

	public int checkInsertedQuantity(String quantity) throws Exception {
		int checker = Integer.parseInt(quantity);

		if (checker < 0) {
			throw new Exception();
		}

		return checker;
	}

	public ArrayList<Product> getProductsByQuantity(int quantity) {
		ArrayList<Product> neededProducts = new ArrayList();

		for (Product product : products) {
			if (product.getProductQuantity() < quantity) {
				neededProducts.add(product);
			}
		}

		return neededProducts;
	}

	public ArrayList<Product> getProductsByQuantityAndName(int quantity, String product) {
		ArrayList<Product> neededProducts = new ArrayList();

		for (Product p : products) {
			if (p.getProductQuantity() < quantity && p.getProduct().equals(product)) {
				neededProducts.add(p);
			}
		}

		return neededProducts;
	}

	public ArrayList<Product> getProductsByExpiryDate(LocalDate productExDate) {
		ArrayList<Product> neededProducts = new ArrayList();

		for (Product product : products) {
			if (product.getProductExpirationDate().isBefore(productExDate)) {
				neededProducts.add(product);
			}
		}

		return neededProducts;
	}

	public ArrayList<Product> getProductsByExpiryDateAndName(LocalDate productExDate, String product) {
		ArrayList<Product> neededProducts = new ArrayList();

		for (Product p : products) {
			if (p.getProductExpirationDate().isBefore(productExDate) && p.getProduct().equals(product)) {
				neededProducts.add(p);
			}
		}

		return neededProducts;
	}

	public ArrayList<String> getProductNames() {
		ArrayList<String> productNames = new ArrayList();
		productNames.add("All");

		for (Product p : products) {
			if (!productNames.contains(p.getProduct())) {
				productNames.add(p.getProduct());
			}
		}

		return productNames;
	}

	public ArrayList<Product> getProductsByName(String product) {
		ArrayList<Product> neededProducts = new ArrayList();

		for (Product p : products) {
			if (p.getProduct().equals(product)) {
				neededProducts.add(p);
			}
		}

		return neededProducts;
	}

	public void deleteProduct(Product product) {
		products.remove(product);
	}

	public String getUploadedFileLocation() {
		return uploadedFileLocation;
	}

	public void setUploadedFileLocation(String uploadedFileLocation) {
		this.uploadedFileLocation = uploadedFileLocation;
	}

}
