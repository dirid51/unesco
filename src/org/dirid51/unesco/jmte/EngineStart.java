package org.dirid51.unesco.jmte;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

import com.floreysoft.jmte.Engine;

public class EngineStart {

	public static void start(Map<String, Object> model, Path templatePath) throws IOException {
		String template = Files.readAllLines(templatePath).stream().collect(Collectors.joining(" "));
		Engine engine = new Engine();
		engine.transform(template, model);
	}
}
