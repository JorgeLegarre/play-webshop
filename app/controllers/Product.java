package controllers;

import java.util.Arrays;
import java.util.List;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.product.listAll;
import views.html.product.upsert;

public final class Product extends Controller {
	@Transactional
	public static void fillDB() {
		JPA.em().persist(
				new models.Product("Lord of the Rings", "JR Tolkien's book",
						180.5, 200, Arrays.asList(new models.Category[] {
								new models.Category(1, "Books", 1),
								new models.Category(2, "Films", 2) })));
		JPA.em().persist(
				new models.Product("Hungry games", "Unknow's book", 120.5, 180,
						Arrays.asList(new models.Category[] {
								new models.Category(1, "Books", 1),
								new models.Category(3, "Games", 3) })));
	}

	@Transactional
	public static Result showOneProduct(int id) {
		models.Product product = getOneProduct(id);

		if (product == null) {
			return notFound("Product not found");
		}

		return ok(upsert.render(product));
	}

	@Transactional
	public static Result listAllProducts() {
		List<models.Product> products = getAllProducts();
		return ok(listAll.render(products));
	}

	private static models.Product getOneProduct(int id) {
		List<models.Product> products = JPA
				.em()
				.createQuery("SELECT p from Product p WHERE p.id = :id",
						models.Product.class).setParameter("id", id)
				.getResultList();

		return (products.size() > 0) ? products.get(0) : null;
	}

	private static List<models.Product> getAllProducts() {
		return JPA.em()
				.createQuery("SELECT p from Product p", models.Product.class)
				.getResultList();
	}

}
