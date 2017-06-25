package model.server;

import java.io.Serializable;
import java.net.Socket;

/**
 * <p>Represent sokoban client information </p>
 * 
 * @author Elad Ben Zaken
 *
 */
public class ClientInformation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int ID = 0;
	private String IP;
	private String port;
	private String state;
	private String task;
	@SuppressWarnings("unused")
	private Socket connectionSocket;

	public ClientInformation(int ID, String IP, String port, Socket connectionSocket) {
		this.ID = ID;
		this.IP = IP;
		this.port = port;
		this.state = "";
		this.task = "";
		this.connectionSocket = connectionSocket;
	}
	
	public ClientInformation() {
	}

	public ClientInformation(int ID, String IP, String port, String state, String task, Socket connectionSocket) {
		this.ID = ID;
		this.IP = IP;
		this.port = port;
		this.state = state;
		this.task = task;
		this.connectionSocket = connectionSocket;
	}


	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getIP() {
		return IP;
	}
	public String getPort() {
		return port;
	}
	public int getID() {
		return ID;
	}

}
