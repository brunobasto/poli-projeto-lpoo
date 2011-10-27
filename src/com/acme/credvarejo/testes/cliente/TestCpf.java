package com.acme.credvarejo.testes.cliente;

import com.acme.credvarejo.cliente.Cpf;

public class TestCpf {

	public static void main(String[] args) {
		Cpf cpf = new Cpf("054377074");

		System.out.println(cpf.getNumero() + "-" + cpf.getDigito());
	}
	
}
