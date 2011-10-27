package com.acme.credvarejo.ado.conta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.acme.credvarejo.conta.MovimentoCrediario;

public class RepositorioMovimentoCrediario {

	private MovimentoCrediario[] movimentos;

	public RepositorioMovimentoCrediario() {
		this.movimentos = new MovimentoCrediario[] {};
	}

	public void addMovimento(MovimentoCrediario conta) {
		List<MovimentoCrediario> list = new ArrayList<MovimentoCrediario>(
			Arrays.asList(getMovimentos()));

		if (movimentos.length < 150) {
			list.add(conta);
		}

		this.movimentos = list.toArray(new MovimentoCrediario[] {});
	}

	public MovimentoCrediario[] getMovimentos() {
		return movimentos;
	}

}
