package gr.aueb.softeng.project1805.dao;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.softeng.project1805.bank.Account;
import gr.aueb.softeng.project1805.bank.AccountDAO;
import gr.aueb.softeng.project1805.domain.ActivePackage;
import gr.aueb.softeng.project1805.domain.Call;
import gr.aueb.softeng.project1805.domain.Data;
import gr.aueb.softeng.project1805.domain.SMS;
import gr.aueb.softeng.project1805.domain.ServiceType;
import gr.aueb.softeng.project1805.domain.ServiceUsage;
import gr.aueb.softeng.project1805.domain.Transfer;
import gr.aueb.softeng.project1805.domain.TransferType;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.domain.Package;
import gr.aueb.softeng.project1805.memorydao.MemoryInitializer;
import gr.aueb.softeng.project1805.utils.SystemDate;

public class DAOTest {

    private AccountDAO accountDAO;
    private ActivePackageDAO activePackageDAO;
    private PackageDAO packageDAO;
    private ServiceUsageDAO serviceUsageDAO;
    private StatisticsDAO statisticsDAO;
    private TransferDAO transferDAO;
    private UserDAO userDAO;

    private static final int INITIAL_USER_COUNT=3;
    private static final int INITIAL_PACKAGE_COUNT=6;

    @Before
    public void setUp() {
        Initializer memoryInitializer = new MemoryInitializer();
        memoryInitializer.prepareData();

        accountDAO= memoryInitializer.getAccountDAO();
        activePackageDAO= memoryInitializer.getActivePackageDAO();
        packageDAO= memoryInitializer.getPackageDAO();
        serviceUsageDAO= memoryInitializer.getServiceUsageDAO();
        statisticsDAO= memoryInitializer.getStatisticsDAO();
        transferDAO= memoryInitializer.getTransferDAO();
        userDAO= memoryInitializer.getUserDAO();
    }

    @Test
    public void findExistingUser() {
        User user=userDAO.find(0);
        Assert.assertEquals("6900000000", user.getPhoneNum());
    }

    @Test
    public void findNonExistingUser() {
        Assert.assertNull(userDAO.find(10));
    }

    @Test
    public void findAllUsers() {
        List<User> users=userDAO.findAll();
        Assert.assertEquals(INITIAL_USER_COUNT, users.size());
    }

    @Test
    public void saveUser() {
        User user=new User(userDAO.nextId(), "6933333333", "someone", "0000");
        userDAO.save(user);
        Assert.assertEquals(INITIAL_USER_COUNT, userDAO.findAll().size()-1);
        Assert.assertNotNull(userDAO.find(user.getUid()));
        Assert.assertTrue(userDAO.findAll().contains(user));
    }

    @Test
    public void deleteUser() {
        List<User> users=userDAO.findAll();
        User user=users.get(0);
        userDAO.delete(user);
        users=userDAO.findAll();
        Assert.assertEquals(INITIAL_USER_COUNT-1, users.size());
        Assert.assertNull(userDAO.find(user.getUid()));
    }

    @Test
    public void findExistingPackage() {
        Package pack=packageDAO.find(0);
        Assert.assertEquals("Surf", pack.getName());
    }

    @Test
    public void findNonExistingPackage() {
        Assert.assertNull(packageDAO.find(100));
    }

    @Test
    public void findAllPackages() {
        List<Package> packages=packageDAO.findAll();
        Assert.assertEquals(INITIAL_PACKAGE_COUNT, packages.size());
    }

    @Test
    public void savePackage() {
        Package pack=new Package(packageDAO.nextId(), "package", 30, 4.60f, ServiceType.Data, 200);
        packageDAO.save(pack);
        Assert.assertEquals(INITIAL_PACKAGE_COUNT, packageDAO.findAll().size()-1);
        Assert.assertNotNull(packageDAO.find(pack.getPackageId()));
        Assert.assertTrue(packageDAO.findAll().contains(pack));
    }

    @Test
    public void deletePackage() {
        List<Package> packages=packageDAO.findAll();
        Package pack=packages.get(0);
        packageDAO.delete(pack);
        packages=packageDAO.findAll();
        Assert.assertEquals(INITIAL_PACKAGE_COUNT-1, packages.size());
        Assert.assertNull(packageDAO.find(pack.getPackageId()));
    }

    @Test
    public void findExistingActivePackage() {
        ActivePackage activePackage=activePackageDAO.find(1, 0);
        Assert.assertEquals(80, activePackage.getRemainingQuantity());
    }

    @Test
    public void findNonExistingActivePackage() {
        ActivePackage activePackage=activePackageDAO.find(100, 0);
        Assert.assertNull(activePackage);
    }

    @Test
    public void findAllUserPackages() {
        List<ActivePackage> packages=activePackageDAO.findAllPackages(0);
        Assert.assertEquals(3, packages.size());
    }

    @Test
    public void findAllActivePackages() {
        List<ActivePackage> packages=activePackageDAO.findAllActive(0);
        Assert.assertEquals(1, packages.size());
    }

