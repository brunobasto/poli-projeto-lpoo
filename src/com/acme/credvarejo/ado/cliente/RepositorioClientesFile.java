package com.acme.credvarejo.ado.cliente;

import java.io.IOException;

import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.RepositorioIdentificaveisFile;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.Cliente;

public class RepositorioClientesFile implements RepositorioClientes {

	private RepositorioIdentificaveisFile repositorio;

	public RepositorioClientesFile() {
		this.repositorio = new RepositorioIdentificaveisFile();
	}

	public void add(Cliente cliente) throws IOException {
		repositorio.add(cliente);
	}

	public Cliente get(Identificador identificador)
		throws NoSuchRegistroException, IOException {

		return repositorio.getCliente(identificador.getNumero());
	}

	public Cliente[] getAll() throws IOException {
		return repositorio.getAllCliente();
	}

	public void remove(Identificador identificador)
		throws NoSuchRegistroException, IOException {

		repositorio.removeCliente(identificador.getNumero());
	}
	
	public void update(Identificador identificador, Cliente cliente)
		throws NoSuchRegistroException, IOException {

		repositorio.updateCliente(identificador.getNumero(), cliente);
	}

}
