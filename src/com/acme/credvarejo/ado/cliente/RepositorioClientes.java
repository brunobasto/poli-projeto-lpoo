package com.acme.credvarejo.ado.cliente;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.Cliente;

public interface RepositorioClientes {

	public void add(Cliente cliente);

	public Cliente get(Identificador identificador)
		throws NoSuchRegistroException;

	public Cliente[] getAll();

	public void remove(Identificador identificador)
		throws NoSuchRegistroException;
	
	public void update(Identificador identificador, Cliente cliente)
		throws NoSuchRegistroException;
	
}
