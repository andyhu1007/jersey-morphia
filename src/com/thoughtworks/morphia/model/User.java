package com.thoughtworks.morphia.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.util.Map;

@Entity(value = "users", noClassnameStored = true)
public class User {

    @Id
    private ObjectId id;

    private String name;
    private String pwd;
    private Map<String, String> extensions;

    public User(){}

    public User(String name, String pwd, Map<String, String> extensions) {
        this.name = name;
        this.pwd = pwd;
        this.extensions = extensions;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }

    public Map<String, String> getExtensions() {
        return extensions;
    }
}

