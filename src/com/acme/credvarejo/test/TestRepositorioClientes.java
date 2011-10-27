package com.acme.credvarejo.test;

import com.acme.credvarejo.Cliente;
import com.acme.credvarejo.Cpf;
import com.acme.credvarejo.RepositorioClientes;

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

		r.removeCliente(bruno);

		System.out.println(r.getClientes().length);
	}
	
}
