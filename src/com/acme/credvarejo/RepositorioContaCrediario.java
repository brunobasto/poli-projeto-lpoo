package com.acme.credvarejo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RepositorioContaCrediario {

	private ContaCrediario[] contas;

	public RepositorioContaCrediario() {
		this.contas = new ContaCrediario[] {};
	}

	public void addConta(ContaCrediario conta) {
		List<ContaCrediario> list = new ArrayList<ContaCrediario>(
			Arrays.asList(getContas()));
		
		if (contas.length < 150) {
			list.add(conta);
		}

		this.contas = list.toArray(new ContaCrediario[] {});
	}

	public ContaCrediario getConta(IdentificadorContaCrediario identificador) {
		return getConta(identificador.getNumero());
	}

	public ContaCrediario getConta(long identificador) {
		for (int i = 0; i < contas.length; i++) {
			IdentificadorContaCrediario curIdentificador =
				contas[i].getIdentificador();

			if (curIdentificador.getNumero() == identificador) {
				return contas[i];
			}
		}

		return null;
	}

	public ContaCrediario[] getContas() {
		return contas;
	}

	public void removeConta(ContaCrediario conta) {
		List<ContaCrediario> list = new ArrayList<ContaCrediario>(
			Arrays.asList(getContas()));

		if (getConta(conta.getIdentificador()) != null) {
			list.remove(conta);
		}

		this.contas = list.toArray(new ContaCrediario[] {});
	}

}
