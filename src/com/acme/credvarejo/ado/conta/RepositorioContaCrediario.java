package com.acme.credvarejo.ado.conta;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.conta.ContaCrediario;

public interface RepositorioContaCrediario {

	public void add(ContaCrediario contaCrediario);

	public ContaCrediario get(Identificador identificador)
		throws NoSuchRegistroException;

	public ContaCrediario[] getAll();

	public void remove(Identificador identificador)
		throws NoSuchRegistroException;

	public void update(
			Identificador identificador, ContaCrediario contaCrediario)
		throws NoSuchRegistroException;

}
