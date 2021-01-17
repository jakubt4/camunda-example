package com.example.camunda.utils;

import static j2html.TagCreator.a;

import j2html.tags.ContainerTag;

public final class J2HtmlUtil {

    private J2HtmlUtil() {
        throw new UnsupportedOperationException("Util class");
    }

    public static ContainerTag createhref(String label, String href) {
        return a(label).withHref(href);
    }
}
