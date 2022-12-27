package br.com.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import br.com.entidades.Estados;
import br.com.entidades.Pessoa;

@Named
public class IDaoPessoaImpl implements IDaoPessoa, Serializable {

	private static final long serialVersionUID = 3790088723756809431L;

	@Inject
	private EntityManager entityManager;

	@Override
	public Pessoa consultarUsuario(String login, String senha) {

		Pessoa pessoa = null;

		// EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		pessoa = (Pessoa) entityManager
				.createQuery("SELECT p FROM Pessoa p WHERE p.login = '" + login + "' and p.senha = '" + senha + "'")
				.getSingleResult();
		entityTransaction.commit();
		// entityManager.close();
		return pessoa;
	}

	@Override
	public List<SelectItem> listaEstados() {

		List<SelectItem> selectItems = new ArrayList<SelectItem>();

		// EntityManager entityManager = JPAUtil.getEntityManager();
		//EntityTransaction entityTransaction = entityManager.getTransaction();
		//entityTransaction.begin();

		TypedQuery<Estados> estadosQuery = entityManager.createQuery("from Estados", Estados.class);
		List<Estados> estados = estadosQuery.getResultList();

		for (Estados estado : estados) {
			selectItems.add(new SelectItem(estado, estado.getNome()));
		}

		return selectItems;
	}

}
