package com.acme.credvarejo.ado.cliente;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.cliente.Cliente;

public interface RepositorioClientes {

	public void add(Cliente cliente);

	public Cliente get(Identificador identificador);

	public Cliente[] getAll();

	public void remove(Identificador identificador);
	
}
