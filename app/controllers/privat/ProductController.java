package controllers.privat;

import java.util.ArrayList;
import java.util.List;

import controllers.GeneralController;
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

		productManager.save(product);

		return redirect(routes.ProductController.listAllProducts());
	}

	private static Result showProduct(Product product) {

		List<Category> categories = categoryManager.listAll();

		if (product == null) {
			return notFound("Product not found");
		}

		return ok(upsert.render(product, categories));
	}

	private static Product parseForm() {
		Form<ProductForm> forms = Form.form(ProductForm.class);
		ProductForm productForm = forms.bindFromRequest().get();
		productForm.setCategories(getListInt("categories"));

		return parseForm(productForm);
	}

	private static Product parseForm(ProductForm productForm) {
		List<Category> categories = parseCategories(productForm.getCategories());

		return new Product(productForm.getId(), productForm.getName(),
				productForm.getDescription(), productForm.getCost(),
				productForm.getRrp(), productForm.getProductStock(), categories);
	}

	private static List<Category> parseCategories(List<Integer> categories) {
		List<Category> retorno = new ArrayList<>();
		for (int id : categories) {
			retorno.add(categoryManager.findById(id));
		}
		return retorno;
	}
}
