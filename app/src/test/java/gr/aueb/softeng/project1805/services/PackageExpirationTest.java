package gr.aueb.softeng.project1805.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import gr.aueb.softeng.project1805.dao.Initializer;
import gr.aueb.softeng.project1805.domain.ActivePackage;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.MemoryInitializer;
import gr.aueb.softeng.project1805.service.PackageExpiration;
import gr.aueb.softeng.project1805.service.UserAuthentication;
import gr.aueb.softeng.project1805.utils.SystemDate;

public class PackageExpirationTest {

    private Initializer memoryInitializer;
    PackageExpiration packageExpiration;

    @Before
    public void setUp() {
        memoryInitializer = new MemoryInitializer();
        memoryInitializer.prepareData();
        packageExpiration=new PackageExpiration();
    }

    @Test
    public void chackDataPackageExpirations() {
        User user=new UserAuthentication().login("Baggelis", "b12345");
        user=memoryInitializer.getUser(user.getUid());

        ActivePackage activePackage=memoryInitializer.getActivePackageDAO().find(1, 0);

        Calendar date=activePackage.getActivationDate();
        date.add(Calendar.DATE, 31);
        SystemDate.setSystemDate(date);

        packageExpiration.checkPackageExpirations(user);
        Assert.assertNull(memoryInitializer.getActivePackageDAO().find(1, 0));
        Assert.assertEquals(0, user.findAllActive().size());
        Assert.assertEquals(0, user.getRemainingData());
    }

    @Test
    public void checkTalkTimePackageExpirations() {
        User user=new UserAuthentication().login("Mike", "m12345");
        user=memoryInitializer.getUser(user.getUid());

        ActivePackage activePackage=memoryInitializer.getActivePackageDAO().find(2, 2);

        Calendar date=activePackage.getActivationDate();
        date.add(Calendar.DATE, 31);
        SystemDate.setSystemDate(date);

        packageExpiration.checkPackageExpirations(user);
        Assert.assertNull(memoryInitializer.getActivePackageDAO().find(2, 2));
        Assert.assertEquals(1, user.findAllActive().size());
        Assert.assertEquals(30, user.getRemainingTalkTime());
    }

    @Test
    public void checkSMSPackageExpirations() {

        User user=new UserAuthentication().login("Katerina", "k12345");
        user=memoryInitializer.getUser(user.getUid());

        Calendar date=Calendar.getInstance();
        date.set(2018, 3, 20);
        SystemDate.setSystemDate(date);

        packageExpiration.checkPackageExpirations(user);
        Assert.assertNotNull(memoryInitializer.getActivePackageDAO().find(4, 1));

        ActivePackage activePackage=memoryInitializer.getActivePackageDAO().find(4, 1);

        date=activePackage.getActivationDate();
        date.add(Calendar.DATE, 31);
        SystemDate.setSystemDate(date);

        packageExpiration.checkPackageExpirations(user);
        Assert.assertNull(memoryInitializer.getActivePackageDAO().find(4, 1));
        Assert.assertEquals(0, user.findAllActive().size());

        Assert.assertEquals(99, user.getRemainingSMS());
    }
}

