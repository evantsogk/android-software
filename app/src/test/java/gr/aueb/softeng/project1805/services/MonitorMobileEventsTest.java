package gr.aueb.softeng.project1805.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.softeng.project1805.dao.Initializer;
import gr.aueb.softeng.project1805.domain.Call;
import gr.aueb.softeng.project1805.domain.Data;
import gr.aueb.softeng.project1805.domain.SMS;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.MemoryInitializer;
import gr.aueb.softeng.project1805.service.MonitorMobileEvents;
import gr.aueb.softeng.project1805.service.UserAuthentication;

public class MonitorMobileEventsTest {
    private Initializer memoryInitializer;

    @Before
    public void setUp() {
        memoryInitializer = new MemoryInitializer();
        memoryInitializer.prepareData();
    }

    @Test
    public void newData() {
        User user=new UserAuthentication().login("Baggelis", "b12345");
        user=memoryInitializer.getUser(user.getUid());

        MonitorMobileEvents mobileEvents=new MonitorMobileEvents(user);

        Assert.assertNull(mobileEvents.newData(100));

        Data data=mobileEvents.newData(50);
        Assert.assertNotNull(data);
        Assert.assertEquals(30, user.getRemainingData());
        Assert.assertEquals(30, memoryInitializer.getActivePackageDAO().find(1, user.getUid()).getRemainingQuantity());
    }

    @Test
    public void newCall(){
        User user=new UserAuthentication().login("Mike", "m12345");
        user=memoryInitializer.getUser(user.getUid());

        MonitorMobileEvents mobileEvents=new MonitorMobileEvents(user);

        Assert.assertNull(mobileEvents.newCall(70,"6911111111"));

        Call call =mobileEvents.newCall(30,"6911111111");
        Assert.assertNotNull(call);
        Assert.assertEquals(20,user.getRemainingTalkTime());
        Assert.assertEquals(90, memoryInitializer.getActivePackageDAO().find(3,user.getUid()).getRemainingQuantity());

    }

    @Test
    public void newSMS(){
        User user=new UserAuthentication().login("Mike", "m12345");
        user=memoryInitializer.getUser(user.getUid());

        MonitorMobileEvents mobileEvents=new MonitorMobileEvents(user);

        Assert.assertNull(mobileEvents.newSMS("6900000000"));

        User user1=new UserAuthentication().login("Katerina", "k12345");
        user1=memoryInitializer.getUser(user1.getUid());

        mobileEvents=new MonitorMobileEvents(user1);

        SMS sms=mobileEvents.newSMS("6900000000");
        Assert.assertNotNull(sms);
        Assert.assertEquals(599,user1.getRemainingSMS());
        Assert.assertNull(memoryInitializer.getActivePackageDAO().find(4,user1.getUid()));

        sms=mobileEvents.newSMS("6900000000");
        Assert.assertNotNull(sms);
        Assert.assertEquals(598,user1.getRemainingSMS());
        Assert.assertEquals(499,memoryInitializer.getActivePackageDAO().find(5,user1.getUid()).getRemainingQuantity());

    }
}


