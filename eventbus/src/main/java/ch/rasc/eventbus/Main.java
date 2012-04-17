package ch.rasc.eventbus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
		ctx.getBean(EventPublisher.class).publishEvent("hello world");
		//		ctx.getBean(DirectoryWatcher.class).startWatch(Paths.get("c:/temp"));
		//
		//		Runtime.getRuntime().addShutdownHook(new Thread() {
		//			@Override
		//			public void run() {
		//				ctx.stop();
		//			}
		//		});

	}

}
