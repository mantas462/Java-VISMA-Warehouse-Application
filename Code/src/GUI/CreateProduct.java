package GUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class CreateProduct {

	@FXML
	private TextField inputProduct;
	@FXML
	private TextField inputCode;
	@FXML
	private TextField inputQuantity;
	@FXML
	private DatePicker datePicker;

	@FXML
	private Text text;

	Stage stage;

	public void createProduct() {
		String product = inputProduct.getText();
		String productCodeString = inputCode.getText();
		String inputQuantityString = inputQuantity.getText();

		LocalDate productExDate = datePicker.getValue();

		try {
			long productCode = Integer.parseInt(productCodeString);
			int productQuantity = Integer.parseInt(inputQuantityString);

			Start.todo.createProduct(product, productCode, productQuantity, productExDate);
			text.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Input error");
			alert.setHeaderText("ERROR");
			alert.setContentText(
					"Error happened. Reasons:  \n1.Quantity and code inputs should be numebrs 2.All fields should be filled.\n");
			alert.showAndWait();
		}
	}

	// Scenes

	public void aboutScene() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About this program");
		alert.setHeaderText("Hello!");
		alert.setContentText(
				"This program was created by Mantas Nacickas for Visma Intership. If you see any bugs, please tell me, I want to improve myself. :)");
		alert.showAndWait();
	}

	public void saveFile() throws FileNotFoundException, UnsupportedEncodingException {
		Start.todo.saveFileFromProgram();
	}

	public void exit() {
		System.exit(0);
	}
	
	public void quantityScene() throws IOException {
		drawScene("ProductsByQuantity.fxml");
	}

	public void exDateScene() throws IOException {
		drawScene("ProductsByExDate.fxml");
	}

	public void editDeleteScene() throws IOException {
		drawScene("EditDelete.fxml");
	}

	public void mainScene() throws IOException {
		drawScene("Main.fxml");
	}
	
	public void drawScene(String fileName) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getResource(fileName));
		Parent root = load.load();
		stage = (Stage) text.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
