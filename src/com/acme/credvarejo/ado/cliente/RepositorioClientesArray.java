package com.acme.credvarejo.ado.cliente;

import java.util.Arrays;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.Registro;
import com.acme.credvarejo.classesGerais.RepositorioIdentificaveisArray;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.Cliente;

public class RepositorioClientesArray implements RepositorioClientes {

	private RepositorioIdentificaveisArray repositorio;

	public RepositorioClientesArray() {
		this.repositorio = new RepositorioIdentificaveisArray();
	}

	public void add(Cliente cliente) {
		repositorio.add(cliente);
	}

	public Cliente get(Identificador identificador)
		throws NoSuchRegistroException {

		return (Cliente)repositorio.get(identificador.getNumero());
	}

	public Cliente[] getAll() {
		Registro[] registros = repositorio.getAll();

		Cliente[] clientes = new Cliente[registros.length];

		return Arrays.copyOf(registros, registros.length, clientes.getClass());
	}

	public void remove(Identificador identificador)
		throws NoSuchRegistroException {

		repositorio.remove(identificador.getNumero());
	}
	
	public void update(Identificador identificador, Cliente cliente)
		throws NoSuchRegistroException {

		repositorio.update(identificador.getNumero(), cliente);
	}

}
