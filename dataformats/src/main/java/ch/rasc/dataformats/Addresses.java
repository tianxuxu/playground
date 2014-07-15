package ch.rasc.dataformats;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Addresses {
	private List<Address> addresses;

	public Addresses() {
	}

	public Addresses(List<Address> items) {
		this.addresses = items;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}


}