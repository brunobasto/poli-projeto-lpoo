package com.acme.credvarejo.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.acme.credvarejo.ado.cliente.RepositorioClientesMySQL;
import com.acme.credvarejo.cliente.Cliente;
import com.acme.credvarejo.cliente.Cpf;
import com.acme.credvarejo.conta.ContaCrediario;
import com.acme.credvarejo.conta.MovimentoCrediario;

public class JSONAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		String cmd = request.getParameter("cmd");
		String callback = request.getParameter("callback");

		if (cmd.equals("cadastrarCliente")) {
			JSONObject test = new JSONObject();

			try {
				test.put("success", true);
			}
			catch (JSONException e) {
				e.printStackTrace();
			}

			PrintWriter writer = response.getWriter();
			writer.print(callback + "(" + test.toString() + ")");
		}
		else {
			RepositorioClientesMySQL repo = new RepositorioClientesMySQL();

			Cpf cpf = new Cpf("05437707452");

			Cliente cliente = new Cliente(cpf, "Bruno Basto", 23, 3500, 0);

			repo.add(cliente);

			JSONArray jsonArray = new JSONArray();

			Cliente[] clientes = repo.getAll();

			for (Cliente cliente2 : clientes) {
				if (cliente2 == null) {
					continue;
				}

				try {
					jsonArray.put(getClienteJSONObject(cliente2));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");

		    PrintWriter writer = response.getWriter();
		    writer.print(jsonArray.toString());
		}
	}

	private JSONObject getClienteJSONObject(Cliente cliente)
		throws JSONException {

		JSONObject clienteJSONObject = new JSONObject();

		clienteJSONObject.put("cpf", getCpfJSONObject(cliente.getCpf()));
		clienteJSONObject.put("nome", cliente.getNome());
		clienteJSONObject.put("idade", cliente.getIdade());
		clienteJSONObject.put("clienteDesde", cliente.getClienteDesde());
		clienteJSONObject.put("renda", cliente.getRenda());
		clienteJSONObject.put("sexo", cliente.getSexo());

		return clienteJSONObject;
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

	private JSONObject getCpfJSONObject(Cpf cpf) throws JSONException {
		JSONObject cpfJSONObject = new JSONObject();

		cpfJSONObject.put("numero", cpf.getNumero());
		cpfJSONObject.put("digito", cpf.calcularDigito());

		return cpfJSONObject;
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
