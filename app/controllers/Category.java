package controllers;

import java.util.Arrays;
import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.category.listAll;
import views.html.category.upsert;

public class Category extends Controller {
	private static List<models.Category> listCategories = Arrays
			.asList(new models.Category[] { new models.Category(1, "Books", 1),
					new models.Category(2, "Films", 2),
					new models.Category(3, "Games", 3) });

	public static Result showOneCategory(int id) {
		models.Category category = getOneCategory(id);

		if (category == null) {
			return notFound("Category not found");
		}

		return ok(upsert.render(category));
	}

	public static Result listAllCategories() {
		return ok(listAll.render(listCategories));
	}

	private static models.Category getOneCategory(int id) {
		if (id > 0 && id <= listCategories.size()) {
			return listCategories.get(id - 1);
		}
		return null;
	}

}
