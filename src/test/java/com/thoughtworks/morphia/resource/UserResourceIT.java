package com.thoughtworks.morphia.resource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.thoughtworks.morphia.dao.UserDAO;
import com.thoughtworks.morphia.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static junit.framework.Assert.assertTrue;

public class UserResourceIT {

    private static final String HOST = "host";
    private UserDAO userDAO;
    private Client client;
    private Properties properties;

    @Before
    public void setUp() {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});

        userDAO = (UserDAO) ctx.getBean("userDAO");

        client = Client.create();

        properties = new Properties();
        try {
            properties.load(new FileInputStream("./src/test/resources/test.properties"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    @Test
    public void shouldGetUser() {
        Map<String, String> extensions = new HashMap<String, String>();
        extensions.put("mobile", "13232323");
        extensions.put("phone", "2323232");
        User user = new User("name", "password", extensions);
        userDAO.save(user);
        WebResource resource = client.resource(properties.getProperty(HOST) + "users/" + user.getId());

        String response = resource.accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        assertTrue(response.startsWith("{\"extensions\":\"{phone=2323232, mobile=13232323}\""));
        assertTrue(response.endsWith("\"name\":\"name\",\"pwd\":\"password\"}"));
    }

    @Test
    public void shouldCreateUser() {
        WebResource createResource = client.resource(properties.getProperty(HOST) + "users");
        ClientResponse createResponse = createResource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, "{\"name\":\"name\",\"pwd\":\"password\"}");

        WebResource getResource = client.resource(createResponse.getLocation());
        String getResponse = getResource.accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        assertTrue(getResponse.endsWith("\"name\":\"name\",\"pwd\":\"password\"}"));
    }


}
