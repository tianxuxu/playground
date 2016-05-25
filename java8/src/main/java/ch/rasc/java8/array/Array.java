package ch.rasc.java8.array;

import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
public class Array {

	private final Random rand = new Random();

	public static void main(String... args) throws RunnerException {
		Options opt = new OptionsBuilder().exclude(Grow.class.getSimpleName()).include(Array.class.getSimpleName()).forks(1)
				.build();

		new Runner(opt).run();
	}

	int[] testArray;

	@Setup
	public void prepare() {
		testArray = new int[30];
		for (int i = 0; i < testArray.length; i++) {
			testArray[i] = this.rand.nextInt(32);
		}
	}

	@Benchmark
	public void unwrapped(Blackhole bh) {
		int sum = 0;
		sum += testArray[0];
		sum += testArray[1];
		sum += testArray[2];
		sum += testArray[3];
		sum += testArray[4];
		sum += testArray[5];
		sum += testArray[6];
		sum += testArray[7];
		sum += testArray[8];
		sum += testArray[9];
		sum += testArray[10];
		sum += testArray[11];
		sum += testArray[12];
		sum += testArray[13];
		sum += testArray[14];
		sum += testArray[15];
		sum += testArray[16];
		sum += testArray[17];
		sum += testArray[18];
		sum += testArray[19];
		sum += testArray[20];
		sum += testArray[21];
		sum += testArray[22];
		sum += testArray[23];
		sum += testArray[24];
		sum += testArray[25];
		sum += testArray[26];
		sum += testArray[27];
		sum += testArray[28];
		sum += testArray[29];
		
		bh.consume(sum);
	}

	@Benchmark
	public void loop(Blackhole bh) {
		int sum = 0;
		for (int i = 0; i < testArray.length; i++) {
			sum += testArray[i];
		}
		bh.consume(sum);
	}

	@Benchmark
	public void loopLength(Blackhole bh) {
		int sum = 0;
		int len = testArray.length;
		for (int i = 0; i < len; i++) {
			sum += testArray[i];
		}
		bh.consume(sum);
	}
}
