Ext.define('DF.Application', {
	extend: 'Ext.app.Application',
	requires: ['DF.data.reader.Cbor'],
	name: 'DF',

	models: [ 'Address' ],

	launch: function() {
		Ext.fly('appLoadingIndicator').destroy();
	}
});
