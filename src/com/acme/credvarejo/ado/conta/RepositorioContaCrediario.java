package com.acme.credvarejo.ado.conta;

import java.io.IOException;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.conta.ContaCrediario;

public interface RepositorioContaCrediario {

	public void add(ContaCrediario contaCrediario) throws IOException;

	public ContaCrediario get(Identificador identificador)
		throws NoSuchRegistroException, IOException;

	public ContaCrediario[] getAll()
		throws IOException, NoSuchRegistroException;

	public void remove(Identificador identificador)
		throws NoSuchRegistroException, IOException;

	public void update(
			Identificador identificador, ContaCrediario contaCrediario)
		throws NoSuchRegistroException, IOException;

}
