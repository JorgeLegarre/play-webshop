package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Orders")
public class Order {
	public static int DEFAULT_ID = 0;

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	private User user;

	@Column(nullable = false)
	private Date date;

	@ManyToOne
	private OrderStatus status;

	@OneToMany(mappedBy = "order", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<OrderDetail> orderDetails;

	public Order() {
		orderDetails = new ArrayList<>();
	}

	public Order(int id, User user, Date date, OrderStatus status,
			List<OrderDetail> orderDetails) {
		this.id = id;
		this.user = user;
		this.date = date;
		this.status = status;
		this.orderDetails = orderDetails;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public String getDateString() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public double getTotalCost() {
		double total = 0;

		for (OrderDetail detail : orderDetails) {
			total += detail.getSubTotalCost();
		}

		return total;
	}

	public double getTotalRrp() {
		double total = 0;

		for (OrderDetail detail : orderDetails) {
			total += detail.getSubTotalRrp();
		}

		return total;
	}

	public double getTotalProfit() {
		return getTotalRrp() - getTotalCost();
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
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", user=" + user + ", date=" + date
				+ ", status=" + status + ", orderDetails=" + orderDetails + "]";
	}

}
