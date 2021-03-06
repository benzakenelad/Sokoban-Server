package model.server;

/**
 * <p>Server interface, define start and stop method </p>
 * @author Elad Ben Zaken
 *
 */
public interface Server {
	/**
	 * start the server (Active Object)
	 * @throws Exception
	 */
	public void start() throws Exception;
	public void stop() throws Exception;
}
