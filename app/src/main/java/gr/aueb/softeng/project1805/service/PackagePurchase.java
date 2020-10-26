package gr.aueb.softeng.project1805.service;

import java.util.ArrayList;

import gr.aueb.softeng.project1805.bank.Account;
import gr.aueb.softeng.project1805.bank.AccountDAO;
import gr.aueb.softeng.project1805.bank.AccountDAOMemory;
import gr.aueb.softeng.project1805.dao.ActivePackageDAO;
import gr.aueb.softeng.project1805.dao.StatisticsDAO;
import gr.aueb.softeng.project1805.dao.UserDAO;
import gr.aueb.softeng.project1805.domain.Package;
import gr.aueb.softeng.project1805.domain.ActivePackage;
import gr.aueb.softeng.project1805.domain.ServiceType;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.ActivePackageDAOMemory;
import gr.aueb.softeng.project1805.memorydao.StatisticsDAOMemory;
import gr.aueb.softeng.project1805.memorydao.UserDAOMemory;
import gr.aueb.softeng.project1805.utils.SystemDate;

/**
 * Η υπηρεσία της αγοράς πακέτου.
 */
public class PackagePurchase {
    /**
     * Ο προκαθορισμένος κατασκευαστής
     */
    public PackagePurchase() {}

    /**
     * Αναλαμβάνει την προσθήκη του επιθυμητού πακέτου στη λίστα με τα ενεργά πακέτα του χρήστη,
     * την ανανέωση του υπολοίπου του ανάλογα με το πακέτο που αγόρασε και τη μείωση του υπολοίπου της κάρτας του.
     * @param user Ο χρήστης που επιθυμεί να αγοράσει το πακέτο
     * @param pack Το πακέτο προς αγορά
     * @param card_num Ο αριθμός κάρτας του χρήστη
     * @return Το ενεργό πακέτο που προστέθηκε στη λίστα του χρήστη εάν είναι δυνατή η αγορά δηλαδή αν
     * είνα επαρκές το υπόλοιπο της κάρτας του χρήστη και εφόσον δεν είναι ήδη ενεργοποιημένο το πακέτο που
     * επιθυμεί να αγοράσει
     * Επιστρέφει {@code null} αν δεν είναι δυνατή η αγορά
     */
    public ActivePackage purchasePackage(User user, Package pack, String card_num) {
        boolean alreadyPurchased=false;
        ActivePackageDAO activePackageDAO = new ActivePackageDAOMemory();
        ArrayList<ActivePackage> activePackages=(ArrayList<ActivePackage>) activePackageDAO.findAllActive(user.getUid());
        for (ActivePackage activePackage : activePackages) {
            if (activePackage.getPackageId()==pack.getPackageId()) {
                alreadyPurchased=true;
                break;
            }
        }

        if (!alreadyPurchased) {
            boolean paymentSuccessful=payment(card_num, pack.getPrice());
            if (paymentSuccessful) {
                ActivePackage purchasedPackage = new ActivePackage(pack.getPackageId(), SystemDate.getSystemDate(), true, pack.getQuantity());
                user.addPackage(purchasedPackage);
                activePackageDAO.save(purchasedPackage, user.getUid());

                UserDAO userDAO = new UserDAOMemory();
                ServiceType serviceType = pack.getServiceType();
                if (serviceType == ServiceType.Data) {
                    user.setRemainingData(user.getRemainingData() + pack.getQuantity());
                } else if (serviceType == ServiceType.TalkTime) {
                    user.setRemainingTalkTime(user.getRemainingTalkTime() + pack.getQuantity());
                } else {
                    user.setRemainingSMS(user.getRemainingSMS() + pack.getQuantity());
                }

                StatisticsDAO statisticsDAO=new StatisticsDAOMemory();
                statisticsDAO.save(user.getUid(), 2, pack.getPrice());

                return purchasedPackage;
            }
        }
        return null;
    }

    /**
     * Ελέγχει αν είναι επαρκές το υπόλοιπο ενός λογαριασμού για την αγορά πακέτου με προκαθορισμένη τιμή
     * εφόσον ο αριθμός της κάρτας αντιστοιχεί σε κάποιον λογαριασμό και αφαιρεί την τιμή.
     * @param card_num Ο αριθμός της κάρτας
     * @param price Η τιμή του πακέτου
     * @return {@code true} αν είναι επαρκές το υπόλοιπο της κάρτας
     */
    public boolean payment(String card_num, float price) {
        AccountDAO accountDAO = new AccountDAOMemory();

        Account acc=accountDAO.find(card_num);
        if (acc!=null && acc.getBalance()>=price) {
            acc.setBalance(acc.getBalance()-price);
            return true;
        }
        return false;
    }

    /**
     * Ελέγχει αν είναι επαρκές το υπόλοιπο ενός λογαριασμού για την αγορά πακέτου με προκαθορισμένη τιμή
     * εφόσον ο αριθμός της κάρτας αντιστοιχεί σε κάποιον λογαριασμό, χωρίς να αφαιρέσει την τιμή.
     * @param card_num Ο αριθμός της κάρτας
     * @param price Η τιμή του πακέτου
     * @return {@code true} αν είναι επαρκές το υπόλοιπο της κάρτας
     */
    public boolean simpleCheckPayment(String card_num, float price) {
        AccountDAO accountDAO = new AccountDAOMemory();

        Account acc=accountDAO.find(card_num);
        if (acc!=null && acc.getBalance()>=price) {
            return true;
        }
        return false;
    }
}

