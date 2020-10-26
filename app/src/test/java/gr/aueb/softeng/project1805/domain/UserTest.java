package gr.aueb.softeng.project1805.domain;

import junit.framework.Assert;

import org.junit.Test;

public class UserTest {

    @Test
    public void memberFunctions() {

        User user=new User();

        user.setUid(0);
        Assert.assertEquals(0, user.getUid());

        user.setPhoneNum("6900000000");
        Assert.assertEquals("6900000000", user.getPhoneNum());

        user.setUserName("name");
        Assert.assertEquals("name", user.getUsername());

        user.setPassword("password");
        Assert.assertEquals("password", user.getPassword());

        user.setRemainingData(50);
        Assert.assertEquals(50, user.getRemainingData());

        user.setRemainingTalkTime(30);
        Assert.assertEquals(30, user.getRemainingTalkTime());

        user.setRemainingSMS(20);
        Assert.assertEquals(20, user.getRemainingSMS());

        user=new User(0, "6900000000", "name", "password");
        Assert.assertNotNull(user);
    }

    @Test
    public void addTransfer() {
        User user=new User();
        Transfer transfer=new Transfer();
        user.addTransfer(transfer);
        Assert.assertEquals(1, user.getTransfers().size());
        Assert.assertEquals(1, user.friendTransfers().size());
        Assert.assertTrue(user.getTransfers().contains(transfer));
    }

    @Test
    public void addNullTransfer() {
        User user=new User();
        user.addTransfer(null);
        Assert.assertEquals(0, user.getTransfers().size());
    }

    @Test
    public void removeTransfer() {
        User user=new User();
        Transfer transfer=new Transfer();
        user.addTransfer(transfer);
        user.removeTransfer(transfer);
        Assert.assertEquals(0, user.getTransfers().size());
        Assert.assertFalse(user.getTransfers().contains(transfer));
    }

    @Test
    public void removeNullTransfer() {
        User user=new User();
        Transfer transfer=new Transfer();
        user.addTransfer(transfer);
        user.removeTransfer(null);
        Assert.assertEquals(1, user.getTransfers().size());
    }

    @Test
    public void addPackage() {
        User user=new User();
        ActivePackage activePackage=new ActivePackage();
        activePackage.setActive(true);
        user.addPackage(activePackage);
        Assert.assertEquals(1, user.getPackages().size());
        Assert.assertEquals(1, user.findAllActive().size());
        Assert.assertEquals(1, user.friendPackages().size());
        Assert.assertTrue(user.getPackages().contains(activePackage));
    }

    @Test
    public void addNullPackage() {
        User user=new User();
        user.addPackage(null);
        Assert.assertEquals(0, user.getPackages().size());
    }

    @Test
    public void removePackage() {
        User user=new User();
        ActivePackage activePackage=new ActivePackage();
        user.addPackage(activePackage);
        user.removePackage(activePackage);
        Assert.assertEquals(0, user.getPackages().size());
        Assert.assertFalse(user.getPackages().contains(activePackage));
    }

    @Test
    public void removeNullPackage() {
        User user=new User();
        ActivePackage activePackage=new ActivePackage();
        user.addPackage(activePackage);
        user.removePackage(null);
        Assert.assertEquals(1, user.getPackages().size());
    }

    @Test
    public void addServiceUsage() {
        User user=new User();
        Data data=new Data();
        user.addService(data);
        Assert.assertEquals(1, user.getServices().size());
        Assert.assertEquals(1, user.friendServices().size());
        Assert.assertTrue(user.getServices().contains(data));
    }

    @Test
    public void addNullServiceUsage() {
        User user=new User();
        user.addService(null);
        Assert.assertEquals(0, user.getServices().size());
    }

    @Test
    public void removeServiceUsage() {
        User user=new User();
        Data data=new Data();
        user.addService(data);
        user.removeService(data);
        Assert.assertEquals(0, user.getServices().size());
        Assert.assertFalse(user.getServices().contains(data));
    }

    @Test
    public void removeNullServiceUsage() {
        User user=new User();
        Data data=new Data();
        user.addService(data);
        user.removeService(null);
        Assert.assertEquals(1, user.getServices().size());
    }
}

