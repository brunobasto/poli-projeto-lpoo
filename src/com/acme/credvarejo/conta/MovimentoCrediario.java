package com.acme.credvarejo.conta;

import java.util.Date;

import com.acme.credvarejo.cliente.Cliente;

public class MovimentoCrediario {
	
	private ContaCrediario conta;

	private double valor;

	private int tipo;

	private Date data;

	public static final int TIPO_CREDITO = 0;

	public static final int TIPO_DEBITO = 1;

	public MovimentoCrediario(
		ContaCrediario conta, double valor, int tipo, Date data) {

		this.conta = conta;
		this.data = data;
		this.tipo = tipo;
		this.valor = valor;
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

	public int getTipo() {
		return tipo;
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

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
