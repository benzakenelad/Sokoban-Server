package model.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.eladproj.SokoBanServer.App;

import ORM.LevelSolution;
import model.data.Level;
import searchLib.Action;
import solver.SolveSokobanLevel;

/**
 * <p>
 * Sokoban solving server client handler.<br>
 * Solving a Sokoban level
 * </p>
 * 
 * @author Elad Ben Zaken
 *
 */
public class SokobanClientHandler extends Observable implements ClientHandler {

	static final int times = 10;
	private ExecutorService executor = Executors.newCachedThreadPool();
	private SolveSokobanLevel solver = new SolveSokobanLevel();
	private ClientInformation clientInfo;

	public SokobanClientHandler(ClientInformation clientInfo) {
		this.clientInfo = clientInfo;
	}

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) throws Exception {

		ObjectInputStream objectReader = new ObjectInputStream(inFromClient);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inFromClient));

		Level lvl = (Level) objectReader.readObject();
		String command = reader.readLine();
		clientInfo.setState("Working");
		clientInfo.setTask(command + " " + lvl.getID());
		PrintWriter writeToClient = new PrintWriter(outToClient, true);

		setChanged();
		notifyObservers();
		Thread.sleep(50);

		String solution = null;

		// checking if solved already
		String levelData = App.levelAsOneString(lvl);
		try{solution = App.getSolution(levelData);}catch(Exception e){}
	
		if (solution == null) {
			switch (command) {
			case "quick solving":
				solution = quickSolve(lvl);
				writeToClient.println(solution);
				break;
			case "optimal solving":
				solution = optimalSolve(lvl);
				writeToClient.println(solution);
				break;
			default:
				break;
			}
			try{saveSolutionInDB(levelData, solution);}catch(Exception e){}	
		}else
			writeToClient.println(solution);
		

		executor.shutdown();
		objectReader.close();
		writeToClient.close();

		clientInfo.setState("Terminated");
		setChanged();
		notifyObservers();
	}

	public String optimalSolve(Level lvl) throws Exception {
		if (lvl == null)
			return null;

		final List<Action> actions = MultiThreadedSolver(lvl, 50, 50);

		if (actions == null) {
			System.out.println("Could not solve the level.");
			return null;
		}

		return generateSolution(actions);
	}

	public String quickSolve(Level lvl) throws Exception {
		if (lvl == null)
			return null;

		List<Action> actions = solver.noneOptimalSolve(lvl);

		if (actions == null) {
			System.out.println("Could not solve the level.");
			return null;
		}

		return generateSolution(actions);
	}

	private List<Action> MultiThreadedSolver(Level lvl, int threadsNum, int testSize) throws Exception {
		if (lvl == null)
			return null;
		// Future product (futures)
		ArrayList<Future<List<Action>>> futures = new ArrayList<Future<List<Action>>>();

		// asynchronous solving
		for (int i = 0; i < threadsNum; i++) {
			futures.add(executor.submit(new Callable<List<Action>>() {
				@Override
				public List<Action> call() throws Exception {
					List<Action> actions = solver.optimalSolve(lvl, testSize);
					return actions;
				}
			}));
		}
		ArrayList<List<Action>> lists = new ArrayList<List<Action>>();

		for (int i = 0; i < threadsNum; i++)
			lists.add(futures.get(i).get());

		List<Action> returnedList = null;

		int largest = Integer.MAX_VALUE;

		for (int i = 0; i < threadsNum; i++) {
			if (lists.get(i) != null)
				if (lists.get(i).size() < largest) {
					largest = lists.get(i).size();
					returnedList = lists.get(i);
				}
		}

		return returnedList;
	}

	private String generateSolution(List<Action> actions) throws Exception {
		if (actions == null)
			return null;
		StringBuilder builder = new StringBuilder("");
		actions.forEach((a) -> builder.append(a.getName().split(" ")[1].charAt(0)));

		return builder.toString();
	}

	public ClientInformation getClientInfo() {
		return clientInfo;
	}

	public void saveSolutionInDB(String levelData, String solution) {
		LevelSolution lSol = new LevelSolution(levelData,solution);
		try{App.addSolution(lSol);}catch(Exception e){}
	}
}
