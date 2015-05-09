package ch.rasc.reactorsandbox.quickstart.core;

/**
 * @author Jon Brisbin
 */
public class Order {

	private final Long id;
	private Trade trade;
	private Long timestamp;

	public Order(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public Trade getTrade() {
		return this.trade;
	}

	public Order setTrade(Trade trade) {
		this.trade = trade;
		return this;
	}

	public Long getTimestamp() {
		return this.timestamp;
	}

	public Order setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	@Override
	public String toString() {
		return "Order{" + "id=" + this.id + ", trade=" + this.trade + ", timestamp="
				+ this.timestamp + '}';
	}

}
