package com.thoughtworks.morphia.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.PrePersist;
import com.thoughtworks.morphia.validation.SecurityCheck;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Map;

@Entity(value = "users", noClassnameStored = true)
@XmlRootElement
public class User {

    @Id
    private ObjectId id;

    @NotBlank
    private String name;

    @NotBlank(message = "102", groups = SecurityCheck.class)
    private String pwd;

    private Map<String, String> extensions;

    private Date lastUpdated = new Date();


    public User() {
    }

    public User(String name, String pwd, Map<String, String> extensions) {
        this.name = name;
        this.pwd = pwd;
        this.extensions = extensions;
    }

    // http://code.google.com/p/morphia/wiki/LifecycleMethods
    @PrePersist
    private void prePersist() {
        lastUpdated = new Date();
    }


    @XmlElement(required = true)
    public String getId() {
        return id.toString();
    }

    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(required = true)
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    @XmlElement(required = true)
    public String getExtensions() {
        return extensions == null ? null : extensions.toString();
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }
}

