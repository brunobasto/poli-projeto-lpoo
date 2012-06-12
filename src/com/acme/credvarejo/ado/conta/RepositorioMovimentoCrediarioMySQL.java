package com.acme.credvarejo.ado.conta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.acme.credvarejo.ado.util.MySQLUtil;
import com.acme.credvarejo.classesGerais.Identificador;
import com.acme.credvarejo.classesGerais.Registro;
import com.acme.credvarejo.classesGerais.RepositorioIdentificaveisArray;
import com.acme.credvarejo.classesGerais.RepositorioIdentificaveisMySQL;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.MovimentoCrediario;
import com.acme.credvarejo.conta.MovimentoCrediarioCredito;
import com.acme.credvarejo.conta.MovimentoCrediarioDebito;

public class RepositorioMovimentoCrediarioMySQL
	implements RepositorioMovimentoCrediario {

	private RepositorioContaCrediarioMySQL repositorioContaCrediario;

	public RepositorioMovimentoCrediarioMySQL() {
		this.repositorioContaCrediario = new RepositorioContaCrediarioMySQL();
	}

	public void add(MovimentoCrediario movimentoCrediario) {
		ContaCrediario conta = movimentoCrediario.getConta();

		try {
			Connection connection = MySQLUtil.openConnection();

			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO movimento_crediario values (default, ?, ?, ?, ?)");

			preparedStatement.setString(1, conta.getChave());
			preparedStatement.setDouble(2, movimentoCrediario.getValor());
			preparedStatement.setDate(
				3, new java.sql.Date(movimentoCrediario.getData().getTime()));

			int tipo = 0;

			if (movimentoCrediario instanceof MovimentoCrediarioCredito) {
				tipo = 1;
			}

			preparedStatement.setInt(1, tipo);

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

	public MovimentoCrediario[] getAll() {
		MovimentoCrediario[] movimentos = new MovimentoCrediario[50];

		try {
			Connection connection = MySQLUtil.openConnection();

			PreparedStatement preparedStatement = connection.prepareStatement("SELECT conta_identificador, valor, data FROM movimento_crediario");

			ResultSet resultSet = preparedStatement.executeQuery();

			int count = 0;

			while (resultSet.next()) {
				String identificadorConta = resultSet.getString(
					"conta_identificador");
				double valor = resultSet.getDouble("valor");
				Date data = resultSet.getDate("data");
				int tipo = resultSet.getInt("tipo");

				ContaCrediario conta = repositorioContaCrediario.get(
					identificadorConta);

				MovimentoCrediario movimento = null;

				if (tipo == 1) {
					movimento = new MovimentoCrediarioCredito(
						conta, valor, data);
				}
				else {
					movimento = new MovimentoCrediarioDebito(
						conta, valor, data);
				}

				movimentos[count++] = movimento;
			}
		}
		catch (Exception e) {
			System.out.println("Ocorreu um erro.");
		}

		return movimentos;
	}

	public MovimentoCrediario[] search(Identificador identificador) {
		ArrayList<MovimentoCrediario> movimentos =
			new ArrayList<MovimentoCrediario>();

		String numero = identificador.getNumero();

		for (MovimentoCrediario movimentoCrediario : getAll()) {
			ContaCrediario conta = movimentoCrediario.getConta();

			if (conta.getChave().equals(numero)) {
				movimentos.add(movimentoCrediario);
			}
		}

		return movimentos.toArray(new MovimentoCrediario[] {});
	}

}
