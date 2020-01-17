package GUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ProductsByQuantity {
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
	private TextField textfield;

	@FXML
	private ComboBox combobox;

	Stage stage;

	@FXML
	public void initialize() {
		tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableview.setPlaceholder(new Label("File was not uploaded or there are not any products"));

		combobox.getItems().addAll(Start.todo.getProductNames());
		combobox.getSelectionModel().selectFirst();
	}

	private int quantity;

	public void show() {
		String givenQuantity = textfield.getText();
		try {
			quantity = Start.todo.checkInsertedQuantity(givenQuantity);
			tableView();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Wrong input");
			alert.setHeaderText("ERROR");
			alert.setContentText("Input could be only positive number! Please insert correct one.");
			alert.showAndWait();
		}
	}

	public void tableView() {

		String product = (String) combobox.getSelectionModel().getSelectedItem();
		ObservableList observableList = null;

		if (product.equals("All")) {
			observableList = FXCollections.observableList(Start.todo.getProductsByQuantity(quantity));
		} else {
			observableList = FXCollections.observableList(Start.todo.getProductsByQuantityAndName(quantity, product));
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

		tableview.setItems(observableList.sorted());

	}

	// scenes
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

	public void drawScene(String fileName) throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getResource(fileName));
		Parent root = load.load();
		stage = (Stage) combobox.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void mainScene() throws IOException {
		drawScene("Main.fxml");
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

}
