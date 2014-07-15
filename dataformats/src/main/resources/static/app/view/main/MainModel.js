Ext.define('DF.view.main.MainModel', {
	extend: 'Ext.app.ViewModel',
	alias: 'viewmodel.main',

	stores: {
		addressesJson: {
			model: 'DF.model.Address',
			autoLoad: true,
			pageSize: 0,
			remoteSort: false,
			remoteFilter: false,
			autoSync: false,
			proxy: {
				type: 'ajax',
				url: 'addresses.json',
				reader: {
					type: 'json'
				}
			}
		},
		addressesXML: {
			model: 'DF.model.Address',
			autoLoad: true,
			pageSize: 0,
			remoteSort: false,
			remoteFilter: false,
			autoSync: false,
			proxy: {
				type: 'ajax',
				url: 'addresses.xml',
				reader: {
					type: 'xml',
		            record: 'address',
		            rootProperty: 'addresses'
				}
			}
		}
	}
});