package com.acme.credvarejo.cliente;

import java.util.Date;

import com.acme.credvarejo.classesGerais.Identificavel;

public class Cliente extends Identificavel {

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

}
