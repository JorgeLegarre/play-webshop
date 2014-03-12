package models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

public final class Order {
	public static final int DEFAULT_ID = -1;

	private final int id;
	private final int userId;
	private final Date date;
	private final List<OrderDetail> orderDetails;

	public Order(int id, int userId, Date date, List<OrderDetail> orderDetails) {
		this.id = id;
		this.userId = userId;
		this.date = DateUtils.round(date, Calendar.SECOND);
		this.orderDetails = orderDetails;
	}

	public Order(int userId, Date date, List<OrderDetail> orderDetails) {
		this(DEFAULT_ID, userId, date, orderDetails);
	}

	public Order(int id, Order other) {
		this(id, other.userId, other.date, other.orderDetails);
	}

	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public Date getDate() {
		return date;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public double getTotalCost() {
		double totalCost = 0;
		for (OrderDetail orderDetail : orderDetails) {
			totalCost += orderDetail.getSubTotalCost();
		}
		return totalCost;
	}

	public double getTotalRRP() {
		double totalRrp = 0;
		for (OrderDetail orderDetail : orderDetails) {
			totalRrp += orderDetail.getSubTotalRrp();
		}
		return totalRrp;
	}

	public double getProfit() {
		return getTotalRRP() - getTotalCost();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((orderDetails == null) ? 0 : orderDetails.hashCode());
		result = prime * result + userId;
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
		Order other = (Order) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (orderDetails == null) {
			if (other.orderDetails != null)
				return false;
		} else if (!orderDetails.equals(other.orderDetails))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", user_id=" + userId + ", date=" + date
				+ ", orderDetails=" + orderDetails + "]";
	}

}
