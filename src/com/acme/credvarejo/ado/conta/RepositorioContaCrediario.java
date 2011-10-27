package com.acme.credvarejo.ado.conta;

import com.acme.credvarejo.classesGerais.RepositorioIdentificaveis;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.IdentificadorContaCrediario;

public class RepositorioContaCrediario {

	private RepositorioIdentificaveis repositorio;

	public RepositorioContaCrediario() {
		this.repositorio = new RepositorioIdentificaveis();
	}

	public void addConta(ContaCrediario conta) {
		repositorio.add(conta);
	}

	public ContaCrediario getConta(IdentificadorContaCrediario identificador) {
		return (ContaCrediario)repositorio.get(
			String.valueOf(identificador.getNumero()));
	}

	public ContaCrediario getConta(long identificador) {
		return (ContaCrediario)repositorio.get(String.valueOf(identificador));
	}

	public ContaCrediario[] getContas() {
		return (ContaCrediario[])repositorio.getAll();
	}

	public void removeConta(IdentificadorContaCrediario identificador) {
		repositorio.remove(String.valueOf(identificador.getNumero()));
	}

}
