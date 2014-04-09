package manager;

import models.ShoppingCart;
import DAO.GenericJpaDao;

public final class ShoppingCartManager extends
		GeneralManager<Integer, ShoppingCart> {

	public ShoppingCartManager(GenericJpaDao<Integer, ShoppingCart> dao) {
		super(dao);
	}

}
