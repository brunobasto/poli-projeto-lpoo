package com.acme.credvarejo.test;

import com.acme.credvarejo.Cliente;
import com.acme.credvarejo.ContaCrediario;
import com.acme.credvarejo.Cpf;
import com.acme.credvarejo.IdentificadorContaCrediario;
import com.acme.credvarejo.RepositorioContaCrediario;

public class TestRepositorioContaCrediario {

	public static void main(String[] args) {
		RepositorioContaCrediario r = new RepositorioContaCrediario();

		IdentificadorContaCrediario identificador = new IdentificadorContaCrediario(123456);

		Cpf cpf = new Cpf("054377074");
		
		Cliente cliente = new Cliente(cpf, "Bruno Basto", 22, 360, 0);

		ContaCrediario conta = new ContaCrediario(identificador, cliente, 2000, 10);

		r.addConta(conta);

		// Retorna o conta
		System.out.println(r.getConta(identificador));
		
		// Retorna null - Nao Achou
		System.out.println(
			r.getConta(new IdentificadorContaCrediario(987654)));

		r.removeConta(conta);

		System.out.println(r.getContas().length);
	}
	
}
