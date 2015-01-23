package drawNGuess;

import java.util.ArrayList;

public class Game {
	private ArrayList<User> users = new ArrayList<User>();
	private String gameStatus = "init";
	private String gameTitle;
	private String dataType;
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getGameTitle() {
		return gameTitle;
	}

	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}
	
	public void randomGameTitle()
	{
		setGameTitle(GameUtil.getRandomTitle());
	}
}
