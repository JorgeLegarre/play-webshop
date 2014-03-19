package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

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

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	List<Order> orders;

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

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getDob() {
		return dob;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getTown() {
		return town;
	}

	public String getPostcode() {
		return postcode;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	@Override
	public String toString() {
		return String
				.format("Id: %s, User: %s, Firstname: %s, Lastname: %s, Dob: %s, Telephone: %s, Address: %s %s %s %s, isAdmin: %s ",
						getId(), getEmail(), getFirstname(), getLastname(),
						getDob(), getTelephone(), getAddress1(), getAddress2(),
						getTown(), getPostcode(), isAdmin());
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result
				+ ((address1 == null) ? 0 : address1.hashCode());
		result = prime * result
				+ ((address2 == null) ? 0 : address2.hashCode());
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + id;
		result = prime * result + (isAdmin ? 1231 : 1237);
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((postcode == null) ? 0 : postcode.hashCode());
		result = prime * result
				+ ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((town == null) ? 0 : town.hashCode());
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
		if (address1 == null) {
			if (other.address1 != null)
				return false;
		} else if (!address1.equals(other.address1))
			return false;
		if (address2 == null) {
			if (other.address2 != null)
				return false;
		} else if (!address2.equals(other.address2))
			return false;
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id != other.id)
			return false;
		if (isAdmin != other.isAdmin)
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (postcode == null) {
			if (other.postcode != null)
				return false;
		} else if (!postcode.equals(other.postcode))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (town == null) {
			if (other.town != null)
				return false;
		} else if (!town.equals(other.town))
			return false;
		return true;
	}

}
