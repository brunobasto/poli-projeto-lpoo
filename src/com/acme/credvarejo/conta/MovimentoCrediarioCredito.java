package com.acme.credvarejo.conta;

import java.util.Date;

public class MovimentoCrediarioCredito extends MovimentoCrediario {

	public MovimentoCrediarioCredito(
		ContaCrediario conta, double valor, Date data) {

		super(conta, valor, data);
	}

}
