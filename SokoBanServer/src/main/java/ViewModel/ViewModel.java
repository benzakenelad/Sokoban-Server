package ViewModel;

import javafx.collections.ObservableList;
import model.server.ClientInformation;

/**
 * <p> View Model - part of MVVM architecture </p>
 * @author Elad Ben Zaken
 *
 */
public interface ViewModel {
	public void close();
	public void removeClient(int ID);
	public void setBindedData(ObservableList<ClientInformation> bindedData);
}
