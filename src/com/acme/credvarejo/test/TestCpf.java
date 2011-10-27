package com.acme.credvarejo.test;

import com.acme.credvarejo.Cpf;

public class TestCpf {

	public static void main(String[] args) {
		Cpf cpf = new Cpf("054377074");

		System.out.println(cpf.getNumero() + "-" + cpf.getDigito());
	}
	
}
