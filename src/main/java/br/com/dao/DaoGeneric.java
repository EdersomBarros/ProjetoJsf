package br.com.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.jpautil.JPAUtil;

@Named
public class DaoGeneric<E> implements Serializable {

	private static final long serialVersionUID = -7396945223182945150L;
	@Inject
	private EntityManager entityManager;
	@Inject
	private JPAUtil jpaUtil;

	public void salvar(E entidade) {

		// EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.persist(entidade);
		entityTransaction.commit();
		// entityManager.close();
	}

	public E merge(E entidade) {

		// EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		E retorno = entityManager.merge(entidade);
		entityTransaction.commit();
		// entityManager.close();

		return retorno;
	}

	public void delete(E entidade) {

		// EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.remove(entidade);
		entityTransaction.commit();
		// entityManager.close();
	}

	public void deletePorId(E entidade) {

		// EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		Object id = jpaUtil.getPrimareKey(entidade);
		entityManager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id = " + id)
				.executeUpdate();
		entityTransaction.commit();
		// entityManager.close();
	}

	public List<E> getListEntity(Class<E> entidade) {

		// EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		List<E> retorno = entityManager.createQuery(" from " + entidade.getName()).getResultList();

		entityTransaction.commit();
		// entityManager.close();

		return retorno;

	}

	public E consultar(Class<E> entidade, String codigo) {
		// EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		E objeto = (E) entityManager.find(entidade, Long.parseLong(codigo));
		entityTransaction.commit();
		return objeto;

	}
}
