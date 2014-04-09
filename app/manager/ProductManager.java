package manager;

import models.Product;
import DAO.GenericJpaDao;

public final class ProductManager extends GeneralManager<Integer, Product> {

	public ProductManager(GenericJpaDao<Integer, Product> dao) {
		super(dao);
	}

}
