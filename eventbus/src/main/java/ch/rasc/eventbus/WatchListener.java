package ch.rasc.eventbus;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;

@Component
public class WatchListener {

	@Subscribe
	public void handleWatchEvent(PathEvents pathEvents) {
		System.out.println(new Date() + ": " + pathEvents.getWatchedDirectory());
		for (PathEvent event : pathEvents.getEvents()) {
			System.out.print("  ");
			System.out.print(event.getEventTarget());
			System.out.print("  ");
			System.out.println(event.getType());
		}
	}
}
