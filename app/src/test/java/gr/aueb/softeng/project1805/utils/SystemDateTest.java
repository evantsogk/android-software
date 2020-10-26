package gr.aueb.softeng.project1805.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class SystemDateTest {
    private static final int YEAR=2018;
    private static final int MONTH=2;
    private static final int DATE=12;

    @Before
    public void setUp() {
        SystemDate.removeSystemDate();
    }

    @Test
    public void testWithoutStub() {
        Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), SystemDate.getSystemDate().get(Calendar.YEAR));
        Assert.assertEquals(Calendar.getInstance().get(Calendar.MONTH), SystemDate.getSystemDate().get(Calendar.MONTH));
        Assert.assertEquals(Calendar.getInstance().get(Calendar.DATE), SystemDate.getSystemDate().get(Calendar.DATE));
    }

    @Test
    public void testWithStub() {
        Calendar stub=Calendar.getInstance();
        stub.set(YEAR, MONTH, DATE);

        SystemDate.setSystemDate(stub);
        Assert.assertEquals(stub.get(Calendar.YEAR), SystemDate.getSystemDate().get(Calendar.YEAR));
        Assert.assertEquals(stub.get(Calendar.MONTH), SystemDate.getSystemDate().get(Calendar.MONTH));
        Assert.assertEquals(stub.get(Calendar.DATE), SystemDate.getSystemDate().get(Calendar.DATE));
    }
}

