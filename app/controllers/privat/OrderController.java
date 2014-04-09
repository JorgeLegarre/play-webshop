package controllers.privat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import manager.OrderManager;
import manager.OrderStatusManager;
import manager.UserManager;
import models.Order;
import models.OrderDetail;
import models.OrderStatus;
import models.User;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import utils.DateUtils;
import views.html.privat.order.listAll;
import views.html.privat.order.upsert;
import DAO.OrderDao;
import DAO.OrderStatusDao;
import DAO.UserDao;
import controllers.GeneralController;
import forms.OrderForm;

public final class OrderController extends GeneralController {

	private final static OrderManager orderManager = new OrderManager(
			new OrderDao());
	private final static OrderStatusManager statusManager = new OrderStatusManager(
			new OrderStatusDao());
	private final static UserManager userManager = new UserManager(
			new UserDao());

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result listAllOrders() {
		List<Order> orders = orderManager.listAll();

		return ok(listAll.render(orders));
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showOneOrder(int id) {
		Order order = orderManager.findById(id);

		return showOrder(order);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showEditOrder(int id) {
		Order order = orderManager.findById(id);

		enableEditionMode();

		return showOrder(order);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result saveOrder() {
		Order order = parseForm();

		orderManager.save(order);

		return redirect(routes.OrderController.listAllOrders());

	}

	private static Result showOrder(Order order) {
		if (order == null) {
			return notFound("Order not found");
		}

		List<OrderStatus> status = statusManager.listAll();

		return ok(upsert.render(order, status));
	}

	private static Order parseForm() {
		Form<OrderForm> forms = Form.form(OrderForm.class);
		OrderForm orderForm = forms.bindFromRequest().get();

		orderForm.setProductIds(getListInt("productId"));
		orderForm.setProductNames(getListString("productName"));
		orderForm.setQuantitys(getListInt("quantity"));
		orderForm.setCosts(getListDouble("cost"));
		orderForm.setRrps(getListDouble("rrp"));

		return parseForm(orderForm);
	}

	private static Order parseForm(OrderForm autoForm) {
		User user = userManager.findById(autoForm.getUserId());
		Date date = DateUtils.parse(autoForm.getDate());
		OrderStatus status = statusManager.findById(autoForm.getStatus());
		List<OrderDetail> orderDetails = parseOrderDetails(autoForm);

		return new Order(autoForm.getId(), user, date, status, orderDetails);
	}

	private static List<OrderDetail> parseOrderDetails(OrderForm autoForm) {
		List<OrderDetail> details = new ArrayList<>();

		for (int i = 0; i < autoForm.getQuantitys().size(); i++) {
			details.add(new OrderDetail(autoForm.getId(), autoForm
					.getProductId(i), autoForm.getProductName(i), autoForm
					.getCost(i), autoForm.getRrp(i), autoForm.getQuantity(i)));
		}

		return details;
	}
}
