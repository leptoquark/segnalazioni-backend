package it.anac.segnalazioni.backend.report.util.test;

import java.io.IOException;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class MyTemplateExceptionHandler implements TemplateExceptionHandler {
	public void handleTemplateException(TemplateException te, Environment env, java.io.Writer out)
			throws TemplateException {
		try {
			out.write("[" + te.getMessage() + "](ERROR:)");
		} catch (IOException e) {
			throw new TemplateException("Failed to print error message. Cause: " + e, env);
		}
	}
}
