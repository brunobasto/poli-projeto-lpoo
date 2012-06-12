package com.acme.credvarejo.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.acme.credvarejo.ado.cliente.RepositorioClientesFile;
import com.acme.credvarejo.ado.cliente.RepositorioClientesMySQL;
import com.acme.credvarejo.ado.conta.RepositorioContaCrediarioFile;
import com.acme.credvarejo.ado.conta.RepositorioContaCrediarioMySQL;
import com.acme.credvarejo.ado.conta.RepositorioMovimentoCrediarioFile;
import com.acme.credvarejo.ado.conta.RepositorioMovimentoCrediarioMySQL;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.ControladorCliente;
import com.acme.credvarejo.cliente.Cpf;
import com.acme.credvarejo.cliente.exceptions.ClienteException;
import com.acme.credvarejo.cliente.exceptions.CpfException;
import com.acme.credvarejo.cliente.exceptions.IdadeException;
import com.acme.credvarejo.cliente.exceptions.NomeException;
import com.acme.credvarejo.cliente.exceptions.RendaException;
import com.acme.credvarejo.cliente.exceptions.SexoException;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.ControladorContaCrediario;
import com.acme.credvarejo.conta.ControladorMovimentoCrediario;
import com.acme.credvarejo.conta.IdentificadorContaCrediario;
import com.acme.credvarejo.conta.MovimentoCrediario;
import com.acme.credvarejo.conta.MovimentoCrediarioCredito;
import com.acme.credvarejo.conta.exceptions.ContaCrediarioException;
import com.acme.credvarejo.conta.exceptions.VencimentoException;

public class Main {

	private static final ControladorCliente controladorCliente =
		new ControladorCliente(new RepositorioClientesMySQL());

	private static final ControladorContaCrediario controladorContaCrediario =
		new ControladorContaCrediario(new RepositorioContaCrediarioMySQL());

	private static final ControladorMovimentoCrediario controladorMovimentoCrediario =
		new ControladorMovimentoCrediario(new RepositorioMovimentoCrediarioMySQL());

