package com.acme.credvarejo;

import java.util.Date;

public class MovimentoCrediario {
	
	private ContaCrediario conta;

	private double valor;

	private int tipo;

	private Date data;

	public MovimentoCrediario(
		ContaCrediario conta, double valor, int tipo, Date data) {

		this.conta = conta;
		this.data = data;
		this.tipo = tipo;
		this.valor = valor;
	}

	public String getNomeExtrato() {
		Cliente cliente = conta.getCliente();

		StringBuffer sb = new StringBuffer(4);

		sb.append(cliente.getPrimeiroNome());
		sb.append(", ");
		sb.append(cliente.getUltimoNome());

		if (cliente.getSexo() == 0) {
			sb.append(" MR.");
		}
		else {
			sb.append(" MRS.");
		}

		return sb.toString();
	}

}
