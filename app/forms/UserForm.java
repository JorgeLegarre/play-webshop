package forms;

public class UserForm {
	private int id;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private String dob;
	private String telephone;
	private String address1;
	private String address2;
	private String town;
	private String postcode;
	private boolean isAdmin;

	public UserForm() {

	}

	public UserForm(int id, String email, String password, String firstname,
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

	public void setAdmin(String[] isAdmin) {
		this.isAdmin = (isAdmin.length > 0) ? true : false;
	}

	@Override
	public String toString() {
		return "UserForm [id=" + id + ", email=" + email + ", password="
				+ password + ", firstname=" + firstname + ", lastname="
				+ lastname + ", dob=" + dob + ", telephone=" + telephone
				+ ", address1=" + address1 + ", address2=" + address2
				+ ", town=" + town + ", postcode=" + postcode + ", isAdmin="
				+ isAdmin + "]";
	}

}
