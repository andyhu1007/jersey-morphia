package com.thoughtworks.consultant.resource;

import com.thoughtworks.consultant.dao.UserDAO;
import com.thoughtworks.consultant.model.User;
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

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        User user = userDAO.get(new ObjectId(id));
        if (user == null) {
            throw new WebApplicationException(404);
        }
        return Response.ok(user).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User user) {
        userDAO.save(user);
        return Response.created(URI.create(user.getId())).build();
    }


    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
