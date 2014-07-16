Ext.define('DF.view.main.AddressBaseStore', {
	extend: 'Ext.data.Store',
	model: 'DF.model.Address',
	autoLoad: true,
	pageSize: 0,
	remoteSort: false,
	remoteFilter: false,
	autoSync: false
});