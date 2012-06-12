package com.acme.credvarejo.classesGerais;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.LinkedList;

import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.IdentificadorContaCrediario;
import com.acme.credvarejo.conta.MovimentoCrediario;
import com.acme.credvarejo.conta.MovimentoCrediarioCredito;
import com.acme.credvarejo.conta.MovimentoCrediarioDebito;

public class RepositorioIdentificaveisMySQL {
	
	private static final String CLIENTE_DIR = "clientes";

	private static final String CONTA_CREDIARIO_DIR = "conta_crediario";

	private static final String MOVIMENTO_CREDIARIO_DIR = "movimento_crediario";

	public RepositorioIdentificaveisMySQL() {
		createDir(CLIENTE_DIR);
		createDir(CONTA_CREDIARIO_DIR);
		createDir(MOVIMENTO_CREDIARIO_DIR);
	}

	public void add(Cliente cliente) throws IOException {
		clienteToFile(cliente);
	}

	public void add(ContaCrediario contaCrediario) throws IOException {
		contaCrediarioToFile(contaCrediario);
	}

	public void add(MovimentoCrediario movimentoCrediario) throws IOException {
		movimentoCrediarioToFile(movimentoCrediario);
	}

	private Cliente clienteFromFile(File file) throws IOException {
		LinkedList<String> contents = getFileContents(file);

		String numeroCPF = contents.poll();
		String digitoCPF = contents.poll();

		Cpf cpf = new Cpf(numeroCPF, digitoCPF);

		String nome = contents.poll();
		String idade = contents.poll();
		String renda = contents.poll();
		String sexo = contents.poll();

		return new Cliente(
			cpf,
			nome,
			Integer.parseInt(idade),
			Double.parseDouble(renda),
			Integer.parseInt(sexo)
		);
	}
	
	private void clienteToFile(Cliente cliente) throws IOException {
		BufferedWriter out = new BufferedWriter(
			new FileWriter(CLIENTE_DIR + "/" + cliente.getChave(), false));

		Cpf cpf = cliente.getCpf();

		out.write(String.valueOf(cpf.getNumero()));
		out.write("\n");
		out.write(String.valueOf(cpf.getDigito()));
		out.write("\n");
		out.write(String.valueOf(cliente.getNome()));
		out.write("\n");
		out.write(String.valueOf(cliente.getIdade()));
		out.write("\n");
		out.write(String.valueOf(cliente.getRenda()));
		out.write("\n");
		out.write(String.valueOf(cliente.getSexo()));
		
		out.close();
	}

	private ContaCrediario contaCrediarioFromFile(File file)
		throws IOException, NoSuchRegistroException {

		LinkedList<String> contents = getFileContents(file);

		String numero = contents.poll();

		IdentificadorContaCrediario identificador =
			new IdentificadorContaCrediario(numero);

		String chaveCliente = contents.poll();
		String limiteDeCredito = contents.poll();
		String saldoDevido = contents.poll();
		String vencimento = contents.poll();

		Cliente cliente = getCliente(chaveCliente);

		ContaCrediario contaCrediario = new ContaCrediario(
			identificador,
			cliente,
			Double.parseDouble(limiteDeCredito),
			Integer.parseInt(vencimento)
		);

		contaCrediario.setSaldoDevido(Double.parseDouble(saldoDevido));

		return contaCrediario;
	}

	private void contaCrediarioToFile(ContaCrediario contaCrediario)
		throws IOException {

		BufferedWriter out = new BufferedWriter(
			new FileWriter(
				CONTA_CREDIARIO_DIR + "/" + contaCrediario.getChave(), false));
		
		Cliente cliente = contaCrediario.getCliente();

		out.write(String.valueOf(contaCrediario.getChave()));
		out.write("\n");
		out.write(String.valueOf(cliente.getChave()));
		out.write("\n");
		out.write(String.valueOf(contaCrediario.getLimiteDeCredito()));
		out.write("\n");
		out.write(String.valueOf(contaCrediario.getSaldoDevido()));
		out.write("\n");
		out.write(String.valueOf(contaCrediario.getVencimento()));

		out.close();
	}

	public Cliente[] getAllCliente() throws IOException {
		File clienteDir = new File(CLIENTE_DIR);

		File[] clienteFiles = clienteDir.listFiles();

		Cliente[] clientes = new Cliente[clienteFiles.length];

		for (int i = 0; i < clienteFiles.length; i++) {
			clientes[i] = clienteFromFile(clienteFiles[i]);
		}

		return clientes;
	}

