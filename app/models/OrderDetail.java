package models;

//I don't use inheritance from product because i
// want to keep separate the concepts of 
//product and orderdetail
public final class OrderDetail {
	public static final int DEFAULT_ORDER_ID = -1;
	private final int orderId;
	private final int productId;
	private final String name;
	private final String description;
	private final double cost;
	private final double rrp;
	private final int quantity;

	public static class Builder {
		// Required parameters
		private final int productId;
		private final double cost;
		private final double rrp;
		private final int quantity;

		// optional parameters
		private int orderId;
		private String name;
		private String description;

		public Builder(int productId, double cost, double rrp, int quantity) {
			this.productId = productId;
			this.cost = cost;
			this.rrp = rrp;
			this.quantity = quantity;

			this.orderId = DEFAULT_ORDER_ID;
			this.name = "";
			this.description = "";
		}

		public Builder orderId(int orderId) {
			this.orderId = orderId;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public OrderDetail build() {
			return new OrderDetail(this);
		}

	}

	private OrderDetail(Builder builder) {
		this.orderId = builder.orderId;
		this.productId = builder.productId;
		this.name = builder.name;
		this.description = builder.description;
		this.cost = builder.cost;
		this.rrp = builder.rrp;
		this.quantity = builder.quantity;
	}

	public OrderDetail(int orderId, OrderDetail other) {
		this.orderId = orderId;
		this.productId = other.productId;
		this.name = other.name;
		this.description = other.description;
		this.cost = other.cost;
		this.rrp = other.rrp;
		this.quantity = other.quantity;
	}

	public int getOrderId() {
		return orderId;
	}

	public int getProductId() {
		return productId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getCost() {
		return cost;
	}

	public double getRrp() {
		return rrp;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getSubTotalCost() {
		return cost * quantity;
	}

	public double getSubTotalRrp() {
		return rrp * quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + orderId;
		result = prime * result + productId;
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderId != other.orderId)
			return false;
		if (productId != other.productId)
			return false;
		if (quantity != other.quantity)
			return false;
		if (Double.doubleToLongBits(rrp) != Double.doubleToLongBits(other.rrp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderDetail [orderId=" + orderId + ", productId=" + productId
				+ ", name=" + name + ", description=" + description + ", cost="
				+ cost + ", rrp=" + rrp + ", quantity=" + quantity + "]";
	}

}
