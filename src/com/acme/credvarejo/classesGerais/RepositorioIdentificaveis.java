package com.acme.credvarejo.classesGerais;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RepositorioIdentificaveis {

	private Registro[] identificavel;

	public RepositorioIdentificaveis() {
		this.identificavel = new Registro[] {};
	}

	public void add(Registro registro) {
		List<Registro> list = new ArrayList<Registro>(
			Arrays.asList(getAll()));

		if (identificavel.length < 150) {
			list.add(registro);
		}

		this.identificavel = list.toArray(new Registro[] {});
	}

	public Registro get(String chave) {
		for (int i = 0; i < identificavel.length; i++) {
			if (identificavel[i].getChave().equals(chave)) {
				return identificavel[i];
			}
		}

		return null;
	}

	public Registro[] getAll() {
		return identificavel;
	}
	
	public void remove(String chave) {
		List<Registro> list = new ArrayList<Registro>(
			Arrays.asList(getAll()));

		Registro identificavel = get(chave);

		if (identificavel != null) {
			list.remove(identificavel);
		}

		this.identificavel = list.toArray(new Registro[] {});
	}

}
