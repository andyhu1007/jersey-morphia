package com.thoughtworks.morphia;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class DatastoreFactoryBean extends AbstractFactoryBean<Datastore> {

    private Morphia morphia;
    private Mongo mongo;
    private String dbName;
    private String user;
    private String password;

    @Override
    public Class<?> getObjectType() {
        return Datastore.class;
    }

    @Override
    protected Datastore createInstance() throws Exception {
        return morphia.createDatastore(mongo, dbName, user, password.toCharArray());
    }

    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
