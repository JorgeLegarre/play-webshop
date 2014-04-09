package controllers.publica;

import java.util.List;

import manager.ProductManager;
import models.Product;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.publica.product.listAll;
import DAO.ProductDao;

public class ProductsPublicController extends Controller {
	private final static ProductManager productManager = new ProductManager(
			new ProductDao());

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result listAllProducts() {
		List<Product> products = productManager.listAll();

		return ok(listAll.render(products));
	}
}
