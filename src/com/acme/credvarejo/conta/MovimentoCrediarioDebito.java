package com.acme.credvarejo.conta;

import java.util.Date;

public class MovimentoCrediarioDebito extends MovimentoCrediario {

	public MovimentoCrediarioDebito(
		ContaCrediario conta, double valor, Date data) {

		super(conta, valor, data);
	}
	
}
