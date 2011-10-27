package com.acme.credvarejo.testes.cliente;

import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;

public class TestCliente {

	public static void main(String[] args) {
		Cpf cpf = new Cpf("054377074");
		
		Cliente cliente = new Cliente(cpf, "Bruno Basto", 22, 360, 0);
		
		System.out.println(cliente.getPrimeiroNome() + " " + cliente.getUltimoNome());
	}
	
}
