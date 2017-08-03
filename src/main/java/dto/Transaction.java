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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (zip != null ? !zip.equals(that.zip) : that.zip != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (address1 != null ? !address1.equals(that.address1) : that.address1 != null) return false;
        if (address2 != null ? !address2.equals(that.address2) : that.address2 != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        return bought != null ? bought.equals(that.bought) : that.bought == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (address1 != null ? address1.hashCode() : 0);
        result = 31 * result + (address2 != null ? address2.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (bought != null ? bought.hashCode() : 0);
        return result;
    }
}
