package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.category.mainCategories;

public class Category extends Controller {
	public static Result index() {
		return ok(mainCategories.render("This is the product index."));
	}

}
