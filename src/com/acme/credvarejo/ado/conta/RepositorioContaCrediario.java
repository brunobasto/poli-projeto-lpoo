package com.acme.credvarejo.ado.conta;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.Registro;
import com.acme.credvarejo.conta.ContaCrediario;

public interface RepositorioContaCrediario {

	public void add(ContaCrediario contaCrediario);

	public ContaCrediario get(Identificador identificador);

	public ContaCrediario[] getAll();

	public void remove(Identificador identificador);
	
}
