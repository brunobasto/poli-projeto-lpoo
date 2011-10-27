package com.acme.credvarejo.ado.cliente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;

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

	public void removeCliente(Cpf cpf) {
		List<Cliente> list = new ArrayList<Cliente>(
			Arrays.asList(getClientes()));

		Cliente cliente = getCliente(cpf);

		if (cliente != null) {
			list.remove(cliente);
		}

		this.clientes = list.toArray(new Cliente[] {});
	}

}
