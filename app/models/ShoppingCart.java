package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public final class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 20140318;

	@Id
	@GeneratedValue
	private int id;

	@OneToOne
	private User user;

	@OneToMany(mappedBy = "id.userId", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<ShoppingCartDetail> shoppingCartDetails;

	public ShoppingCart() {
		shoppingCartDetails = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ShoppingCartDetail> getShoppingCartDetails() {
		return shoppingCartDetails;
	}

	public void setShoppingCartDetails(
			List<ShoppingCartDetail> shoppingCartDetails) {
		this.shoppingCartDetails = shoppingCartDetails;
	}

	public int getTotalItems() {
		int total = 0;
		for (ShoppingCartDetail detail : shoppingCartDetails) {
			total += detail.getQuantity();
		}
		return total;
	}

	public double getCostTotal() {
		double costTotal = 0;
		for (ShoppingCartDetail detail : shoppingCartDetails) {
			costTotal += detail.getSubTotal();
		}
		return costTotal;
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
		ShoppingCart other = (ShoppingCart) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", user=" + user
				+ ", shoppingCartDetails=" + shoppingCartDetails + "]";
	}

}
