package controllers.privat;

import java.util.List;

import controllers.GeneralController;
import manager.UserManager;
import models.User;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import views.html.privat.user.listAll;
import views.html.privat.user.upsert;
import DAO.UserDao;
import forms.UserForm;

public class UserController extends GeneralController {

	private final static UserManager userManager = new UserManager(
			new UserDao());

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showCurrentUser() {
		String currentEmail = session().get("username");

		User user = userManager.findByEmail(currentEmail);

		if (user == null) {
			session().clear();
			return redirect(controllers.routes.SignInController
					.showPrivateLogin());
		}

		return showUser(user);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result listAllUsers() {
		List<User> users = userManager.listAll();

		return ok(listAll.render(users));
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showAddUser() {
		User user = new User();

		enableEditionMode();

		return showUser(user);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showOneUser(int id) {
		User user = userManager.findById(id);

		return showUser(user);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showEditUser(int id) {
		User user = userManager.findById(id);

		enableEditionMode();

		return showUser(user);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result deleteUser() {
		int id = getParamInt("userId");

		userManager.removeById(id);

		return redirect(routes.UserController.listAllUsers());

	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result saveUser() {
		User user = parseForm();

		user = userManager.save(user);

		return redirect(routes.UserController.listAllUsers());
	}

	private static Result showUser(User user) {
		if (user == null) {
			return notFound("User not found");
		}

		return ok(upsert.render(user));
	}

	private static User parseForm() {
		Form<UserForm> forms = Form.form(UserForm.class);
		UserForm autoForm = forms.bindFromRequest().get();
		autoForm.setAdmin(getParamBoolean("isAdmin"));

		return parseForm(autoForm);
	}

	private static User parseForm(UserForm userForm) {
		return new User(userForm.getId(), userForm.getEmail(),
				userForm.getPassword(), userForm.getFirstname(),
				userForm.getLastname(), userForm.getDob(),
				userForm.getTelephone(), userForm.getAddress1(),
				userForm.getAddress2(), userForm.getTown(),
				userForm.getPostcode(), userForm.isAdmin());
	}
}
