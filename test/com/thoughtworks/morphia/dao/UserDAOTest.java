package com.thoughtworks.morphia.dao;

import com.google.code.morphia.Key;
import com.thoughtworks.morphia.model.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDAOTest {

    @Test
    public void shouldCreateUser() {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});

        UserDAO userDAO = (UserDAO)ctx.getBean("userDAO");
        User user = new User("name", "password");
        Key key = userDAO.save(user);

    }
}
