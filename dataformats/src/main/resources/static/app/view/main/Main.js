Ext.define('DF.view.main.Main', {
	extend: 'Ext.container.Container',
	requires: [ 'DF.view.main.MainController', 'DF.view.main.MainModel' ],
	controller: 'main',
	viewModel: {
		type: 'main'
	},

	layout: 'fit',

	items: [ {
		xtype: 'grid',
		bind: '{addressesXML}',
		columns: [ {
			dataIndex: 'firstName',
			text: 'First Name',
			flex: 1
		} ]
	} ]

});
