package com.acme.credvarejo.ado.conta;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.Registro;
import com.acme.credvarejo.conta.MovimentoCrediario;

public interface RepositorioMovimentoCrediario {

	public void add(MovimentoCrediario movimentoCrediario);
	
	public MovimentoCrediario[] search(Identificador identificador);

	public Registro[] getAll();

}
