(function() {

var _STR_BLANK = '';

var _TPL_PLAYER = '<li><a data-player="${player}" href="#">${player}</a></li>';

LPOO.CredVarejo = $.klass(
	LPOO,
	{
		currentPlayList: [],

		init: function(config) {
			var instance = this;

			instance._super(arguments, config);

			instance.menuList = $('#menuList');
			instance.cadastroClienteForm = $('#cadastroClienteForm');

			instance.renderUI();
			instance.bindUI();
		},

		bindUI: function() {
			var instance = this;

			$('#salvarCadastroCliente').bind('click', $.proxy(instance._onClickSalvarCadastroCliente, instance));

			instance.menuList.delegate('li a','tap', $.proxy(instance._onClickMenuItem, instance));
		},

		renderUI: function() {
			var instance = this;

			instance.connectionForm = $('#connectionForm');

			// Compile Templates

			$.template('player', _TPL_PLAYER);
		},

		_addToPlaylist: function(song) {
			var instance = this;

			instance.currentPlayList.push(song);
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

		_onClickConnect: function(event) {
			var instance = this;

			var form = instance.connectionForm;
			var data = instance.getFormData(form);

			var ip = data.ip;
			var remember = data.remember;

			if (ip === _STR_BLANK) {
				instance.showMessage('Please enter a valid IP Address');

				return false;
			}

			var port = instance.get('port');
			var baseURL = 'http://' + ip + ':' + port + '/player/';

			instance.set('baseURL', baseURL);

			var url = baseURL + 'get_players';

			instance.showLoading();

			instance.request(
				url,
				{},
				function(response) {
					instance._renderPlayers(response);

					instance.hideLoading();
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

		_renderAlbum: function(songs) {
			var instance = this;

			var songsList = instance.songsList;

			$.tmpl('song', songs).appendTo(songsList.empty());

			$.mobile.changePage(
				'#songsView',
				{
					transition: 'slide'
				}
			);

			songsList.listview('refresh');
		}
	}
);

LPOO.CredVarejo.viewMap = {
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