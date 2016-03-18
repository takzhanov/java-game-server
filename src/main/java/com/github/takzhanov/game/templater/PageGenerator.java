package com.github.takzhanov.game.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    public static final String HTML_DIR = "templates";
    private static final Configuration cfg = new Configuration();
    private static final PageGenerator pageGenerator = new PageGenerator();

    public static PageGenerator instance() {
        return pageGenerator;
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

    private PageGenerator() {
    }
}
