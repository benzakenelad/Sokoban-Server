package model.server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p> Handling a client interface, defines the handleClient method </p>
 * @author Elad Ben Zaken
 *
 */
public interface ClientHandler {
	public void handleClient(InputStream inFromClient, OutputStream outToClient) throws Exception; // handle a client by a well defined protocol
}
