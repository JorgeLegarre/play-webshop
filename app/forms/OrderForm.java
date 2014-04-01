package forms;

import java.util.Arrays;

public class OrderForm {
	private int id;
	private int userId;
	private String date;
	private int status;
	private int[] quantity;
	private double[] cost;
	private double[] rrp;

	public OrderForm() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int[] getQuantity() {
		return quantity;
	}

	public void setQuantity(int[] quantity) {
		this.quantity = quantity;
	}

	public double[] getCost() {
		return cost;
	}

	public void setCost(double[] cost) {
		this.cost = cost;
	}

	public double[] getRrp() {
		return rrp;
	}

	public void setRrp(double[] rrp) {
		this.rrp = rrp;
	}

	@Override
	public String toString() {
		return "OrderForm [id=" + id + ", userId=" + userId + ", date=" + date
				+ ", status=" + status + ", quantity="
				+ Arrays.toString(quantity) + ", cost=" + Arrays.toString(cost)
				+ ", rrp=" + Arrays.toString(rrp) + "]";
	}

}
