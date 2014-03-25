package controllers;

import java.util.List;

import models.Category;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.category.listAll;
import views.html.category.upsert;

public class CategoryController extends Controller {

	@Transactional
	public static Result showOneCategory(int id) {
		Category category = getOneCategory(id);

		if (category == null) {
			return notFound("Category not found");
		}

		return ok(upsert.render(category));
	}

	@Transactional
	public static Result listAllCategories() {
		List<Category> categories = getAllCategories();

		return ok(listAll.render(categories));
	}

	private static Category getOneCategory(int id) {
		return JPA.em().find(Category.class, id);
	}

	private static List<Category> getAllCategories() {
		return JPA.em().createQuery("SELECT c from Category c", Category.class)
				.getResultList();
	}

}
