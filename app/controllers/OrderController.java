package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Order;
import models.OrderDetail;
import models.OrderStatus;
import models.Product;
import models.User;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.privat.order.listAll;
import views.html.privat.order.upsert;
import forms.OrderForm;

public final class OrderController extends Controller {

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result listAllOrders() {
		List<Order> orders = getAllOrders();

		return ok(listAll.render(orders));
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showAddOrder() {
		Order order = new Order();
		List<OrderStatus> status = getAllOrderStatus();
		List<User> users = getAllUsers();

		flash().put("edit", "yes");
		return ok(upsert.render(order, status, users));
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showOneOrder(int id) {
		return showOrder(id, false);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showEditOrder(int id) {
		return showOrder(id, true);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result deleteOrder() {
		Map<String, String[]> parameters = request().body().asFormUrlEncoded();
		String[] orderId = parameters.get("orderId");

		if (orderId != null && orderId.length > 0) {
			int id = Integer.parseInt(orderId[0]);

			Order order = getOneOrder(id);

			if (order != null) {
				JPA.em().remove(order);
			}

			return redirect(routes.OrderController.listAllOrders());
		}

		return badRequest("Order unknow");
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result saveOrder() {
		Form<OrderForm> forms = Form.form(OrderForm.class);
		OrderForm autoForm = forms.bindFromRequest().get();
		completeAutoForm(autoForm);

		saveFromForm(autoForm);

		return redirect(routes.OrderController.listAllOrders());
	}

	private static void completeAutoForm(OrderForm autoForm) {
		Map<String, String[]> parameters = request().body().asFormUrlEncoded();

		autoForm.setQuantity(getIntArray(parameters.get("quantity")));
		autoForm.setCost(getDoubleArray(parameters.get("cost")));
		autoForm.setRrp(getDoubleArray(parameters.get("rrp")));

	}

	private static void saveFromForm(OrderForm form) {
		Order order = getOneOrder(form.getId());
		order.setStatus(getStatus(form.getStatus()));

		List<OrderDetail> toDelete = new ArrayList<>();
		for (int i = 0; i < order.getOrderDetails().size(); i++) {
			OrderDetail oldDetail = order.getOrderDetails().get(i);

			int newQuantity = form.getQuantity()[i];
			double newCost = form.getCost()[i];
			double newRrp = form.getRrp()[i];

			updateProductStock(oldDetail.getId().getProductId(),
					oldDetail.getQuantity(), newQuantity);

			if (newQuantity == 0) {
				toDelete.add(oldDetail);
			} else {
				oldDetail.setQuantity(newQuantity);
				oldDetail.setCost(newCost);
				oldDetail.setRrp(newRrp);
			}

		}

		// order.getOrderDetails().removeAll(toDelete);
		// This not work and is suppossed that is the way to do it... so I
		// delete one by one
		for (OrderDetail del : toDelete) {
			JPA.em().remove(del);
		}

	}

	private static void updateProductStock(int productId, int oldQuantity,
			int newQuantity) {
		Product product = getOneProduct(productId);
		int oldProductStock = product.getProductStock();

		int newProductStock = oldProductStock + oldQuantity - newQuantity;

		product.setProductStock(newProductStock);

	}

	private static double[] getDoubleArray(String[] strings) {
		double[] retorno = new double[strings.length];
		for (int i = 0; i < strings.length; i++) {
			retorno[i] = Double.parseDouble(strings[i]);
		}
		return retorno;
	}

	private static int[] getIntArray(String[] strings) {
		int[] retorno = new int[strings.length];
		for (int i = 0; i < strings.length; i++) {
			retorno[i] = Integer.parseInt(strings[i]);
		}
		return retorno;
	}

	private static Result showOrder(int id, boolean edit) {
		Order order = getOneOrder(id);
		List<OrderStatus> status = getAllOrderStatus();
		List<User> users = getAllUsers();

		if (order == null) {
			return notFound("Order not found");
		}

		if (edit) {
			flash().put("edit", "yes");
		}
		return ok(upsert.render(order, status, users));
	}

	private static Product getOneProduct(int productId) {
		return JPA.em().find(Product.class, productId);
	}

	private static Order getOneOrder(int id) {
		return JPA.em().find(Order.class, id);
	}

	private static List<Order> getAllOrders() {
		return JPA.em().createQuery("SELECT o from Order o", Order.class)
				.getResultList();
	}

	private static OrderStatus getStatus(int status) {
		return JPA.em().find(OrderStatus.class, status);
	}

	private static List<OrderStatus> getAllOrderStatus() {
		return JPA.em()
				.createQuery("SELECT s from OrderStatus s", OrderStatus.class)
				.getResultList();
	}

	private static List<User> getAllUsers() {
		return JPA.em().createQuery("SELECT u from User u", User.class)
				.getResultList();
	}
}