	private static boolean backOrRepeat(BufferedReader reader)
		throws IOException {

		System.out.println("Deseja repetir a operacao");

		System.out.println("1 - Sim");

		System.out.println("2 - Nao");

		System.out.print("operacao: ");

		if (readInteger(reader) == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	private static void creditar(BufferedReader reader) throws Exception {
		System.out.print("\nDigite o número do CPF (sem o dígito): ");
		String numero = reader.readLine();

		System.out.print("Digite o valor a ser creditado: ");
		double valor = readDouble(reader);

		IdentificadorContaCrediario identificador =
			new IdentificadorContaCrediario(numero);

		try {
			controladorContaCrediario.creditar(
				identificador, valor, controladorMovimentoCrediario);
		} catch (Exception e) {
			if (e instanceof NoSuchRegistroException) {
				System.out.println("\nConta não encontrada.\n");
			}
			else if (e instanceof ContaCrediarioException) {
				System.out.println("\n" + e.getMessage() + "\n");
			}
			else {
				throw e;
			}

			if (backOrRepeat(reader)) {
				creditar(reader);
			}
			else {
				showMainMenu(reader);
			}
		}

		System.out.println("\nTransação efetivada com sucesso!\n");

		if (backOrRepeat(reader)) {
			creditar(reader);
		}
		else {
			showMainMenu(reader);
		}
	}

	private static void debitar(BufferedReader reader) throws Exception {
		System.out.print("\nDigite o número do CPF (sem o dígito): ");
		String numero = reader.readLine();

		System.out.print("Digite o valor a ser debitado: ");
		double valor = readDouble(reader);

		IdentificadorContaCrediario identificador =
			new IdentificadorContaCrediario(numero);

		try {
			controladorContaCrediario.debitar(
				identificador, valor, controladorMovimentoCrediario);
		} catch (Exception e) {
			if (e instanceof NoSuchRegistroException) {
				System.out.println("\nConta não encontrada.\n");
			}
			else if (e instanceof ContaCrediarioException) {
				System.out.println("\n" + e.getMessage() + "\n");
			}
			else {
				throw e;
			}

			if (backOrRepeat(reader)) {
				debitar(reader);
			}
			else {
				showMainMenu(reader);
			}
		}

		System.out.println("\nTransação efetivada com sucesso!\n");

		if (backOrRepeat(reader)) {
			debitar(reader);
		}
		else {
			showMainMenu(reader);
		}
	}

	private static void deleteCliente(BufferedReader reader)
		throws Exception {

		Cliente[] clientes = controladorCliente.buscarTodos();

		if (clientes.length == 0) {
			System.out.println("\nNenhum cliente cadastrado.\n");
		}
		else {
			showClientes();

			System.out.print("Digite o número do CPF (sem o dígito): ");
			String numero = reader.readLine();

			System.out.print("Digite o digito do CPF: ");
			String digito = reader.readLine();

			Cpf cpf = new Cpf(numero, digito);

			controladorCliente.excluir(cpf);

			System.out.println("\nCliente excluido com sucesso!\n");
		}

		if (backOrRepeat(reader)) {
			deleteCliente(reader);
		}
		else {
			showMainMenu(reader);
		}
	}

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(
			new InputStreamReader(System.in));

		try {
			showMainMenu(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static double readDouble(BufferedReader reader) throws IOException {
		double value = 0;

		String line = reader.readLine();

		try {
			value = Double.parseDouble(line);
		}
		catch (NumberFormatException ex) {
			System.out.print("Número inválido, digite novamente: ");

			value = readInteger(reader);
		}

		return value;
	}

	private static int readInteger(BufferedReader reader) throws IOException {
		int value = 0;

		String line = reader.readLine();

		try {
			value = Integer.parseInt(line);
		}
		catch (NumberFormatException ex) {
			System.out.print("Número inválido, digite novamente: ");

			value = readInteger(reader);
		}

		return value;
	}

	private static void searchAllClientes(BufferedReader reader)
		throws Exception {

		showClientes();

		if (backOrRepeat(reader)) {
			searchCliente(reader);
		}
		else {
			showMainMenu(reader);
		}
	}

	private static void searchCliente(BufferedReader reader)
		throws Exception {

		System.out.print("Digite o número do CPF (sem o dígito): ");
		String numero = reader.readLine();

		System.out.print("Digite o digito do CPF: ");
		String digito = reader.readLine();

		Cpf cpf = new Cpf(numero, digito);

		Cliente cliente = null;

		try {
			cliente = controladorCliente.buscar(cpf);
		}
		catch (NoSuchRegistroException nsre) {
			System.out.println("\nCliente não encontrado.\n");

			if (backOrRepeat(reader)) {
				searchCliente(reader);
			}
			else {
				showMainMenu(reader);
			}
		}

		if (cliente != null) {
			showCliente(cliente);
		}

		if (backOrRepeat(reader)) {
			searchCliente(reader);
		}
		else {
			showMainMenu(reader);
		}
	}

	private static void showCliente(Cliente cliente) throws IOException {
		System.out.println("\nNome: " + cliente.getNome());
		System.out.println("Idade: " + cliente.getIdade());
		System.out.println("Renda: " + cliente.getRenda());

		int sexo = cliente.getSexo();

		System.out.print("Sexo: ");

		if (sexo == Cliente.SEXO_MASCULINO) {
			System.out.println("Masculino");
		}
		else {
			System.out.println("Feminino");
		}

		try {
			ContaCrediario contaCrediario = controladorContaCrediario.buscar(
				new IdentificadorContaCrediario(cliente.getChave()));

			System.out.println(
				"Limite de Crédito: " + contaCrediario.getLimiteDeCredito());
			System.out.println(
				"Dia do Vencimento: " + contaCrediario.getVencimento());
		} catch (NoSuchRegistroException e) {
			System.out.println("\nEste cliente não possui conta.");
		}

		System.out.println("\n");
	}

	private static void showClientes() throws IOException {
		Cliente[] clientes = controladorCliente.buscarTodos();

		if (clientes.length > 0) {
			System.out.println("\nClientes: \n");

			for (int i = 0; i < clientes.length; i++) {
				Cliente cliente = clientes[i];

				System.out.println(
					cliente.getChave() + " - " + cliente.getNome());
			}
		}
		else {
			System.out.println("\nNenhum cliente cadastrado.\n");
		}
	}

	private static void showExtratoCliente(BufferedReader reader)
		throws Exception {

		System.out.print("Digite o número do CPF (sem o dígito): ");
		String numero = reader.readLine();

		System.out.print("Digite o digito do CPF: ");
		String digito = reader.readLine();

		Cpf cpf = new Cpf(numero, digito);

		Cliente cliente = null;

		try {
			cliente = controladorCliente.buscar(cpf);
		}
		catch (NoSuchRegistroException nsre) {
			System.out.println("\n" + nsre.getMessage() + "\n");

			if (backOrRepeat(reader)) {
				showExtratoCliente(reader);
			}
			else {
				showMainMenu(reader);
			}
		}

		if (cliente != null) {
			IdentificadorContaCrediario identificador =
				new IdentificadorContaCrediario(cliente.getChave());

			ContaCrediario contaCrediario =
				controladorContaCrediario.buscar(identificador);

			if (contaCrediario != null) {
				System.out.println(
					"\n" + contaCrediario.getNomeExtrato() + "\n");

				MovimentoCrediario[] movimentos =
					controladorMovimentoCrediario.buscar(identificador);

				for (int i = 0; i < movimentos.length; i++) {
					MovimentoCrediario movimentoCrediario = movimentos[i];

					if (movimentoCrediario instanceof MovimentoCrediarioCredito) {
						System.out.print("Crédito");
					}
					else {
						System.out.print("Débito");
					}

					System.out.println(
						" -- R$ " + movimentoCrediario.getValor());
				}

				System.out.println(
					"\nSaldo: R$ " + contaCrediario.getSaldoDevido() + "\n");
			}
		}

		if (backOrRepeat(reader)) {
			searchCliente(reader);
		}
		else {
			showMainMenu(reader);
		}
	}

	private static void showMainMenu(BufferedReader reader) throws Exception {
		System.out.println("Que operação você deseja realizar?\n");

		int index = 0;

		System.out.println(++index + " - Cadastrar Cliente");
		System.out.println(++index + " - Alterar cadastro de Cliente");
		System.out.println(++index + " - Excluir Cliente");
		System.out.println(++index + " - Mostrar Todos Clientes");
		System.out.println(++index + " - Buscar Cliente");
		System.out.println(++index + " - Creditar");
		System.out.println(++index + " - Debitar");
		System.out.println(++index + " - Transferir");
		System.out.println(++index + " - Mostrar Extrato de Cliente");
		System.out.println(++index + " - Sair");

		System.out.println("\n");

		int option = 0;

		do {
			System.out.print("Opção: ");

			option = readInteger(reader);
		}
		while (option < 1 || option > index);

		if (option == 1) {
			updateCliente(reader);
		}
		else if (option == 2) {
			updateCliente(reader);
		}
		else if (option == 3) {
			deleteCliente(reader);
		}
		else if (option == 4) {
			searchAllClientes(reader);
		}
		else if (option == 5) {
			searchCliente(reader);
		}
		else if (option == 6) {
			creditar(reader);
		}
		else if (option == 7) {
			debitar(reader);
		}
		else if (option == 8) {
			transferir(reader);
		}
		else if (option == 9) {
			showExtratoCliente(reader);
		}
		else {
			Thread.currentThread().stop();
		}
	}

	private static void transferir(BufferedReader reader) throws Exception {
		System.out.print(
			"\nDigite o número do CPF (sem o dígito) da conta a ser creditada: ");
		String numeroCredito = reader.readLine();

		System.out.print(
			"Digite o número do CPF (sem o dígito) da conta a ser debitada: ");
		String numeroDebito = reader.readLine();

		System.out.print("Digite o valor da transferencia: ");
		double valor = readDouble(reader);

		IdentificadorContaCrediario identificadorCredito =
			new IdentificadorContaCrediario(numeroCredito);

		IdentificadorContaCrediario identificadorDebito =
			new IdentificadorContaCrediario(numeroDebito);

		try {
			controladorContaCrediario.debitar(
				identificadorDebito, valor, controladorMovimentoCrediario);

			controladorContaCrediario.creditar(
				identificadorCredito, valor, controladorMovimentoCrediario);
		} catch (Exception e) {
			if (e instanceof NoSuchRegistroException) {
				System.out.println("\nConta não encontrada.\n");
			}
			else if (e instanceof ContaCrediarioException) {
				System.out.println("\n" + e.getMessage() + "\n");
			}
			else {
				throw e;
			}

			if (backOrRepeat(reader)) {
				transferir(reader);
			}
			else {
				showMainMenu(reader);
			}
		}

		System.out.println("\nTransação efetivada com sucesso!\n");

		if (backOrRepeat(reader)) {
			transferir(reader);
		}
		else {
			showMainMenu(reader);
		}
	}

	private static void updateCliente(BufferedReader reader) throws Exception {
		System.out.print("Digite o número do CPF (sem o dígito): ");
		String numero = reader.readLine();

		System.out.print("Digite o digito do CPF: ");
		String digito = reader.readLine();

		Cpf cpf = new Cpf(numero, digito);

		System.out.print("Digite o Nome do cliente: ");
		String nome = reader.readLine();

		System.out.print("Digite a Idade do cliente: ");
		int idade = readInteger(reader);

		System.out.print("Digite a Renda do cliente: ");
		double renda = readDouble(reader);

		System.out.print("Digite o Sexo do cliente (0 - Masc, 1 - Fem): ");
		int sexo = readInteger(reader);

		Cliente cliente = new Cliente(cpf, nome, idade, renda, sexo);

		System.out.print("Digite o Limite de Crédito do cliente: ");
		double limiteDeCredito = readDouble(reader);

		System.out.print("Digite o dia de vencimento da fatura do cliente: ");
		int vencimento = readInteger(reader);

		try {
			controladorCliente.alterar(cliente);
			controladorContaCrediario.alterar(
				cliente, limiteDeCredito, vencimento);
		}
		catch (NoSuchRegistroException nsre) {
			controladorCliente.incluir(cliente);
			controladorContaCrediario.inserir(
				cliente, limiteDeCredito, vencimento);
		}
		catch (ClienteException ce) {
			if (ce instanceof CpfException) {
				System.out.println("Erro: Cpf inválido.");
			}
			else if (ce instanceof NomeException) {
				System.out.println("Erro: Nome inválido.");
			}
			else if (ce instanceof IdadeException) {
				System.out.println("Erro: Idade inválida.");
			}
			else if (ce instanceof RendaException) {
				System.out.println("Erro: Renda inválida.");
			}
			else if (ce instanceof SexoException) {
				System.out.println("Erro: Sexo inválido.");
			}

			if (backOrRepeat(reader)) {
				updateCliente(reader);
			}
			else {
				showMainMenu(reader);
			}
		}
		catch (ContaCrediarioException cce) {
			if (cce instanceof VencimentoException) {
				System.out.println("Erro: Vencimento inválido.");
			}

			if (backOrRepeat(reader)) {
				updateCliente(reader);
			}
			else {
				showMainMenu(reader);
			}
		}

		System.out.println("\nCliente salvo com sucesso!\n");

		if (backOrRepeat(reader)) {
			updateCliente(reader);
		}
		else {
			showMainMenu(reader);
		}
	}

}
