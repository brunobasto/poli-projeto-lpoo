package com.acme.credvarejo.classesGerais;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;

public class RepositorioIdentificaveisArray {

	private Registro[] identificavel;

	public RepositorioIdentificaveisArray() {
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
	
	public Registro get(String chave) throws NoSuchRegistroException {
		for (int i = 0; i < identificavel.length; i++) {
			if (identificavel[i].getChave().equals(chave)) {
				return identificavel[i];
			}
		}

		throw new NoSuchRegistroException();
	}

	public Registro[] getAll() {
		return identificavel;
	}

	public void remove(String chave) throws NoSuchRegistroException {
		List<Registro> list = new ArrayList<Registro>(
			Arrays.asList(getAll()));

		Registro identificavel = get(chave);

		if (identificavel != null) {
			list.remove(identificavel);
		}

		this.identificavel = list.toArray(new Registro[] {});
	}
	
	public void update(String chave, Registro registro)
		throws NoSuchRegistroException {

		for (int i = 0; i < identificavel.length; i++) {
			if (identificavel[i].getChave().equals(chave)) {
				identificavel[i] = registro;

				return;
			}
		}

		throw new NoSuchRegistroException();
	}

}
