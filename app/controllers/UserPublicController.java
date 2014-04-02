package controllers;

import java.util.List;

import models.User;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.publica.user.newUser;
import views.html.publica.user.upsert;
import forms.UserForm;

public class UserPublicController extends Controller {

	@Transactional
	@Security.Authenticated(PublicAutenticatedController.class)
	public static Result showCurrentUser() {
		String currentEmail = session().get("username");

		User user = getOneUserByEmail(currentEmail);

		if (user == null) {
			session().clear();
			return redirect(routes.SignInController.showPublicLogin());
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
		Form<UserForm> forms = Form.form(UserForm.class);
		UserForm autoForm = forms.bindFromRequest().get();

		User user = parseForm(autoForm);
		JPA.em().merge(user);

		System.out.println(user);

		if (session().get("username") == null) {
			session().put("username", user.getEmail());
			return redirect(routes.ApplicationController.publicMainMenu());
		} else {
			return redirect(routes.UserPublicController.showCurrentUser());
		}
	}

	private static User parseForm(UserForm userForm) {
		return new User(userForm.getId(), userForm.getEmail(),
				userForm.getPassword(), userForm.getFirstname(),
				userForm.getLastname(), userForm.getDob(),
				userForm.getTelephone(), userForm.getAddress1(),
				userForm.getAddress2(), userForm.getTown(),
				userForm.getPostcode(), false);
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
