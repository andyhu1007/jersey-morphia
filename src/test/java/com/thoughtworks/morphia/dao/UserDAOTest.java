package com.thoughtworks.morphia.dao;

import com.google.code.morphia.validation.VerboseJSR303ConstraintViolationException;
import com.thoughtworks.morphia.model.User;
import com.thoughtworks.morphia.validation.SecurityCheck;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import static junit.framework.Assert.*;

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

        assertNotNull(user.getLastUpdated());

        user = userDAO.get(new ObjectId(user.getId()));
        assertNotNull(user);
        assertEquals("name", user.getName());

    }

    @Test
    public void shouldNotCreateUserWithoutName() {
        User user = new User(null, "pwd", null);

        try {
            userDAO.save(user);
            fail();
        } catch (VerboseJSR303ConstraintViolationException e) {
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.info(e.getMessage());
        }
    }

    @Test
    public void shouldAbleToValidate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        User user = new User(null, "", null);

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertEquals(1, constraintViolations.size());

        constraintViolations = validator.validate(user, SecurityCheck.class);
        assertEquals(1, constraintViolations.size());
        assertEquals("102", constraintViolations.iterator().next().getMessage());
    }


}
