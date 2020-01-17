package GUI;

import BackEnd.ProductManipulation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Start extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	static ProductManipulation todo = new ProductManipulation();

	@Override
	public void start(Stage stage) throws Exception {
		stage.setResizable(false);
		FXMLLoader load = new FXMLLoader(getClass().getResource("Hello.fxml"));
		Parent root = load.load();
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("Mantas Nacickas Visma intership task");
		stage.getIcons().add(new Image("/resources/visma-icon.png"));
		stage.show();

	}
}