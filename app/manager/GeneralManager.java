package manager;

import java.util.List;

import DAO.GenericJpaDao;

public abstract class GeneralManager<K, E> {
	protected final GenericJpaDao<K, E> dao;

	public GeneralManager(GenericJpaDao<K, E> dao) {
		this.dao = dao;
	}

	public List<E> listAll() {
		return dao.listAll();
	}

	public E save(E entity) {
		return dao.save(entity);
	}

	public void remove(E entity) {
		dao.remove(entity);
	}

	public boolean removeById(K id) {
		return dao.removeById(id);
	}

	public E findById(K id) {
		return dao.findById(id);
	}
}
