package GUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import BackEnd.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LongStringConverter;

public class EditDelete {

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
	private ComboBox combobox;

	@FXML
	private Button button;

	@FXML
	public void initialize() {
		tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableview.setPlaceholder(new Label("File was not uploaded or there are not any products"));

		combobox.getItems().addAll(Start.todo.getProductNames());
		combobox.getSelectionModel().selectFirst();
		show();
	}

	public void show() {

		String product = (String) combobox.getSelectionModel().getSelectedItem();

		if (product == null) {
			product = "All";
		}

		ObservableList observableList = null;

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

		tableview.setEditable(true);
		tableColumn1.setCellFactory(TextFieldTableCell.forTableColumn());
		tableColumn2.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
		tableColumn3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		tableColumn4.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

		tableview.getColumns().add(tableColumn1);
		tableview.getColumns().add(tableColumn2);
		tableview.getColumns().add(tableColumn3);
		tableview.getColumns().add(tableColumn4);

		tableview.setItems(observableList.sorted());
	}

	public void delete() {
		Product product = (Product) tableview.getSelectionModel().getSelectedItem();
		Start.todo.deleteProduct(product);
		combobox.getItems().removeAll(combobox.getItems());
		combobox.getItems().addAll(Start.todo.getProductNames());
		combobox.getSelectionModel().selectFirst();
		show();
	}

	public void editProduct(TableColumn.CellEditEvent<Product, String> productStringCellEditEvent) {
		Product product = (Product) tableview.getSelectionModel().getSelectedItem();
		product.setProduct(productStringCellEditEvent.getNewValue());
		combobox.getItems().removeAll(combobox.getItems());
		combobox.getItems().addAll(Start.todo.getProductNames());
		combobox.getSelectionModel().selectFirst();
	}

	public void editProductCode(TableColumn.CellEditEvent<Product, String> productStringCellEditEvent) {
		String stringNumber = String.valueOf(productStringCellEditEvent.getNewValue());
		long changedCode = Long.parseLong(stringNumber);
		Product product = (Product) tableview.getSelectionModel().getSelectedItem();
		product.setProductCode(changedCode);
	}

	public void editProductQuantity(TableColumn.CellEditEvent<Product, String> productStringCellEditEvent) {
		String stringNumber = String.valueOf(productStringCellEditEvent.getNewValue());
		int changedQuantity = Integer.parseInt(stringNumber);
		Product product = (Product) tableview.getSelectionModel().getSelectedItem();
		product.setProductQuantity(changedQuantity);
	}

	public void editProductExDate(TableColumn.CellEditEvent<Product, String> productStringCellEditEvent) {
		String inputDate = String.valueOf(productStringCellEditEvent.getNewValue());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate productExDate = LocalDate.parse(inputDate, formatter);
		Product product = (Product) tableview.getSelectionModel().getSelectedItem();
		product.setProductExpirationDate(productExDate);
	}

	// SCENES

	public void saveFile() throws FileNotFoundException, UnsupportedEncodingException {
		Start.todo.saveFileFromProgram();
	}

	public void aboutScene() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About this program");
		alert.setHeaderText("Hello!");
		alert.setContentText(
				"This program was created by Mantas Nacickas for Visma Intership. If you see any bugs, please tell me, I want to improve myself. :)");
		alert.showAndWait();
	}

	public void exit() {
		System.exit(0);
	}
	
	public void drawScene(String fileName) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getResource(fileName));
		Parent root = load.load();
		stage = (Stage) combobox.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	public void exDateScene() throws IOException {
		drawScene("ProductsByExDate.fxml");
	}

	public void createProductScene() throws IOException {
		drawScene("CreateProduct.fxml");
	}

	public void quantityScene() throws IOException {
		drawScene("ProductsByQuantity.fxml");
	}

	public void mainScene() throws IOException {
		drawScene("Main.fxml");
	}

}
