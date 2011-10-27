package com.acme.credvarejo.conta;

import java.util.Date;

import com.acme.credvarejo.ado.conta.RepositorioContaCrediario;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;

public class ControladorContaCrediario {

	private RepositorioContaCrediario repositorioContas;
	
	public ControladorContaCrediario(
		RepositorioContaCrediario repositorioContas) {

		this.repositorioContas = repositorioContas;
	}
	
	public void alterar(ContaCrediario conta) {
		
	}

	public ContaCrediario buscar(IdentificadorContaCrediario identificador) {
		if (identificador != null) {
			return repositorioContas.getConta(identificador);
		}

		System.err.println("Esta conta não existe.");

		return null;
	}
	
	public ContaCrediario[] buscarTodos() {
		return repositorioContas.getContas();
	}
	
	public void creditar(
		IdentificadorContaCrediario identificador, double valor,
		ControladorMovimentoCrediario controladorMovimentoCrediario) {

		if (identificador == null) {
			System.err.println("O identificador passado não existe.");

			return;
		}

		ContaCrediario conta = repositorioContas.getConta(identificador);

		conta.efetuarPagamento(valor);

		MovimentoCrediario movimento = new MovimentoCrediario(
			conta, valor, new Date());

		controladorMovimentoCrediario.inserir(movimento);
	}
	
	public void debitar(
		IdentificadorContaCrediario identificador, double valor,
		ControladorMovimentoCrediario controladorMovimentoCrediario) {

		if (identificador == null) {
			System.err.println("O identificador passado não existe.");

			return;
		}

		ContaCrediario conta = repositorioContas.getConta(identificador);

		if (conta.getLimiteDeCredito() <= valor) {
			conta.efetuarCompra(valor);

			MovimentoCrediario movimento = new MovimentoCrediario(
				conta, valor, new Date());

			controladorMovimentoCrediario.inserir(movimento);
		}
	}

	public void excluir(IdentificadorContaCrediario identificador) {
		if (identificador != null) {
			repositorioContas.removeConta(identificador);
		}
		else {
			System.err.println("Esta conta não existe.");
		}
	}

	public void inserir(
		Cliente cliente, double limiteDeCredito, int vencimento) {

		if (cliente == null) {
			System.err.println("Este cliente não existe.");

			return;
		}

		if (limiteDeCredito <= 0) {
			System.err.println(
				"O limite de crédito passado é menor ou igual a zero.");

			return;
		}

		if (vencimento < 1 || vencimento > 31) {
			System.err.println("O vencimento passado não é válido.");

			return;
		}

		Cpf cpf = cliente.getCpf();

		long numeroCpf = Integer.parseInt(cpf.getNumero());

		IdentificadorContaCrediario identificador =
			new IdentificadorContaCrediario(numeroCpf);

		repositorioContas.addConta(
			new ContaCrediario(
				identificador, cliente, limiteDeCredito, vencimento));
	}
	
}
