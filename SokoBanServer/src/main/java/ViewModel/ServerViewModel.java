package ViewModel;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.ObservableList;
import model.Model;
import model.server.ClientInformation;

public class ServerViewModel extends Observable implements ViewModel,Observer{
	
	private ObservableList<ClientInformation> bindedData;
	private Model model;
	
	public ServerViewModel(Model model) {
		this.model = model;
		try {model.start();} catch (Exception e) {e.printStackTrace();}
	}

	@Override
	public void close() {
		try {
			model.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0 == model){
			new Thread(()->{bindedData.clear();bindedData.addAll(model.getClientsList());}).start();
		}
		
	}

	
	public ObservableList<ClientInformation> getBindedData() {
		return bindedData;
	}
	public void setBindedData(ObservableList<ClientInformation> bindedData) {
		this.bindedData = bindedData;
	}


	@Override
	public void removeClient(int ID) {
		model.removeClient(ID);
	}

}
