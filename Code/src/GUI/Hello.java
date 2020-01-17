package GUI;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Hello {

	Stage stage;

	@FXML
	private Button button;

	public void startProgram() throws IOException {
		FXMLLoader load = new FXMLLoader(getClass().getResource("Main.fxml"));
		Parent root = load.load();
		stage = (Stage) button.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
