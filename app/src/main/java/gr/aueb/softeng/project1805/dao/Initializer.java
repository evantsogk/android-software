package gr.aueb.softeng.project1805.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gr.aueb.softeng.project1805.bank.Account;
import gr.aueb.softeng.project1805.bank.AccountDAO;
import gr.aueb.softeng.project1805.domain.ActivePackage;
import gr.aueb.softeng.project1805.domain.Call;
import gr.aueb.softeng.project1805.domain.Data;
import gr.aueb.softeng.project1805.domain.Package;
import gr.aueb.softeng.project1805.domain.SMS;
import gr.aueb.softeng.project1805.domain.ServiceType;
import gr.aueb.softeng.project1805.domain.ServiceUsage;
import gr.aueb.softeng.project1805.domain.Transfer;
import gr.aueb.softeng.project1805.domain.TransferType;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.utils.SystemDate;

/**
 * Κλάση που αναλαμβάνει να αρχικοποιήσει τα δεδομένα της βάσης δεδομένων
 * Βοηθητική κλάση που παρέχει δεδομένα για τα παραδείγματα και τις δοκιμασίες ελέγχου
 *
 *--------------------------------USERS------------------------------------
 *
 * Id: 0, phone: 6900000000, username: Baggelis, password: b12345, Data: 80, TalkTime: 0, SMS: 10
 * Id: 1, phone: 6911111111, username: Katerina, password: k12345, Data: 0, TalkTime: 0, SMS: 600
 * Id: 2, phone: 6922222222, username: Mike, password: m12345, Data: 20, TalkTime: 50, SMS: 0
 *
 *------------------------------PACKAGES----------------------------------
 *
 * ----Data----
 * Id: 0, name: Surf, duration: 30 days, price: 4,90, serviceType: Data, quantity: 500 MB
 * Id: 1, name: SurfBonus, duration: 30 days, price: 8,90, serviceType: Data, quantity: 1000 MB
 *
 * ----TalkTime----
 * Id: 2, name: Talk, duration: 30 days, price: 7,50, serviceType: TalkTime, quantity: 100 min
 * Id: 3, name: TalkBonus, duration: 30 days, price: 18,90, serviceType: TalkTime, quantity: 300 min
 *
 * ----SMS----
 * Id: 4, name: Message, duration: 30 days, price: 3,20, serviceType: SMS, quantity: 100
 * Id: 5, name: MessageBonus, duration: 30 days, price: 9,90, serviceType: SMS, quantity: 500
 *
 * --------------------------ACTIVE PACKAGES-------------------------------
 * { user 0
 *     Id: 1, activationDate: 16/4/2018, isActive: true, remainingQuantity: 80 MB
 *     Id: 1, activationDate: 3/2/2018, isActive: false, remainingQuantity: 0 MB
 *     Id: 3, activationDate: 5/12/2017, isActive: false, remainingQuantity: 0 min
 * }
 *
 * {user 1
 *     Id: 4, activationDate: 15/4/2018, isActive: true, remainingQuantity: 1
 *     Id: 5, activationDate: 15/4/2018, isActive: true, remainingQuantity: 500
 *     Id: 4, activationsDate: 16/4/2018, isActive: false, remainingQuantity: 0
 * }
 *
 * {user 2
 *     Id: 2, activationDate: 12/4/2018, isActive: true, remainingQuantity: 20 min
 *     Id: 3, activationDate: 16/4/2018, isActive: true, remainingQuantity: 100 min
 *     Id: 0, activationDate: 25/2/2018, isActive: false, remainingQuantity: 0 MB
 * }
 *
 * -----------------------------TRANSFERS---------------------------------------
 *
 * { user 0
 *     date: 12/2/2018, senderNum: 6900000000, receiverNum: 6922222222, quantity: 50 MB, serviceType: Data, transferType: outgoing
 *     date: 19/3/2018, senderNum: 6911111111, receiverNum: 6900000000, quantity: 10, serviceType: SMS, transferType: incoming
 * }
 *
 * { user 1
 *     date: 19/3/2018, senderNum: 6911111111, receiverNum: 6900000000, quantity: 10, serviceType: SMS, transferType: outgoing
 * }
 *
 * { user 2
 *     date: 12/2/2018, senderNum: 6900000000, receiverNum: 6922222222, quantity: 50 MB, serviceType: Data, transferType: incoming
 * }
 *
 * ----------------------------SERVICE USAGE------------------------------------
 *
 * { user 0
 *     date: 21/3/2018, dataVolume: 30 MB
 *     date: 22/3/2018, dataVolume: 50 MB
 *     date: 2/4/2018, receiverPhone: 6911111111
 * }
 *
 * { user 1
 *     date: 3/4/2018, receiverPhone: 6900000000
 *     date: 15/4/2018, receiverPhone: 6922222222
 * }
 *
 * { user 2
 *     date: 5/4/2018, dataVolume: 50 MB
 *     date: 13/3/2018, calledPhone: 6900000000, duration: 15 min
 *     date: 14/3/2018, calledPhone: 6922222222, duration: 30 min
 * }
 *
 * -----------------------------STATISTICS---------------------------------------
 *
 * { user 0
 *     year: 2018, month: 2, price: 4,90, data: 100 MB, talkTime: 0, SMS: 1
 *     year: 2018, month: 4, price: 8,90, data: 200 MB, talkTime: 0, SMS: 0
 * }
 *
 * { user 1
 *     year: 2018, month: 4, price: 9,90, data: 0 MB, talkTime: 0, SMS: 8
 *     year: 2018, month: 2, price: 3,20, data: 0 MB, talkTime: 0, SMS: 5
 * }
 *
 * { user 2
 *     year: 2018, month: 3, price: 5,90, data: 50 MB, talkTime: 40, SMS: 0
 *     year: 2018, month: 4, price: 9,90, data: 0 MB, talkTime: 70, SMS: 0
 *
 * }
 *
 * ---------------------------BANK ACCOUNTS-------------------------------------
 *
 * card_num: 0000 0000 0007, balance: 1,90
 * card_num: 1111 1111 1118, balance: 20,30
 * card_num: 2222 2222 2229, balance: 15,15
 *
 * */

