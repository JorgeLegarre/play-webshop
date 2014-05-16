package manager;

import java.util.List;

import models.Product;
import DAO.GenericJpaDao;
import DAO.ProductDao;

public final class ProductManager extends GeneralManager<Integer, Product> {

	public ProductManager(GenericJpaDao<Integer, Product> dao) {
		super(dao);
	}

	public List<String> getImagePaths() {
		return ((ProductDao) dao).getImagePaths();
	}

}
