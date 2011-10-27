package com.acme.credvarejo.testes.conta;

import com.acme.credvarejo.ado.conta.RepositorioContaCrediario;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.IdentificadorContaCrediario;

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

		r.removeConta(identificador);

		System.out.println(r.getContas().length);
	}
	
}
