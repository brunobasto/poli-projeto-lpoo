package com.acme.credvarejo.cliente;

import com.acme.credvarejo.ado.cliente.RepositorioClientes;

public class ControladorCliente {

	private RepositorioClientes repositorioClientes;
	
	public ControladorCliente(RepositorioClientes repositorioClientes) {
		this.repositorioClientes = repositorioClientes;
	}
	
	public void alterar(Cliente cliente) {
		
	} 
	
	public Cliente buscar(Cpf cpf) {
		if (cpf != null) {
			return repositorioClientes.getCliente(cpf);
		}

		System.err.println("Este cliente não existe.");

		return null;
	}
	
	public Cliente[] buscarTodos() {
		return repositorioClientes.getClientes();
	}

	public void excluir(Cpf cpf) {
		if (cpf != null) {
			repositorioClientes.removeCliente(cpf);
		}
		else {
			System.err.println("Este cliente não existe.");
		}
	}

	public void incluir(Cliente cliente) {
		if (cliente != null) {
			repositorioClientes.addCliente(cliente);
		}
		else {
			System.err.println("Este cliente não existe.");
		}
	}
	
}
