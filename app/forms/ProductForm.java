package forms;

import java.util.ArrayList;
import java.util.List;

public final class ProductForm {
	private int id;
	private String name;
	private String description;
	private double cost;
	private double rrp;
	private List<Integer> categories;

	public ProductForm() {
		categories = new ArrayList<>();
	}

	public ProductForm(int id, String name, String description, double cost,
			double rrp, List<Integer> categories) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.rrp = rrp;
		this.categories = categories;
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

	public List<Integer> getCategories() {
		return categories;
	}

	public void setCategories(List<Integer> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "ProductForm [id=" + id + ", name=" + name + ", description="
				+ description + ", cost=" + cost + ", rrp=" + rrp
				+ ", categories=" + categories + "]";
	}

}
