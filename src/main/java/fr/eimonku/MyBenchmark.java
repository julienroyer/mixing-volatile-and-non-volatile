package fr.eimonku;

import static org.openjdk.jmh.annotations.Scope.Benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;

@Threads(10)
public class MyBenchmark {
	@State(Benchmark)
	public static class UsingVolatile {
		volatile String name;

		String getName() {
			return name;
		}
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

	@Benchmark
	public String baseline(final UsingVolatile o1, final MixingVolatileAndNonVolatile o2) {
		return null;
	}

	@Benchmark
	public String usingVolatile(final UsingVolatile o) {
		return o.getName();
	}

	@Benchmark
	public String mixingVolatileAndNonVolatile(final MixingVolatileAndNonVolatile o) {
		return o.getName();
	}
}
