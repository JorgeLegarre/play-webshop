package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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

	@Column(nullable = false, columnDefinition = "int(10) default '0'")
	private int productStock;

	@ManyToMany
	@JoinTable(name = "Product_Category", joinColumns = { @JoinColumn(name = "product_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "category_id", referencedColumnName = "id") })
	private List<Category> categories;

	@Column(columnDefinition = "varchar(255) default 'assets/img/noImage.png'")
	private String picture;

	public Product() {
		categories = new ArrayList<Category>();
		this.picture = "assets/img/noImage.png";
	}

	public Product(int id, String name, String description, double cost,
			double rrp, int productStock, List<Category> categories) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.rrp = rrp;
		this.productStock = productStock;
		this.categories = categories;
		this.picture = "assets/img/noImage.png";
	}

	public Product(String name, String description, double cost, double rrp,
			int productStock, List<Category> categories) {
		this(DEFAULT_PRODUCT_ID, name, description, cost, rrp, productStock,
				categories);
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

	public int getProductStock() {
		return productStock;
	}

	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description="
				+ description + ", cost=" + cost + ", rrp=" + rrp
				+ ", productStock=" + productStock + ", categories="
				+ categories + "]";
	}
}
