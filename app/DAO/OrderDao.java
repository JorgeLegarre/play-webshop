package DAO;

import models.Order;
import play.db.jpa.JPA;

public final class OrderDao extends GenericJpaDao<Integer, Order> {
	public int getNOrders() {
		return ((Number) JPA
				.em()
				.createQuery(
						"SELECT count(c) from " + entityClass.getName() + " c")
				.getSingleResult()).intValue();
	}
}
