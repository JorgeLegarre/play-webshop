package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Product {
	public static int DEFAULT_PRODUCT_ID = 0;

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String name;

	private String description;

	@Column(nullable = false)
	private double cost;

	@Column(nullable = false)
	private double rrp;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Category> categories;

	public Product() {

	}

	public Product(int id, String name, String description, double cost,
			double rrp, List<Category> categories) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.rrp = rrp;
		this.categories = categories;
	}

	public Product(String name, String description, double cost, double rrp,
			List<Category> categories) {
		this(DEFAULT_PRODUCT_ID, name, description, cost, rrp, categories);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getRrp() {
		return rrp;
	}

	public void setRrp(double rrp) {
		this.rrp = rrp;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categories == null) ? 0 : categories.hashCode());
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(rrp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Product other = (Product) obj;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (Double.doubleToLongBits(cost) != Double
				.doubleToLongBits(other.cost))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(rrp) != Double.doubleToLongBits(other.rrp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String
				.format("Id: %s, Name: %s, Description: %s, Cost: %s, RRP: %s, Categories: %s",
						id, name, description, cost, rrp, categories);
	}
}
