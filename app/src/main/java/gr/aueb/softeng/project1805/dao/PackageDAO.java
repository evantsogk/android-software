package gr.aueb.softeng.project1805.dao;

import java.util.List;

import gr.aueb.softeng.project1805.domain.Package;

/**
 * Η διεπαφή DAO για την κλάση {@link Package}
 */
public interface PackageDAO {

    /**
     * Αποθηκεύει ένα πακέτο.
     * @param entity Το πακέτο
     */
    void save(Package entity);

    /**
     * Διαγράφει ένα πακέτο.
     * @param entity Το πακέτο
     */
    void delete(Package entity);

    /**
     * Επιστρέφει όλα τα πακέτα.
     * @return Τα πακέτα
     */
    List<Package> findAll();

    /**
     * Βρίσκει ένα πακέτο με βάση τον κωδικό του.
     * @param packageId Ο κωδικός του πακέτου
     * @return Το πακέτο που βρέθηκε ή null
     */
    Package find(int packageId);

    /**
     * Επιστρέφει τον επόμενο κωδικό που μπορεί να αποδοθεί σε ένα πακέτο.
     * @return Ο κωδικός του πακέτου
     */
    int nextId();

    /**
     * Διαγράφει όλα τα πακέτα.
     */
    void deleteAll();
}

