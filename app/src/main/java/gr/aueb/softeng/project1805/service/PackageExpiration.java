package gr.aueb.softeng.project1805.service;

import java.util.ArrayList;
import java.util.Calendar;

import gr.aueb.softeng.project1805.dao.ActivePackageDAO;
import gr.aueb.softeng.project1805.dao.PackageDAO;
import gr.aueb.softeng.project1805.domain.ActivePackage;
import gr.aueb.softeng.project1805.domain.ServiceType;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.domain.Package;
import gr.aueb.softeng.project1805.memorydao.ActivePackageDAOMemory;
import gr.aueb.softeng.project1805.memorydao.PackageDAOMemory;
import gr.aueb.softeng.project1805.utils.SystemDate;

/**
 * Η υπηρεσία λήξης των (ενεργών) πακέτων.
 */
public class PackageExpiration {
    /**
     * Ο προκαθορισμένος κατασκευαστής
     */
    public PackageExpiration() {}

    /**
     * Αναλαμβάνει τη λήξη των ενεργών πακέτων του χρήστη εάν η ημερομηνία του συστήματος είναι μεγαλύτερη από
     * την ημερομηνία ενεργοποίησης των πακέτων + τη διάρκειά τους.
     * Σε όσα πακέτα βρει ότι έχουν λήξει μηδενίζει το υπόλοιπό τους και τα θέτει μη ενεργά ενώ μειώνει και το υπόλοιπο που είχε μείνει στο
     * ληγμένο πακέτο από το υπόλοιπο του χρήστη(υπόλοιπο με ίδιο service type με το ληγμένο πακέτο).
     * @param user Ο χρήστης
     */
    public void checkPackageExpirations(User user) {
        Calendar currentDate= SystemDate.getSystemDate();

        ActivePackageDAO activePackageDAO=new ActivePackageDAOMemory();
        ArrayList<ActivePackage> activePackages=(ArrayList<ActivePackage>) activePackageDAO.findAllActive(user.getUid());
        PackageDAO packageDAO=new PackageDAOMemory();

        for (ActivePackage activePackage : activePackages) {
            Package pack=packageDAO.find(activePackage.getPackageId());

            Calendar expirationDate=activePackage.getActivationDate();
            expirationDate.add(Calendar.DATE, pack.getDuration());

            if (currentDate.after(expirationDate)) {
                if (pack.getServiceType()== ServiceType.Data) {
                    user.setRemainingData(user.getRemainingData()-activePackage.getRemainingQuantity());
                }
                else if (pack.getServiceType()==ServiceType.TalkTime) {
                    user.setRemainingTalkTime(user.getRemainingTalkTime()-activePackage.getRemainingQuantity());
                }
                else {
                    user.setRemainingSMS(user.getRemainingSMS()-activePackage.getRemainingQuantity());
                }

                activePackage.setRemainingQuantity(0);
                activePackage.setActive(false);


            }
        }
    }
}

