package controllers.privat;

import java.util.ArrayList;
import java.util.List;

import manager.CategoryManager;
import manager.ProductManager;
import models.Category;
import models.Product;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
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

	@Transactional
	@Security.Authenticated(RestAutenticatedController.class)
	public static Result saveProductRest() {
		Product product = parseForm();

		productManager.save(product);

		return ok(Json.toJson(true));
	}

	@Transactional
	@Security.Authenticated(RestAutenticatedController.class)
	public static Result getImagePathsRest() {
		List<String> imagePaths = productManager.getImagePaths();

		return ok(Json.toJson(imagePaths));
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
		product.setPicture(getParamString("picture"));

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
