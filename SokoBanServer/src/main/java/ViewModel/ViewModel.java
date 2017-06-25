package ViewModel;

import javafx.collections.ObservableList;
import model.server.ClientInformation;

public interface ViewModel {
	public void close();
	public void removeClient(int ID);
	public void setBindedData(ObservableList<ClientInformation> bindedData);
}
