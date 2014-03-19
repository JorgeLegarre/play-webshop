package controllers;

import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.mainMenu;
import views.html.privateLogin;
import forms.SignIn;

public class ApplicationController extends Controller {

	@Transactional
	public static Result init() {
		CategoryController.fillDB();
		ProductController.fillDB();
		UserController.fillDB();

		return redirect("/private");
	}

	public static Result show404(String requestedUri) {
		return notFound("The url (" + requestedUri + ") is not know to us!! ");
	}

	public static Result showLogin() {
		return ok(privateLogin.render(false));
	}

	public static Result login() {

		// DynamicForm requestData = Form.form().bindFromRequest();
		// String email = requestData.get("email");
		// String pwd = requestData.get("password");

		Form<SignIn> signInForm = Form.form(SignIn.class);
		SignIn signIn = signInForm.bindFromRequest().get();

		String email = signIn.email;
		String pwd = signIn.password;

		if (validate(email, pwd)) {
			session("user", email);
			return redirect("/private/mainMenu");
		}

		return ok(privateLogin.render(true));
	}

	public static Result mainMenu() {
		return ok(mainMenu.render());
	}

	private static boolean validate(String email, String password) {
		return "enkidugan@gmail.com".equals(email)
				&& "password".equals(password);
	}
}
