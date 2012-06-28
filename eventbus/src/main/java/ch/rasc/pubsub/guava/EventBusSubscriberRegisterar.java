package ch.rasc.pubsub.guava;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

@Service
public class EventBusSubscriberRegisterar implements BeanPostProcessor {

	private final EventBus eventBus;

	@Autowired
	public EventBusSubscriberRegisterar(final EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {

		for (Method method : bean.getClass().getMethods()) {
			if (method.getAnnotation(Subscribe.class) != null) {
				eventBus.register(bean);
				break;
			}
		}

		return bean;
	}
}