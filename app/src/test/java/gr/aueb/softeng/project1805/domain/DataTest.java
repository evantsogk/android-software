package gr.aueb.softeng.project1805.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

import gr.aueb.softeng.project1805.utils.SystemDate;

public class DataTest {

    @Test
    public void memberFunctions() {

        Data data=new Data();

        data.setDate(SystemDate.getSystemDate());
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.YEAR), data.getDate().get(Calendar.YEAR));
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.MONTH), data.getDate().get(Calendar.MONTH));
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.DATE), data.getDate().get(Calendar.DATE));

        data.setDataVolume(40);
        Assert.assertEquals(40, data.getDataVolume());

        data=new Data(SystemDate.getSystemDate(), 40);
        Assert.assertNotNull(data);
    }
}

