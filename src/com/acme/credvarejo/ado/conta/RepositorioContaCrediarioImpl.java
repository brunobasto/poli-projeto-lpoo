package com.acme.credvarejo.ado.conta;

import java.util.Arrays;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.Registro;
import com.acme.credvarejo.classesGerais.RepositorioIdentificaveis;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.conta.ContaCrediario;

public class RepositorioContaCrediarioImpl
	implements RepositorioContaCrediario {

	private RepositorioIdentificaveis repositorio;

	public RepositorioContaCrediarioImpl() {
		this.repositorio = new RepositorioIdentificaveis();
	}

	public void add(ContaCrediario contaCrediario) {
		repositorio.add(contaCrediario);
	}

	public ContaCrediario get(Identificador identificador)
		throws NoSuchRegistroException {

		return (ContaCrediario)repositorio.get(identificador.getNumero());
	}

	public ContaCrediario[] getAll() {
		Registro[] registros = repositorio.getAll();

		ContaCrediario[] clientes = new ContaCrediario[registros.length];

		return Arrays.copyOf(registros, registros.length, clientes.getClass());
	}

	public void remove(Identificador identificador)
		throws NoSuchRegistroException {

		repositorio.remove(identificador.getNumero());
	}
	
	public void update(
			Identificador identificador, ContaCrediario contaCrediario)
		throws NoSuchRegistroException {

		repositorio.update(identificador.getNumero(), contaCrediario);
	}

}
