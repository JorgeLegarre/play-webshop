package DAO;

import java.util.List;

import models.User;
import play.db.jpa.JPA;

public final class UserDao extends GenericJpaDao<Integer, User> {
	public User findByEmail(String email) {
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

	public User loginUser(String email, String password, boolean requiredAdmin) {
		List<User> result = JPA
				.em()
				.createQuery(
						"SELECT u from User u where email=:email and password=:password"
								+ ((requiredAdmin) ? " and isAdmin = true" : ""),
						User.class).setParameter("email", email)
				.setParameter("password", password).getResultList();

		return result.size() > 0 ? result.get(0) : null;
	}
}
