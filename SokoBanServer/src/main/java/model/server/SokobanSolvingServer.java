package model.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.Model;

/**
 * <p>
 * This server handle multiple Sokoban clients
 * </p>
 * 
 * @author Elad Ben Zaken
 *
 */
public class SokobanSolvingServer extends Observable implements Model, Server, Observer {
	private Thread serverThread = null;
	private int port = 0;
	private volatile boolean stop = false;
	private ServerSocket listeningSocket = null;
	private ExecutorService executor = Executors.newCachedThreadPool();
	private ArrayList<ClientInformation> clientsList;
	private int clientsCounter = 0;
	private SokobanSolvingServer IM= this;

	public SokobanSolvingServer(int port) {
		this.port = port;
		clientsList = new ArrayList<ClientInformation>();
	}

	@Override
	public void start() throws Exception {
		serverThread = new Thread(() -> runServer());
		serverThread.start();
	}

	private void runServer() {
		try {
			listeningSocket = new ServerSocket(this.port);
			System.out.println("Server is alive and waiting for connections.");
			
			// T - E - S - T //
			clientsList.add(new ClientInformation(clientsCounter++,"127.0.0.1","49369","Offline","",null));
			update(null,null);
			
			while (!stop) {
				Socket connectionSocket = listeningSocket.accept();
				ClientInformation newClient = addClient(connectionSocket);
				executor.submit(new Runnable() {

					@Override
					public void run() {
						SokobanClientHandler handler = new SokobanClientHandler(newClient);
						handler.addObserver(IM);
						try {
							handler.handleClient(connectionSocket.getInputStream(), connectionSocket.getOutputStream());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}

			listeningSocket.close();

		} catch (Exception e) {
		}

	}

	@Override
	public void stop() throws Exception {
		this.stop = true;
		listeningSocket.close();
		executor.shutdown();
		System.out.println("Server is closed");
	}

	public List<ClientInformation> getClientsList() {
		return this.clientsList;
	}

	@Override
	public ClientInformation removeClient(int ID) {
		int size = clientsList.size();
		for (int i = 0; i < size; i++) {
			if (clientsList.get(i).getID() == ID) {
				ClientInformation removedClient = clientsList.remove(i);
				setChanged();
				notifyObservers();
				return removedClient;
			}
		}
		return null;
	}

	private ClientInformation addClient(Socket connectionSocket) {
		ClientInformation newClient = new ClientInformation(clientsCounter++,
				connectionSocket.getInetAddress().toString().substring(1), connectionSocket.getPort() + "", connectionSocket);
		clientsList.add(newClient);
		setChanged();
		notifyObservers();
		return newClient;
	}

	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}

}
