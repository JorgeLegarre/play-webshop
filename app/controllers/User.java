package controllers;

import java.util.Arrays;
import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.user.listAll;
import views.html.user.upsert;

public class User extends Controller {
	private static final List<models.User> listUsers = Arrays
			.asList(new models.User[] {
					new models.User.Builder("enkidugan@gmail.com", "password",
							"Jorge", "Legarre Peris", "Dalbobranten 13",
							"Sköndal", "12868").id(1).build(),
					new models.User.Builder("mail2@gmail.com", "password2",
							"Björn", "Göransson", "Ingen aning", "Stockholm",
							"12331").id(2).build() });

	public static Result showOneUser(int id) {
		models.User user = getOneUser(id);

		if (user == null) {
			return notFound("User not found");
		}

		return ok(upsert.render(user));
	}

	public static Result listAllUsers() {
		return ok(listAll.render(listUsers));
	}

	private static models.User getOneUser(int id) {
		if (id > 0 && id <= listUsers.size()) {
			return listUsers.get(id - 1);
		}
		return null;
	}
}
