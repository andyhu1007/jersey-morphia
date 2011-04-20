package com.thoughtworks.consultant.config;


import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;


/**
 * http://blogs.sun.com/enterprisetechtips/entry/configuring_json_for_restful_web
 *
 * http://jersey.java.net/nonav/documentation/latest/json.html
 */

@Provider
public final class JAXBContextResolver implements ContextResolver<JAXBContext> {
    private final static String ENTITY_PACKAGE = "com.thoughtworks.morphia.model";

    private final JAXBContext context;

    public JAXBContextResolver() throws Exception {
        this.context = new JSONJAXBContext(JSONConfiguration.natural().build(), ENTITY_PACKAGE);
    }

    public JAXBContext getContext(Class<?> objectType) {
        return objectType.getPackage().getName().contains(ENTITY_PACKAGE) ? context : null;
    }
}

