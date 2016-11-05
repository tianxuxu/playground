package ch.rasc.reactor;

import java.util.concurrent.TimeUnit;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Simple {

	// http://spring.io/blog/2016/06/13/notes-on-reactive-programming-part-ii-writing-some-code
	public static void main(String[] args) throws InterruptedException {

		Flux<String> flux = Flux.just("red", "white", "blue");

		flux.log().map(value -> value.toUpperCase()).subscribe(System.out::println);

		System.out.println("===============================");

		flux.map(value -> value.toLowerCase()).subscribe(new Subscriber<String>() {

			private long count = 0;
			private Subscription subscription;

			@Override
			public void onSubscribe(Subscription sub) {
				this.subscription = sub;
				sub.request(2);
			}

			@Override
			public void onNext(String t) {
				System.out.println("on next: " + t);
				count++;
				if (count >= 2) {
					count = 0;
					System.out.println("request more");
					subscription.request(2);
				}
			}

			@Override
			public void onError(Throwable t) {
				System.out.println("on error");
			}

			@Override
			public void onComplete() {
				System.out.println("on complete");
			}
		});

		System.out.println("===============================");
		Flux.just("red", "white", "blue").log().map(String::toUpperCase)
				.subscribeOn(Schedulers.parallel()).subscribe(null, 2);
		TimeUnit.SECONDS.sleep(1);

		System.out.println("===============================");
		Flux.just("red", "white", "blue").log().flatMap(value -> Mono
				.just(value.toUpperCase()).subscribeOn(Schedulers.parallel()), 2)
				.subscribe(value -> {
					System.out.println("Consumed: " + value);
				});
	}

}
