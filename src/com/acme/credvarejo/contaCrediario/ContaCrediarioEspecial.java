package com.acme.credvarejo.contaCrediario;

import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.IdentificadorContaCrediario;

public class ContaCrediarioEspecial extends ContaCrediario {
	
	private double percentualDesconto;
	
	private int pontosAcumulados;

	public ContaCrediarioEspecial(IdentificadorContaCrediario identificador,
		Cliente cliente, double limiteDeCredito, int vencimento) {

		super(identificador, cliente, limiteDeCredito, vencimento);

		this.pontosAcumulados = 0;
	}

	public ContaCrediarioEspecial(IdentificadorContaCrediario identificador,
		Cliente cliente, double limiteDeCredito, int vencimento,
		double percentualDesconto) {

		super(identificador, cliente, limiteDeCredito, vencimento);

		this.percentualDesconto = percentualDesconto;
		this.pontosAcumulados = 0;
	}

	@Override
	public void efetuarPagamento(double pagamento) {
		super.efetuarPagamento(pagamento);

		this.pontosAcumulados += Math.ceil(pagamento / 100);
	}
	
	public void efetuarPagamento(double pagamento, double desconto) {
		if (desconto == 0) {
			super.efetuarPagamento(pagamento);
		}
		else {
			if (desconto > getPercentualDesconto()) {
				setPercentualDesconto(desconto);
			}

			pagamento =
				(((100 - getPercentualDesconto()) / 100) * pagamento);

			efetuarPagamento(pagamento);
		}
	}

	public double getPercentualDesconto() {
		return percentualDesconto;
	}

	public int getPontosAcumulados() {
		return pontosAcumulados;
	}

	public void setPercentualDesconto(double percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}

	public void setPontosAcumulados(int pontosAcumulados) {
		this.pontosAcumulados = pontosAcumulados;
	}

}
