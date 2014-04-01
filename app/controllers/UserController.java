package controllers;

import java.util.List;
import java.util.Map;

import models.User;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.privat.user.listAll;
import views.html.privat.user.upsert;
import forms.UserForm;

public class UserController extends Controller {

	@Transactional
	public static Result showCurrentUser() {
		String currentEmail = session().get("username");

		User user = getOneUserByEmail(currentEmail);

		return showUser(user, false);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result listAllUsers() {
		List<User> users = getAllUsers();

		return ok(listAll.render(users));
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showAddUser() {
		User user = new User();

		flash().put("edit", "yes");
		return ok(upsert.render(user));
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showOneUser(int id) {
		User user = getOneUser(id);

		return showUser(user, false);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result showEditUser(int id) {
		User user = getOneUser(id);

		return showUser(user, true);
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result deleteUser() {
		Map<String, String[]> parameters = request().body().asFormUrlEncoded();
		String[] userId = parameters.get("userId");

		if (userId != null && userId.length > 0) {
			int id = Integer.parseInt(userId[0]);

			User user = getOneUser(id);

			if (user != null) {
				JPA.em().remove(user);
			}

			return redirect(routes.UserController.listAllUsers());
		}

		return badRequest("User unknow");
	}

	@Transactional
	@Security.Authenticated(PrivateAutenticatedController.class)
	public static Result saveUser() {
		Form<UserForm> forms = Form.form(UserForm.class);
		UserForm autoForm = forms.bindFromRequest().get();

		Map<String, String[]> parameters = request().body().asFormUrlEncoded();
		autoForm.setAdmin(parameters.get("isAdmin") != null);

		User user = parseForm(autoForm);
		JPA.em().merge(user);

		return redirect(routes.UserController.listAllUsers());
	}

	private static User parseForm(UserForm userForm) {
		return new User(userForm.getId(), userForm.getEmail(),
				userForm.getPassword(), userForm.getFirstname(),
				userForm.getLastname(), userForm.getDob(),
				userForm.getTelephone(), userForm.getAddress1(),
				userForm.getAddress2(), userForm.getTown(),
				userForm.getPostcode(), userForm.isAdmin());
	}

	private static Result showUser(User user, boolean edit) {
		if (user == null) {
			return notFound("User not found");
		}

		if (edit) {
			flash().put("edit", "yes");
		}

		return ok(upsert.render(user));
	}

	private static User getOneUserByEmail(String email) {
		List<User> matchingUsers = JPA
				.em()
				.createQuery("SELECT u from User u where email = :email",
						User.class).setParameter("email", email)
				.getResultList();

		if (matchingUsers.size() == 1) {
			return matchingUsers.get(0);
		}

		return null;
	}

	private static User getOneUser(int id) {
		return JPA.em().find(User.class, id);
	}

	private static List<User> getAllUsers() {
		return JPA.em().createQuery("SELECT u from User u", User.class)
				.getResultList();
	}
}
