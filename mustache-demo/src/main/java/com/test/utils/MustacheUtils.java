package com.test.utils;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author jingLv
 * @date 2020-04-15 10:28 AM
 */
public class MustacheUtils {
    private final String path;
    private final Object method;

    public MustacheUtils(String path, Object method) {
        this.path = path;
        this.method = method;
    }

    public String execute() {
        StringWriter writer = new StringWriter();

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(this.path);
        try {
            mustache.execute(writer, this.method).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }
}
