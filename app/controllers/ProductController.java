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
import views.html.product.listAll;
import views.html.product.upsert;
import forms.ProductForm;

public final class ProductController extends Controller {

	@Transactional
	@Security.Authenticated
	public static Result listAllProducts() {
		List<Product> products = getAllProducts();

		return ok(listAll.render(products));
	}

	@Transactional
	@Security.Authenticated
	public static Result showAddProduct() {
		Product product = new Product();
		List<Category> categories = getAllCategories();

		return ok(upsert.render(product, categories, true));
	}

	@Transactional
	@Security.Authenticated
	public static Result showOneProduct(int id) {
		return showProduct(id, false);
	}

	@Transactional
	@Security.Authenticated
	public static Result showEditProduct(int id) {
		return showProduct(id, true);
	}

	@Transactional
	@Security.Authenticated
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
	@Security.Authenticated
	public static Result saveProduct() {
		Form<ProductForm> forms = Form.form(ProductForm.class);
		ProductForm productForm = forms.bindFromRequest().get();
		// automatic form dont read categories
		productForm.setCategories(readCategories());

		if (productForm.getId() == 0) {
			createProduct(productForm);
		} else {
			updateProduct(productForm);
		}

		return redirect(routes.ProductController.listAllProducts());
	}

	private static Result showProduct(int id, boolean edit) {
		Product product = getOneProduct(id);
		List<Category> categories = getAllCategories();

		if (product == null) {
			return notFound("Product not found");
		}

		return ok(upsert.render(product, categories, edit));
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

	private static void createProduct(ProductForm productForm) {
		Product product = parseForm(productForm);
		JPA.em().persist(product);
	}

	private static void updateProduct(ProductForm productForm) {
		Product p = JPA.em().find(Product.class, productForm.getId());

		p.setName(productForm.getName());
		p.setDescription(productForm.getDescription());
		p.setCost(productForm.getCost());
		p.setRrp(productForm.getRrp());
		p.setCategories(searchCategories(productForm.getCategories()));

	}

	private static Product parseForm(ProductForm productForm) {
		return new Product(productForm.getId(), productForm.getName(),
				productForm.getDescription(), productForm.getCost(),
				productForm.getRrp(),
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
