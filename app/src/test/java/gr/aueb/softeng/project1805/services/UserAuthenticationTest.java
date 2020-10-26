package gr.aueb.softeng.project1805.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.softeng.project1805.dao.Initializer;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.MemoryInitializer;
import gr.aueb.softeng.project1805.service.UserAuthentication;

public class UserAuthenticationTest {

    @Before
    public void setUp() {
        Initializer memoryInitializer = new MemoryInitializer();
        memoryInitializer.prepareData();
    }

    @Test
    public void login() {
        User user=new UserAuthentication().login("Baggelis", "b12345");
        Assert.assertNotNull(user);

        user=new UserAuthentication().login("B", "b");
        Assert.assertNull(user);
    }

    @Test
    public void signUp() {
        User user=new UserAuthentication().signUp("Baggelis", "b12345", "6900000000");
        Assert.assertNull(user);

        user=new UserAuthentication().signUp("name", "pw", "6933333333");
        Assert.assertNotNull(user);
    }
}

