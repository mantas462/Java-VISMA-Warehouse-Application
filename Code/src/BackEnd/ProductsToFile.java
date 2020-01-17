package BackEnd;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProductsToFile {

	public void saveFile(ArrayList<Product> products, String fileLocation)
			throws FileNotFoundException, UnsupportedEncodingException {
		String product;
		long productCode;
		int productQuantity;
		LocalDate productExpirationDate;

		try (PrintWriter writer = new PrintWriter(fileLocation, "UTF-8")) {
			writer.println("Item Name,Code,Quantity,Expiration Date");
			for (int i = 0; i < products.size(); i++) {
				product = products.get(i).getProduct();
				productCode = products.get(i).getProductCode();
				productQuantity = products.get(i).getProductQuantity();
				productExpirationDate = products.get(i).getProductExpirationDate();
				writer.println(product + "," + productCode + "," + productQuantity + "," + productExpirationDate);
			}
		}
	}

}
