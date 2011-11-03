package com.acme.credvarejo.testes.conta;

import com.acme.credvarejo.conta.IdentificadorContaCrediario;

public class TestIdentificadorContaCrediario {

	public static void main(String[] args) {
		IdentificadorContaCrediario identificador =
			new IdentificadorContaCrediario("123456");
		
		System.out.println(identificador.getNumero());
	}
	
}
