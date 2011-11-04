package com.acme.credvarejo.ado.conta;

import java.io.IOException;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.RepositorioIdentificaveisFile;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.conta.ContaCrediario;

public class RepositorioContaCrediarioFile
	implements RepositorioContaCrediario {

	private RepositorioIdentificaveisFile repositorio;

	public RepositorioContaCrediarioFile() {
		this.repositorio = new RepositorioIdentificaveisFile();
	}

	public void add(ContaCrediario contaCrediario) throws IOException {
		repositorio.add(contaCrediario);
	}

	public ContaCrediario get(Identificador identificador)
		throws NoSuchRegistroException, IOException {

		return repositorio.getContaCrediario(identificador.getNumero());
	}

	public ContaCrediario[] getAll()
		throws IOException, NoSuchRegistroException {

		return repositorio.getAllContaCrediario();
	}

	public void remove(Identificador identificador)
		throws NoSuchRegistroException, IOException {

		repositorio.removeContaCrediario(identificador.getNumero());
	}
	
	public void update(
			Identificador identificador, ContaCrediario contaCrediario)
		throws NoSuchRegistroException, IOException {

		repositorio.updateContaCrediario(
			identificador.getNumero(), contaCrediario);
	}

}
