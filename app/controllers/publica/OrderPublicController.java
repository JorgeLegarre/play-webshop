package controllers.publica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import manager.OrderManager;
import manager.OrderStatusManager;
import manager.ShoppingCartManager;
import manager.UserManager;
import models.Order;
import models.OrderDetail;
import models.OrderDetail.Key;
import models.OrderStatus;
import models.ShoppingCartDetail;
import models.User;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import utils.DateUtils;
import views.html.publica.orders.listUserOrders;
import views.html.publica.orders.userOrder;
import DAO.OrderDao;
import DAO.OrderStatusDao;
import DAO.ShoppingCartDao;
import DAO.UserDao;
import controllers.GeneralController;
import controllers.privat.RestAutenticatedController;

public class OrderPublicController extends GeneralController {
	private final static OrderManager orderManager = new OrderManager(
			new OrderDao());
	private final static UserManager userManager = new UserManager(
			new UserDao());
	private final static OrderStatusManager statusManager = new OrderStatusManager(
			new OrderStatusDao());
	private final static ShoppingCartManager shoppingCartManager = new ShoppingCartManager(
			new ShoppingCartDao());

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
	public static Result placeOrder() {
		Order order = getOrder();

		voidShoppingCart(order.getUser());

		orderManager.save(order);

		session().put("items", "0");

		return redirect(routes.OrderPublicController.showUserOrders());
	}

	@Transactional
	@Security.Authenticated(RestAutenticatedController.class)
	public static Result placeOrderRest() {
		Order order = getOrder();
		System.out.println("order" + 1);
		voidShoppingCart(order.getUser());
		System.out.println("order" + 2);
		orderManager.save(order);
		System.out.println("order" + 3);
		session().put("items", "0");
		System.out.println("order" + 4);
		return ok(Json.toJson(true));
	}

	private static void voidShoppingCart(User user) {
		int id = user.getShoppingCart().getId();
		user.setShoppingCart(null);
		shoppingCartManager.removeById(id);
	}

	private static Order getOrder() {
		Order order = new Order();
		order.setDate(DateUtils.reduceDate(new Date()));
		order.setStatus(statusManager.findById(OrderStatus.FIRST_STATUS));
		order.setUser(getCurrentUser());

		List<OrderDetail> details = getDetails(order);
		order.setOrderDetails(details);

		return order;
	}

	private static List<OrderDetail> getDetails(Order order) {
		List<OrderDetail> details = new ArrayList<OrderDetail>();

		for (ShoppingCartDetail scDetail : order.getUser().getShoppingCart()
				.getShoppingCartDetails()) {
			OrderDetail detail = new OrderDetail();
			Key id = new Key();
			id.setProductId(scDetail.getId().getProductId());
			id.setOrderId(order.getId());
			detail.setId(id);
			detail.setOrder(order);
			detail.setName(scDetail.getProduct().getName());
			detail.setCost(scDetail.getProduct().getCost());
			detail.setRrp(scDetail.getProduct().getRrp());
			detail.setQuantity(scDetail.getQuantity());

			details.add(detail);
		}

		return details;
	}

	private static User getCurrentUser() {
		String email = session().get("email");
		return userManager.findByEmail(email);
	}

}
