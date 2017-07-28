package dto;

public class Token {

	private String token;
	private int id;

	public Token(String token) {
		this.token = token;
	}

	public Token(String token, int id) {
		this.token = token;
		this.id = id;
	}

	public Token() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
