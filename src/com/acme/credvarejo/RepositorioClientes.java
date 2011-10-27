package com.acme.credvarejo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RepositorioClientes {

	private Cliente[] clientes;

	public RepositorioClientes() {
		this.clientes = new Cliente[] {};
	}

	public void addCliente(Cliente cliente) {
		List<Cliente> list = new ArrayList<Cliente>(
			Arrays.asList(getClientes()));
		
		if (clientes.length < 150) {
			list.add(cliente);
		}

		this.clientes = list.toArray(new Cliente[] {});
	}

	public Cliente getCliente(Cpf cpf) {
		for (int i = 0; i < clientes.length; i++) {
			if (clientes[i].getCpf().equals(cpf)) {
				return clientes[i];
			}
		}

		return null;
	}

	public Cliente[] getClientes() {
		return clientes;
	}

	public void removeCliente(Cliente cliente) {
		List<Cliente> list = new ArrayList<Cliente>(
			Arrays.asList(getClientes()));

		if (getCliente(cliente.getCpf()) != null) {
			list.remove(cliente);
		}

		this.clientes = list.toArray(new Cliente[] {});
	}

}
