package view;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import ViewModel.ViewModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.server.ClientInformation;

public class ClientsTableView implements View,Initializable{

	// Data Members
	@FXML
	private TableView<ClientInformation> detailsTable;

	private ObservableList<ClientInformation> data; // Record List Data
	private ViewModel viewModel;
	


	// Initialization
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Data members Initialization
		data = FXCollections.observableArrayList();
		detailsTable.setEditable(true);

		
		// Record table columns Initialization
		TableColumn<ClientInformation, String> column1 = new TableColumn<>("Client IP");
		column1.setCellValueFactory(new PropertyValueFactory<>("IP"));
		column1.setMinWidth(100);
		column1.setMaxWidth(100);

		TableColumn<ClientInformation, String> column2 = new TableColumn<>("Client Port");
		column2.setCellValueFactory(new PropertyValueFactory<>("port"));
		column2.setMinWidth(100);
		column2.setMaxWidth(100);

		TableColumn<ClientInformation, String> column3 = new TableColumn<>("State");
		column3.setCellValueFactory(new PropertyValueFactory<>("state"));
		column3.setMinWidth(100);
		column3.setMaxWidth(100);

		TableColumn<ClientInformation, String> column4 = new TableColumn<>("Task");
		column4.setCellValueFactory(new PropertyValueFactory<>("task"));
		column4.setMinWidth(200);
		column4.setMaxWidth(200);
		

		// Columns set up
		detailsTable.getColumns().addAll(column1, column2, column3, column4);
		detailsTable.setItems(data);
		

		// Mouse click event handler
		detailsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				ClientInformation client = detailsTable.getSelectionModel().getSelectedItem();
				if (client != null){
					viewModel.removeClient(client.getID());
				}
			}
		});
		
		detailsTable.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ESCAPE)
					close();
				
			}
		});

		// T - E - S - T // 
		data.clear();
	}

	public void close(){
		viewModel.close();
		Platform.exit();
	}
	
	public ObservableList<ClientInformation> getObserverableList(){
		return this.data;
	}
	

	public ViewModel getViewModel() {
		return viewModel;
	}

	public void setViewModel(ViewModel viewModel) {
		this.viewModel = viewModel;
		if(viewModel != null)
			viewModel.setBindedData(data);
	}

	@Override
	public void update(Observable arg0, Object arg1) {

	}
}
