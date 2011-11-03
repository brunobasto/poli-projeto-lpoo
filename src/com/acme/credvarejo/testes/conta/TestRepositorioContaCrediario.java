package com.acme.credvarejo.testes.conta;

import com.acme.credvarejo.ado.conta.RepositorioContaCrediarioImpl;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.IdentificadorContaCrediario;

public class TestRepositorioContaCrediario {

	public static void main(String[] args) {
		RepositorioContaCrediarioImpl r = new RepositorioContaCrediarioImpl();

		IdentificadorContaCrediario identificador =
			new IdentificadorContaCrediario("123456");

		Cpf cpf = new Cpf("054377074", "52");
		
		Cliente cliente = new Cliente(cpf, "Bruno Basto", 22, 360, 0);

		ContaCrediario conta = new ContaCrediario(
			identificador, cliente, 2000, 10);

		r.add(conta);

		// Retorna o conta
		System.out.println(r.get(identificador));
		
		// Retorna null - Nao Achou
		System.out.println(
			r.get(new IdentificadorContaCrediario("987654")));

		r.remove(identificador);

		ContaCrediario[] contas = (ContaCrediario[])r.getAll();

		System.out.println(contas.length);
	}
	
}
