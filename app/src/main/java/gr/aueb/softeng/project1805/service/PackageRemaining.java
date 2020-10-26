package gr.aueb.softeng.project1805.service;

import java.util.ArrayList;

import gr.aueb.softeng.project1805.dao.ActivePackageDAO;
import gr.aueb.softeng.project1805.dao.PackageDAO;
import gr.aueb.softeng.project1805.domain.ActivePackage;
import gr.aueb.softeng.project1805.domain.Package;
import gr.aueb.softeng.project1805.domain.ServiceType;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.ActivePackageDAOMemory;
import gr.aueb.softeng.project1805.memorydao.PackageDAOMemory;

/**
 * Η υπηρεσία ενημέρωσης του υπολοίπου των ενεργών πακέτων
 */
public class PackageRemaining {
    /**
     * Ο προκαθορισμένος κατασκευαστής
     */
    public PackageRemaining() {}

    /**
     * Αναλαμβάνει την ενημέρωση του υπολοίπου των ενεργών πακέτων του χρήστη.
     * Βρίσκει όλα τα ενεργά πακέτα του χρήστη(που είναι ενεργοποιημένα) και έχουν service type ίδιο
     * με το service type που καταναλώθηκε. Αν το πρώτο πακέτο που βρει έχει υπόλοιπο μεγαλύτερο της
     * ποσότητας που καταναλώθηκε τότε απλά αφαιρεί τη ποσότητα από το υπόλοιπο και τελιώνει η διαδικασία.
     * Αν δεν επαρκεί, μηδενίζει τον υπόλοιπο του πακέτου και το καθιστά μη ενεργό και συνεχίζει
     * με τα υπόλοιπα πακέτα μέχρι να αφαιρεθεί όλη η ποσότητα που καταναλώθηκε από τον χρήστη.
     * @param user Ο χρήστης
     * @param serviceType Ο τύπος της υπηρεσίας που καταναλώθηκε
     * @param quantity Η ποσότητα της υπηρεσίας που καταναλώθηκε
     */
     public void updatePackageRemaining(User user, ServiceType serviceType, int quantity) {
        ActivePackageDAO activePackageDAO = new ActivePackageDAOMemory();
        ArrayList<ActivePackage> activePackages = (ArrayList<ActivePackage>) activePackageDAO.findAllActive(user.getUid());
        PackageDAO packageDAO = new PackageDAOMemory();

        for (ActivePackage activePackage : activePackages) {
            Package pack = packageDAO.find(activePackage.getPackageId());
            if (pack.getServiceType() == serviceType) {
                if (activePackage.getRemainingQuantity() >= quantity && quantity!=0) {
                    activePackage.setRemainingQuantity(activePackage.getRemainingQuantity() - quantity);
                    if (activePackage.getRemainingQuantity()==0) activePackage.setActive(false);
                    break;
                }
                else {
                    quantity -= activePackage.getRemainingQuantity();
                    activePackage.setRemainingQuantity(0);
                    activePackage.setActive(false);
                }
            }
        }
    }
}

