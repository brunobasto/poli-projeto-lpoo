package com.acme.credvarejo.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.acme.credvarejo.ado.cliente.RepositorioClientesMySQL;
import com.acme.credvarejo.ado.conta.RepositorioContaCrediarioMySQL;
import com.acme.credvarejo.ado.conta.RepositorioMovimentoCrediarioMySQL;
import com.acme.credvarejo.classesGerais.exceptions.NoSuchRegistroException;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.IdentificadorContaCrediario;
import com.acme.credvarejo.conta.MovimentoCrediario;
import com.acme.credvarejo.conta.MovimentoCrediarioCredito;
import com.acme.credvarejo.conta.MovimentoCrediarioDebito;
import com.acme.credvarejo.conta.exceptions.ContaCrediarioException;

public class JSONAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static double _getDouble(HttpServletRequest request, String param) {
		double value = 0;

		try {
			value = Double.parseDouble(request.getParameter(param));
		}
		catch (Exception e) {
		}

		return value;
	}

	private static int _getInt(HttpServletRequest request, String param) {
		int value = 0;

		try {
			value = Integer.parseInt(request.getParameter(param));
		}
		catch (Exception e) {
		}

		return value;
	}

	private static Cpf _getCpf(HttpServletRequest request, String param) {
		Cpf cpf = null;

		try {
			String paramValue = request.getParameter(param);

			if ((paramValue != null) && paramValue.length() == 11) {
				String numero = paramValue.substring(0, 9);
				String digito = paramValue.substring(10);

				cpf = new Cpf(numero, digito);
			}
		}
		catch (Exception e) {
		}

		return cpf;
	}

	private static String _getUniqueID() {
		long time = (new Date()).getTime();

		return String.valueOf(time);
	}

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		String cmd = request.getParameter("cmd");
		String callback = request.getParameter("callback");

		RepositorioClientesMySQL repositorioCliente =
			new RepositorioClientesMySQL();
		RepositorioContaCrediarioMySQL repositorioContaCrediarioMySQL =
			new RepositorioContaCrediarioMySQL();
		RepositorioMovimentoCrediarioMySQL repositorioMovimentoCrediarioMySQL =
			new RepositorioMovimentoCrediarioMySQL();

		if (cmd.equals("cadastrarCliente")) {
			JSONObject clienteJSONObject = new JSONObject();

			try {
				Cpf cpf = _getCpf(request, "cpf");
				String nome = request.getParameter("nome");
				int idade = _getInt(request, "idade");
				double renda = _getDouble(request, "renda");
				int sexo = _getInt(request, "sexo");
				double limiteDeCredito = _getDouble(request, "limiteDeCredito");
				int vencimento = _getInt(request, "vencimento");

				Cliente cliente = new Cliente(cpf, nome, idade, renda, sexo);

				IdentificadorContaCrediario identificador =
					new IdentificadorContaCrediario(_getUniqueID());

				ContaCrediario contaCrediario = new ContaCrediario(identificador, cliente, limiteDeCredito, vencimento);

				repositorioCliente.add(cliente);
				repositorioContaCrediarioMySQL.add(contaCrediario);

				clienteJSONObject = getClienteJSONObject(cliente);
			}
			catch (JSONException e) {
				e.printStackTrace();
			}

			PrintWriter writer = response.getWriter();
			writer.print(callback + "(" + clienteJSONObject.toString() + ")");
		}
		else if (cmd.equals("alterarCadastroCliente")) {
			JSONObject clienteJSONObject = new JSONObject();

			try {
				Cpf cpf = _getCpf(request, "cpf");
				String nome = request.getParameter("nome");
				int idade = _getInt(request, "idade");
				double renda = _getDouble(request, "renda");
				int sexo = _getInt(request, "sexo");
				double limiteDeCredito = _getDouble(request, "limiteDeCredito");
				int vencimento = _getInt(request, "vencimento");

				Cliente cliente = new Cliente(cpf, nome, idade, renda, sexo);

				IdentificadorContaCrediario identificador =
					new IdentificadorContaCrediario(_getUniqueID());

				ContaCrediario contaCrediario = new ContaCrediario(identificador, cliente, limiteDeCredito, vencimento);

				repositorioCliente.update(cpf, cliente);
				repositorioContaCrediarioMySQL.update(identificador, contaCrediario);

				clienteJSONObject = getClienteJSONObject(cliente);
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
			catch (NoSuchRegistroException e) {
				try {
					clienteJSONObject.put("error", true);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				e.printStackTrace();
			}

			PrintWriter writer = response.getWriter();
			writer.print(callback + "(" + clienteJSONObject.toString() + ")");
		}
		else if (cmd.equals("excluirCliente")) {
			JSONObject reponseJSONObject = new JSONObject();

			try {
				Cpf cpf = _getCpf(request, "cpf");

				repositorioCliente.remove(cpf);

				for (ContaCrediario conta : repositorioContaCrediarioMySQL.getAll()) {
					if (conta != null) {
						if (conta.getCliente().getChave().equals(cpf.getNumero())) {
							repositorioContaCrediarioMySQL.remove(conta.getIdentificador());
						}
					}
				}
			}
			catch (NoSuchRegistroException e) {
				try {
					reponseJSONObject.put("error", true);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				e.printStackTrace();
			}

			PrintWriter writer = response.getWriter();
			writer.print(callback + "(" + reponseJSONObject.toString() + ")");
		}
		else if (cmd.equals("buscarCliente")) {
			JSONObject clienteJSONObject = new JSONObject();

			try {
				Cpf cpf = _getCpf(request, "cpf");

				clienteJSONObject = getClienteJSONObject(repositorioCliente.get(cpf));
			}
			catch (NoSuchRegistroException e) {
				try {
					clienteJSONObject.put("error", true);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				e.printStackTrace();
			}
			catch (JSONException e) {
				e.printStackTrace();
			}

			PrintWriter writer = response.getWriter();
			writer.print(callback + "(" + clienteJSONObject.toString() + ")");
		}
		else if (cmd.equals("mostrarExtratoCliente")) {
			JSONObject clienteJSONObject = new JSONObject();

			try {
				Cpf cpf = _getCpf(request, "cpf");

				clienteJSONObject = getClienteJSONObject(repositorioCliente.get(cpf));
			}
			catch (NoSuchRegistroException e) {
				try {
					clienteJSONObject.put("error", true);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				e.printStackTrace();
			}
			catch (JSONException e) {
				e.printStackTrace();
			}

			PrintWriter writer = response.getWriter();
			writer.print(callback + "(" + clienteJSONObject.toString() + ")");
		}
		else if (cmd.equals("creditar")) {
			JSONObject movimentoJSONObject = new JSONObject();

			try {
				String numeroIdentificador = request.getParameter(
					"numeroIdentificador");
				double valor = _getDouble(request, "valor");

				IdentificadorContaCrediario identificador =
					new IdentificadorContaCrediario(numeroIdentificador);

				ContaCrediario contaCrediario =
					repositorioContaCrediarioMySQL.get(numeroIdentificador);

				contaCrediario.efetuarPagamento(valor);

				MovimentoCrediario movimento = new MovimentoCrediarioCredito(
					contaCrediario, valor, new Date());

				movimento.validar();

				repositorioMovimentoCrediarioMySQL.add(movimento);

				repositorioContaCrediarioMySQL.update(identificador, contaCrediario);

				movimentoJSONObject = getMovimentoCrediarioJSONObject(
					movimento);
			}
			catch (NoSuchRegistroException e) {
				try {
					movimentoJSONObject.put("error", true);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				e.printStackTrace();
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
			catch (ContaCrediarioException e) {
				e.printStackTrace();
			}

			PrintWriter writer = response.getWriter();
			writer.print(callback + "(" + movimentoJSONObject.toString() + ")");
		}
		else if (cmd.equals("debitar")) {
			JSONObject movimentoJSONObject = new JSONObject();

			try {
				String numeroIdentificador = request.getParameter(
					"numeroIdentificador");
				double valor = _getDouble(request, "valor");

				IdentificadorContaCrediario identificador =
					new IdentificadorContaCrediario(numeroIdentificador);

				ContaCrediario contaCrediario =
					repositorioContaCrediarioMySQL.get(numeroIdentificador);

				contaCrediario.efetuarCompra(valor);

				MovimentoCrediario movimento = new MovimentoCrediarioDebito(
					contaCrediario, valor, new Date());

				movimento.validar();

				repositorioMovimentoCrediarioMySQL.add(movimento);

				repositorioContaCrediarioMySQL.update(identificador, contaCrediario);

				movimentoJSONObject = getMovimentoCrediarioJSONObject(
					movimento);
			}
			catch (NoSuchRegistroException e) {
				try {
					movimentoJSONObject.put("error", true);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				e.printStackTrace();
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
			catch (ContaCrediarioException e) {
				e.printStackTrace();
			}

			PrintWriter writer = response.getWriter();
			writer.print(callback + "(" + movimentoJSONObject.toString() + ")");
		}
		else if (cmd.equals("transferir")) {
			JSONObject movimentoJSONObject = new JSONObject();

			try {
				String numeroIdentificadorDebito = request.getParameter(
					"numeroIdentificadorDebito");
				String numeroIdentificadorCredito = request.getParameter(
					"numeroIdentificadorCredito");
				double valor = _getDouble(request, "valor");

				IdentificadorContaCrediario identificadorDebito =
					new IdentificadorContaCrediario(numeroIdentificadorDebito);

				IdentificadorContaCrediario identificadorCredito =
					new IdentificadorContaCrediario(numeroIdentificadorCredito);

				ContaCrediario contaCrediarioDebito =
					repositorioContaCrediarioMySQL.get(
						numeroIdentificadorDebito);

				contaCrediarioDebito.efetuarCompra(valor);

				ContaCrediario contaCrediarioCredito =
					repositorioContaCrediarioMySQL.get(
						numeroIdentificadorCredito);

				contaCrediarioCredito.efetuarPagamento(valor);

				MovimentoCrediario movimentoDebito = new MovimentoCrediarioDebito(
					contaCrediarioDebito, valor, new Date());

				movimentoDebito.validar();

				repositorioMovimentoCrediarioMySQL.add(movimentoDebito);

				MovimentoCrediario movimentoCredito =
					new MovimentoCrediarioCredito(
						contaCrediarioDebito, valor, new Date());

				movimentoCredito.validar();

				repositorioMovimentoCrediarioMySQL.add(movimentoCredito);

				repositorioContaCrediarioMySQL.update(
					identificadorDebito, contaCrediarioDebito);
				repositorioContaCrediarioMySQL.update(
					identificadorCredito, contaCrediarioCredito);

				movimentoJSONObject = getMovimentoCrediarioJSONObject(
					movimentoCredito);
			}
			catch (NoSuchRegistroException e) {
				try {
					movimentoJSONObject.put("error", true);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				e.printStackTrace();
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
			catch (ContaCrediarioException e) {
				e.printStackTrace();
			}

			PrintWriter writer = response.getWriter();
			writer.print(callback + "(" + movimentoJSONObject.toString() + ")");
		}
		else if (cmd.equals("mostrarTodosClientes")) {
			Cliente[] clientes = repositorioCliente.getAll();

			JSONArray jsonArray = new JSONArray();

			try {
				for (Cliente cliente : clientes) {
					if (cliente == null) {
						continue;
					}

					jsonArray.put(getClienteJSONObject(cliente));
				}
			}
			catch (JSONException e) {
				e.printStackTrace();
			}

			PrintWriter writer = response.getWriter();
			writer.print(callback + "(" + jsonArray.toString() + ")");
		}
		else {

		}
	}

	private JSONObject getClienteJSONObject(Cliente cliente)
		throws JSONException {

		JSONObject clienteJSONObject = new JSONObject();

		clienteJSONObject.put("cpf", cliente.getChave());
		clienteJSONObject.put("nome", cliente.getNome());
		clienteJSONObject.put("idade", cliente.getIdade());
		clienteJSONObject.put("clienteDesde", cliente.getClienteDesde());
		clienteJSONObject.put("renda", cliente.getRenda());
		clienteJSONObject.put("sexo", cliente.getSexo());

		ContaCrediario conta = getContaCliente(cliente);

		String numeroConta = "Sem conta";

		if (conta != null) {
			numeroConta = conta.getChave();
		}

		clienteJSONObject.put("conta", numeroConta);

		return clienteJSONObject;
	}

	private ContaCrediario getContaCliente(Cliente cliente) {
		RepositorioContaCrediarioMySQL repositorioContaCrediarioMySQL =
			new RepositorioContaCrediarioMySQL();

		try {
			for (ContaCrediario conta : repositorioContaCrediarioMySQL.getAll()) {
				if ((conta != null) &&
					conta.getCliente().getChave().equals(cliente.getChave())) {

					return conta;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchRegistroException e) {
			e.printStackTrace();
		}

		return null;
	}

	private JSONObject getContaCrediarioJSONObject(
			ContaCrediario contaCrediario)
		throws JSONException {

		JSONObject contaCrediarioJSONObject = new JSONObject();

		contaCrediarioJSONObject.put("ativa", contaCrediario.isAtiva());
		contaCrediarioJSONObject.put(
			"cliente", getClienteJSONObject(contaCrediario.getCliente()));
		contaCrediarioJSONObject.put(
			"identificador", contaCrediario.getChave());
		contaCrediarioJSONObject.put(
			"limiteCredito", contaCrediario.getLimiteDeCredito());
		contaCrediarioJSONObject.put(
			"saldoDevido", contaCrediario.getSaldoDevido());
		contaCrediarioJSONObject.put(
			"vencimento", contaCrediario.getSaldoDevido());

		return contaCrediarioJSONObject;
	}

	private JSONObject getMovimentoCrediarioJSONObject(
			MovimentoCrediario movimentoCrediario)
		throws JSONException {

		JSONObject movimentoCrediarioJSONObject = new JSONObject();

		movimentoCrediarioJSONObject.put(
			"conta", getContaCrediarioJSONObject(
				movimentoCrediario.getConta()));
		movimentoCrediarioJSONObject.put("data", movimentoCrediario.getData());
		movimentoCrediarioJSONObject.put(
			"valor", movimentoCrediario.getValor());

		return movimentoCrediarioJSONObject;
	}

}
