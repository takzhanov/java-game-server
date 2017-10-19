package io.github.takzhanov.game.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public abstract class PageGenerator {
    public static final String HTML_DIR = "templates";
    private static final Configuration cfg = new Configuration();

    public static String getPage(String fileName) {
        return getPage(fileName, null);
    }

    public static String getPage(String fileName, Map<String, Object> pageVariables) {
        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + File.separator + fileName);
            template.process(pageVariables, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }
}
