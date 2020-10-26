package gr.aueb.softeng.project1805.domain;

import junit.framework.Assert;

import org.junit.Test;

public class PackageTest {

    @Test
    public void memberFunctions() {

        Package pack=new Package();

        pack.setPackageId(0);
        Assert.assertEquals(0, pack.getPackageId());

        pack.setName("pack");
        Assert.assertEquals("pack", pack.getName());

        pack.setDuration(30);
        Assert.assertEquals(30, pack.getDuration());

        pack.setPrice(1.90f);
        Assert.assertEquals(1.90, pack.getPrice(), 0.001f);

        pack.setServiceType(ServiceType.TalkTime);
        Assert.assertEquals(ServiceType.TalkTime, pack.getServiceType());

        pack.setQuantity(20);
        Assert.assertEquals(20, pack.getQuantity());

        pack=new Package(0, "pack", 30, 1.90f, ServiceType.TalkTime, 20);
        Assert.assertNotNull(pack);
    }
}

