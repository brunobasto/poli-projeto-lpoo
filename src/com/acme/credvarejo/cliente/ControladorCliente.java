package com.acme.credvarejo.cliente;

import com.acme.credvarejo.ado.cliente.RepositorioClientes;
import com.acme.credvarejo.cliente.exceptions.ClienteException;
import com.acme.credvarejo.cliente.exceptions.NoSuchClienteException;

public class ControladorCliente {

	private RepositorioClientes repositorioClientes;
	
	public ControladorCliente(RepositorioClientes repositorioClientes) {
		this.repositorioClientes = repositorioClientes;
	}
	
	public void alterar(Cliente cliente) {
		
	} 

	public Cliente buscar(Cpf cpf) throws NoSuchClienteException {
		Cliente cliente = null;

		if (cpf != null) {
			cliente = repositorioClientes.get(cpf);
		}

		if (cliente == null) {
			throw new NoSuchClienteException("Este cliente não existe.");
		}

		return cliente;
	}

	public Cliente[] buscarTodos() {
		return repositorioClientes.getAll();
	}

	public void excluir(Cpf cpf) throws NoSuchClienteException {
		if (cpf != null) {
			repositorioClientes.remove(cpf);
		}
		else {
			throw new NoSuchClienteException("Este cliente não existe.");
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
