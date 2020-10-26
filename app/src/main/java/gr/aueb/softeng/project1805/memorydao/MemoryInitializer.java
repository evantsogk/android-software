package gr.aueb.softeng.project1805.memorydao;

import gr.aueb.softeng.project1805.bank.AccountDAO;
import gr.aueb.softeng.project1805.bank.AccountDAOMemory;
import gr.aueb.softeng.project1805.dao.ActivePackageDAO;
import gr.aueb.softeng.project1805.dao.Initializer;
import gr.aueb.softeng.project1805.dao.PackageDAO;
import gr.aueb.softeng.project1805.dao.ServiceUsageDAO;
import gr.aueb.softeng.project1805.dao.StatisticsDAO;
import gr.aueb.softeng.project1805.dao.TransferDAO;
import gr.aueb.softeng.project1805.dao.UserDAO;
import gr.aueb.softeng.project1805.utils.SystemDate;


public class MemoryInitializer extends Initializer {

    public MemoryInitializer() {}

    /**
     * Διαγράφουμε όλα τα δεδομένα στη βάση δεδομένων
     */
    public void eraseData() {

        SystemDate.removeSystemDate();

        getAccountDAO().deleteAll();
        getActivePackageDAO().deleteAll();
        getPackageDAO().deleteAll();
        getServiceUsageDAO().deleteAll();
        getStatisticsDAO().deleteAll();
        getTransferDAO().deleteAll();
        getUserDAO().deleteAll();
    }

    /**
     * Επιστρέφει την εξωτερική λίστα με τους χρήστες.
     * @return Η εξωτερική λίστα με τους χρήστες
     */
    public UserDAO getUserDAO() {
        return new UserDAOMemory();
    }

    /**
     * Επιστρέφει την εξωτερική λίστα με τα πακέτα.
     * @return Η εξωτερική λίστα με τα πακέτα
     */
    public PackageDAO getPackageDAO() {
        return new PackageDAOMemory();
    }

    /**
     * Επιστρέφει την εξωτερική λίστα με τα ενεργά πακέτα των χρηστών
     * @return Η εξωτερική λίστα με τα ενερά πακέτα των χρηστών
     */
    public ActivePackageDAO getActivePackageDAO() {
        return new ActivePackageDAOMemory();
    }

    /**
     * Επιστρέφει την εξωτερική λίστα με τις παραχωρήσεις των χρηστών.
     * @return Η εξωτερική λίστα με τις παραχωρήσεις των χρηστών
     */
    public TransferDAO getTransferDAO() {
        return new TransferDAOMemory();
    }

    /**
     * Επιστρέφει την εξωτερική λίστα με τα service usages των χρηστών.
     * @return Η εξωτερική λίστα με τα service usages των χρηστων
     */
    public ServiceUsageDAO getServiceUsageDAO() {
        return new ServiceUsageDAOMemory();
    }

    /**
     * Επιστρέφει την εξωτερική λίστα με τα στατιστικά στοιχεία των χρηστών.
     * @return Η εξωτερική λίστα με τα στατιστικά στοιχεία των χρηστών
     */
    public StatisticsDAO getStatisticsDAO() {
        return new StatisticsDAOMemory();
    }

    /**
     * Επιστρέφει την εξωτερική λίστα με τους λογαριασμούς.
     * @return Η εξωτερική λίστα με τους λογαριασμούς
     */
    public AccountDAO getAccountDAO() { return new AccountDAOMemory(); }
}

