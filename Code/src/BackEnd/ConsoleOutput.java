package BackEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleOutput {

	static Scanner scanner;

	public static void main(String[] args) throws Exception {

		// BUTINAI IRASYTI FILE LOCATION
		File file = new File("C:\\Users\\Mantas\\Desktop\\sample.csv");
		// !!!!!!!!!!!!!

		int input = 0;

		ProductManipulation todo = new ProductManipulation();
		
		todo.uploadProducts(file);

		while (input != 9) {
			System.out.println(
					"Welcome to warehouse app! Make sure file is uploaded! You can:\n1.Check products quantity.\n2.Check expiry date. \n3.Get all products.\n9.Exit\nPlease insert number of your choice:");

			scanner = new Scanner(System.in);
			String choice = scanner.nextLine();
			switch (choice) {
			case "1":
				checkProductsQuantity(todo);
				break;
			case "2":
				checkExDate(todo);
				break;
			case "3":
				getAllProducts(todo);
				break;
			case "9":
				System.out.println("Bye!");
				System.exit(0);
				break;
			default:
				System.out.println("Please insert from list: 1 2 3 9\n");

			}

		}
	}

	public static void getAllProducts(ProductManipulation todo) {
		ArrayList<Product> products;
		products = todo.getProducts();
		printArrayList(products);
	}

	private static void checkExDate(ProductManipulation todo) {
		boolean goodInput = false;

		while (!goodInput) {
			System.out.println(
					"Insert date and products will be showed which have less quantity (format should be: yyyy-mm-dd)");
			scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			
			InputChecker inputChecker=new InputChecker();
			goodInput = inputChecker.isDateFormat(input);

			if (goodInput) {
				ArrayList<Product> products;
				LocalDate dateInput;
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

				dateInput = LocalDate.parse(input, formatter);
				products = todo.getProductsByExpiryDate(dateInput);
				printArrayList(products);
				goodInput = true;
			}
		}

	}

	private static void checkProductsQuantity(ProductManipulation todo) {
		boolean goodInput = false;

		while (!goodInput) {
			System.out.println(
					"Insert quantity and products will be showed which have less quantity (should be numeric):");
			scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			
			InputChecker inputChecker=new InputChecker();
			goodInput = inputChecker.isInteger(input);

			if (goodInput) {
				ArrayList<Product> products;
				products = todo.getProductsByQuantity(Integer.parseInt(input));
				printArrayList(products);

				goodInput = true;
			}
		}
	}

	private static void printArrayList(ArrayList<Product> products) {

		Collections.sort(products, new SortByProduct());

		System.out.println();
		String product;
		long productCode;
		int productQuantity;
		LocalDate productExDate;

		if (products.size() == 0) {
			System.out.println("There are not any products added.");
		} else {
			for (int i = 0; i < products.size(); i++) {
				product = products.get(i).getProduct();
				productCode = products.get(i).getProductCode();
				productQuantity = products.get(i).getProductQuantity();
				productExDate = products.get(i).getProductExpirationDate();
				System.out.println(product + " " + productCode + " " + productQuantity + " " + productExDate);

			}
		}
		System.out.println();

	}

}
