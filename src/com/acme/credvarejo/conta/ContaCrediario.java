package com.acme.credvarejo.conta;

import com.acme.credvarejo.cliente.Cliente;

public class ContaCrediario {

	private boolean ativa;

	private Cliente cliente;

	private IdentificadorContaCrediario identificador;

	private double limiteDeCredito;

	private double saldoDevido;

	private int vencimento;

	public ContaCrediario(
		IdentificadorContaCrediario identificador, Cliente cliente,
		double limiteDeCredito, int vencimento) {

		this.ativa = true;
		this.cliente = cliente;
		this.identificador = identificador;
		this.limiteDeCredito = limiteDeCredito;
		this.saldoDevido = 0;
		this.vencimento = vencimento;
	}

	public void desativar() {
		this.ativa = false;
	}

	public void efetuarCompra(double compra) {
		this.saldoDevido += compra;
	}

	public void efetuarPagamento(double pagamento) {
		this.saldoDevido -= pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public IdentificadorContaCrediario getIdentificador() {
		return identificador;
	}

	public double getLimiteDeCredito() {
		return limiteDeCredito;
	}

	public double getSaldoDevido() {
		return saldoDevido;
	}

	public int getVencimento() {
		return vencimento;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void reativar() {
		this.ativa = true;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setIdentificador(IdentificadorContaCrediario identificador) {
		this.identificador = identificador;
	}

	public void setLimiteDeCredito(double limiteDeCredito) {
		this.limiteDeCredito = limiteDeCredito;
	}

	public void setSaldoDevido(double saldoDevido) {
		this.saldoDevido = saldoDevido;
	}

	public void setVencimento(int vencimento) {
		this.vencimento = vencimento;
	}

}
