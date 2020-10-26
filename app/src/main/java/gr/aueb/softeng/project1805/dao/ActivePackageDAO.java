package gr.aueb.softeng.project1805.dao;

import java.util.List;

import gr.aueb.softeng.project1805.domain.ActivePackage;

/**
 * Η διεπαφή DAO για την κλάση {@link ActivePackage}
 */
public interface ActivePackageDAO {

    /**
     * Προσθέτει μια λίστα που αντιστοιχεί σε έναν καινούριο χρήστη.
     */
    void addUser();

    /**
     * Αποθηκεύει ένα ενεργό πακέτο στη λίστα ενός χρήστη.
     * @param entity Το ενεργό πακέτο
     * @param user Ο κωδικός του χρήστη
     */
    void save(ActivePackage entity, int user);

    /**
     * Διαγράφει ένα ενεργό πακέτο από τη λίστα ενός χρήστη.
     * @param entity Το ενεργό πακέτο
     * @param user Ο κωδικός του χρήστη
     */
    void delete(ActivePackage entity, int user);

    /**
     * Επιστρέφει όλα τα πακέτα που έχει αγοράσει ένας χρήστης.
     * @param user Ο χρήστης
     * @return Τα πακέτα
     */
    List<ActivePackage> findAllPackages(int user);

    /**
     * Επιστρέφει όλα τα πακέτα που έχει αγοράσει ένας χρήστης και τα οποία είναι ακόμα ενεργά.
     * @param user Ο κωδικός του χρήστη
     * @return Τα ενεργά πακέτα
     */
    List<ActivePackage> findAllActive(int user);

    /**
     * Βρίσκει ένα ενεργό πακέτο ενός χρήστη με βάση τον κωδικό του.
     * @param packageId Ο κωδικός του πακέτου
     * @param user Ο κωδικός του χρήστη
     * @return Το ενεργό πακέτο που βρέθηκε ή null
     */
    ActivePackage find(int packageId, int user);

    /**
     * Διαγράφει όλες τις λίστες.
     */
    void deleteAll();
}

