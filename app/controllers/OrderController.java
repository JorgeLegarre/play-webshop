package controllers;

import java.util.Arrays;
import java.util.List;

import models.Order;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.privat.order.listAll;
import views.html.privat.order.upsert;

public final class OrderController extends Controller {
	/*
	 * private static List<OrderDetail> detail1 = Arrays .asList(new
	 * OrderDetail[] { new OrderDetail.Builder(1, 10, 20.5, 3).name("Piece 1")
	 * .build(), new OrderDetail.Builder(2, 20, 400.5, 1).name("Piece 2")
	 * .build(), new OrderDetail.Builder(3, 1, 0.5, 50).name("Piece 3") .build()
	 * });
	 * 
	 * private static List<OrderDetail> detail2 = Arrays .asList(new
	 * OrderDetail[] { new OrderDetail.Builder(4, 1, 20.5, 3).name("Piece 4")
	 * .build(), new OrderDetail.Builder(5, 2, 400.5, 1).name("Piece 5")
	 * .build(), new OrderDetail.Builder(6, 3, 0.5, 50).name("Piece 6")
	 * .build(), new OrderDetail.Builder(7, 4, 20, 3).name("Piece 7") .build(),
	 * new OrderDetail.Builder(8, 5, 13.5, 1).name("Piece 8") .build(), new
	 * OrderDetail.Builder(9, 6, 1000, 50).name("Piece 9") .build() });
	 * 
	 * private static List<Order> listOrders = Arrays .asList(new Order[] { new
	 * Order(1, 1, new Date(), detail1), new Order(2, 2, new Date(), detail2),
	 * new Order(3, 1, new Date(), detail1), new Order(4, 1, new Date(),
	 * detail2) });
	 */
	private static List<Order> listOrders = Arrays.asList();

	@Transactional
	public static void fillDB() {

	}

	@Transactional
	public static Result showOneOrder(int id) {
		Order order = getOneOrder(id);

		if (order == null) {
			return notFound("Order not found");
		}

		return ok(upsert.render(order));
	}

	@Transactional
	public static Result listAllOrders() {
		return ok(listAll.render(listOrders));
	}

	private static Order getOneOrder(int id) {
		if (id > 0 && id <= listOrders.size()) {
			return listOrders.get(id - 1);
		}
		return null;
	}
}
