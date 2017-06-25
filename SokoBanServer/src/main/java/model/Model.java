package model;

import java.util.List;

import model.server.ClientInformation;

public interface Model  {
	public void start() throws Exception;
	public void stop() throws Exception;
	public ClientInformation removeClient(int ID);
	public List<ClientInformation> getClientsList();
}
