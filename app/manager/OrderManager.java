package manager;

import java.util.ArrayList;
import java.util.List;

import models.Order;
import models.OrderDetail;
import models.Product;
import DAO.GenericJpaDao;
import DAO.OrderDao;
import DAO.ProductDao;

public final class OrderManager extends GeneralManager<Integer, Order> {
	private final ProductDao productDao;

	public OrderManager(GenericJpaDao<Integer, Order> dao) {
		super(dao);

		productDao = new ProductDao();
	}

	@Override
	public Order save(Order order) {
		Order oldOrder = super.findById(order.getId());

		List<OrderDetail> oldOrderDetails = (oldOrder == null) ? new ArrayList<OrderDetail>()
				: oldOrder.getOrderDetails();

		updateProductsStock(oldOrderDetails, order.getOrderDetails());

		removeDetailsWithZeroQuantity(order);

		return super.save(order);
	}

	public int getNOrders() {
		return ((OrderDao) dao).getNOrders();
	}

	private void updateProductsStock(List<OrderDetail> oldOrderDetails,
			List<OrderDetail> orderDetails) {

		for (OrderDetail detail : orderDetails) {
			int index = oldOrderDetails.indexOf(detail);

			int oldQuantity = (index < 0) ? 0 : oldOrderDetails.get(index)
					.getQuantity();

			int quantity = oldQuantity - detail.getQuantity();

			addProductStock(detail.getId().getProductId(), quantity);// reduce
																		// or
																		// increment
		}

	}

	private void removeDetailsWithZeroQuantity(Order order) {
		List<OrderDetail> detailsToRemove = new ArrayList<>();
		for (OrderDetail detail : order.getOrderDetails()) {
			if (detail.getQuantity() == 0) {
				detailsToRemove.add(detail);
			}
		}

		order.getOrderDetails().removeAll(detailsToRemove);
	}

	// common
	private void addProductStock(int productId, int quantity) {
		Product product = productDao.findById(productId);
		product.setProductStock(product.getProductStock() + quantity);

	}
}
