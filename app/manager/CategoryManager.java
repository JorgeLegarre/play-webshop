package manager;

import models.Category;
import DAO.GenericJpaDao;

public final class CategoryManager extends GeneralManager<Integer, Category> {

	public CategoryManager(GenericJpaDao<Integer, Category> dao) {
		super(dao);
	}
}
