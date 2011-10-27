package com.acme.credvarejo.test;

import com.acme.credvarejo.IdentificadorContaCrediario;

public class TestIdentificadorContaCrediario {

	public static void main(String[] args) {
		IdentificadorContaCrediario identificador =
			new IdentificadorContaCrediario(123456);
		
		System.out.println(identificador.calcularDigitoVerificador());
	}
	
}
