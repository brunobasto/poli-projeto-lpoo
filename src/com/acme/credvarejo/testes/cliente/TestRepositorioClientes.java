package com.acme.credvarejo.testes.cliente;

import com.acme.credvarejo.ado.cliente.RepositorioClientesImpl;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;

public class TestRepositorioClientes {

	public static void main(String[] args) {
		RepositorioClientesImpl r = new RepositorioClientesImpl();

		Cpf cpf = new Cpf("054377074", "52");

		Cliente bruno = new Cliente(cpf, "Bruno Basto", 22, 360, 0);

		r.add(bruno);

		// Retorna o cliente Bruno
		System.out.println(r.get(cpf));
		
		// Retorna null - Nao Achou
		System.out.println(r.get(new Cpf("123456789", "53")));

		r.remove(cpf);

		Cliente[] clientes = r.getAll();

		System.out.println(clientes.length);
	}
	
}
