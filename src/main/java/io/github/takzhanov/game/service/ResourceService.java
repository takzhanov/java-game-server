package io.github.takzhanov.game.service;

import io.github.takzhanov.game.resources.TestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ResourceService implements ResourceServiceMBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private TestResource testResource;

    @Override
    public String getName() {
        return testResource != null ? testResource.getName() : null;
    }

    @Override
    public int getAge() {
        return testResource != null ? testResource.getAge() : 0;
    }

    public void processResource(String path) {
        Object o = readXML(path);
        if (o instanceof TestResource) {
            testResource = (TestResource) o;
        }
    }

    private Object read(String fileName) {
        try (FileInputStream out = new FileInputStream(fileName)) {
            BufferedInputStream bout = new BufferedInputStream(out);
            ObjectInputStream dout = new ObjectInputStream(bout);
            return dout.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object readXML(String xmlFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            //LogSaxHandler handler = new LogSaxHandler();
            SaxHandler handler = new SaxHandler();
            saxParser.parse(xmlFile, handler);

            return handler.getObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
