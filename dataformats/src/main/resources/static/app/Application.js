Ext.define('DF.Application', {
	extend: 'Ext.app.Application',
	name: 'DF',

	models: [ 'Address' ],

	launch: function() {
		Ext.fly('appLoadingIndicator').destroy();
	}
});
