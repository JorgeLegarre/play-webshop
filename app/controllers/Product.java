package controllers;

import java.util.Arrays;
import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.product.listAll;
import views.html.product.upsert;

public final class Product extends Controller {

	private static final List<models.Product> listProducts = Arrays
			.asList(new models.Product[] {
					new models.Product.Builder("Lord of the Rings").id(1)
							.cost(180.5).build(),
					new models.Product.Builder("Hungry games").id(2)
							.cost(120.5).build() });

	public static Result showOneProduct(int id) {
		models.Product product = getOneProduct(id);

		if (product == null) {
			return notFound("Product not found");
		}

		return ok(upsert.render(product));
	}

	public static Result listAllProducts() {
		return ok(listAll.render(listProducts));
	}

	private static models.Product getOneProduct(int id) {
		if (id > 0 && id <= listProducts.size()) {
			return listProducts.get(id - 1);
		}
		return null;
	}

}
