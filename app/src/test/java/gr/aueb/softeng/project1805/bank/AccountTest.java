package gr.aueb.softeng.project1805.bank;

import junit.framework.Assert;

import org.junit.Test;

public class AccountTest {

    @Test
    public void memberFunctions() {

        Account account=new Account();

        account.setCard_num("0000 0000 0007");
        Assert.assertEquals("0000 0000 0007", account.getCard_num());

        account.setBalance(30.80f);
        Assert.assertEquals(30.80f, account.getBalance(), 0.001f);

        account=new Account("0000 0000 0007", 30.80f);
        Assert.assertNotNull(account);
    }
}

