Ext.define('DF.view.main.Main', {
	extend: 'Ext.container.Container',
	requires: [ 'DF.view.main.MainController', 'DF.view.main.MainModel', 'DF.view.main.AddressGrid' ],
	
	controller: {
		xclass: 'DF.view.main.MainController',
	},
	
	viewModel: {
		xclass: 'DF.view.main.MainModel'
	},

	layout: 'fit',

	items: [ {
		xtype: 'tabpanel',
		items: [ {
			title: 'XML',
			xclass: 'DF.view.main.AddressGrid',
			bind: '{addressesXML}'
		}, {
			title: 'JSON',
			xclass: 'DF.view.main.AddressGrid',
			bind: '{addressesJSON}'
		}, {
			title: 'JSON-ARRAY',
			xclass: 'DF.view.main.AddressGrid',
			bind: '{addressesJSONARRAY}'
		}, {
			title: 'CBOR',
			xclass: 'DF.view.main.AddressGrid',
			bind: '{addressesCBOR}'
		} ]
	} ]

});
