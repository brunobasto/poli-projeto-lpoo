package com.acme.credvarejo.ado.cliente;

import java.io.IOException;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.Cliente;

public interface RepositorioClientes {

	public void add(Cliente cliente) throws IOException;

	public Cliente get(Identificador identificador)
		throws NoSuchRegistroException, IOException;

	public Cliente[] getAll() throws IOException;

	public void remove(Identificador identificador)
		throws NoSuchRegistroException, IOException;
	
	public void update(Identificador identificador, Cliente cliente)
		throws NoSuchRegistroException, IOException;
	
}
