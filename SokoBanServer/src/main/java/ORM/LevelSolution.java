package ORM;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LevelSolution implements Serializable{
	
	private Integer ID;
	private String levelData; // TXT display chained into one string
	private String solution; // TXT solution
	
	// C'tors
	public LevelSolution() {}
	
	public LevelSolution(String levelData, String solution){
		this.levelData = levelData;
		this.solution = solution;
	}
	
	// Getters and setters
	public Integer getID() {
		return ID;
	}
	
	public void setID(Integer iD) {
		ID = iD;
	}
	
	public String getLevelData() {
		return levelData;
	} 
	
	public void setLevelData(String levelData) {
		this.levelData = levelData;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	@Override
	public String toString() {
		return levelData + " " + solution;
	}

}
