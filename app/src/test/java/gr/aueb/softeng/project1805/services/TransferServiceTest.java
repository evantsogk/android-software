package gr.aueb.softeng.project1805.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.softeng.project1805.dao.Initializer;
import gr.aueb.softeng.project1805.domain.ServiceType;
import gr.aueb.softeng.project1805.domain.Transfer;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.MemoryInitializer;
import gr.aueb.softeng.project1805.service.TransferService;
import gr.aueb.softeng.project1805.service.UserAuthentication;

public class TransferServiceTest {

    private Initializer memoryInitializer;
    private TransferService transferService;
    private User user;

    @Before
    public void setUp() {
        memoryInitializer = new MemoryInitializer();
        memoryInitializer.prepareData();
        user=new UserAuthentication().login("Baggelis", "b12345");
        user=memoryInitializer.getUser(user.getUid());
        transferService=new TransferService();
    }

    @Test
    public void transfer() {
        Transfer transfer=transferService.transfer(user, "6911111111", ServiceType.Data, 200);
        Assert.assertNull(transfer);

        transfer=transferService.transfer(user, "6911111112", ServiceType.Data, 20);
        Assert.assertNull(transfer);

        transfer=transferService.transfer(user, "6911111111", ServiceType.Data, 20);
        Assert.assertNotNull(transfer);

        Assert.assertEquals(3, memoryInitializer.getTransferDAO().findAll(user.getUid()).size());
        Assert.assertEquals(2, memoryInitializer.getTransferDAO().findAll(1).size());
    }

    @Test
    public void receive() {

        Assert.assertFalse(memoryInitializer.getTransferDAO().findAll(user.getUid()).get(1).getReceived());
        transferService.receive(user);
        Assert.assertTrue(memoryInitializer.getTransferDAO().findAll(user.getUid()).get(1).getReceived());
        transferService.receive(user);
        Assert.assertTrue(memoryInitializer.getTransferDAO().findAll(user.getUid()).get(1).getReceived());
    }

    @Test
    public void updateRemaining() {

        transferService.updateRemaining(user, true, ServiceType.Data, 20);
        Assert.assertEquals(100, user.getRemainingData());
        transferService.updateRemaining(user, false, ServiceType.Data, 40);
        Assert.assertEquals(60, user.getRemainingData());

        transferService.updateRemaining(user, true, ServiceType.TalkTime, 20);
        Assert.assertEquals(20, user.getRemainingTalkTime());
        transferService.updateRemaining(user, false, ServiceType.TalkTime, 10);
        Assert.assertEquals(10, user.getRemainingTalkTime());

        transferService.updateRemaining(user, true, ServiceType.SMS, 20);
        Assert.assertEquals(30, user.getRemainingSMS());
        transferService.updateRemaining(user, false, ServiceType.SMS, 5);
        Assert.assertEquals(25, user.getRemainingSMS());
    }

    @Test
    public void sufficientRemaining() {

        Assert.assertTrue(transferService.sufficientRemaining(user, ServiceType.Data, 50));
        Assert.assertFalse(transferService.sufficientRemaining(user, ServiceType.Data, 200));

        Assert.assertTrue(transferService.sufficientRemaining(user, ServiceType.TalkTime, 0));
        Assert.assertFalse(transferService.sufficientRemaining(user, ServiceType.TalkTime, 200));

        Assert.assertTrue(transferService.sufficientRemaining(user, ServiceType.SMS, 5));
        Assert.assertFalse(transferService.sufficientRemaining(user, ServiceType.SMS, 200));
    }
}

