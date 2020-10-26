package gr.aueb.softeng.project1805.domain;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Calendar;

import gr.aueb.softeng.project1805.utils.SystemDate;

public class TransferTest {

    @Test
    public void memberFunctions() {

        Transfer transfer=new Transfer();

        transfer.setDate(SystemDate.getSystemDate());
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.YEAR), transfer.getDate().get(Calendar.YEAR));
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.MONTH), transfer.getDate().get(Calendar.MONTH));
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.DATE), transfer.getDate().get(Calendar.DATE));

        transfer.setSenderNum("6900000000");
        Assert.assertEquals("6900000000", transfer.getSenderNum());

        transfer.setReceiverNum("6911111111");
        Assert.assertEquals("6911111111", transfer.getReceiverNum());

        transfer.setQuantity(30);
        Assert.assertEquals(30, transfer.getQuantity());

        transfer.setServiceType(ServiceType.TalkTime);
        Assert.assertEquals(ServiceType.TalkTime, transfer.getServiceType());

        transfer.setTransferType(TransferType.Outgoing);
        Assert.assertEquals(TransferType.Outgoing, transfer.getTransferType());

        boolean received = transfer.getReceived();
        Assert.assertFalse(received);

        transfer.received();
        Assert.assertTrue(transfer.getReceived());

        transfer=new Transfer(SystemDate.getSystemDate(), "6900000000", "6911111111", 30, ServiceType.TalkTime, TransferType.Outgoing);
        Assert.assertNotNull(transfer);
    }
}

