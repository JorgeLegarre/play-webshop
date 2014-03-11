package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.user.mainUsers;

public class User extends Controller {
	public static Result index() {
		return ok(mainUsers.render("This is the user index."));
	}
}
