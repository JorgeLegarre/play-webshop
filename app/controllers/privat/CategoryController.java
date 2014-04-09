package controllers.privat;

import java.util.List;

import controllers.GeneralController;
import manager.CategoryManager;
import models.Category;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import views.html.privat.category.listAll;
import views.html.privat.category.upsert;
import DAO.CategoryDao;
import forms.CategoryForm;

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

	private static Result showCategory(Category category) {
		if (category == null) {
			return notFound("Category not found");
		}

		return ok(upsert.render(category));
	}

	private static Category parseForm() {
		Form<CategoryForm> forms = Form.form(CategoryForm.class);
		CategoryForm autoForm = forms.bindFromRequest().get();

		return parseForm(autoForm);
	}

	private static Category parseForm(CategoryForm categoryForm) {
		return new Category(categoryForm.getId(), categoryForm.getName());
	}

}
