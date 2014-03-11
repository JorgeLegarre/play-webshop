package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.product.mainProducts;

public final class Product extends Controller {
	public static Result index() {
		return ok(mainProducts.render("This is the product index."));
	}
}
