(function() {

var CACHE = {};

LPOO = $.klass(
	{
		init: function(config) {
			var instance = this;

			$.each(
				config,
				function(key, value) {
					instance.set(key, value);
				}
			);
		},

		set: function(key, value) {
			var instance = this;

			instance[key] = value;
		},

		get: function(key) {
			var instance = this;

			return instance[key];
		},

		getCurrentPage: function() {
			var instance = this;

			return $('.ui-page-active').attr('id');
		},

		showLoading: function() {
			var instance = this;

			$.mobile.showPageLoadingMsg();
		},

		showMessage: function(message) {
			var instance = this;

			$('.ui-page-active').simpledialog(
				{
					mode: 'bool',
					prompt: message,
					useModal: true,
					buttons: {
						'Ok': {
							click: $.proxy(instance.hideMessage, instance),
							icon: 'ok'
						}
					}
				}
			);
		},

		hideLoading: function() {
			var instance = this;

			$.mobile.hidePageLoadingMsg();
		},

		hideMessage: function() {
			var instance = this;

			$('.ui-page-active').simpledialog('close');
		},

		namespace: function(namespace) {
			var levels = namespace.split(".");
			var curLevel = LPOO;

			var i, length = levels.length;

			for (i = (levels[0] === "LPOO") ? 1 : 0; i < length; i++) {
				curLevel[levels[i]] = curLevel[levels[i]] || {};
				curLevel = curLevel[levels[i]];
			}

			return curLevel;
		},

		getFormData: function(form) {
			var instance = this;

			var data = {};

			$.each(
				form.serializeArray(),
				function(index, item) {
					data[item.name] = item.value;
				}
			);

			return data;
		},

		request: function(url, params, successFn, errorFn, cache) {
			var instance = this;

			var ajaxParams = {
				cache: cache,
				crossDomain: true,
				data: params,
				dataType: 'jsonp',
				url: url,
				timeout: 5000,
				success: function(response) {
					if (response && !response.error && successFn) {
						successFn.apply(instance, [response]);

						if (cache) {
							CACHE[url + instance.serialize(params)] = response;
						}
					}
					else if (response && response.error) {
						var error = response.error;
						var message = error.message;

						instance.showMessage(message);

						if (errorFn) {
							errorFn.apply(instance, [error]);
						}
					}
					else {
						console.log('An unexpected error occurred with the response:', response);
					}
				},
				error: function(response, status, error) {
					alert('error');

					instance.showMessage('An error occurred during your request. Please try again later.');

					if (errorFn) {
						errorFn.apply(instance, [error]);
					}
				}
			};

			var sparams = instance.serialize(params);

			if (cache && CACHE[url + sparams]) {
				ajaxParams.success.apply(instance, [CACHE[url + sparams]]);
			} else {
				$.ajax(ajaxParams);
			}
		},

		serialize: function(hash, glue) {
			var str = [], i;

			for (i in hash) {
				if (hash.hasOwnProperty(i)) {
					str.push(i + '=' + hash[i]);
				}
			}

			str.sort();

			return str.join(glue || '&');
		}
	}
);

}());