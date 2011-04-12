package com.thoughtworks.morphia.dao;

import com.thoughtworks.morphia.model.User;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class UserDAOTest {

    private UserDAO userDAO;

    @Before
    public void setUp() {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});

        userDAO = (UserDAO) ctx.getBean("userDAO");
    }

    @Test
    public void shouldCreateUser() {
        Map<String, String> extensions = new HashMap<String, String>();
        extensions.put("mobile", "13232323");
        extensions.put("phone", "2323232");
        User user = new User("name", "password", extensions);
        userDAO.save(user);

        user = userDAO.get(new ObjectId(user.getId()));
        assertNotNull(user);
        assertEquals("name", user.getName());

    }


}