    @Test
    public void addUserToActivePackages() {
        activePackageDAO.addUser();
        Assert.assertNotNull(activePackageDAO.findAllPackages(INITIAL_USER_COUNT));
    }

    @Test
    public void saveActivePackage() {
        ActivePackage activePackage=new ActivePackage(2, SystemDate.getSystemDate(), true, 50);
        activePackageDAO.save(activePackage, 0);
        Assert.assertEquals(4, activePackageDAO.findAllPackages(0).size());
        Assert.assertNotNull(activePackageDAO.find(activePackage.getPackageId(), 0));
        Assert.assertTrue(activePackageDAO.findAllPackages(0).contains(activePackage));
        Assert.assertTrue(activePackageDAO.findAllActive(0).contains(activePackage));

        activePackage.setActive(false);
        Assert.assertFalse(activePackageDAO.findAllActive(0).contains(activePackage));
    }

    @Test
    public void deleteActivePackages() {
        List<ActivePackage> packages=activePackageDAO.findAllPackages(0);
        ActivePackage activePackage=packages.get(0);
        activePackageDAO.delete(activePackage, 0);
        packages=activePackageDAO.findAllPackages(0);
        Assert.assertEquals(2, packages.size());
        Assert.assertNull(activePackageDAO.find(activePackage.getPackageId(), 0));
    }

    @Test
    public void findAllTransfers() {
        List<Transfer> transfers=transferDAO.findAll(0);
        Assert.assertEquals(2, transfers.size());
    }

    @Test
    public void addUserToTransfers() {
        transferDAO.AddUser();
        Assert.assertNotNull(transferDAO.findAll(INITIAL_USER_COUNT));
    }

    @Test
    public void saveTransfer() {
        Transfer transfer=new Transfer(SystemDate.getSystemDate(), "6900000000", "6911111111", 20, ServiceType.Data, TransferType.Outgoing);
        transferDAO.save(transfer, 0);
        Assert.assertEquals(3, transferDAO.findAll(0).size());
        Assert.assertTrue(transferDAO.findAll(0).contains(transfer));
    }

    @Test
    public void deleteTransfer() {
        List<Transfer> transfers=transferDAO.findAll(0);
        Transfer transfer=transfers.get(0);
        transferDAO.delete(transfer, 0);
        transfers=transferDAO.findAll(0);
        Assert.assertEquals(1, transfers.size());

    }

    @Test
    public void findAllServiceUsage() {
        List<ServiceUsage> allServiceUsage=serviceUsageDAO.findAll(0);
        Assert.assertEquals(3, allServiceUsage.size());
    }

    @Test
    public void findAllDataUsage() {
        List<ServiceUsage> allDataUsage=serviceUsageDAO.findData(0);
        Assert.assertEquals(2, allDataUsage.size());
    }

    @Test
    public void findAllCallsUsage() {
        List<ServiceUsage> allCallsUsage=serviceUsageDAO.findCalls(0);
        Assert.assertEquals(0, allCallsUsage.size());
    }

    @Test
    public void findAllSMSUsage() {
        List<ServiceUsage> allSMSUsage=serviceUsageDAO.findSMS(0);
        Assert.assertEquals(1, allSMSUsage.size());
    }

    @Test
    public void addUserToServiceUsage() {
        serviceUsageDAO.addUser();
        Assert.assertNotNull(serviceUsageDAO.findAll(INITIAL_USER_COUNT));
    }

    @Test
    public void saveServiceUsage() {
        ServiceUsage data=new Data(SystemDate.getSystemDate(), 50);
        serviceUsageDAO.save(data, 0);
        Assert.assertEquals(4, serviceUsageDAO.findAll(0).size());
        Assert.assertTrue(serviceUsageDAO.findAll(0).contains(data));
        Assert.assertTrue(serviceUsageDAO.findData(0).contains(data));

        ServiceUsage call=new Call(SystemDate.getSystemDate(), 50, "6911111111");
        serviceUsageDAO.save(call, 0);
        Assert.assertEquals(5, serviceUsageDAO.findAll(0).size());
        Assert.assertTrue(serviceUsageDAO.findAll(0).contains(call));
        Assert.assertTrue(serviceUsageDAO.findCalls(0).contains(call));

        ServiceUsage sms=new SMS(SystemDate.getSystemDate(), "6911111111");
        serviceUsageDAO.save(sms, 0);
        Assert.assertEquals(6, serviceUsageDAO.findAll(0).size());
        Assert.assertTrue(serviceUsageDAO.findAll(0).contains(sms));
        Assert.assertTrue(serviceUsageDAO.findSMS(0).contains(sms));

    }

    @Test
    public void deleteServiceUsage() {
        List<ServiceUsage> allServiceUsage=serviceUsageDAO.findAll(0);
        ServiceUsage serviceUsage=allServiceUsage.get(0);
        serviceUsageDAO.delete(serviceUsage, 0);
        allServiceUsage=serviceUsageDAO.findAll(0);
        Assert.assertEquals(2, allServiceUsage.size());
    }

