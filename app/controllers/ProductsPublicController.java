package controllers;

import java.util.List;

import models.Product;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.publica.product.listAll;

public class ProductsPublicController extends Controller {
	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result listAllProducts() {
		List<Product> products = getAllProducts();

		return ok(listAll.render(products));
	}

	private static List<Product> getAllProducts() {
		return JPA.em().createQuery("SELECT p from Product p", Product.class)
				.getResultList();
	}
}
