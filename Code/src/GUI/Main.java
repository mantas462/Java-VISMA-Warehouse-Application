package GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import BackEnd.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class Main {

	Stage stage;

	@FXML
	private TableView tableview;
	@FXML
	private TableColumn tableColumn1;
	@FXML
	private TableColumn tableColumn2;
	@FXML
	private TableColumn tableColumn3;
	@FXML
	private TableColumn tableColumn4;

	@FXML
	private Button button;
	@FXML
	private ComboBox combobox;

	@FXML
	public void initialize() throws Exception {
		tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableview.setPlaceholder(new Label("File was not uploaded or there are not any products"));
		fillCombobox();
		tableview();
	}

	public void fillCombobox() {
		ObservableList<String> comboBoxList = FXCollections.observableArrayList(Start.todo.getProductNames());
		combobox.setItems(comboBoxList);
		combobox.getSelectionModel().select("All");
	}

	public void chooseFile() throws Exception {

		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV File", "*.csv");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(stage);

		try {

			Start.todo.uploadProducts(file);
			Start.todo.setUploadedFileLocation(file.getPath());
			fillCombobox();
			tableview();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("File was uploaded");
			alert.setHeaderText("Success!");
			alert.setContentText("File was successfully uploaded! Total products: " + Start.todo.getProducts().size()
					+ ". The quantity of duplicates was merged.");
			alert.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("File error");
			alert.setHeaderText("ERROR");
			alert.setContentText(
					"Something went wrong. Reasons: \n1.Wrong format of text.\n2.Wrong file format. \n3.File was not chosen.");
			alert.showAndWait();
		}

	}

	public void tableview() throws Exception {

		String product = product = (String) combobox.getValue();

		ObservableList observableList = null;

		if (product == null) {
			product = "All";
		}

		if (product.equals("All")) {
			observableList = FXCollections.observableList(Start.todo.getProducts());
		} else {

			observableList = FXCollections.observableList(Start.todo.getProductsByName(product));
		}

		tableview.getColumns().clear();

		tableColumn1.setCellValueFactory(new PropertyValueFactory<>("product"));
		tableColumn2.setCellValueFactory(new PropertyValueFactory<>("productCode"));
		tableColumn3.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
		tableColumn4.setCellValueFactory(new PropertyValueFactory<>("productExpirationDate"));

		tableview.getColumns().add(tableColumn1);
		tableview.getColumns().add(tableColumn2);
		tableview.getColumns().add(tableColumn3);
		tableview.getColumns().add(tableColumn4);

		tableview.setItems(observableList);
		tableview.getSortOrder().add(tableColumn1);

	}

	// SCENES
	

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

	public void createProductScene() throws IOException {
		drawScene("CreateProduct.fxml");
	}

	public void editDeleteScene() throws IOException {
		drawScene("EditDelete.fxml");
	}
	

	public void drawScene(String fileName) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getResource(fileName));
		Parent root = load.load();
		stage = (Stage) button.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	

}
