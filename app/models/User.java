package models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 20140407;

	public static int DEFAULT_USER_ID = 0;

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String firstname;

	@Column(nullable = false)
	private String lastname;

	private String dob;

	@Column(nullable = false)
	private String telephone;

	@Column(nullable = false)
	private String address1;

	private String address2;

	@Column(nullable = false)
	private String town;

	@Column(nullable = false)
	private String postcode;

	private boolean isAdmin;

	@OneToOne(mappedBy = "user", cascade = { CascadeType.ALL }, orphanRemoval = true)
	ShoppingCart shoppingCart;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL })
	private List<Order> orders;

	public User() {
	}

	public User(int id, String email, String password, String firstname,
			String lastname, String dob, String telephone, String address1,
			String address2, String town, String postcode, boolean isAdmin) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dob = dob;
		this.telephone = telephone;
		this.address1 = address1;
		this.address2 = address2;
		this.town = town;
		this.postcode = postcode;
		this.isAdmin = isAdmin;
	}

	public User(String email, String password, String firstname,
			String lastname, String dob, String telephone, String address1,
			String address2, String town, String postcode, boolean isAdmin) {
		this(DEFAULT_USER_ID, email, password, firstname, lastname, dob,
				telephone, address1, address2, town, postcode, isAdmin);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password
				+ ", firstname=" + firstname + ", lastname=" + lastname
				+ ", dob=" + dob + ", telephone=" + telephone + ", address1="
				+ address1 + ", address2=" + address2 + ", town=" + town
				+ ", postcode=" + postcode + ", isAdmin=" + isAdmin + "]";
	}

}
