package com.acme.credvarejo.testes.cliente;

import com.acme.credvarejo.ado.cliente.RepositorioClientes;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;

public class TestRepositorioClientes {

	public static void main(String[] args) {
		RepositorioClientes r = new RepositorioClientes();

		Cpf cpf = new Cpf("054377074");

		Cliente bruno = new Cliente(cpf, "Bruno Basto", 22, 360, 0);

		r.addCliente(bruno);

		// Retorna o cliente Bruno
		System.out.println(r.getCliente(cpf));
		
		// Retorna null - Nao Achou
		System.out.println(r.getCliente(new Cpf("123456789")));

		r.removeCliente(cpf);

		System.out.println(r.getClientes().length);
	}
	
}
