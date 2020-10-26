package gr.aueb.softeng.project1805.domain;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Calendar;

import gr.aueb.softeng.project1805.utils.SystemDate;

public class SMSTest {

    @Test
    public void memberFunctions() {

        SMS sms=new SMS();

        sms.setDate(SystemDate.getSystemDate());
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.YEAR),sms.getDate().get(Calendar.YEAR));
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.MONTH),sms.getDate().get(Calendar.MONTH));
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.DATE),sms.getDate().get(Calendar.DATE));

        sms.setReceiverPhoneNum("6911111111");
        Assert.assertEquals("6911111111", sms.getReceiverPhoneNum());

        sms=new SMS(SystemDate.getSystemDate(), "6911111111");
        Assert.assertNotNull(sms);
    }
}

