package com.thoughtworks.morphia.model;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {


    public ObjectFactory() {
    }

    public User createUser() {
        return new User();
    }

}
