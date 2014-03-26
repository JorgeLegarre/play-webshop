package controllers;

import java.util.List;

import models.User;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.privat.user.listAll;
import views.html.privat.user.upsert;

public class UserController extends Controller {

	@Transactional
	public static Result showCurrentUser() {
		String currentEmail = session().get("username");
		return showUser(currentEmail);

	}

	private static Result showUser(String email) {
		User user = getOneUserByEmail(email);

		if (user == null) {
			return notFound("User not found");
		}

		return ok(upsert.render(user));
	}

	@Transactional
	public static Result showOneUser(int id) {
		User user = getOneUser(id);

		if (user == null) {
			return notFound("User not found");
		}

		return ok(upsert.render(user));
	}

	@Transactional
	public static Result listAllUsers() {
		List<User> users = getAllUsers();
		return ok(listAll.render(users));
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
