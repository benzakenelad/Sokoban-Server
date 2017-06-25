package view;

import ViewModel.ServerViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.server.SokobanSolvingServer;

public class Main extends Application {
	private static final int port = 5555;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Main Window Loading
			FXMLLoader mainWindowloader = new FXMLLoader(getClass().getResource("ClientsTableView.fxml"));
			BorderPane mainWindowroot = (BorderPane) mainWindowloader.load();

			// Controllers catching
			ClientsTableView view = (ClientsTableView) mainWindowloader.getController();
			SokobanSolvingServer model = new SokobanSolvingServer(port);
			ServerViewModel viewModel = new ServerViewModel(model);	
			view.setViewModel(viewModel);
			model.addObserver(viewModel);
			viewModel.addObserver(view);
			
			// Main Window
			Scene mainWindowScene = new Scene(mainWindowroot); 
			
			mainWindowScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(mainWindowScene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args); // application launch
	}

}
