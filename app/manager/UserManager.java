package manager;

import models.User;
import DAO.GenericJpaDao;
import DAO.UserDao;

public final class UserManager extends GeneralManager<Integer, User> {

	public UserManager(GenericJpaDao<Integer, User> dao) {
		super(dao);
	}

	public User findByEmail(String currentEmail) {
		return ((UserDao) dao).findByEmail(currentEmail);
	}

	public User loginUser(String email, String password, boolean requiredAdmin) {
		return ((UserDao) dao).loginUser(email, password, requiredAdmin);
	}

}
