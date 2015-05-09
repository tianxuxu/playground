package ch.rasc.reactorsandbox.quickstart.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Jon Brisbin
 */
@Entity
public class Client {

	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column
	private Long tradeCount;

	protected Client() {
	}

	public Client(String name) {
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Long getTradeCount() {
		return this.tradeCount;
	}

	public Client setTradeCount(Long tradeCount) {
		this.tradeCount = tradeCount;
		return this;
	}

	@Override
	public String toString() {
		return "Client{" + "id=" + this.id + ", name='" + this.name + '\''
				+ ", tradeCount=" + this.tradeCount + '}';
	}

}
