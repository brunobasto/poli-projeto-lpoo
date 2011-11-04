package com.acme.credvarejo.testes.conta;

import com.acme.credvarejo.ado.conta.RepositorioContaCrediarioArray;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.IdentificadorContaCrediario;

public class TestRepositorioContaCrediario {

	public static void main(String[] args) {
		RepositorioContaCrediarioArray r = new RepositorioContaCrediarioArray();

		IdentificadorContaCrediario identificador =
			new IdentificadorContaCrediario("123456");

		Cpf cpf = new Cpf("054377074", "52");
		
		Cliente cliente = new Cliente(cpf, "Bruno Basto", 22, 360, 0);

		ContaCrediario conta = new ContaCrediario(
			identificador, cliente, 2000, 10);

		r.add(conta);

		// Retorna o conta
		try {
			System.out.println(r.get(identificador));

			// Retorna null - Nao Achou
			System.out.println(
				r.get(new IdentificadorContaCrediario("987654")));
			
			r.remove(identificador);
		} catch (NoSuchRegistroException e) {
			e.printStackTrace();
		}

		ContaCrediario[] contas = (ContaCrediario[])r.getAll();

		System.out.println(contas.length);
	}
	
}
