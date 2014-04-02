package controllers;

import java.util.List;

import models.User;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.publica.publicLogin;
import views.html.privat.privateLogin;
import forms.SignInForm;

public class SignInController extends Controller {

	// PRIVATE
	public static Result showPrivateLogin() {
		if (session().get("username") != null) {
			return redirect(routes.ApplicationController.privateMainMenu());
		}
		return ok(privateLogin.render());
	}

	@Transactional
	public static Result privateLogin() {
		session().clear();

		Form<SignInForm> signInForm = Form.form(SignInForm.class);
		SignInForm signIn = signInForm.bindFromRequest().get();

		User user = getPrivateUser(signIn.email, signIn.password);

		if (user != null) {
			session().put("username", user.getEmail());
			return redirect(routes.ApplicationController.privateMainMenu());
		}

		flash().put("invalid", "yes");
		return ok(privateLogin.render());
	}

	public static Result privateLogOut() {
		session().clear();
		return redirect(routes.SignInController.showPrivateLogin());
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

	// PUBLIC
	public static Result showPublicLogin() {
		if (session().get("username") != null) {
			return redirect(routes.ApplicationController.publicMainMenu());
		}
		return ok(publicLogin.render());
	}

	@Transactional
	public static Result publicLogin() {
		session().clear();

		Form<SignInForm> signInForm = Form.form(SignInForm.class);
		SignInForm signIn = signInForm.bindFromRequest().get();

		User user = getPublicUser(signIn.email, signIn.password);

		if (user != null) {
			session().put("username", user.getEmail());
			return redirect(routes.ApplicationController.publicMainMenu());
		}

		flash().put("invalid", "yes");
		return ok(publicLogin.render());
	}

	public static Result publicLogOut() {
		session().clear();
		return redirect(routes.SignInController.showPublicLogin());
	}

	private static User getPublicUser(String email, String password) {
		List<User> result = JPA
				.em()
				.createQuery(
						"SELECT u from User u where email=:email and password=:password",
						User.class).setParameter("email", email)
				.setParameter("password", password).getResultList();

		return result.size() > 0 ? result.get(0) : null;
	}
}
