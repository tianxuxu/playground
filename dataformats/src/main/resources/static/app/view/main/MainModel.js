Ext.define('DF.view.main.MainModel', {
	extend: 'Ext.app.ViewModel',
	requires: ['DF.view.main.AddressBaseStore', 'DF.model.AddressWithMapping'],

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
		addressesJSONARRAY: {			
			xclass: 'DF.view.main.AddressBaseStore',
			model: 'DF.model.AddressWithMapping',			
			proxy: {
				type: 'ajax',
				url: 'addressesArray.json',
				reader: {
					type: 'array'
				}
			}
		},
		addressesXML: {
			xclass: 'DF.view.main.AddressBaseStore',
			autoLoad: true,
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
		addressesCBORARRAY: {
			xclass: 'DF.view.main.AddressBaseStore',
			model: 'DF.model.AddressWithMapping',
			proxy: {
				type: 'ajax',
				binary: true,
				url: 'addressesArray.cbor',
				reader: {
					xclass: 'DF.data.reader.CborArray'
				}
			}
		},		
		addressesMSGPACK: {
			xclass: 'DF.view.main.AddressBaseStore',
			proxy: {
				type: 'ajax',
				binary: true,
				url: 'addresses.msgpack',
				reader: {
					xclass: 'DF.data.reader.Msgpack'
				}
			}
		},		
		addressesMSGPACKARRAY: {
			xclass: 'DF.view.main.AddressBaseStore',
			model: 'DF.model.AddressWithMapping',
			proxy: {
				type: 'ajax',
				binary: true,
				url: 'addressesArray.msgpack',
				reader: {
					xclass: 'DF.data.reader.MsgpackArray'
				}
			}
		}
	}
});