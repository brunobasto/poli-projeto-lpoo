package com.acme.credvarejo.test;

import com.acme.credvarejo.Cliente;
import com.acme.credvarejo.Cpf;

public class TestCliente {

	public static void main(String[] args) {
		Cpf cpf = new Cpf("054377074");
		
		Cliente cliente = new Cliente(cpf, "Bruno Basto", 22, 360, 0);
		
		System.out.println(cliente.getPrimeiroNome() + " " + cliente.getUltimoNome());
	}
	
}
