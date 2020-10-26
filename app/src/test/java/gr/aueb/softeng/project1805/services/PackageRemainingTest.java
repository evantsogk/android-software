package gr.aueb.softeng.project1805.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.softeng.project1805.dao.Initializer;
import gr.aueb.softeng.project1805.domain.ServiceType;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.MemoryInitializer;
import gr.aueb.softeng.project1805.service.PackageRemaining;
import gr.aueb.softeng.project1805.service.UserAuthentication;

public class PackageRemainingTest {
    private Initializer memoryInitializer;

    @Before
    public void setUp() {
        memoryInitializer = new MemoryInitializer();
        memoryInitializer.prepareData();
    }

    @Test
    public void updatePackageRemaining() {
        PackageRemaining packageRemaining=new PackageRemaining();

        User user=new UserAuthentication().login("Baggelis", "b12345");
        user=memoryInitializer.getUser(user.getUid());

        packageRemaining.updatePackageRemaining(user, ServiceType.Data, 50);
        Assert.assertEquals(30, memoryInitializer.getActivePackageDAO().find(1, user.getUid()).getRemainingQuantity());

        user=new UserAuthentication().login("Mike", "m12345");
        user=memoryInitializer.getUser(user.getUid());
        Assert.assertNotNull(user);

        Assert.assertNotNull(memoryInitializer.getActivePackageDAO().find(2, user.getUid()));
        packageRemaining.updatePackageRemaining(user, ServiceType.TalkTime, 50);
        Assert.assertNull(memoryInitializer.getActivePackageDAO().find(2, user.getUid()));
        Assert.assertEquals(70, memoryInitializer.getActivePackageDAO().find(3, user.getUid()).getRemainingQuantity());
    }
}

