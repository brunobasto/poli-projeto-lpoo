package com.acme.credvarejo.ado.cliente;

import java.util.Arrays;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.Registro;
import com.acme.credvarejo.classesGerais.RepositorioIdentificaveis;
import com.acme.credvarejo.cliente.Cliente;

public class RepositorioClientesImpl implements RepositorioClientes {

	private RepositorioIdentificaveis repositorio;

	public RepositorioClientesImpl() {
		this.repositorio = new RepositorioIdentificaveis();
	}

	public void add(Cliente cliente) {
		repositorio.add(cliente);
	}

	public Cliente get(Identificador identificador) {
		return (Cliente)repositorio.get(identificador.getNumero());
	}

	public Cliente[] getAll() {
		Registro[] registros = repositorio.getAll();

		Cliente[] clientes = new Cliente[registros.length];

		return Arrays.copyOf(registros, registros.length, clientes.getClass());
	}

	public void remove(Identificador identificador) {
		repositorio.remove(identificador.getNumero());
	}

}
