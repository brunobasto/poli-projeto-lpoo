package com.acme.credvarejo.testes.cliente;

import com.acme.credvarejo.ado.cliente.RepositorioClientesArray;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;

public class TestRepositorioClientes {

	public static void main(String[] args) {
		RepositorioClientesArray r = new RepositorioClientesArray();

		Cpf cpf = new Cpf("054377074", "52");

		Cliente bruno = new Cliente(cpf, "Bruno Basto", 22, 360, 0);

		r.add(bruno);

		// Retorna o cliente Bruno
		try {
			System.out.println(r.get(cpf));
		} catch (NoSuchRegistroException e) {
			e.printStackTrace();
		}
		
		try {
			// Retorna null - Nao Achou
			System.out.println(r.get(new Cpf("123456789", "53")));

			r.remove(cpf);
		} catch (NoSuchRegistroException e) {
			e.printStackTrace();
		}

		Cliente[] clientes = r.getAll();

		System.out.println(clientes.length);
	}
	
}
