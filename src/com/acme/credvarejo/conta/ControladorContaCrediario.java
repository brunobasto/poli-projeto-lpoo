package com.acme.credvarejo.conta;

import java.util.Date;

import com.acme.credvarejo.ado.conta.RepositorioContaCrediario;
import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;
import com.acme.credvarejo.conta.exceptions.ContaCrediarioException;

public class ControladorContaCrediario {

	private RepositorioContaCrediario repositorioContas;
	
	public ControladorContaCrediario(RepositorioContaCrediario repositorioContas) {
		this.repositorioContas = repositorioContas;
	}

	public void alterar(
			Cliente cliente, double limiteDeCredito, int vencimento)
		throws ContaCrediarioException, NoSuchRegistroException {
		
		Identificador identificador = cliente.getCpf();

		ContaCrediario contaCrediario = repositorioContas.get(identificador);
		
		contaCrediario.setLimiteDeCredito(limiteDeCredito);
		contaCrediario.setVencimento(vencimento);

		contaCrediario.validar();

		repositorioContas.update(identificador, contaCrediario);
	}

	public ContaCrediario buscar(IdentificadorContaCrediario identificador)
		throws NoSuchRegistroException {

		if (identificador != null) {
			return repositorioContas.get(identificador);
		}

		System.err.println("Esta conta não existe.");

		return null;
	}
	
	public ContaCrediario[] buscarTodos() {
		return repositorioContas.getAll();
	}
	
	public void creditar(
			IdentificadorContaCrediario identificador, double valor,
			ControladorMovimentoCrediario controladorMovimentoCrediario)
		throws ContaCrediarioException, NoSuchRegistroException {

		if (identificador == null) {
			System.err.println("O identificador passado não existe.");

			return;
		}

		ContaCrediario conta = repositorioContas.get(identificador);
		
		if (conta == null) {
			throw new ContaCrediarioException("Esta conta não existe.");
		}

		conta.efetuarPagamento(valor);

		MovimentoCrediario movimento = new MovimentoCrediarioCredito(
			conta, valor, new Date());

		movimento.validar();

		controladorMovimentoCrediario.inserir(movimento);
	}
	
	public void debitar(
			IdentificadorContaCrediario identificador, double valor,
			ControladorMovimentoCrediario controladorMovimentoCrediario)
		throws ContaCrediarioException, NoSuchRegistroException {

		if (identificador == null) {
			System.err.println("O identificador passado não existe.");

			return;
		}

		ContaCrediario conta = repositorioContas.get(identificador);
		
		if (conta == null) {
			throw new ContaCrediarioException("Esta conta não existe.");
		}

		if (conta.getLimiteDeCredito() <= valor) {
			conta.efetuarCompra(valor);

			MovimentoCrediario movimento = new MovimentoCrediarioDebito(
				conta, valor, new Date());

			movimento.validar();

			controladorMovimentoCrediario.inserir(movimento);
		}
	}

	public void excluir(IdentificadorContaCrediario identificador)
		throws NoSuchRegistroException {

		if (identificador != null) {
			repositorioContas.remove(identificador);
		}
		else {
			System.err.println("Esta conta não existe.");
		}
	}

	public void inserir(
			Cliente cliente, double limiteDeCredito, int vencimento)
		throws Exception {

		Cpf cpf = cliente.getCpf();

		IdentificadorContaCrediario identificador =
			new IdentificadorContaCrediario(cpf.getNumero());
		
		ContaCrediario contaCrediario = new ContaCrediario(
			identificador, cliente, limiteDeCredito, vencimento);

		contaCrediario.validar();
		
		repositorioContas.add(contaCrediario);
	}
	
}
