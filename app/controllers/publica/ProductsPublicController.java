package controllers.publica;

import java.util.List;

import manager.ProductManager;
import models.Category;
import models.Product;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.publica.product.listAll;
import views.html.publica.product.showProduct;
import DAO.ProductDao;
import controllers.privat.RestAutenticatedController;

public class ProductsPublicController extends Controller {
	private final static ProductManager productManager = new ProductManager(
			new ProductDao());

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result listAllProducts() {
		List<Product> products = productManager.listAll();

		return ok(listAll.render(products));
	}

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result showProduct(int id) {
		Product product = productManager.findById(id);

		return ok(showProduct.render(product));
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(RestAutenticatedController.class)
	public static Result listAllProductsRest() {
		List<Product> products = productManager.listAll();

		setNullToProductsOfCategories(products);

		return ok(Json.toJson(products));
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(RestAutenticatedController.class)
	public static Result getProductRest(int id) {
		Product product = productManager.findById(id);

		if (product != null) {
			setNullToProductCategories(product);
			return ok(Json.toJson(product));
		}

		return ok("{}");
	}

	private static void setNullToProductsOfCategories(List<Product> products) {
		for (Product product : products) {
			setNullToProductCategories(product);
		}

	}

	private static void setNullToProductCategories(Product product) {
		List<Category> categories = product.getCategories();
		for (Category category : categories) {
			category.setProducts(null);
		}
	}
}
