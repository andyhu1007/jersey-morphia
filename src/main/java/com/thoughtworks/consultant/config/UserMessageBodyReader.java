package com.thoughtworks.consultant.config;

import com.thoughtworks.consultant.model.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

/*
    MessageBodyReader VS StringReaderProvider

    1. MessageBodyReader only deals with body, and it deals with Stream.

       e.g.
       @Post
       @Consumes(MediaType.APPLICATION_JSON)
       public Response create(User user) {...}

       StringReaderProvider deals with String, which are already extracted by, for example: @PathParam, @QueryParam, @FormParam, etc.

       e.g.
       @Post
       @Consumes(MediaType.APPLICATION_JSON)
       public Response create(@FormParam("users") User user) {...}

    2. Both of them will override default behavior.

 */

/*@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class UserMessageBodyReader implements MessageBodyReader<User> {

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return User.class.isAssignableFrom(aClass);
    }

    // Return the user parsed from Stream. Here is just an example.
    @Override
    public User readFrom(Class<User> userClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> stringStringMultivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        for (Map.Entry entry : stringStringMultivaluedMap.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        char[] chars = new char[inputStream.available()];
        streamReader.read(chars, 0, inputStream.available());
        System.out.println(new String(chars));
        return new User();
    }
}*/

/*
@Provider
@Consumes(MediaType.APPLICATION_JSON)
Class UserStringReader implements StringReaderProvider<User> {

    @Override
    public StringReader<User> getStringReader(Class<?> aClass, Type type, Annotation[] annotations) {
        return new StringReader<User>() {

            @Override
            public User fromString(String s) {
                System.out.println("String reader");
                System.out.println(s);
                return new User();
            }
        };
    }
}*/
