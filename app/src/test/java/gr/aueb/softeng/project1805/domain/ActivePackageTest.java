package gr.aueb.softeng.project1805.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

import gr.aueb.softeng.project1805.utils.SystemDate;

public class ActivePackageTest {

    @Test
    public void memberFunctions() {
        ActivePackage activePackage=new ActivePackage();

        activePackage.setPackageId(0);
        Assert.assertEquals(0, activePackage.getPackageId());

        activePackage.setActivationDate(SystemDate.getSystemDate());
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.YEAR), activePackage.getActivationDate().get(Calendar.YEAR));
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.MONTH), activePackage.getActivationDate().get(Calendar.MONTH));
        Assert.assertEquals(SystemDate.getSystemDate().get(Calendar.DATE), activePackage.getActivationDate().get(Calendar.DATE));

        activePackage.setActive(true);
        Assert.assertEquals(true, activePackage.isActive());

        activePackage.setRemainingQuantity(1);
        Assert.assertEquals(1, activePackage.getRemainingQuantity());

        activePackage=new ActivePackage(0, SystemDate.getSystemDate(), true, 1);
        Assert.assertNotNull(activePackage);
    }
}

