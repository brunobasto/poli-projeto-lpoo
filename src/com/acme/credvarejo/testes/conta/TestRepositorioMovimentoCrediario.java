package com.acme.credvarejo.testes.conta;

import java.util.Date;

import com.acme.credvarejo.ado.conta.RepositorioMovimentoCrediario;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.IdentificadorContaCrediario;
import com.acme.credvarejo.conta.MovimentoCrediario;

public class TestRepositorioMovimentoCrediario {

	public static void main(String[] args) {
		RepositorioMovimentoCrediario r = new RepositorioMovimentoCrediario();

		IdentificadorContaCrediario identificador =
			new IdentificadorContaCrediario(123456);

		Cpf cpf = new Cpf("054377074");
		
		Cliente cliente = new Cliente(cpf, "Bruno Basto", 22, 360, 0);

		ContaCrediario conta = new ContaCrediario(identificador, cliente, 2000, 10);
		
		MovimentoCrediario movimento = new MovimentoCrediario(
			conta, 500, 1, new Date());

		r.addMovimento(movimento);

		System.out.println(r.getMovimentos().length);
	}
	
}
