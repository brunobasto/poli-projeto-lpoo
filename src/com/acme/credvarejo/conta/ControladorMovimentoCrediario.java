package com.acme.credvarejo.conta;

import com.acme.credvarejo.ado.conta.RepositorioMovimentoCrediario;

public class ControladorMovimentoCrediario {

	private RepositorioMovimentoCrediario repositorioMovimentoCrediario;
	
	public ControladorMovimentoCrediario(
		RepositorioMovimentoCrediario repositorioMovimentoCrediario) {

		this.repositorioMovimentoCrediario = repositorioMovimentoCrediario;
	}
	
	public void inserir(MovimentoCrediario movimento) {
		if (movimento != null) {
			repositorioMovimentoCrediario.addMovimento(movimento);
		}
		else {
			System.err.println("O movimento crediário passado não existe.");
		}
	} 
	
}
