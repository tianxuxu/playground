package ch.rasc.java8;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class LoopTest {
	private List<Shape> shapes = new ArrayList<>();

	@Setup(Level.Invocation)
	public void init() {
		Color[] twoColors = new Color[] { Color.BLACK, Color.BLUE };
		for (int i = 0; i < 1_000_000; i++) {
			shapes.add(new Shape(twoColors[i % 2]));
		}
	}

	@Benchmark
	public void loop() throws Exception {
		for (Shape s : shapes) {
			if (s.getColor() == Color.BLUE) {
				s.setColor(Color.RED);
			}
		}
	}

	@Benchmark
	public void iterator() throws Exception {
		Iterator<Shape> it = shapes.iterator();
		while (it.hasNext()) {
			Shape s = it.next();
			if (s.getColor() == Color.BLUE) {
				s.setColor(Color.RED);
			}
		}
	}

	@Benchmark
	public void lambdaStreamEmbeddedIf() throws Exception {
		shapes.stream().forEach(s -> {
			if (s.getColor() == Color.BLUE) {
				s.setColor(Color.RED);
			}
		});
	}

	@Benchmark
	public void lambdaStream() throws Exception {
		shapes.stream().filter(s -> s.getColor() == Color.BLUE)
				.forEach(s -> s.setColor(Color.RED));
	}

	@Benchmark
	public void lambdaParallel() throws Exception {
		shapes.parallelStream().filter(s -> s.getColor() == Color.BLUE)
				.forEach(s -> s.setColor(Color.RED));
	}
}