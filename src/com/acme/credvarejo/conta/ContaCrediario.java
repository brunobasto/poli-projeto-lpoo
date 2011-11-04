package com.acme.credvarejo.conta;

import com.acme.credvarejo.classesGerais.Registro;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.conta.exceptions.ContaCrediarioException;
import com.acme.credvarejo.conta.exceptions.IdentificadorException;
import com.acme.credvarejo.conta.exceptions.SaldoException;
import com.acme.credvarejo.conta.exceptions.VencimentoException;

public class ContaCrediario extends Registro {

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

	@Override
	public String getChave() {
		IdentificadorContaCrediario identificador = getIdentificador();

		return String.valueOf(identificador.getNumero());
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

	public String getNomeExtrato() {
		Cliente cliente = getCliente();

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

	@Override
	public void validar()
		throws ContaCrediarioException, NoSuchRegistroException {

		if (getIdentificador() == null) {
			throw new IdentificadorException(
				"O identificador não pode ser nulo.");
		}

		if (getCliente() == null) {
			throw new NoSuchRegistroException("O cliente não pode ser nulo.");
		}

		if (getSaldoDevido() < 0) {
			throw new SaldoException("O saldo devido não pode ser negativo.");
		}

		if (getVencimento() < 1 || getVencimento() > 31) {
			 throw new VencimentoException(
			     "O vencimento não pode ser menor que 1 ou maior que 31.");
		}
	}

}
