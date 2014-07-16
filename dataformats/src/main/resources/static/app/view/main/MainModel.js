Ext.define('DF.view.main.MainModel', {
	extend: 'Ext.app.ViewModel',
	requires: ['DF.view.main.AddressBaseStore'],

	stores: {
		addressesJSON: {
			xclass: 'DF.view.main.AddressBaseStore',
			proxy: {
				type: 'ajax',
				url: 'addresses.json',
				reader: {
					type: 'json'
				}
			}
		},
		addressesXML: {
			xclass: 'DF.view.main.AddressBaseStore',
			proxy: {
				type: 'ajax',
				url: 'addresses.xml',
				reader: {
					type: 'xml',
					record: 'address',
					rootProperty: 'addresses'
				}
			}
		},
		addressesCBOR: {
			xclass: 'DF.view.main.AddressBaseStore',
			proxy: {
				type: 'ajax',
				binary: true,
				url: 'addresses.cbor',
				reader: {
					xclass: 'DF.data.reader.Cbor'
				}
			}
		},
	}
});