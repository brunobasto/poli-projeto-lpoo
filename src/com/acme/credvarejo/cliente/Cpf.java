package com.acme.credvarejo.cliente;

public class Cpf {

	private String numero;
	
	public Cpf(String numero) {
		this.numero = numero;
	}

	public boolean equals(Cpf cpf) {
		return getNumero().equals(cpf.getNumero());
	}

	public String getDigito() {
		int primeiroDigito = 0;
		int segundoDigito = 0;

		String numero = getNumero();

		// Cálculo do Primeiro Dígito

		int somatorio = 0;

		for (int i = 0; i < numero.length(); i++) {
			int curDigito = Integer.parseInt(numero.substring(i, i+1));
			
			somatorio += (-1*i + 10) * curDigito;
		}

		int resto = somatorio % 11;
		
		if (resto >= 2) {
			primeiroDigito = 11 - resto;
		}
		
		// Cálculo do Segundo Dígito

		numero += String.valueOf(primeiroDigito);

		somatorio = 0;

		for (int i = 0; i < numero.length(); i++) {
			int curDigito = Integer.parseInt(numero.substring(i, i+1));

			somatorio += (-1*i + 11) * curDigito;
		}

		resto = somatorio % 11;

		if (resto >= 2) {
			segundoDigito = 11 - resto;
		}

		return String.valueOf(primeiroDigito) + String.valueOf(segundoDigito);
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public boolean validarDigito(String digito) {
		return digito.equals(getDigito());
	}
	
}