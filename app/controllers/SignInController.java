package controllers;

import manager.UserManager;
import models.User;
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

		SignInForm signIn = getLoginForm();

		User user = loginLikeAdmin(signIn.email, signIn.password);

		if (isLogged(user)) {
			return redirect(routes.ApplicationController.privateMainMenu());
		} else {
			flash().put("invalid", "yes");
			return ok(privateLogin.render());
		}
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

		SignInForm signIn = getLoginForm();

		User user = loginLikeUser(signIn.email, signIn.password);

		if (isLogged(user)) {
			return redirect(routes.ApplicationController.publicMainMenu());
		} else {
			flash().put("invalid", "yes");
			return ok(publicLogin.render());
		}

	}

	public static Result publicLogOut() {
		session().clear();
		return redirect(routes.SignInController.showPublicLogin());
	}

	// REST
	@Transactional
	public static Result logInRest() {
		String email = getParamString("email");
		String password = getParamString("password");

		User user = loginLikeAdmin(email, password);

		if (isNotLogged(user)) {
			user = loginLikeUser(email, password);
		}

		if (isNotLogged(user)) {
			session().clear();
			return ok();
		}

		return ok("Log in by ");
	}

	public static Result logOutRest() {
		if (session().get("username") != null) {
			session().clear();
			return ok("Log Out done correctly!!!!");
		} else {
			return ok("You never was logged in");
		}

	}

	// COMMON
	private static User loginLikeAdmin(String email, String password) {
		return login(email, password, true);
	}

	private static User loginLikeUser(String email, String password) {
		return login(email, password, false);
	}

	private static User login(String email, String password, boolean likeAdmin) {
		User user = userManager.loginUser(email, password, likeAdmin);

		if (isLogged(user)) {
			setItemsInSession(user, likeAdmin);
		}
		return user;
	}

	private static void setItemsInSession(User user, boolean isAdmin) {
		session().put("email", user.getEmail());

		session().put("username", user.getFirstname());

		session().put("isAdmin", String.valueOf(isAdmin));

		int nItemsShoppingCart = (user.getShoppingCart() != null) ? user
				.getShoppingCart().getTotalItems() : 0;

		session().put("items", nItemsShoppingCart + "");

	}

	private static boolean isLogged(User user) {
		return user != null;
	}

	private static boolean isNotLogged(User user) {
		return !isLogged(user);
	}

	private static SignInForm getLoginForm() {
		Form<SignInForm> signInForm = Form.form(SignInForm.class);
		return signInForm.bindFromRequest().get();
	}

}
