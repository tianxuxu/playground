package ch.rasc.reactorsandbox.quickstart.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.rasc.reactorsandbox.quickstart.core.TradeServer;
import ch.rasc.reactorsandbox.quickstart.spring.domain.Client;
import ch.rasc.reactorsandbox.quickstart.spring.repository.ClientRepository;
import reactor.bus.Event;
import reactor.bus.EventBus;

/**
 * @author Jon Brisbin
 */
@RestController
public class TradeController {

	private final ClientRepository clients;
	private final EventBus eventBus;
	private final TradeServer tradeServer;

	@Autowired
	public TradeController(ClientRepository clients, EventBus eventBus,
			TradeServer tradeServer) {
		this.clients = clients;
		this.eventBus = eventBus;
		this.tradeServer = tradeServer;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET,
			produces = "application/json")
	@ResponseBody
	public Iterable<Client> listClients() {
		return this.clients.findAll();
	}

	@RequestMapping(value = "/{clientId}", method = RequestMethod.POST,
			produces = "text/plain")
	@ResponseBody
	public String trade(@PathVariable Long clientId) {
		// Retrieve client by id
		Client cl = this.clients.findOne(clientId);
		if (null == cl) {
			throw new IllegalArgumentException("No Client found for id " + clientId);
		}

		this.eventBus.notify("trade.execute", Event.wrap(this.tradeServer.nextTrade()));

		// Update trade count
		cl = this.clients.save(cl.setTradeCount(cl.getTradeCount() + 1));

		// Return result
		return "Hello " + cl.getName() + "! You now have " + cl.getTradeCount()
				+ " trades.";
	}

}
