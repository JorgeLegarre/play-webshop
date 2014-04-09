package forms;

import java.util.ArrayList;
import java.util.List;

public class OrderForm {
	private int id;
	private int userId;
	private String date;
	private int status;
	private List<Integer> productIds;
	private List<String> productNames;
	private List<Integer> quantitys;
	private List<Double> costs;
	private List<Double> rrps;

	public OrderForm() {
		productIds = new ArrayList<>();
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

	public int getQuantity(int i) {
		return ((i >= 0 && i < quantitys.size())) ? quantitys.get(i)
				: Integer.MIN_VALUE;
	}

	public double getCost(int i) {
		return ((i >= 0 && i < costs.size())) ? costs.get(i) : Double.NaN;
	}

	public double getRrp(int i) {
		return ((i >= 0 && i < rrps.size())) ? rrps.get(i) : Double.NaN;
	}

	public List<Integer> getProductIds() {
		return productIds;
	}

	public int getProductId(int i) {
		return ((i >= 0 && i < productIds.size())) ? productIds.get(i)
				: Integer.MIN_VALUE;
	}

	public void setProductIds(List<Integer> productIds) {
		this.productIds = productIds;
	}

	public String getProductName(int i) {
		return ((i >= 0 && i < productNames.size())) ? productNames.get(i)
				: null;
	}

	public List<String> getProductNames() {
		return productNames;
	}

	public void setProductNames(List<String> productNames) {
		this.productNames = productNames;
	}

	public List<Integer> getQuantitys() {
		return quantitys;
	}

	public void setQuantitys(List<Integer> quantitys) {
		this.quantitys = quantitys;
	}

	public List<Double> getCosts() {
		return costs;
	}

	public void setCosts(List<Double> costs) {
		this.costs = costs;
	}

	public List<Double> getRrps() {
		return rrps;
	}

	public void setRrps(List<Double> rrps) {
		this.rrps = rrps;
	}

	@Override
	public String toString() {
		return "OrderForm [id=" + id + ", userId=" + userId + ", date=" + date
				+ ", status=" + status + ", productIds=" + productIds
				+ ", productNames=" + productNames + ", quantitys=" + quantitys
				+ ", costs=" + costs + ", rrps=" + rrps + "]";
	}

}
