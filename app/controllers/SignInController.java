package controllers;

import java.util.List;

import models.User;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.privat.privateLogin;
import forms.SignInForm;

public class SignInController extends Controller {
	public static Result showLogin() {
		if (session().get("username") != null) {
			return redirect(routes.ApplicationController.mainMenu());
		}
		return ok(privateLogin.render());
	}

	@Transactional
	public static Result login() {

		Form<SignInForm> signInForm = Form.form(SignInForm.class);
		SignInForm signIn = signInForm.bindFromRequest().get();

		User user = getPrivateUser(signIn.email, signIn.password);

		if (user != null) {
			session().put("username", user.getEmail());
			return redirect(routes.ApplicationController.mainMenu());
		}

		flash().put("invalid", "yes");
		return ok(privateLogin.render());
	}

	public static Result logOut() {
		session().clear();
		return redirect(routes.SignInController.showLogin());
	}

	private static User getPrivateUser(String email, String password) {
		List<User> result = JPA
				.em()
				.createQuery(
						"SELECT u from User u where email=:email and password=:password and isAdmin = true",
						User.class).setParameter("email", email)
				.setParameter("password", password).getResultList();

		return result.size() > 0 ? result.get(0) : null;
	}
}
