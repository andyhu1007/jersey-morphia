package com.thoughtworks.morphia.dao;

import com.thoughtworks.morphia.model.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class UserDAOTest {

    @Test
    public void shouldCreateUser() {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});

        UserDAO userDAO = (UserDAO)ctx.getBean("userDAO");
        Map<String,String> extensions = new HashMap<String, String>();
        extensions.put("mobile", "13232323");
        extensions.put("phone", "2323232");
        User user = new User("name", "password", extensions);
        userDAO.save(user);

        user = userDAO.get(user.getId());

        assertEquals("name", user.getName());
    }
}
