package com.acme.credvarejo.ado.conta;

import java.io.IOException;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.Registro;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.conta.MovimentoCrediario;

public interface RepositorioMovimentoCrediario {

	public void add(MovimentoCrediario movimentoCrediario) throws IOException;
	
	public Registro[] getAll() throws IOException, NoSuchRegistroException;

	public MovimentoCrediario[] search(Identificador identificador)
		throws IOException, NoSuchRegistroException;

}
