package controllers.privat;

import java.util.ArrayList;
import java.util.List;

import manager.CategoryManager;
import manager.ProductManager;
import models.Category;
import models.Product;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import views.html.privat.product.listAll;
import views.html.privat.product.upsert;
import DAO.CategoryDao;
import DAO.ProductDao;
import controllers.GeneralController;
import forms.ProductForm;

public final class ProductController extends GeneralController {

	private final static ProductManager productManager = new ProductManager(
			new ProductDao());
	private final static CategoryManager categoryManager = new CategoryManager(
			new CategoryDao());

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result listAllProducts() {
		List<Product> products = productManager.listAll();

		return ok(listAll.render(products));
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showAddProduct() {
		Product product = new Product();

		enableEditionMode();

		return showProduct(product);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showOneProduct(int id) {
		Product product = productManager.findById(id);

		return showProduct(product);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showEditProduct(int id) {
		Product product = productManager.findById(id);

		enableEditionMode();

		return showProduct(product);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result deleteProduct() {
		int id = getParamInt("productId");

		productManager.removeById(id);

		return redirect(routes.ProductController.listAllProducts());
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result saveProduct() {
		Product product = parseForm();

		if (formHasErrors()) {
			prepareMsgErrors();

			enableEditionMode();
			return showProduct(product);
		}

		productManager.save(product);

		return redirect(routes.ProductController.listAllProducts());
	}

	private static boolean formHasErrors() {
		return Form.form(ProductForm.class).bindFromRequest().hasErrors();
	}

	private static void prepareMsgErrors() {
		Form<ProductForm> form = Form.form(ProductForm.class).bindFromRequest();

		prepareMsgErrors(form.errors(), form.errorsAsJson());
	}

	private static Result showProduct(Product product) {

		List<Category> categories = categoryManager.listAll();

		if (product == null) {
			return notFound("Product not found");
		}

		return ok(upsert.render(product, categories));
	}

	private static Product parseForm() {
		Product product = new Product();
		product.setId(getParamInt("id"));
		product.setName(getParamString("name"));
		product.setDescription(getParamString("description"));
		product.setCost(getParamDouble("cost"));
		product.setRrp(getParamDouble("rrp"));
		product.setProductStock(getParamInt("productStock"));
		product.setCategories(parseCategories(getListInt("categories")));

		return product;
	}

	private static List<Category> parseCategories(List<Integer> categories) {
		List<Category> retorno = new ArrayList<>();
		for (int id : categories) {
			retorno.add(categoryManager.findById(id));
		}
		return retorno;
	}
}
