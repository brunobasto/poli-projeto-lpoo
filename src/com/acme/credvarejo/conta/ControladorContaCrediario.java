package com.acme.credvarejo.conta;

import java.io.IOException;
import java.util.Date;

import com.acme.credvarejo.ado.conta.RepositorioContaCrediario;
import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;
import com.acme.credvarejo.conta.exceptions.ContaCrediarioException;
import com.acme.credvarejo.conta.exceptions.LimiteDeCreditoException;

public class ControladorContaCrediario {

	private RepositorioContaCrediario repositorioContas;
	
	public ControladorContaCrediario(
		RepositorioContaCrediario repositorioContas) {

		this.repositorioContas = repositorioContas;
	}

	public void alterar(
			Cliente cliente, double limiteDeCredito, int vencimento)
		throws ContaCrediarioException, NoSuchRegistroException, IOException {

		Identificador identificador = cliente.getCpf();

		ContaCrediario contaCrediario = repositorioContas.get(identificador);
		
		contaCrediario.setLimiteDeCredito(limiteDeCredito);
		contaCrediario.setVencimento(vencimento);

		contaCrediario.validar();

		repositorioContas.update(identificador, contaCrediario);
	}

	public ContaCrediario buscar(IdentificadorContaCrediario identificador)
		throws NoSuchRegistroException, IOException {

		if (identificador == null) {
			throw new NoSuchRegistroException("Esta conta não existe.");
		}

		return repositorioContas.get(identificador);
	}

	public ContaCrediario[] buscarTodos()
		throws IOException, NoSuchRegistroException {

		return repositorioContas.getAll();
	}

	public void creditar(
			IdentificadorContaCrediario identificador, double valor,
			ControladorMovimentoCrediario controladorMovimentoCrediario)
		throws ContaCrediarioException, NoSuchRegistroException, IOException {

		if (identificador == null) {
			throw new NoSuchRegistroException(
				"O identificador passado não existe.");
		}

		ContaCrediario contaCrediario = repositorioContas.get(identificador);

		contaCrediario.efetuarPagamento(valor);

		MovimentoCrediario movimento = new MovimentoCrediarioCredito(
			contaCrediario, valor, new Date());

		movimento.validar();

		controladorMovimentoCrediario.inserir(movimento);

		repositorioContas.update(identificador, contaCrediario);
	}

	public void debitar(
			IdentificadorContaCrediario identificador, double valor,
			ControladorMovimentoCrediario controladorMovimentoCrediario)
		throws ContaCrediarioException, NoSuchRegistroException, IOException {

		if (identificador == null) {
			throw new NoSuchRegistroException(
				"O identificador passado não existe.");
		}

		ContaCrediario contaCrediario = repositorioContas.get(identificador);

		if (contaCrediario.getLimiteDeCredito() >= valor) {
			contaCrediario.efetuarCompra(valor);

			MovimentoCrediario movimento = new MovimentoCrediarioDebito(
				contaCrediario, valor, new Date());

			movimento.validar();

			controladorMovimentoCrediario.inserir(movimento);

			repositorioContas.update(identificador, contaCrediario);
		}
		else {
			throw new LimiteDeCreditoException("Limite insuficiente.");
		}
	}

	public void excluir(IdentificadorContaCrediario identificador)
		throws NoSuchRegistroException, IOException {

		if (identificador != null) {
			repositorioContas.remove(identificador);
		}
		else {
			throw new NoSuchRegistroException("Esta conta não existe.");
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
