package com.thoughtworks.morphia.resource;

import com.thoughtworks.morphia.dao.UserDAO;
import com.thoughtworks.morphia.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SimpleLog;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    private UserDAO userDAO;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        Log logger = new SimpleLog("");
        User user = null;
        try {
            user = userDAO.get(new ObjectId(id));
        } catch (Exception e) {
            logger.error(user, e);
            throw new WebApplicationException(500);
        }
        if (user == null) {
            throw new WebApplicationException(404);
        }
        logger.info(user.getId());
        return Response.ok(user).build();
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
