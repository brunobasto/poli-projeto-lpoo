package com.acme.credvarejo.ado.cliente;

import com.acme.credvarejo.classesGerais.RepositorioIdentificaveis;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;

public class RepositorioClientes {

	private RepositorioIdentificaveis repositorio;

	public RepositorioClientes() {
		this.repositorio = new RepositorioIdentificaveis();
	}

	public void addCliente(Cliente cliente) {
		repositorio.add(cliente);
	}

	public Cliente getCliente(Cpf cpf) {
		return (Cliente)repositorio.get(cpf.getNumero());
	}

	public Cliente[] getClientes() {
		return (Cliente[])repositorio.getAll();
	}

	public void removeCliente(Cpf cpf) {
		repositorio.remove(cpf.getNumero());
	}

}
