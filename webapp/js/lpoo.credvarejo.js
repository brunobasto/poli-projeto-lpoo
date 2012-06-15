(function() {

var _STR_BLANK = '';

var _TPL_CLIENTE = '<li><h3>${nome}</h3><p>CPF: ${cpf}</p><p>Conta: ${conta}</p></li>';

LPOO.CredVarejo = $.klass(
	LPOO,
	{
		currentPlayList: [],

		init: function(config) {
			var instance = this;

			instance._super(arguments, config);

			instance.clientesList = $('#clientesList');
			instance.menuList = $('#menuList');

			// Forms

			instance.alterarCadastroClienteForm = $('#alterarCadastroClienteForm');
			instance.buscarClienteForm = $('#buscarClienteForm');
			instance.cadastroClienteForm = $('#cadastroClienteForm');
			instance.creditarForm = $('#creditarForm');
			instance.debitarForm = $('#debitarForm');
			instance.excluirClienteForm = $('#excluirClienteForm');
			instance.mostrarExtratoClienteForm = $('#mostrarExtratoClienteForm');
			instance.transferirForm = $('#transferirForm');

			instance.renderUI();
			instance.bindUI();
		},

		bindUI: function() {
			var instance = this;

			$('#buscarCliente').bind('click', $.proxy(instance._onClickBuscarCliente, instance));
			$('#creditar').bind('click', $.proxy(instance._onClickCreditar, instance));
			$('#debitar').bind('click', $.proxy(instance._onClickDebitar, instance));
			$('#excluirCliente').bind('click', $.proxy(instance._onClickExcluirCliente, instance));
			$('#mostrarExtratoCliente').bind('click', $.proxy(instance._onClickMostrarExtratoCliente, instance));
			$('#salvarAlterarCadastroCliente').bind('click', $.proxy(instance._onClickSalvarAlterarCadastroCliente, instance));
			$('#salvarCadastroCliente').bind('click', $.proxy(instance._onClickSalvarCadastroCliente, instance));
			$('#transferir').bind('click', $.proxy(instance._onClickTransferir, instance));

			$(document).delegate('#mostrarTodosClientesView', 'pageshow', function() {
				instance._renderClientesList();
			});

			instance.menuList.delegate('li a','tap', $.proxy(instance._onClickMenuItem, instance));
		},

		renderUI: function() {
			var instance = this;

			// Compile Templates

			$.template('cliente', _TPL_CLIENTE);
		},

		_onClickMenuItem: function(event) {
			var instance = this;

			var target = $(event.target);
			var view = target.attr('data-action');

			$.mobile.changePage(
				'#' + LPOO.CredVarejo.viewMap[view],
				{
					transition: 'slide'
				}
			);
		},

		_onClickMostrarExtratoCliente: function() {
			var instance = this;

			var url = instance.get('url');

			var form = instance.alterarCadastroClienteForm;
			var data = instance.getFormData(form);

			data.cmd = 'alterarCadastroCliente';

			instance.showLoading();

			instance.request(
				url,
				data,
				function(response) {
					console.log(response);

					instance.hideLoading();
				},
				function() {
					instance.hideLoading();
				}
			);
		},

		_onClickBuscarCliente: function() {
			var instance = this;

			var url = instance.get('url');

			var form = instance.alterarCadastroClienteForm;
			var data = instance.getFormData(form);

			data.cmd = 'alterarCadastroCliente';

			instance.showLoading();

			instance.request(
				url,
				data,
				function(response) {
					instance.hideLoading();

					if (response.nome) {
						instance.showMessage('Cliente encontrado: ' + response.nome);
					}
					else {
						instance.showMessage('Cliente não encontrado.');
					}
				},
				function() {
					instance.hideLoading();
				}
			);
		},

		_onClickExcluirCliente: function() {
			var instance = this;

			var url = instance.get('url');

			var form = instance.excluirClienteForm;
			var data = instance.getFormData(form);

			data.cmd = 'excluirCliente';

			instance.showLoading();

			instance.request(
				url,
				data,
				function(response) {
					instance.hideLoading();

					$.mobile.changePage(
						'#' + LPOO.CredVarejo.viewMap['main'],
						{
							transition: 'slide'
						}
					);

					instance.showMessage('Cliente excluido com sucesso');
				},
				function() {
					instance.hideLoading();
				}
			);
		},

		_onClickCreditar: function() {
			var instance = this;

			var url = instance.get('url');

			var form = instance.creditarForm;
			var data = instance.getFormData(form);

			data.cmd = 'creditar';

			instance.showLoading();

			instance.request(
				url,
				data,
				function(response) {
					instance.hideLoading();

					$.mobile.changePage(
						'#' + LPOO.CredVarejo.viewMap['main'],
						{
							transition: 'slide'
						}
					);

					instance.showMessage('Crédito realizado com sucesso.');
				},
				function() {
					instance.hideLoading();
				}
			);
		},

		_onClickDebitar: function() {
			var instance = this;

			var url = instance.get('url');

			var form = instance.debitarForm;
			var data = instance.getFormData(form);

			data.cmd = 'debitar';

			instance.showLoading();

			instance.request(
				url,
				data,
				function(response) {
					instance.hideLoading();

					$.mobile.changePage(
						'#' + LPOO.CredVarejo.viewMap['main'],
						{
							transition: 'slide'
						}
					);

					instance.showMessage('Débito realizado com sucesso.');
				},
				function() {
					instance.hideLoading();
				}
			);
		},

		_onClickTransferir: function() {
			var instance = this;

			var url = instance.get('url');

			var form = instance.transferirForm;
			var data = instance.getFormData(form);

			data.cmd = 'transferir';

			instance.showLoading();

			instance.request(
				url,
				data,
				function(response) {
					instance.hideLoading();

					$.mobile.changePage(
						'#' + LPOO.CredVarejo.viewMap['main'],
						{
							transition: 'slide'
						}
					);

					instance.showMessage('Transferencia feita com sucesso.');
				},
				function() {
					instance.hideLoading();
				}
			);
		},

		_onClickSalvarAlterarCadastroCliente: function() {
			var instance = this;

			var url = instance.get('url');

			var form = instance.alterarCadastroClienteForm;
			var data = instance.getFormData(form);

			data.cmd = 'alterarCadastroCliente';

			instance.showLoading();

			instance.request(
				url,
				data,
				function(response) {
					instance.hideLoading();

					$.mobile.changePage(
						'#' + LPOO.CredVarejo.viewMap['main'],
						{
							transition: 'slide'
						}
					);

					instance.showMessage('Cliente salvo com sucesso');
				},
				function() {
					instance.hideLoading();
				}
			);
		},

		_onClickSalvarCadastroCliente: function() {
			var instance = this;

			var url = instance.get('url');

			var form = instance.cadastroClienteForm;
			var data = instance.getFormData(form);

			data.cmd = 'cadastrarCliente';

			instance.showLoading();

			instance.request(
				url,
				data,
				function(response) {
					instance.hideLoading();

					$.mobile.changePage(
						'#' + LPOO.CredVarejo.viewMap['main'],
						{
							transition: 'slide'
						}
					);

					instance.showMessage('Cliente salvo com sucesso');
				},
				function() {
					instance.hideLoading();
				}
			);
		},

		_renderClientesList: function() {
			var instance = this;

			var url = instance.get('url');

			var data = {
				cmd: 'mostrarTodosClientes'
			};

			instance.showLoading();

			instance.request(
				url,
				data,
				function(clientes) {
					var clientesList = instance.clientesList;

					$.tmpl('cliente', clientes).appendTo(clientesList.empty());

					clientesList.listview('refresh');

					instance.hideLoading();
				},
				function() {
					instance.hideLoading();
				}
			);
		}
	}
);

LPOO.CredVarejo.viewMap = {
	'main': 'mainView',
	0: 'cadastroClienteView',
	1: 'alterarCadastroClienteView',
	2: 'excluirClienteView',
	3: 'mostrarTodosClientesView',
	4: 'buscarClienteView',
	5: 'creditarView',
	6: 'debitarView',
	7: 'transferirView',
	8: 'mostrarExtratoClienteView'
};

}());