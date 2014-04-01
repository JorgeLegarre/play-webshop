package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class OrderDetail {
	@Embeddable
	public static class Key implements Serializable {

		private static final long serialVersionUID = 20140318;

		private int orderId;
		private int productId;

		public Key() {
		}

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

	@Column(nullable = false)
	private double cost;

	@Column(nullable = false)
	private double rrp;

	@Column(nullable = false)
	private int quantity;

	public OrderDetail() {
	}

	public OrderDetail(Order order, int productId, String name, double cost,
			double rrp, int quantity) {
		super();
		this.id = new Key(order.getId(), productId);
		this.order = order;
		this.name = name;
		this.cost = cost;
		this.rrp = rrp;
		this.quantity = quantity;
	}

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getSubTotalCost() {
		return quantity * cost;
	}

	public double getSubTotalRrp() {
		return quantity * rrp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderDetail [id=" + id + ", order=" + order + ", name=" + name
				+ ", cost=" + cost + ", rrp=" + rrp + ", quantity=" + quantity
				+ "]";
	}

}
