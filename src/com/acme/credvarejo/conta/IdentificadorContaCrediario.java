package com.acme.credvarejo.conta;

import com.acme.credvarejo.classesGerais.Identificador;

public class IdentificadorContaCrediario extends Identificador {

	private String digito;
	private String numero;

	public IdentificadorContaCrediario(String numero) {
		this.numero = numero;
	}

	protected String calcularDigito() {
		int digitoVerificador = 0;

		for (int i = 0; i < numero.length(); i++) {
			digitoVerificador += Integer.parseInt(
				numero.substring(i, i+1));
		}

		return String.valueOf(digitoVerificador % 11);
	}
	
	public String getDigito() {
		return digito;
	}

	public String getNumero() {
		return numero;
	}

	public void setDigito(String digito) {
		this.digito = digito;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public boolean verificarDigito() {
		return getDigito().equals(calcularDigito());
	}

}
