package com.acme.credvarejo.ado.conta;

import com.acme.credvarejo.classesGerais.RepositorioIdentificaveis;
import com.acme.credvarejo.conta.MovimentoCrediario;

public class RepositorioMovimentoCrediario {

	private RepositorioIdentificaveis repositorio;

	public RepositorioMovimentoCrediario() {
		this.repositorio = new RepositorioIdentificaveis();
	}

	public void addMovimento(MovimentoCrediario movimento) {
		repositorio.add(movimento);
	}

	public MovimentoCrediario[] getMovimentos() {
		return (MovimentoCrediario[])repositorio.getAll();
	}

}
