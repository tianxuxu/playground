package ch.rasc.eventbus.spring;

import java.util.Date;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class EventListener implements ApplicationListener<AppEvent> {

	@Override
	public void onApplicationEvent(AppEvent event) {
		System.out.println(new Date() + ": " + event.getMessage());
	}

}
