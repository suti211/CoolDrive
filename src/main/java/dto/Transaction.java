package dto;

/**
 * Created by David Szilagyi on 2017. 07. 24..
 */
public class Transaction {
    private int id;
    private int userId;
    private String userToken;
    private String firstName;
    private String lastName;
    private String zip;
    private String city;
    private String address1;
    private String address2;
    private String phone;
    private String bought;
    private String boughtDate;
    

    public Transaction() {
	}

	public Transaction(int id, int userId, String firstName, String lastName, String zip, String city, String address1, String address2, String phone, String bought, String boughtDate) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zip = zip;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.phone = phone;
        this.bought = bought;
        this.boughtDate = boughtDate;
    }

    public Transaction(int userId, String firstName, String lastName, String zip, String city, String address1, String address2, String phone, String bought, String boughtDate) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zip = zip;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.phone = phone;
        this.bought = bought;
        this.boughtDate = boughtDate;
    }
    
    public Transaction(String userName, String firstName, String lastName, String zip, String city, String address1, String bought) {
		super();
		this.userToken = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.zip = zip;
		this.city = city;
		this.address1 = address1;
		this.bought = bought;
	}

	public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getPhone() {
        return phone;
    }

    public String getBought() {
        return bought;
    }

    public String getBoughtDate() {
        return boughtDate;
    }

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", zip=" + zip + ", city=" + city + ", address1=" + address1 + ", address2=" + address2 + ", phone="
				+ phone + ", bought=" + bought + ", boughtDate=" + boughtDate + "]";
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
    
}