	public ContaCrediario[] getAllContaCrediario()
		throws IOException, NoSuchRegistroException {

		File contaCrediarioDir = new File(CONTA_CREDIARIO_DIR);

		File[] contaCrediarioFiles = contaCrediarioDir.listFiles();

		ContaCrediario[] contasCrediario =
			new ContaCrediario[contaCrediarioFiles.length];

		for (int i = 0; i < contaCrediarioFiles.length; i++) {
			contasCrediario[i] = contaCrediarioFromFile(contaCrediarioFiles[i]);
		}

		return contasCrediario;
	}

	public MovimentoCrediario[] getAllMovimentoCrediario()
		throws IOException, NoSuchRegistroException {

		File movimentoCrediarioDir = new File(MOVIMENTO_CREDIARIO_DIR);

		File[] movimentoCrediarioFiles = movimentoCrediarioDir.listFiles();

		MovimentoCrediario[] movimentosCrediario =
			new MovimentoCrediario[movimentoCrediarioFiles.length];

		for (int i = 0; i < movimentoCrediarioFiles.length; i++) {
			movimentosCrediario[i] = movimentoCrediarioFromFile(
				movimentoCrediarioFiles[i]);
		}

		return movimentosCrediario;
	}
	
	public Cliente getCliente(String chave)
		throws NoSuchRegistroException, IOException {

		for (Cliente cliente : getAllCliente()) {
			if (cliente.getChave().equals(chave)) {
				return cliente;
			}
		}

		throw new NoSuchRegistroException("Cliente não encontrado.");
	}

	public ContaCrediario getContaCrediario(String chave)
		throws NoSuchRegistroException, IOException {

		for (ContaCrediario contaCrediario : getAllContaCrediario()) {
			if (contaCrediario.getChave().equals(chave)) {
				return contaCrediario;
			}
		}

		throw new NoSuchRegistroException("Conta Crediário não encontrada.");
	}

	private File createDir(String path) {
		File directory = new File(path);

		if (!directory.exists()) {
			directory.mkdir();
		}

		return directory;
	}
	
	private LinkedList<String> getFileContents(File file) throws IOException {
		FileInputStream is = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line;

		LinkedList<String> contents = new LinkedList<String>();

		while ((line = br.readLine()) != null) {
			contents.add(line);
		}

		is.close();

		return contents;
	}

	private MovimentoCrediario movimentoCrediarioFromFile(File file)
		throws IOException, NoSuchRegistroException {

		LinkedList<String> contents = getFileContents(file);

		String chaveConta = contents.poll();
		String valor = contents.poll();
		String data = contents.poll();
		String tipo = contents.poll();
		
		ContaCrediario contaCrediario = getContaCrediario(chaveConta);

		MovimentoCrediario movimentoCrediario;
		
		if (tipo.equals("credito")) {
			movimentoCrediario = new MovimentoCrediarioCredito(
				contaCrediario,
				Double.parseDouble(valor),
				new Date(Long.parseLong(data))
			); 
		}
		else {
			movimentoCrediario = new MovimentoCrediarioDebito(
				contaCrediario,
				Double.parseDouble(valor),
				new Date(Long.parseLong(data))
			);
		}

		return movimentoCrediario;
	}

	private void movimentoCrediarioToFile(MovimentoCrediario movimentoCrediario)
		throws IOException {

		BufferedWriter out = new BufferedWriter(
			new FileWriter(
				MOVIMENTO_CREDIARIO_DIR + "/" + movimentoCrediario.getChave()));
		
		ContaCrediario contaCrediario = movimentoCrediario.getConta();

		out.write(String.valueOf(contaCrediario.getChave()));
		out.write("\n");
		out.write(String.valueOf(movimentoCrediario.getValor()));
		out.write("\n");
		out.write(String.valueOf(movimentoCrediario.getData().getTime()));
		out.write("\n");

		if (movimentoCrediario instanceof MovimentoCrediarioCredito) {
			out.write("credito");
		}
		else {
			out.write("debito");
		}

		out.close();
	}

	public void removeCliente(String chave)
		throws NoSuchRegistroException, IOException {

		Cliente cliente = getCliente(chave);

		File file = new File(CLIENTE_DIR + "/" + cliente.getChave());

		file.delete();
	}

	public void removeContaCrediario(String chave)
		throws NoSuchRegistroException, IOException {

		ContaCrediario contaCrediario = getContaCrediario(chave);

		File file = new File(
			CONTA_CREDIARIO_DIR + "/" + contaCrediario.getChave());

		file.delete();
	}
	
	public void updateCliente(String chave, Cliente cliente)
		throws NoSuchRegistroException, IOException {

		removeCliente(chave);

		clienteToFile(cliente);
	}
	
	public void updateContaCrediario(
			String chave, ContaCrediario contaCrediario)
		throws NoSuchRegistroException, IOException {

		removeContaCrediario(chave);

		contaCrediarioToFile(contaCrediario);
	}

}
