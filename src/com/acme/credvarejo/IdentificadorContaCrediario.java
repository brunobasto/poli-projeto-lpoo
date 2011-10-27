package com.acme.credvarejo;

public class IdentificadorContaCrediario {

	private long numero;

	public IdentificadorContaCrediario(long numero) {
		this.numero = numero;
	}

	public int calcularDigitoVerificador() {
		String numeroString = String.valueOf(this.numero);

		int digitoVerificador = 0;

		for (int i = 0; i < numeroString.length(); i++) {
			digitoVerificador += Integer.parseInt(
				numeroString.substring(i, i+1));
		}

		return digitoVerificador % 11;
	}

	public long getNumero() {
		return numero;
	}

	public boolean verificarValidadeDigito(int digito) {
		return digito == calcularDigitoVerificador();
	}

}
