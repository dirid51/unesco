package org.dirid51.unesco.freemarker;

import java.io.File;
import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class Prepare {

	private static volatile Prepare instance = new Prepare();

	private Prepare() {
	}

	public static Prepare getInstance() {
		return instance;
	}

	public Configuration getFreemarkerConfig() throws IOException {
		Configuration cfg = new Configuration();
		cfg.setIncompatibleImprovements(Configuration.getVersion());
		cfg.setDirectoryForTemplateLoading(new File("/unesco/resources/templates"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		return cfg;
	}
	
	
}
