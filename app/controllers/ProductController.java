package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Category;
import models.Product;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.privat.product.listAll;
import views.html.privat.product.upsert;
import forms.ProductForm;

public final class ProductController extends Controller {

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result listAllProducts() {
		List<Product> products = getAllProducts();

		return ok(listAll.render(products));
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showAddProduct() {
		Product product = new Product();
		List<Category> categories = getAllCategories();

		flash().put("edit", "yes");
		return ok(upsert.render(product, categories));
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showOneProduct(int id) {
		return showProduct(id, false);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showEditProduct(int id) {
		return showProduct(id, true);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result deleteProduct() {
		Map<String, String[]> parameters = request().body().asFormUrlEncoded();
		String[] productId = parameters.get("productId");

		if (productId != null && productId.length > 0) {
			int id = Integer.parseInt(productId[0]);

			Product product = getOneProduct(id);

			if (product != null) {
				JPA.em().remove(product);
			}

			return redirect(routes.ProductController.listAllProducts());
		}

		return badRequest("Product unknow");
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result saveProduct() {
		Form<ProductForm> forms = Form.form(ProductForm.class);
		ProductForm productForm = forms.bindFromRequest().get();
		// automatic form dont read categories
		productForm.setCategories(readCategories());

		Product product = parseForm(productForm);
		JPA.em().merge(product);

		return redirect(routes.ProductController.listAllProducts());
	}

	private static Result showProduct(int id, boolean edit) {
		Product product = getOneProduct(id);
		List<Category> categories = getAllCategories();

		if (product == null) {
			return notFound("Product not found");
		}

		if (edit) {
			flash().put("edit", "yes");
		}
		return ok(upsert.render(product, categories));
	}

	private static List<Integer> readCategories() {
		Map<String, String[]> parameters = request().body().asFormUrlEncoded();
		List<Integer> categories = new ArrayList<>();

		if (parameters.get("categories") != null) {
			for (String catId : parameters.get("categories")) {
				categories.add(Integer.parseInt(catId));
			}
		}
		return categories;
	}

	private static Product parseForm(ProductForm productForm) {
		return new Product(productForm.getId(), productForm.getName(),
				productForm.getDescription(), productForm.getCost(),
				productForm.getRrp(), productForm.getProductStock(),
				searchCategories(productForm.getCategories()));
	}

	private static List<Category> searchCategories(List<Integer> categories) {

		List<Category> categoriesFinded = new ArrayList<>();

		for (int categoryId : categories) {
			categoriesFinded.add(JPA.em().find(Category.class, categoryId));
		}

		return categoriesFinded;
	}

	private static Product getOneProduct(int id) {
		return JPA.em().find(Product.class, id);
	}

	private static List<Product> getAllProducts() {
		return JPA.em().createQuery("SELECT p from Product p", Product.class)
				.getResultList();
	}

	private static List<Category> getAllCategories() {
		return JPA.em().createQuery("SELECT c from Category c", Category.class)
				.getResultList();
	}

}
