package fr.eimonku;

import static org.openjdk.jmh.annotations.Level.Iteration;
import static org.openjdk.jmh.annotations.Scope.Benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;

@Threads(10)
@State(Benchmark)
public class MyBenchmark {
	public static class UsingVolatile {
		private volatile String volatileValue;

		public String getValue() {
			return volatileValue;
		}

		public void initValue(final String v) {
			volatileValue = v;
		}
	}

	public static class MixingVolatileAndNonVolatile {
		private String nonVolatileValue;
		private volatile String volatileValue;

		String getValue() {
			final String value = nonVolatileValue;
			return value != null ? value : volatileValue;
		}

		void initValue(final String v) {
			nonVolatileValue = v;
			volatileValue = v;
		}
	}

	@Param({ "false", "true" })
	public boolean nonNullValue;
	public UsingVolatile usingVolatile;
	public MixingVolatileAndNonVolatile mixingVolatileAndNonVolatile;

	@Setup(Iteration)
	public void up() {
		usingVolatile = new UsingVolatile();
		mixingVolatileAndNonVolatile = new MixingVolatileAndNonVolatile();
		if (nonNullValue) {
			usingVolatile.initValue("");
			mixingVolatileAndNonVolatile.initValue("");
		}
	}

	@Benchmark
	public String baseline() {
		return null;
	}

	@Benchmark
	public String usingVolatile() {
		return usingVolatile.getValue();
	}

	@Benchmark
	public String mixingVolatileAndNonVolatile(final UsingVolatile o1, final MixingVolatileAndNonVolatile o2) {
		return mixingVolatileAndNonVolatile.getValue();
	}
}