public abstract class Initializer {

      public void prepareData() {
        // πριν εισάγουμε τα δεδομένα διαγράφουμε ότι υπάρχει
        eraseData();

        Calendar calendar;

        UserDAO userDAO = getUserDAO();
        User user=new User(0, "6900000000", "Baggelis", "b12345");
        user.setRemainingData(80);
        user.setRemainingTalkTime(0);
        user.setRemainingSMS(10);
        userDAO.save(user);

        user=new User(1, "6911111111", "Katerina", "k12345");
        user.setRemainingData(0);
        user.setRemainingTalkTime(0);
        user.setRemainingSMS(600);
        userDAO.save(user);

        user=new User(2, "6922222222", "Mike", "m12345");
        user.setRemainingData(20);
        user.setRemainingTalkTime(50);
        user.setRemainingSMS(0);
        userDAO.save(user);

        PackageDAO packageDAO = getPackageDAO();
        packageDAO.save(new Package(0, "Surf", 30, 4.90f, ServiceType.Data, 500));
        packageDAO.save(new Package(1, "SurfBonus", 30, 8.90f, ServiceType.Data, 1000));
        packageDAO.save(new Package(2, "Talk", 30, 7.50f, ServiceType.TalkTime, 100));
        packageDAO.save(new Package(3, "TalkBonus", 30, 18.90f, ServiceType.TalkTime, 300));
        packageDAO.save(new Package(4, "Message", 30, 3.20f, ServiceType.SMS, 100));
        packageDAO.save(new Package(5, "MessageBonus", 30, 9.90f, ServiceType.SMS, 500));

        ActivePackageDAO activePackageDAO = getActivePackageDAO();
        activePackageDAO.addUser();
        activePackageDAO.addUser();
        activePackageDAO.addUser();
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 3, 16);
        activePackageDAO.save(new ActivePackage(1, calendar, true, 80), 0);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 1, 3);
        activePackageDAO.save(new ActivePackage(1, calendar, false, 0), 0);
        calendar = SystemDate.getSystemDate();
        calendar.set(2017, 11, 5);
        activePackageDAO.save(new ActivePackage(3, calendar, false, 0), 0);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 3, 15);
        activePackageDAO.save(new ActivePackage(4, calendar, true, 1), 1);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 3, 15);
        activePackageDAO.save(new ActivePackage(5, calendar, true, 500), 1);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 3, 16);
        activePackageDAO.save(new ActivePackage(4, calendar, false, 0), 1);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 3, 12);
        activePackageDAO.save(new ActivePackage(2, calendar, true, 20), 2);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 3, 16);
        activePackageDAO.save(new ActivePackage(3, calendar, true, 100), 2);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 1, 25);
        activePackageDAO.save(new ActivePackage(0, calendar, false, 0), 2);

        TransferDAO transferDAO =getTransferDAO();
        transferDAO.AddUser();
        transferDAO.AddUser();
        transferDAO.AddUser();
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 1, 12);
        transferDAO.save(new Transfer(calendar, "6900000000", "6922222222", 50, ServiceType.Data, TransferType.Outgoing), 0);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 2, 19);
        transferDAO.save(new Transfer(calendar, "6911111111", "6900000000", 10, ServiceType.SMS, TransferType.Incoming), 0);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 2, 19);
        transferDAO.save(new Transfer(calendar, "6911111111", "6900000000", 10, ServiceType.SMS, TransferType.Outgoing), 1);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 1, 16);
        transferDAO.save(new Transfer(calendar, "6900000000", "6922222222", 50, ServiceType.Data, TransferType.Incoming), 2);

        ServiceUsageDAO serviceUsageDAO = getServiceUsageDAO();
        serviceUsageDAO.addUser();
        serviceUsageDAO.addUser();
        serviceUsageDAO.addUser();
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 2, 21);
        serviceUsageDAO.save(new Data(calendar, 30), 0);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 2, 22);
        serviceUsageDAO.save(new Data(calendar, 50), 0);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 3, 2);
        serviceUsageDAO.save(new SMS(calendar, "6911111111"), 0);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 3, 3);
        serviceUsageDAO.save(new SMS(calendar, "6900000000"), 1);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 3, 15);
        serviceUsageDAO.save(new SMS(calendar, "6922222222"), 1);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 4, 5);
        serviceUsageDAO.save(new Data(calendar, 50), 2);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 2, 13);
        serviceUsageDAO.save(new Call(calendar, 15, "6900000000"), 2);
        calendar = SystemDate.getSystemDate();
        calendar.set(2018, 2, 14);
        serviceUsageDAO.save(new Call(calendar, 30, "6922222222"), 2);

        StatisticsDAO statisticsDAO = getStatisticsDAO();
        statisticsDAO.addUser();
        statisticsDAO.addUser();
        statisticsDAO.addUser();
        calendar = SystemDate.getSystemDate();
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.MONTH, 0);
        SystemDate.setSystemDate(calendar);
        statisticsDAO.addMonth(0);
        statisticsDAO.save(0, 2, 4.90f);
        statisticsDAO.save(0, 3, 100);
        statisticsDAO.save(0, 5, 1);

        calendar = SystemDate.getSystemDate();
        calendar.set(Calendar.MONTH, 2);
        SystemDate.setSystemDate(calendar);
        statisticsDAO.addMonth(0);
        statisticsDAO.save(0, 2, 8.90f);
        statisticsDAO.save(0, 3, 200);

        calendar = SystemDate.getSystemDate();
        calendar.set(Calendar.MONTH, 2);
        SystemDate.setSystemDate(calendar);
        statisticsDAO.addMonth(1);
        statisticsDAO.save(1, 2, 9.90f);
        statisticsDAO.save(1, 5, 8);

        calendar = SystemDate.getSystemDate();
        calendar.set(Calendar.MONTH, 0);
        SystemDate.setSystemDate(calendar);
        statisticsDAO.addMonth(1);
        statisticsDAO.save(1, 2, 3.20f);
        statisticsDAO.save(1, 5, 5);

        calendar = SystemDate.getSystemDate();
        calendar.set(Calendar.MONTH, 1);
        SystemDate.setSystemDate(calendar);
        statisticsDAO.addMonth(2);
        statisticsDAO.save(2, 2, 5.90f);
        statisticsDAO.save(2, 3, 50);
        statisticsDAO.save(2, 4, 40);

        calendar = SystemDate.getSystemDate();
        calendar.set(Calendar.MONTH, 2);
        SystemDate.setSystemDate(calendar);
        statisticsDAO.addMonth(2);
        statisticsDAO.save(2, 2, 9.90f);
        statisticsDAO.save(2, 4, 70);

        AccountDAO accountDAO=getAccountDAO();
        accountDAO.save(new Account("0000 0000 0007", 1.90f));
        accountDAO.save(new Account("1111 1111 1118", 20.30f));
        accountDAO.save(new Account("2222 2222 2229", 15.15f));
    }

    //διαγράφουμε όλα τα δεδομένα στη βάση δεδομένων
    public abstract void eraseData();

  /**
   * Προσθέτει τα active packages, transfers, service usages του χρήστη στις αντίστοιχες λίστες
   * τα οποία τα βρίσκει στις εξωτερικές λίστες.
   * @param user Ο χρήστης
   * @return Ο χρήστης
   */
    public User getUser(int user) {
        User loggedInUser = getUserDAO().find(user);

        for (ActivePackage activePackage : getActivePackageDAO().findAllPackages(user)) {
            loggedInUser.addPackage(activePackage);
        }

        for (Transfer transfer : getTransferDAO().findAll(user)) {
            loggedInUser.addTransfer(transfer);
        }

        for (ServiceUsage serviceUsage : getServiceUsageDAO().findAll(user)) {
            loggedInUser.addService(serviceUsage);
        }
        return loggedInUser;
    }

  /**
   * Επιστρέφει μια λίστα με τα στατιστικά στοιχεία του χρήστη που βρίσκει
   * σε εξωτερική λίστα.
   * @param user Ο χρήστης
   * @return Η λίστα με τα στατιστικά στοιχεία του χρήστη
   */
    public List<float[]> getUserStatistics(int user) {
        ArrayList<float[]> statistics=new ArrayList<>();
        statistics.addAll(getStatisticsDAO().findAll(user));
        return statistics;
    }
   /**
    * Επιστρέφει την εξωτερική λίστα με τους χρήστες.
    * @return Η εξωτερική λίστα με τους χρήστες
    */
    public abstract UserDAO getUserDAO();

   /**
    * Επιστρέφει την εξωτερική λίστα με τα πακέτα.
    * @return Η εξωτερική λίστα με τα πακέτα
    */
    public abstract PackageDAO getPackageDAO();

   /**
    * Επιστρέφει την εξωτερική λίστα με τα ενεργά πακέτα των χρηστών
    * @return Η εξωτερική λίστα με τα ενερά πακέτα των χρηστών
    */
    public abstract ActivePackageDAO getActivePackageDAO();

   /**
    * Επιστρέφει την εξωτερική λίστα με τις παραχωρήσεις των χρηστών.
    * @return Η εξωτερική λίστα με τις παραχωρήσεις των χρηστών
    */
    public abstract TransferDAO getTransferDAO();

   /**
    * Επιστρέφει την εξωτερική λίστα με τα service usages των χρηστών.
    * @return Η εξωτερική λίστα με τα service usages των χρηστων
    */
    public abstract ServiceUsageDAO getServiceUsageDAO();

   /**
    * Επιστρέφει την εξωτερική λίστα με τα στατιστικά στοιχεία των χρηστών.
    * @return Η εξωτερική λίστα με τα στατιστικά στοιχεία των χρηστών
    */
    public abstract StatisticsDAO getStatisticsDAO();

  /**
   * Επιστρέφει την εξωτερική λίστα με τους λογαριασμούς.
   * @return Η εξωτερική λίστα με τους λογαριασμούς
   */
    public abstract AccountDAO getAccountDAO();
}

