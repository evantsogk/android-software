package gr.aueb.softeng.project1805.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

import gr.aueb.softeng.project1805.utils.SystemDate;

public class CallTest {

    @Test
    public void memberFunctions() {

        Call call=new Call();

        call.setDate(SystemDate.getSystemDate());
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.YEAR), call.getDate().get(Calendar.YEAR));
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.MONTH), call.getDate().get(Calendar.MONTH));
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.DATE), call.getDate().get(Calendar.DATE));

        call.setDuration(20);
        Assert.assertEquals(20, call.getDuration());

        call.setCalledPhone("6900000000");
        Assert.assertEquals("6900000000", call.getCalledPhone());

        call=new Call(SystemDate.getSystemDate(), 20, "6900000000");
        Assert.assertNotNull(call);
    }
}

