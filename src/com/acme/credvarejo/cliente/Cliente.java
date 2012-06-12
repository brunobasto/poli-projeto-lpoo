package com.acme.credvarejo.cliente;

import java.util.Date;

import com.acme.credvarejo.classesGerais.Registro;
import com.acme.credvarejo.cliente.exceptions.ClienteException;
import com.acme.credvarejo.cliente.exceptions.CpfException;
import com.acme.credvarejo.cliente.exceptions.IdadeException;
import com.acme.credvarejo.cliente.exceptions.NomeException;
import com.acme.credvarejo.cliente.exceptions.RendaException;
import com.acme.credvarejo.cliente.exceptions.SexoException;

public class Cliente extends Registro {

	private Cpf cpf;

	private String nome;

	private int idade;

	private Date clienteDesde;

	private double renda;

	private int sexo;

	public static final int SEXO_FEMININO = 1;

	public static final int SEXO_MASCULINO = 0;

	public Cliente(Cpf cpf, String nome, int idade, double renda, int sexo) {
		this.clienteDesde = new Date();
		this.cpf = cpf;
		this.idade = idade;
		this.nome = nome;
		this.renda = renda;
		this.sexo = sexo;
	}

	public boolean equals(Cliente cliente) {
		return getCpf().equals(cliente.getCpf());
	}

	@Override
	public String getChave() {
		Cpf cpf = getCpf();

		return cpf.getNumero();
	}

	public Date getClienteDesde() {
		return clienteDesde;
	}

	public Cpf getCpf() {
		return cpf;
	}

	public int getIdade() {
		return idade;
	}

	public String getNome() {
		return nome;
	}

	public String getPrimeiroNome() {
		 String[] nomes = getNome().split(" ");

		 return nomes[0];
	}

	public double getRenda() {
		return renda;
	}

	public int getSexo() {
		return sexo;
	}

	public String getUltimoNome() {
		String[] nomes = getNome().split(" ");

		return nomes[nomes.length - 1];
	}

	public void setClienteDesde(Date clienteDesde) {
		this.clienteDesde = clienteDesde;
	}

	public void setCpf(Cpf cpf) {
		this.cpf = cpf;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setRenda(double renda) {
		this.renda = renda;
	}

	public void setSexo(int sexo) {
		this.sexo = sexo;
	}

	@Override
	public void validar() throws ClienteException {
		if (getCpf() == null) {
			throw new CpfException("O C.P.F. não pode ser nulo.");
		}

		String nome = getNome();

		if (nome == null) {
			throw new NomeException("O nome não pode ser nulo.");
		}

		if (nome.equals("")) {
			throw new NomeException("O nome não pode ser em branco.");
		}

		if (nome.length() > 60) {
			throw new NomeException(
				"O nome não pode ter mais do que 60 caracteres.");
		}

		int idade = getIdade();

		if (idade < 18 || idade > 200) {
			throw new IdadeException(
				"A idade tem que ser maior do que 18 anos e menor que 200 " +
				"anos.");
		}

		double renda = getRenda();

		if (renda < 0 || renda > 1000000) {
			throw new RendaException(
				"A renda não pode ser menor que zero e não pode ser maior que" +
				" R$ 1.000.000,00.");
		}

		int sexo = getSexo();

		if (sexo != SEXO_FEMININO && sexo != SEXO_MASCULINO) {
			throw new SexoException("O sexo deve ser Masculino ou Feminino.");
		}
	}

}
