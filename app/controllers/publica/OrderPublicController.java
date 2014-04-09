package controllers.publica;

import java.util.Date;

import manager.OrderManager;
import manager.OrderStatusManager;
import manager.ProductManager;
import manager.UserManager;
import models.Order;
import models.OrderDetail;
import models.OrderDetail.Key;
import models.OrderStatus;
import models.Product;
import models.User;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import utils.DateUtils;
import views.html.publica.orders.listUserOrders;
import views.html.publica.orders.userOrder;
import DAO.OrderDao;
import DAO.OrderStatusDao;
import DAO.ProductDao;
import DAO.UserDao;
import controllers.GeneralController;

public class OrderPublicController extends GeneralController {
	private final static OrderManager orderManager = new OrderManager(
			new OrderDao());
	private final static UserManager userManager = new UserManager(
			new UserDao());
	private final static ProductManager productManager = new ProductManager(
			new ProductDao());
	private final static OrderStatusManager statusManager = new OrderStatusManager(
			new OrderStatusDao());

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result showOneOrder(int id) {
		Order order = orderManager.findById(id);

		return ok(userOrder.render(order));
	}

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result showUserOrders() {
		User user = getCurrentUser();

		return ok(listUserOrders.render(user));
	}

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result buy() {
		Order order = new Order();
		order.setDate(DateUtils.reduceDate(new Date()));
		order.setStatus(statusManager.findById(OrderStatus.FIRST_STATUS));
		order.setUser(getCurrentUser());

		int productId = getParamInt("productId");
		Product product = productManager.findById(productId);

		OrderDetail detail = new OrderDetail();
		Key id = new OrderDetail.Key();
		id.setProductId(productId);
		id.setOrderId(order.getId());
		detail.setOrder(order);
		detail.setId(id);
		detail.setName(product.getName());
		detail.setCost(product.getCost());
		detail.setRrp(product.getRrp());
		detail.setQuantity(1);

		order.getOrderDetails().add(detail);

		order = orderManager.save(order);

		return redirect(routes.OrderPublicController.showUserOrders());
	}

	private static User getCurrentUser() {
		String email = session().get("username");
		return userManager.findByEmail(email);
	}

}
