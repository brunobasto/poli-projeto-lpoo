package com.acme.credvarejo.conta;

import com.acme.credvarejo.ado.conta.RepositorioMovimentoCrediario;
import com.acme.credvarejo.conta.exceptions.ContaCrediarioException;

public class ControladorMovimentoCrediario {

	private RepositorioMovimentoCrediario repositorioMovimentoCrediario;

	public ControladorMovimentoCrediario(
			RepositorioMovimentoCrediario repositorioMovimentoCrediario) {

		this.repositorioMovimentoCrediario = repositorioMovimentoCrediario;
	}

	public MovimentoCrediario[] buscar(
		IdentificadorContaCrediario identificador) {

		return repositorioMovimentoCrediario.search(identificador);
	}

	public void inserir(MovimentoCrediario movimentoCrediario)
		throws ContaCrediarioException {

		if (movimentoCrediario != null) {
			movimentoCrediario.validar();

			repositorioMovimentoCrediario.add(movimentoCrediario);
		}
		else {
			System.err.println("O movimento crediário passado não existe.");
		}
	}

}
