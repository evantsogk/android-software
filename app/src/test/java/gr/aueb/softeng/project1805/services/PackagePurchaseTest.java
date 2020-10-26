package gr.aueb.softeng.project1805.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.softeng.project1805.dao.Initializer;
import gr.aueb.softeng.project1805.domain.ActivePackage;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.MemoryInitializer;
import gr.aueb.softeng.project1805.service.PackagePurchase;
import gr.aueb.softeng.project1805.service.UserAuthentication;

public class PackagePurchaseTest {
    private Initializer memoryInitializer;
    private PackagePurchase packagePurchase;

    @Before
    public void setUp() {
        memoryInitializer = new MemoryInitializer();
        memoryInitializer.prepareData();
        packagePurchase=new PackagePurchase();
    }

    @Test
    public void payment() {

        Assert.assertFalse(packagePurchase.payment("123", 4));
        Assert.assertFalse(packagePurchase.payment("0000 0000 0007", 4));

        Assert.assertTrue(packagePurchase.payment("0000 0000 0007", 0.50f));
        Assert.assertEquals(1.40f, memoryInitializer.getAccountDAO().find("0000 0000 0007").getBalance(), 0.001f);
    }

    @Test
    public void simpleCheckPayment() {
        Assert.assertFalse(packagePurchase.simpleCheckPayment("123", 4));
        Assert.assertFalse(packagePurchase.simpleCheckPayment("0000 0000 0007", 4));

        Assert.assertTrue(packagePurchase.simpleCheckPayment("0000 0000 0007", 0.50f));
    }

    @Test
    public void dataPackagePurchaseTest(){

        User user=new UserAuthentication().login("Katerina", "k12345");
        user=memoryInitializer.getUser(user.getUid());

        ActivePackage activePackage=packagePurchase.purchasePackage(user,memoryInitializer.getPackageDAO().find(5),"1111 1111 1118");
        Assert.assertNull(activePackage);

        activePackage=packagePurchase.purchasePackage(user,memoryInitializer.getPackageDAO().find(1),"1111 1111 1118");
        Assert.assertNotNull(activePackage);
        Assert.assertNotNull(memoryInitializer.getActivePackageDAO().find(1, 1));
        Assert.assertNotNull(user.findAllActive().contains(activePackage));
        Assert.assertEquals(1000, user.getRemainingData());


    }

    @Test
    public void TalkTimePackagePurchaseTest() {
        User user=new UserAuthentication().login("Katerina", "k12345");
        user=memoryInitializer.getUser(user.getUid());

        ActivePackage activePackage=packagePurchase.purchasePackage(user,memoryInitializer.getPackageDAO().find(2),"1111 1111 1118");
        Assert.assertNotNull(activePackage);
        Assert.assertNotNull(memoryInitializer.getActivePackageDAO().find(2, 1));
        Assert.assertNotNull(user.findAllActive().contains(activePackage));
        Assert.assertEquals(100, user.getRemainingTalkTime());
    }

    @Test
    public void SMSPackagePurchaseTest() {
        User user=new UserAuthentication().login("Baggelis", "b12345");
        user=memoryInitializer.getUser(user.getUid());

        ActivePackage activePackage=packagePurchase.purchasePackage(user,memoryInitializer.getPackageDAO().find(4),"1111 1111 1118");
        Assert.assertNotNull(activePackage);
        Assert.assertNotNull(memoryInitializer.getActivePackageDAO().find(4, 0));
        Assert.assertNotNull(user.findAllActive().contains(activePackage));
        Assert.assertEquals(110, user.getRemainingSMS());
    }
}

