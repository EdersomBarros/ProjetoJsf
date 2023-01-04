package br.com.projetojsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.dao.DaoGeneric;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoPessoa;

@ViewScoped
@Named(value = "relUsuario")
public class RelUsuario implements Serializable {

	private static final long serialVersionUID = -5842433150738481391L;

	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	@Inject
	private IDaoPessoa iDaoPessoa;
	@Inject
	private DaoGeneric<Pessoa> daoGeneric;

	private Date dataInicio;

	private Date dataFim;

	private String nome;
	
	
	
	

	public void relPessoa() {
		if (dataInicio == null && dataFim == null && nome == null) {

			pessoas = daoGeneric.getListEntity(Pessoa.class);
		}else {
			pessoas= iDaoPessoa.relatorioPessoa(nome, dataInicio, dataFim);
		}

	}

	

	public Date getDataInicio() {
		return dataInicio;
	}



	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}



	public Date getDataFim() {
		return dataFim;
	}



	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}



	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

}
