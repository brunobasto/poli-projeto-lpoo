package com.acme.credvarejo.conta;

import java.util.Date;

import com.acme.credvarejo.classesGerais.Registro;
import com.acme.credvarejo.conta.exceptions.ContaCrediarioException;
import com.acme.credvarejo.conta.exceptions.DataException;
import com.acme.credvarejo.conta.exceptions.ValorException;

public abstract class MovimentoCrediario extends Registro {
	
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

		return conta.getChave() + data.getTime();
	}

	public ContaCrediario getConta() {
		return conta;
	}

	public Date getData() {
		return data;
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
	
	@Override
	public void validar() throws ContaCrediarioException {
		if (getConta() == null) {
			throw new ContaCrediarioException("A conta não pode ser nula.");
		}
		
		if (getValor() <= 0) {
			throw new ValorException(
				"O valor da trasação não pode ser negativo ou igual a 0.");
		}
		
		if (getData() == null) {
			throw new DataException("A data não pode ser nula.");
		}
	}

}
