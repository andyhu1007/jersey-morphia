package com.thoughtworks.morphia.resource;

import com.thoughtworks.morphia.dao.UserDAO;
import com.thoughtworks.morphia.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SimpleLog;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/users")
public class UserResource {

    private UserDAO userDAO;
    private static final Log LOGGER = new SimpleLog("");

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        LOGGER.info("Get user");
        User user = userDAO.get(new ObjectId(id));
        if (user == null) {
            throw new WebApplicationException(404);
        }
        LOGGER.info(user.getId());
        return Response.ok(user).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User user) {
        LOGGER.info("Create user");
        LOGGER.info(user.getName());
        LOGGER.info(user.getPwd());
        userDAO.save(user);
        LOGGER.info(user.getId());
        return Response.created(URI.create("/users/" + user.getId())).build();
    }


    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
