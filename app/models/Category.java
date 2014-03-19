package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Category {
	public static int DEFAULT_ID = 0;

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String name;

	private int staff_responsible;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
	private List<Product> products;

	public Category() {

	}

	public Category(int id, String name, int staff_responsible) {
		this.id = id;
		this.name = name;
		this.staff_responsible = staff_responsible;
	}

	public Category(String name, int staff_responsible) {
		this(DEFAULT_ID, name, staff_responsible);
	}

	public Category(int id, Category other) {
		this(id, other.name, other.staff_responsible);
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

	public int getStaff_responsible() {
		return staff_responsible;
	}

	public void setStaff_responsible(int staff_responsible) {
		this.staff_responsible = staff_responsible;
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
		return String.format("Id: %s, Name: %s, Straff_responsible: %s", id,
				name, staff_responsible);
	}

}
