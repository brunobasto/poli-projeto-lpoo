package com.acme.credvarejo.ado.conta;

import java.io.IOException;
import java.util.ArrayList;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.RepositorioIdentificaveisFile;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.MovimentoCrediario;

public class RepositorioMovimentoCrediarioFile
	implements RepositorioMovimentoCrediario {

	private RepositorioIdentificaveisFile repositorio;

	public RepositorioMovimentoCrediarioFile() {
		this.repositorio = new RepositorioIdentificaveisFile();
	}

	public void add(MovimentoCrediario movimentoCrediario) throws IOException {
		repositorio.add(movimentoCrediario);
	}

	public MovimentoCrediario[] getAll()
		throws IOException, NoSuchRegistroException {

		return repositorio.getAllMovimentoCrediario();
	}

	public MovimentoCrediario[] search(Identificador identificador)
		throws IOException, NoSuchRegistroException {

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

}
