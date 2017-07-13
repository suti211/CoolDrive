package dto;

/**
 * Created by David Szilagyi on 2017. 07. 06..
 */
public class User {
	private int id;
	private String userName;
	private String pass;
	private String email;
	private boolean validated;
	private String firstName;
	private String lastName;
	private boolean admin;
	private double quantity;
	private double usage;
	private String token;
	
	public User(){
		
	}

	public User(int id, String userName, String pass, String email, boolean validated, String firstName,
			String lastName, boolean admin, double quantity, double usage, String token) {
		this.id = id;
		this.userName = userName;
		this.pass = pass;
		this.email = email;
		this.validated = validated;
		this.firstName = firstName;
		this.lastName = lastName;
		this.admin = admin;
		this.quantity = quantity;
		this.usage = usage;
		this.token = token;
	}

	public User(String userName, String firstName, String lastName, String email, String pass) {
		this.userName = userName;
		this.pass = pass;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getPass() {
		return pass;
	}

	public String getEmail() {
		return email;
	}

	public boolean isValidated() {
		return validated;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getToken() { return token; }

	public boolean isAdmin() {
		return admin;
	}

	public double getQuantity() {
		return quantity;
	}

	public double getUsage() {
		return usage;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setUsage(int usage) {
		this.usage = usage;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", pass=" + pass + ", email=" + email + ", validated="
				+ validated + ", firstName=" + firstName + ", lastName=" + lastName + ", admin=" + admin + ", quantity="
				+ quantity + ", usage=" + usage + "]";
	}
}
