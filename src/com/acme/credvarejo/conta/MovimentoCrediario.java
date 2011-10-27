package com.acme.credvarejo.conta;

import java.util.Date;

import com.acme.credvarejo.classesGerais.Identificavel;
import com.acme.credvarejo.cliente.Cliente;

public class MovimentoCrediario extends Identificavel {
	
	private ContaCrediario conta;

	private double valor;

	private Date data;

	public MovimentoCrediario(ContaCrediario conta, double valor, Date data) {
		this.conta = conta;
		this.data = data;
		this.valor = valor;
	}

	@Override
	public String getChave() {
		ContaCrediario conta = getConta();
		Date data = getData();

		return conta.getChave() + data.toString();
	}

	public ContaCrediario getConta() {
		return conta;
	}

	public Date getData() {
		return data;
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

	public double getValor() {
		return valor;
	}

	public void setConta(ContaCrediario conta) {
		this.conta = conta;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
