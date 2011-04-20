package com.thoughtworks.consultant.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.thoughtworks.consultant.model.User;
import org.bson.types.ObjectId;

public class UserDAO extends BasicDAO<User, ObjectId> {

    public UserDAO(Datastore datastore) {
        super(datastore);
    }
}
