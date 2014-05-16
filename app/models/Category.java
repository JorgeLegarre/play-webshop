package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Category {
	public static int DEFAULT_ID = 0;

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String name;

	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "Product_Category", joinColumns = { @JoinColumn(name = "category_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "product_id", referencedColumnName = "id") })
	private List<Product> products;

	public Category() {
		products = new ArrayList<>();
	}

	public Category(int id, String name) {
		this.id = "".equals(id) ? DEFAULT_ID : id;
		this.name = name;
	}

	public Category(String name) {
		this(DEFAULT_ID, name);
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public int hashCode() {
		return 31 * id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Category) {
			Category other = (Category) obj;
			return id == other.id;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("Id: %s, Name: %s", id, name);
	}

}
