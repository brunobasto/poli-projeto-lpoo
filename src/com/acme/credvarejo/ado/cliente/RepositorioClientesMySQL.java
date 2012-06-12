package com.acme.credvarejo.ado.cliente;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.acme.credvarejo.ado.util.MySQLUtil;
import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;

public class RepositorioClientesMySQL implements RepositorioClientes {

	@Override
	public void add(Cliente cliente) {
		try {
			Connection connection = MySQLUtil.openConnection();

			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cliente values (default, ?, ?, ?, ? , ?, ?)");

			preparedStatement.setString(1, cliente.getChave());
			preparedStatement.setString(2, cliente.getNome());
			preparedStatement.setInt(3, cliente.getIdade());
			preparedStatement.setDate(
				4, new java.sql.Date(cliente.getClienteDesde().getTime()));
			preparedStatement.setDouble(5, cliente.getRenda());
			preparedStatement.setInt(6, cliente.getSexo());

			preparedStatement.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();

			System.out.println("Ocorreu um erro.");
		}
		finally {
			MySQLUtil.closeConnection();
		}
	}

	@Override
	public void remove(Identificador identificador)
		throws NoSuchRegistroException, IOException {

		try {
			Connection connection = MySQLUtil.openConnection();

			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM cliente WHERE cliente.cpf = ?");

			preparedStatement.setString(1, identificador.getNumero());

			preparedStatement.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();

			System.out.println("Ocorreu um erro.");
		}
		finally {
			MySQLUtil.closeConnection();
		}
	}

	@Override
	public void update(Identificador identificador, Cliente cliente)
		throws NoSuchRegistroException, IOException {

		try {
			// Valida a existencia
			get(identificador);

			Connection connection = MySQLUtil.openConnection();

			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cliente SET cpf=?, nome=?, idade=?, clienteDesde=?, renda=?, sexo=? WHERE cliente.cpf = ?");

			preparedStatement.setString(1, identificador.getNumero());
			preparedStatement.setString(2, cliente.getNome());
			preparedStatement.setInt(3, cliente.getIdade());
			preparedStatement.setDate(
				4, new java.sql.Date(cliente.getClienteDesde().getTime()));
			preparedStatement.setDouble(5, cliente.getRenda());
			preparedStatement.setInt(6, cliente.getSexo());

			preparedStatement.setString(7, identificador.getNumero());

			preparedStatement.executeUpdate();
		}
		catch (NoSuchRegistroException nsre) {
			throw nsre;
		}
		catch (Exception e) {
			e.printStackTrace();

			System.out.println("Ocorreu um erro.");
		}
		finally {
			MySQLUtil.closeConnection();
		}
	}

	@Override
	public Cliente get(Identificador identificador)
		throws NoSuchRegistroException, IOException {

		return get(identificador.getNumero());
	}

	public Cliente get(String numeroCPF)
		throws NoSuchRegistroException, IOException {

		Cliente cliente = null;

		try {
			Connection connection = MySQLUtil.openConnection();

			PreparedStatement preparedStatement = connection.prepareStatement("SELECT cpf, nome, idade, clienteDesde, renda, sexo FROM cliente WHERE cpf = ?");

			preparedStatement.setString(1, numeroCPF);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String nome = resultSet.getString("nome");
				int idade = resultSet.getInt("idade");
				Date clienteDesde = resultSet.getDate("clienteDesde");
				double renda = resultSet.getDouble("renda");
				int sexo = resultSet.getInt("sexo");

				Cpf cpf = new Cpf(numeroCPF);

				cliente = new Cliente(cpf, nome, idade, renda, sexo);

				cliente.setClienteDesde(clienteDesde);

				break;
			}
		}
		catch (Exception e) {
			e.printStackTrace();

			System.out.println("Ocorreu um erro.");
		}

		if (cliente == null) {
			throw new NoSuchRegistroException();
		}

		return cliente;
	}

	@Override
	public Cliente[] getAll() throws IOException {
		Cliente[] clientes = new Cliente[50];

		try {
			Connection connection = MySQLUtil.openConnection();

			PreparedStatement preparedStatement = connection.prepareStatement("SELECT cpf FROM cliente");

			ResultSet resultSet = preparedStatement.executeQuery();

			int count = 0;

			while (resultSet.next()) {
				String numeroCPF = resultSet.getString("cpf");

				clientes[count++] = get(numeroCPF);
			}
		}
		catch (Exception e) {
			e.printStackTrace();

			System.out.println("Ocorreu um erro.");
		}

		return clientes;
	}

}
