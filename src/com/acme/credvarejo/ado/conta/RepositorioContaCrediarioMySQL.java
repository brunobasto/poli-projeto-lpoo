package com.acme.credvarejo.ado.conta;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.acme.credvarejo.ado.cliente.RepositorioClientesMySQL;
import com.acme.credvarejo.ado.util.MySQLUtil;
import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.IdentificadorContaCrediario;

public class RepositorioContaCrediarioMySQL
	implements RepositorioContaCrediario {

	RepositorioClientesMySQL repositorioClientes;

	public RepositorioContaCrediarioMySQL() {
		this.repositorioClientes = new RepositorioClientesMySQL();
	}

	public void add(ContaCrediario contaCrediario) throws IOException {
		Cliente cliente = contaCrediario.getCliente();

		try {
			Connection connection = MySQLUtil.openConnection();

			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO conta_crediario values (default, ?, ?, ?, ? , ?, ?)");

			preparedStatement.setBoolean(1, contaCrediario.isAtiva());
			preparedStatement.setString(2, cliente.getChave());
			preparedStatement.setString(3, contaCrediario.getChave());
			preparedStatement.setDouble(4, contaCrediario.getLimiteDeCredito());
			preparedStatement.setDouble(5, contaCrediario.getSaldoDevido());
			preparedStatement.setInt(6, contaCrediario.getVencimento());

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

	public ContaCrediario get(Identificador identificador)
		throws NoSuchRegistroException, IOException {

		return get(identificador.getNumero());
	}

	public ContaCrediario get(String numeroIdentificador)
		throws NoSuchRegistroException, IOException {

		ContaCrediario contaCrediario = null;

		try {
			Connection connection = MySQLUtil.openConnection();

			PreparedStatement preparedStatement = connection.prepareStatement("SELECT ativa, cliente_cpf, identificador, limite_credito, saldo_devido, vencimento FROM conta_crediario WHERE identificador = ?");

			preparedStatement.setString(1, numeroIdentificador);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				boolean ativa = resultSet.getBoolean("ativa");
				String clienteCPF = resultSet.getString("cliente_cpf");
				double limiteDeCredito = resultSet.getDouble("limite_credito");
				double saldoDevido = resultSet.getDouble("saldo_devido");
				int vencimento = resultSet.getInt("vencimento");

				Cliente cliente = repositorioClientes.get(clienteCPF);

				IdentificadorContaCrediario identificador =
					new IdentificadorContaCrediario(numeroIdentificador);

				contaCrediario = new ContaCrediario(
					identificador, cliente, limiteDeCredito, vencimento);

				if (!ativa) {
					contaCrediario.desativar();
				}

				contaCrediario.setSaldoDevido(saldoDevido);

				break;
			}
		}
		catch (Exception e) {
			System.out.println("Ocorreu um erro.");
		}

		if (contaCrediario == null) {
			throw new NoSuchRegistroException();
		}

		return contaCrediario;
	}

	public ContaCrediario[] getAll()
		throws IOException, NoSuchRegistroException {

		ContaCrediario[] contas = new ContaCrediario[50];

		try {
			Connection connection = MySQLUtil.openConnection();

			PreparedStatement preparedStatement = connection.prepareStatement("SELECT identificador FROM conta_crediario");

			ResultSet resultSet = preparedStatement.executeQuery();

			int count = 0;

			while (resultSet.next()) {
				String numeroIdentificador = resultSet.getString(
					"identificador");

				contas[count++] = get(numeroIdentificador);
			}
		}
		catch (Exception e) {
			System.out.println("Ocorreu um erro.");
		}

		return contas;
	}

	public void remove(Identificador identificador)
		throws NoSuchRegistroException, IOException {

		try {
			Connection connection = MySQLUtil.openConnection();

			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM conta_crediario WHERE conta_crediario.identificador = ?");

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

	public void update(
			Identificador identificador, ContaCrediario contaCrediario)
		throws NoSuchRegistroException, IOException {

		Cliente cliente = contaCrediario.getCliente();

		try {
			// Valida a existencia
			get(identificador);

			Connection connection = MySQLUtil.openConnection();

			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE conta_crediario SET ativa=?, cliente_cpf=?, identificador=?, limite_credito=?, saldo_devido=?, vencimento=? WHERE conta_crediario.identificador = ?");

			preparedStatement.setBoolean(1, contaCrediario.isAtiva());
			preparedStatement.setString(2, cliente.getChave());
			preparedStatement.setString(3, contaCrediario.getChave());
			preparedStatement.setDouble(4, contaCrediario.getLimiteDeCredito());
			preparedStatement.setDouble(5, contaCrediario.getSaldoDevido());
			preparedStatement.setInt(6, contaCrediario.getVencimento());

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

}
