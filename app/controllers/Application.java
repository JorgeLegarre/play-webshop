package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	public static Result init() {
		Category.fillDB();
		return (redirect("/categories"));
	}

	public static Result show404(String requestedUri) {
		return notFound("The url (" + requestedUri + ") is not know to us!!");
	}

}
