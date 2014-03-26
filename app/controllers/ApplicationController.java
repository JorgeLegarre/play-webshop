package controllers;

import java.util.List;

import models.User;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.mainMenu;
import views.html.privat.privateLogin;
import forms.SignInForm;

public class ApplicationController extends Controller {

	public static Result show404(String requestedUri) {
		return notFound("The url (" + requestedUri + ") is not know to us!! ");
	}

	public static Result showLogin() {
		if (session().get("username") != null) {
			return redirect(routes.ApplicationController.mainMenu());
		}
		return ok(privateLogin.render(false));
	}

	@Transactional
	public static Result login() {
		// this work in GET, in POST I have to test
		// Map<String,String[]> from = request().body().asFormUrlEncoded()
		// String email = form.get("email)[0];

		// DynamicForm requestData = Form.form().bindFromRequest();
		// String email = requestData.get("email");
		// String pwd = requestData.get("password");

		Form<SignInForm> signInForm = Form.form(SignInForm.class);
		SignInForm signIn = signInForm.bindFromRequest().get();

		User user = getUser(signIn.email, signIn.password);

		if (user != null) {
			session().put("username", user.getEmail());
			session().put("userId", String.valueOf(user.getId()));
			return redirect(routes.ApplicationController.mainMenu());
		}

		return ok(privateLogin.render(true));
	}

	@Security.Authenticated
	public static Result mainMenu() {
		return ok(mainMenu.render());
	}

	public static Result logOut() {
		session().clear();
		return redirect(routes.ApplicationController.showLogin());
	}

	private static User getUser(String email, String password) {
		List<User> result = JPA
				.em()
				.createQuery(
						"SELECT u from User u where email=:email and password=:password",
						User.class).setParameter("email", email)
				.setParameter("password", password).getResultList();

		return result.size() > 0 ? result.get(0) : null;
	}
}
