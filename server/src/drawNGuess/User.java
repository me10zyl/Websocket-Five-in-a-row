package drawNGuess;

public class User {
	private String id;
	private String nickname;
	private int score;
	private boolean isReady = false;
	private boolean isTurn = false;
	private boolean isWin = false;
	
	public User(String id, String nickname, int score, boolean isReady, boolean isTurn, boolean isWin) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.score = score;
		this.isReady = isReady;
		this.isTurn = isTurn;
		this.isWin = isWin;
	}
	public String getId() {
		return id;
	}
	public String getNickname() {
		return nickname;
	}
	public int getScore() {
		return score;
	}

	public boolean isReady() {
		return isReady;
	}

	public boolean isTurn() {
		return isTurn;
	}

	public boolean isWin() {
		return isWin;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}
}
