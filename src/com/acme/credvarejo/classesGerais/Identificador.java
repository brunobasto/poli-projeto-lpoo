package com.acme.credvarejo.classesGerais;

public abstract class Identificador {

	protected abstract String calcularDigito();
	
	public abstract String getDigito();
	
	public abstract String getNumero();

	public abstract boolean verificarDigito();

}
