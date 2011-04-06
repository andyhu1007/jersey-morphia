package com.thoughtworks.morphia.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@Entity(value = "users", noClassnameStored = true)
@XmlRootElement
public class User {

    @Id
    private ObjectId id;

    private String name;
    private String pwd;
    private Map<String, String> extensions;

    public User() {
    }

    public User(String name, String pwd, Map<String, String> extensions) {
        this.name = name;
        this.pwd = pwd;
        this.extensions = extensions;
    }

    @XmlElement(required = true)
    public String getId() {
        return id.toString();
    }

    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    @XmlElement(required = true)
    public String getPwd() {
        return pwd;
    }

    @XmlElement(required = true)
    public String getExtensions() {
        return extensions.toString();
    }
}

