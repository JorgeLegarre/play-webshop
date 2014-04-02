package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.Order;
import models.OrderDetail;
import models.OrderStatus;
import models.Product;
import models.User;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.publica.orders.listUserOrders;
import views.html.publica.orders.userOrder;

public class OrderPublicController extends Controller {
	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result showOneOrder(int id) {
		Order order = getOneOrder(id);

		return ok(userOrder.render(order));
	}

	private static Order getOneOrder(int id) {
		return JPA.em().find(Order.class, id);
	}

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result showUserOrders() {
		User user = getCurrenUser();

		return ok(listUserOrders.render(user));
	}

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result buy() {
		Map<String, String[]> parameters = request().body().asFormUrlEncoded();

		if (parameters.get("productId") == null) {
			return badRequest("Any product selected");
		}

		int productId = Integer.parseInt(parameters.get("productId")[0]);
		Product product = getOneProduct(productId);
		OrderStatus status = getFirstStatus();

		User user = getCurrenUser();

		Order order = new Order();

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			String txtDate = sf.format(new Date());
			date = sf.parse(txtDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		order.setDate(date);
		order.setStatus(status);
		order.setUser(user);

		JPA.em().persist(order);

		OrderDetail detail = new OrderDetail();
		detail.setName(product.getName());
		detail.setCost(product.getCost());
		detail.setRrp(product.getRrp());
		detail.setQuantity(1);
		detail.setOrder(order);
		OrderDetail.Key key = new OrderDetail.Key();
		key.setProductId(product.getId());
		key.setOrderId(order.getId());
		detail.setId(key);

		order.getOrderDetails().add(detail);

		return redirect(routes.OrderPublicController.showUserOrders());
	}

	private static OrderStatus getFirstStatus() {
		return JPA.em().find(OrderStatus.class, 1);
	}

	private static User getCurrenUser() {
		String email = session().get("username");
		List<User> matchingUsers = JPA
				.em()
				.createQuery("SELECT u from User u where email = :email",
						User.class).setParameter("email", email)
				.getResultList();

		if (matchingUsers.size() == 1) {
			return matchingUsers.get(0);
		}

		return null;
	}

	private static Product getOneProduct(int productId) {
		return JPA.em().find(Product.class, productId);
	}
}
