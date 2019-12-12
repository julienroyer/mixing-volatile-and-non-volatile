package fr.eimonku;

import static org.openjdk.jmh.annotations.Scope.Benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;

//https://blog.soat.fr/2015/07/benchmark-java-introduction-a-jmh/
//https://stackoverflow.com/questions/504103/how-do-i-write-a-correct-micro-benchmark-in-java
@Threads(10)
public class MyBenchmark {
	public @Benchmark String baseline() {
		return null;
	}

	@State(Benchmark)
	public static class UsingVolatile {
		volatile String name;

		String getName() {
			return name;
		}
	}

	public @Benchmark String usingVolatile(final UsingVolatile o) {
		return o.getName();
	}

	@State(Benchmark)
	public static class MixingVolatileAndNonVolatile {
		String nonVolatileName;
		volatile String volatileName;

		String getName() {
			final String name = nonVolatileName;
			return name != null ? name : volatileName;
		}
	}

	public @Benchmark String mixingVolatileAndNonVolatile(final MixingVolatileAndNonVolatile o) {
		return o.getName();
	}
}
