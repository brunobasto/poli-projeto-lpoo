package com.acme.credvarejo.classesGerais;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.acme.credvarejo.cliente.Cliente;

public class RepositorioIdentificaveis {

	private Identificavel[] identificavel;

	public RepositorioIdentificaveis() {
		this.identificavel = new Cliente[] {};
	}

	public void add(Identificavel cliente) {
		List<Identificavel> list = new ArrayList<Identificavel>(
			Arrays.asList(getAll()));
		
		if (identificavel.length < 150) {
			list.add(cliente);
		}

		this.identificavel = list.toArray(new Identificavel[] {});
	}

	public Identificavel get(String chave) {
		for (int i = 0; i < identificavel.length; i++) {
			if (identificavel[i].getChave().equals(chave)) {
				return identificavel[i];
			}
		}

		return null;
	}

	public Identificavel[] getAll() {
		return identificavel;
	}

	public void remove(String chave) {
		List<Identificavel> list = new ArrayList<Identificavel>(
			Arrays.asList(getAll()));

		Identificavel identificavel = get(chave);

		if (identificavel != null) {
			list.remove(identificavel);
		}

		this.identificavel = list.toArray(new Identificavel[] {});
	}

}
