package gr.aueb.softeng.project1805.bank;

import java.util.List;

/**
 * Η διεπαφή DAO για την κλάση {@link Account}
 */
public interface AccountDAO {

    /**
     * Αποθηκεύει έναν λογαριασμό.
     * @param entity Ο λογαριασμός
     */
    void save(Account entity);

    /**
     * Διαγράφει έναν λογαριασμό.
     * @param entity Ο λογαριασμός
     */
    void delete(Account entity);

    /**
     * Επιστρέφει όλους τους λογαριασμούς.
     * @return Οι λογαριασμοί
     */
    List<Account> findAll();

    /**
     * Βρίσκει έναν λογαριασμό με βάση τον αριθμό κάρτας.
     * @param card_num Ο αριθμός κάρτας
     * @return Ο λογαριασμός που βρέθηκε ή null
     */
    Account find(String card_num);

    /**
     * Διαγράφει όλους τους λογαριασμούς.
     */
    void deleteAll();
}

