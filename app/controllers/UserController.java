package controllers;

import java.util.List;

import models.User;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.user.listAll;
import views.html.user.upsert;

public class UserController extends Controller {
	@Transactional
	public static void fillDB() {
		JPA.em().persist(
				new User("enkidugan@gmail.com", "password", "Jorge", "Legarre",
						"1981-12-11", "0735560243", "Dalbobranten 12", "",
						"Sköndal", "12868", true));
		JPA.em().persist(
				new User("mail2@gmail.com", "password2", "Björn", "Göransson",
						"1980-06-06", "0735555555", "Ingen aning", "",
						"Stockholm", "12331", false));
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

	private static User getOneUser(int id) {
		List<User> users = JPA
				.em()
				.createQuery("SELECT u from User u WHERE u.id = :id",
						User.class).setParameter("id", id).getResultList();

		return (users.size() > 0) ? users.get(0) : null;
	}

	private static List<User> getAllUsers() {
		return JPA.em().createQuery("SELECT u from User u", User.class)
				.getResultList();
	}
}