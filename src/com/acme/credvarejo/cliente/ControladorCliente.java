package com.acme.credvarejo.cliente;

import com.acme.credvarejo.ado.cliente.RepositorioClientes;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.exceptions.ClienteException;

public class ControladorCliente {

	private RepositorioClientes repositorioClientes;
	
	public ControladorCliente(RepositorioClientes repositorioClientes) {
		this.repositorioClientes = repositorioClientes;
	}
	
	public void alterar(Cliente cliente)
		throws NoSuchRegistroException, ClienteException {

		if (cliente != null) {
			cliente.validar();
			
			repositorioClientes.update(cliente.getCpf(), cliente);
		}
		else {
			throw new NoSuchRegistroException("Este cliente não existe.");
		}
	} 

	public Cliente buscar(Cpf cpf) throws NoSuchRegistroException {
		Cliente cliente = null;

		if (cpf != null) {
			cliente = repositorioClientes.get(cpf);
		}
		else {
			throw new NoSuchRegistroException("Este cliente não existe.");
		}

		return cliente;
	}

	public Cliente[] buscarTodos() {
		return repositorioClientes.getAll();
	}

	public void excluir(Cpf cpf) throws NoSuchRegistroException {
		if (cpf != null) {
			repositorioClientes.remove(cpf);
		}
		else {
			throw new NoSuchRegistroException("Este cliente não existe.");
		}
	}

	public void incluir(Cliente cliente) throws ClienteException {
		if (cliente != null) {
			cliente.validar();
			
			repositorioClientes.add(cliente);
		}
		else {
			System.err.println("Este cliente não existe.");
		}
	}
	
}
