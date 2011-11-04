package com.acme.credvarejo.ado.conta;

import java.util.Arrays;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.Registro;
import com.acme.credvarejo.classesGerais.RepositorioIdentificaveisArray;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.conta.ContaCrediario;

public class RepositorioContaCrediarioArray
	implements RepositorioContaCrediario {

	private RepositorioIdentificaveisArray repositorio;

	public RepositorioContaCrediarioArray() {
		this.repositorio = new RepositorioIdentificaveisArray();
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
