package controllers;

import java.util.List;
import java.util.Map;

import models.Category;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.privat.category.listAll;
import views.html.privat.category.upsert;
import forms.CategoryForm;

public class CategoryController extends Controller {

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result listAllCategories() {
		List<Category> categories = getAllCategories();

		return ok(listAll.render(categories));
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showAddCategory() {
		Category category = new Category();

		flash().put("edit", "yes");
		return ok(upsert.render(category));
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showOneCategory(int id) {
		return showCategory(id, false);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showEditCategory(int id) {
		return showCategory(id, true);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result deleteCategory() {
		Map<String, String[]> parameters = request().body().asFormUrlEncoded();
		String[] categoryId = parameters.get("categoryId");

		if (categoryId != null && categoryId.length > 0) {
			int id = Integer.parseInt(categoryId[0]);

			Category category = getOneCategory(id);

			if (category != null) {
				JPA.em().remove(category);
			}

			return redirect(routes.CategoryController.listAllCategories());
		}

		return badRequest("Category unknow");
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result saveCategory() {
		Form<CategoryForm> forms = Form.form(CategoryForm.class);
		CategoryForm autoForm = forms.bindFromRequest().get();

		Category categoryForm = parseForm(autoForm);

		if (categoryForm.getId() != Category.DEFAULT_ID) {
			Category category = getOneCategory(categoryForm.getId());
			category.setName(categoryForm.getName());
		} else {
			JPA.em().persist(new Category(categoryForm.getName()));
		}
		// We dont read products so we cant use merge (it erase them)
		// JPA.em().merge(category);

		return redirect(routes.CategoryController.listAllCategories());
	}

	private static Category parseForm(CategoryForm categoryForm) {
		return new Category(categoryForm.getId(), categoryForm.getName());
	}

	private static Result showCategory(int id, boolean edit) {
		Category category = getOneCategory(id);

		if (category == null) {
			return notFound("Category not found");
		}

		if (edit) {
			flash().put("edit", "yes");
		}
		return ok(upsert.render(category));
	}

	private static Category getOneCategory(int id) {
		return JPA.em().find(Category.class, id);
	}

	private static List<Category> getAllCategories() {
		return JPA.em().createQuery("SELECT c from Category c", Category.class)
				.getResultList();
	}

}
