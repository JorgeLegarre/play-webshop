package manager;

import models.OrderStatus;
import DAO.GenericJpaDao;

public final class OrderStatusManager extends
		GeneralManager<Integer, OrderStatus> {

	public OrderStatusManager(GenericJpaDao<Integer, OrderStatus> dao) {
		super(dao);
	}

}
