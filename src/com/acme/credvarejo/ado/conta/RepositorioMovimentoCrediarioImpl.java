package com.acme.credvarejo.ado.conta;

import java.util.ArrayList;
import java.util.Arrays;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.Registro;
import com.acme.credvarejo.classesGerais.RepositorioIdentificaveis;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.MovimentoCrediario;

public class RepositorioMovimentoCrediarioImpl
	implements RepositorioMovimentoCrediario {

	private RepositorioIdentificaveis repositorio;

	public RepositorioMovimentoCrediarioImpl() {
		this.repositorio = new RepositorioIdentificaveis();
	}

	public void add(MovimentoCrediario movimentoCrediario) {
		repositorio.add(movimentoCrediario);
	}

	public MovimentoCrediario[] search(Identificador identificador) {
		ArrayList<MovimentoCrediario> movimentos =
			new ArrayList<MovimentoCrediario>();

		String numero = identificador.getNumero();

		for (MovimentoCrediario movimentoCrediario : getAll()) {
			ContaCrediario conta = movimentoCrediario.getConta();

			if (conta.getChave().equals(numero)) {
				movimentos.add(movimentoCrediario);
			}
		}

		return movimentos.toArray(new MovimentoCrediario[] {});
	}

	public MovimentoCrediario[] getAll() {
		Registro[] registros = repositorio.getAll();

		MovimentoCrediario[] clientes = new MovimentoCrediario[registros.length];

		return Arrays.copyOf(registros, registros.length, clientes.getClass());
	}

}
