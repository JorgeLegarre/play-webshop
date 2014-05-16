package controllers.privat;

import java.util.List;

import manager.CategoryManager;
import models.Category;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import views.html.privat.category.listAll;
import views.html.privat.category.upsert;
import DAO.CategoryDao;
import controllers.GeneralController;

public class CategoryController extends GeneralController {

	private final static CategoryManager categoryManager = new CategoryManager(
			new CategoryDao());

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result listAllCategories() {
		List<Category> categories = categoryManager.listAll();

		return ok(listAll.render(categories));
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showAddCategory() {
		Category category = new Category();

		enableEditionMode();

		return showCategory(category);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showOneCategory(int id) {
		Category category = categoryManager.findById(id);

		return showCategory(category);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showEditCategory(int id) {
		Category category = categoryManager.findById(id);

		enableEditionMode();

		return showCategory(category);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result deleteCategory() {
		int id = getParamInt("categoryId");

		categoryManager.removeById(id);

		return redirect(routes.CategoryController.listAllCategories());
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result saveCategory() {
		Category category = parseForm();

		categoryManager.save(category);

		return redirect(routes.CategoryController.listAllCategories());
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(RestAutenticatedController.class)
	public static Result listAllCategoriesRest() {
		List<Category> categories = categoryManager.listAll();

		setNullToProducts(categories);

		return ok(Json.toJson(categories));
	}

	@Transactional(readOnly = true)
	@Security.Authenticated(RestAutenticatedController.class)
	public static Result getCategoryRest(int id) {
		Category category = categoryManager.findById(id);

		if (category != null) {
			category.setProducts(null);
			return ok(Json.toJson(category));
		}

		return ok("{}");
	}

	@Transactional
	@Security.Authenticated(RestAutenticatedController.class)
	public static Result saveCategoryRest() {

		Category category = parseForm();

		categoryManager.save(category);

		return ok(Json.toJson(true));
	}

	private static void setNullToProducts(List<Category> categories) {
		for (Category category : categories) {
			category.setProducts(null);
		}

	}

	private static Result showCategory(Category category) {
		if (category == null) {
			return notFound("Category not found");
		}

		return ok(upsert.render(category));
	}

	private static Category parseForm() {
		Category category = new Category();
		category.setId(getParamInt("id"));
		category.setName(getParamString("name"));

		return category;
	}

}
