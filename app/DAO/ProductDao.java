package DAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Product;
import play.db.jpa.JPA;

public final class ProductDao extends GenericJpaDao<Integer, Product> {

	public List<String> getImagePaths() {
		@SuppressWarnings("unchecked")
		List<String> listPictures = JPA
				.em()
				.createQuery(
						"SELECT c.picture from " + entityClass.getName() + " c")
				.getResultList();

		// JPA dont support distinct queries, so I make it in memory with the
		// help of a auxilar Set
		Set<String> setPictures = new HashSet<>(listPictures);

		return new ArrayList<String>(setPictures);
	}

}
