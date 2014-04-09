package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public final class OrderStatus {
	public static final int FIRST_STATUS = 1;

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String description;

	public OrderStatus() {

	}

	public OrderStatus(int id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "OrderStatus [id=" + id + ", desc=" + description + "]";
	}

	@Override
	public int hashCode() {
		return 31 * id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderStatus other = (OrderStatus) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
