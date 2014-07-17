Ext.define('DF.view.main.Main', {
	extend: 'Ext.tab.Panel',
	requires: [ 'DF.view.main.MainController', 'DF.view.main.MainModel',
			'DF.view.main.AddressGrid' ],

	controller: {
		xclass: 'DF.view.main.MainController',
	},

	viewModel: {
		xclass: 'DF.view.main.MainModel'
	},

	listeners: {
		beforetabchange: 'onBeforeTabChange'
	},

	items: [ {
		title: 'XML',
		xclass: 'DF.view.main.AddressGrid',
		bind: '{addressesXML}'
	}, {
		title: 'JSON',
		xclass: 'DF.view.main.AddressGrid',
		bind: '{addressesJSON}'
	}, {
		title: 'JSON Array',
		xclass: 'DF.view.main.AddressGrid',
		bind: '{addressesJSONARRAY}'
	}, {
		title: 'CBOR',
		xclass: 'DF.view.main.AddressGrid',
		bind: '{addressesCBOR}'
	}, {
		title: 'CBOR Array',
		xclass: 'DF.view.main.AddressGrid',
		bind: '{addressesCBORARRAY}'
	}, {
		title: 'MessagePack',
		xclass: 'DF.view.main.AddressGrid',
		bind: '{addressesMSGPACK}'
	}, {
		title: 'MessagePack Array',
		xclass: 'DF.view.main.AddressGrid',
		bind: '{addressesMSGPACKARRAY}'
	} ]

});
