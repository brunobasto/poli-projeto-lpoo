package com.acme.credvarejo.testes.conta;

import java.util.Date;

import com.acme.credvarejo.ado.conta.RepositorioMovimentoCrediarioImpl;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.IdentificadorContaCrediario;
import com.acme.credvarejo.conta.MovimentoCrediario;
import com.acme.credvarejo.conta.MovimentoCrediarioDebito;

public class TestRepositorioMovimentoCrediario {

	public static void main(String[] args) {
		RepositorioMovimentoCrediarioImpl r = new RepositorioMovimentoCrediarioImpl();

		IdentificadorContaCrediario identificador =
			new IdentificadorContaCrediario("123456");

		Cpf cpf = new Cpf("054377074", "52");
		
		Cliente cliente = new Cliente(cpf, "Bruno Basto", 22, 360, 0);

		ContaCrediario conta = new ContaCrediario(
			identificador, cliente, 2000, 10);
		
		MovimentoCrediario movimento = new MovimentoCrediarioDebito(
			conta, 500, new Date());

		r.add(movimento);

		System.out.println(r.getAll().length);
	}
	
}
