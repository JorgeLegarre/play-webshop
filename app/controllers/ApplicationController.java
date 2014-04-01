package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.privat.privateMainMenu;

public class ApplicationController extends Controller {

	public static Result show404(String requestedUri) {
		return notFound("The url (" + requestedUri + ") is not know to us!! ");
	}

	@Security.Authenticated
	public static Result mainMenu() {

		return ok(privateMainMenu.render());
	}
}
