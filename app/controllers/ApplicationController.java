package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.privat.privateMainMenu;
import views.html.publica.publicMainMenu;

public class ApplicationController extends Controller {

	public static Result show404(String requestedUri) {
		return notFound("The url (" + requestedUri + ") is not know to us!! ");
	}

	@Security.Authenticated
	public static Result privateMainMenu() {
		return ok(privateMainMenu.render());
	}

	@Security.Authenticated
	public static Result publicMainMenu() {
		return ok(publicMainMenu.render());
	}
}
