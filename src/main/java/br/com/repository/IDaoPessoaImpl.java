package br.com.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		try {
			pessoa = (Pessoa) entityManager
					.createQuery("SELECT p FROM Pessoa p WHERE p.login = '" + login + "' and p.senha = '" + senha + "'")
					.getSingleResult();
		} catch (javax.persistence.NoResultException e) {

		}

		entityTransaction.commit();

		// entityManager.close();
		return pessoa;
	}

	@Override
	public List<SelectItem> listaEstados() {

		List<SelectItem> selectItems = new ArrayList<SelectItem>();

		// EntityManager entityManager = JPAUtil.getEntityManager();
		// EntityTransaction entityTransaction = entityManager.getTransaction();
		// entityTransaction.begin();

		TypedQuery<Estados> estadosQuery = entityManager.createQuery("from Estados", Estados.class);
		List<Estados> estados = estadosQuery.getResultList();

		for (Estados estado : estados) {
			selectItems.add(new SelectItem(estado, estado.getNome()));
		}

		return selectItems;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> relatorioPessoa(String nome, Date dataInicio, Date dataFim) {
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select l from Pessoa l ");

		if (dataInicio == null && dataFim == null && nome != null && !nome.isEmpty()) {

			sql.append(" where upper(l.nome) like '%").append(nome.trim().toUpperCase()).append("%'");

		} else if (nome == null || (nome != null && nome.isEmpty()) && dataInicio != null && dataFim == null) {

			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataInicio);

			sql.append(" where l.dataNascimento >= '").append(dataIniString).append("'");

		} else if (nome == null || (nome != null && nome.isEmpty()) && dataInicio == null && dataFim != null) {

			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);

			sql.append(" where l.dataNascimento <= '").append(dataFimString).append("'");

		} else if (nome == null || (nome != null && nome.isEmpty()) && dataInicio != null && dataFim != null) {

			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataInicio);
			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);

			sql.append(" where l.dataNascimento >= '").append(dataIniString).append("'");
			sql.append(" and l.dataNascimento <= '").append(dataFimString).append("'");

		} else if (nome != null && nome.isEmpty() && dataInicio != null && dataFim != null) {

			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataInicio);
			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);

			sql.append(" where l.dataNascimento >= '").append(dataIniString).append("'");
			sql.append(" and l.dataNascimento <= '").append(dataFimString).append("'");
			sql.append(" and upper(l.nome) like '%").append(nome.trim().toUpperCase()).append("%'");

		}
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		pessoas = entityManager.createQuery(sql.toString()).getResultList();
		transaction.commit();
		
		return pessoas;
	}

}