    @Test
    public void findAllStatistics() {
        List<float[]> statistics=statisticsDAO.findAll(0);
        Assert.assertEquals(2, statistics.size());
    }

    @Test
    public void addUserStatistics() {
        statisticsDAO.addUser();
        Assert.assertNotNull(statisticsDAO.findAll(INITIAL_USER_COUNT));
    }

    @Test
    public void addMonth() {
        //empty
        statisticsDAO.addUser();
        statisticsDAO.addMonth(INITIAL_USER_COUNT);
        Assert.assertEquals(1, statisticsDAO.findAll(INITIAL_USER_COUNT).size());

        //add
        Calendar date=SystemDate.getSystemDate();
        date.set(Calendar.MONTH, 5);
        SystemDate.setSystemDate(date);
        statisticsDAO.addMonth(0);
        Assert.assertEquals(3, statisticsDAO.findAll(0).size());

        //does not add
        date.set(Calendar.MONTH, 3);
        SystemDate.setSystemDate(date);
        statisticsDAO.addMonth(0);
        Assert.assertEquals(3, statisticsDAO.findAll(0).size());
    }

    @Test
    public void addMonthSimple() {
        statisticsDAO.addUser();
        statisticsDAO.addMonthSimple(INITIAL_USER_COUNT);
        Assert.assertEquals(1, statisticsDAO.findAll(INITIAL_USER_COUNT).size());
    }

    @Test
    public void saveStatistic() {
        Calendar date=SystemDate.getSystemDate();
        date.set(Calendar.MONTH, 3);
        SystemDate.setSystemDate(date);

        statisticsDAO.save(0, 2, 10);
        Assert.assertEquals(18.90, statisticsDAO.findAll(0).get(1)[2], 0.01);

        statisticsDAO.save(0, 0, 2020);
        Assert.assertEquals(2020, statisticsDAO.findAll(0).get(1)[0], 0);
    }

    @Test
    public void deleteMonthStatistics() {
        List<float[]> statistics=statisticsDAO.findAll(0);
        float[] monthStatistics=statistics.get(0);
        statisticsDAO.delete(monthStatistics, 0);
        statistics=statisticsDAO.findAll(0);
        Assert.assertEquals(1, statistics.size());
    }

    @Test
    public void findExistingAccount(){
        Account account=accountDAO.find("0000 0000 0007");
        Assert.assertEquals(1.90, account.getBalance(), 0.001);
    }

    @Test
    public void findNonExistingAccount(){
        Account account=accountDAO.find("a");
        Assert.assertNull(account);
    }

    @Test
    public void findAllAccounts() {
        List<Account> accounts=accountDAO.findAll();
        Assert.assertEquals(3, accounts.size());
    }

    @Test
    public void saveAccount() {
        Account account=new Account("3333 3333 3333", 0.90);
        accountDAO.save(account);
        Assert.assertEquals(4, accountDAO.findAll().size());
        Assert.assertNotNull(accountDAO.find(account.getCard_num()));
        Assert.assertTrue(accountDAO.findAll().contains(account));
    }

    @Test
    public void deleteAccount() {
        List<Account> accounts=accountDAO.findAll();
        Account account=accounts.get(0);
        accountDAO.delete(account);
        accounts=accountDAO.findAll();
        Assert.assertEquals(2, accounts.size());
        Assert.assertNull(accountDAO.find(account.getCard_num()));
    }

    @Test
    public void getUser () {
        Initializer memoryInitializer = new MemoryInitializer();
        User user=memoryInitializer.getUser(0);

        List<ActivePackage> activePackage=user.getPackages();
        List<ActivePackage> activePackageList=activePackageDAO.findAllPackages(0);
        for (int i=0; i<activePackage.size(); i++) {
            Assert.assertEquals(activePackage.get(i).getPackageId(), activePackageList.get(i).getPackageId());
        }

        List<Transfer> transfer=user.getTransfers();
        List<Transfer> transferList=transferDAO.findAll(0);
        for (int i=0; i<transfer.size(); i++) {
            Assert.assertEquals(transfer.get(i).getQuantity(), transferList.get(i).getQuantity());
        }

        List<ServiceUsage> serviceUsage=user.getServices();
        List<ServiceUsage> serviceUsageList=serviceUsageDAO.findAll(0);
        for (int i=0; i<serviceUsage.size(); i++) {
            Assert.assertEquals(serviceUsage.get(i).getDate().get(Calendar.DATE), serviceUsageList.get(i).getDate().get(Calendar.DATE));
        }
    }

    @Test
    public void getStatistics() {
        Initializer memoryInitializer = new MemoryInitializer();
        List<float[]> userStatistics = memoryInitializer.getUserStatistics(0);
        List<float[]> statistics=statisticsDAO.findAll(0);
        for (int i=0; i<statistics.size(); i++) {
            Assert.assertEquals(statistics.get(i)[1], userStatistics.get(i)[1], 0.001f);
        }
    }
}

