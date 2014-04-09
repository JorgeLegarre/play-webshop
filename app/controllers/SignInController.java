package controllers;

import manager.UserManager;
import models.User;
import play.api.mvc.Call;
import play.api.templates.Html;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;
import views.html.privat.privateLogin;
import views.html.publica.publicLogin;
import DAO.UserDao;
import forms.SignInForm;

public class SignInController extends GeneralController {
	private final static UserManager userManager = new UserManager(
			new UserDao());

	// PRIVATE
	public static Result showPrivateLogin() {
		if (session().get("username") != null) {
			return redirect(routes.ApplicationController.privateMainMenu());
		}
		return ok(privateLogin.render());
	}

	@Transactional
	public static Result privateLogin() {
		return login(true, routes.ApplicationController.privateMainMenu(),
				privateLogin.render());
	}

	public static Result privateLogOut() {
		session().clear();
		return redirect(routes.SignInController.showPrivateLogin());
	}

	// PUBLIC
	public static Result showPublicLogin() {
		if (session().get("username") != null) {
			return redirect(routes.ApplicationController.publicMainMenu());
		}
		return ok(publicLogin.render());
	}

	@Transactional
	public static Result publicLogin() {
		return login(false, routes.ApplicationController.publicMainMenu(),
				publicLogin.render());
	}

	public static Result publicLogOut() {
		session().clear();
		return redirect(routes.SignInController.showPublicLogin());
	}

	// COMMON
	private static Result login(boolean adminRequired, Call destinationOk,
			Html destinationNOK) {
		session().clear();

		Form<SignInForm> signInForm = Form.form(SignInForm.class);
		SignInForm signIn = signInForm.bindFromRequest().get();

		User user = userManager.loginUser(signIn.email, signIn.password,
				adminRequired);

		if (user != null) {
			session().put("username", user.getEmail());

			int nItems = (user.getShoppingCart() != null) ? user
					.getShoppingCart().getTotalItems() : 0;
			session().put("items", nItems + "");
			return redirect(destinationOk);
		}

		flash().put("invalid", "yes");

		return ok(destinationNOK);
	}

}
