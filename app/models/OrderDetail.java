package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

//I don't use inheritance from product because i
// want to keep separate the concepts of 
//product and orderdetail
@Entity
public class OrderDetail {
	@Embeddable
	public static class Key implements Serializable {

		private static final long serialVersionUID = 20140318;

		private int orderId;
		private int productId;

		public Key(int orderId, int productId) {
			this.orderId = orderId;
			this.productId = productId;
		}

		public int getOrderId() {
			return orderId;
		}

		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + orderId;
			result = prime * result + productId;
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
			Key other = (Key) obj;
			if (orderId != other.orderId)
				return false;
			if (productId != other.productId)
				return false;
			return true;
		}

	}

	@EmbeddedId
	private Key id;

	@ManyToOne
	@MapsId("orderId")
	private Order order;

	@Column(nullable = false)
	private String name;

	private String description;

	@Column(nullable = false)
	private double cost;

	@Column(nullable = false)
	private double rrp;

	@Column(nullable = false)
	private int quantity;

	public OrderDetail() {
	}

	public OrderDetail(int orderId, int productId, Order order, String name,
			String description, double cost, double rrp, int quantity) {
		super();
		this.id = new Key(orderId, productId);
		this.order = order;
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.rrp = rrp;
		this.quantity = quantity;
	}

	public int getOrder() {
		return id.orderId;
	}

	public int getProductId() {
		return id.productId;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public double getSubTotalCost() {
		return cost * quantity;
	}

	public double getSubTotalRrp() {
		return rrp * quantity;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + quantity;
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
		OrderDetail other = (OrderDetail) obj;
		if (Double.doubleToLongBits(cost) != Double
				.doubleToLongBits(other.cost))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (quantity != other.quantity)
			return false;
		if (Double.doubleToLongBits(rrp) != Double.doubleToLongBits(other.rrp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderDetail [order=" + id.orderId + ", productId="
				+ id.productId + ", name=" + name + ", description="
				+ description + ", cost=" + cost + ", rrp=" + rrp
				+ ", quantity=" + quantity + "]";
	}

}
