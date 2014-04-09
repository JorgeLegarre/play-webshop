package DAO;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import play.db.jpa.JPA;

public abstract class GenericJpaDao<K, E> {
	protected Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public GenericJpaDao() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();

		this.entityClass = ((Class<E>) genericSuperclass
				.getActualTypeArguments()[1]);
	}

	public List<E> listAll() {
		return JPA
				.em()
				.createQuery("SELECT c from " + entityClass.getName() + " c",
						entityClass).getResultList();
	}

	public E save(E entity) {
		return JPA.em().merge(entity);
	}

	public void remove(E entity) {
		JPA.em().remove(entity);
	}

	public boolean removeById(K id) {
		E e = findById(id);

		if (e != null) {
			remove(e);
			return true;
		}

		return false;
	}

	public E findById(K id) {
		return JPA.em().find(entityClass, id);
	}
}