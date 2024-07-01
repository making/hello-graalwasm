package org.example;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class Main {
	public static void main(String[] args) throws IOException {
		try (Context context = Context.newBuilder().allowAllAccess(true).build()) {
			context.initialize("wasm");
			URL resource = Main.class.getClassLoader().getResource("SumSquared.wasm");
			Source source = Source.newBuilder("wasm", Objects.requireNonNull(resource)).name("main").build();
			context.eval(source);
			Value sumSquared = context.getBindings("wasm").getMember("main").getMember("SumSquared");
			Value result = sumSquared.execute(2, 3);
			System.out.println("Result: " + result.asInt()); // should print 25 ((2 + 3) * (2 + 3) = 25)
		}
	}
}