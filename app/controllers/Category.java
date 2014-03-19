package controllers;

import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.category.listAll;
import views.html.category.upsert;

import com.avaje.ebean.Ebean;

public class Category extends Controller {
	public static void fillDB() {
		Ebean.save(new models.Category(1, "Books", 1));
		Ebean.save(new models.Category(2, "Films", 2));
		Ebean.save(new models.Category(3, "Games", 3));
	}

	public static Result showOneCategory(int id) {
		models.Category category = getOneCategory(id);

		if (category == null) {
			return notFound("Category not found");
		}

		return ok(upsert.render(category));
	}

	public static Result listAllCategories() {
		List<models.Category> categories = getAllCategories();
		return ok(listAll.render(categories));
	}

	private static models.Category getOneCategory(int id) {
		return Ebean.find(models.Category.class, id);
	}

	private static List<models.Category> getAllCategories() {
		return Ebean.find(models.Category.class).findList();
	}

}
