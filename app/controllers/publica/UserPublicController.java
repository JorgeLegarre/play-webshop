package controllers.publica;

import manager.UserManager;
import models.User;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.publica.user.newUser;
import views.html.publica.user.upsert;
import DAO.UserDao;
import controllers.routes;
import forms.UserForm;

public class UserPublicController extends Controller {

	private final static UserManager userManager = new UserManager(
			new UserDao());

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result showCurrentUser() {
		User user = getCurrentUser();

		if (user == null) {
			session().clear();
			return redirect(controllers.routes.SignInController
					.showPublicLogin());
		}

		return ok(upsert.render(user));
	}

	@Transactional
	public static Result showAddUser() {
		User user = new User();

		return ok(newUser.render(user));
	}

	@Transactional
	public static Result saveUser() {
		User user = parseForm();

		user = userManager.save(user);
		session().put("username", user.getEmail());

		return redirect(routes.ApplicationController.publicMainMenu());
	}

	private static User parseForm() {
		Form<UserForm> forms = Form.form(UserForm.class);
		UserForm autoForm = forms.bindFromRequest().get();

		return parseForm(autoForm);
	}

	private static User parseForm(UserForm userForm) {
		return new User(userForm.getId(), userForm.getEmail(),
				userForm.getPassword(), userForm.getFirstname(),
				userForm.getLastname(), userForm.getDob(),
				userForm.getTelephone(), userForm.getAddress1(),
				userForm.getAddress2(), userForm.getTown(),
				userForm.getPostcode(), false);
	}

	private static User getCurrentUser() {
		String email = session().get("email");
		return userManager.findByEmail(email);
	}
}
