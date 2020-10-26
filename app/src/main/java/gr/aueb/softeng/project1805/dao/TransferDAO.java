package gr.aueb.softeng.project1805.dao;

import java.util.List;

import gr.aueb.softeng.project1805.domain.Transfer;

/**
 * Η διεπαφή DAO για την κλάση {@link Transfer}
 */
public interface TransferDAO {

    /**
     * Προσθέτει μια λίστα που αντιστοιχεί σε έναν καινούριο χρήστη.
     */
    void AddUser();

    /**
     * Αποθηκεύει μια παραχώρηση στη λίστα ενός χρήστη.
     * @param entity Η παραχώρηση
     * @param user Ο κωδικός του χρήστη
     */
    void save(Transfer entity, int user);

    /**
     * Διαγράφει μια παραχώρηση από τη λίστα ενός χρήστη
     * @param entity Η παραχώρηση
     * @param user Ο κωδικός του χρήστη
     */
    void delete(Transfer entity, int user);

    /**
     * Επιστρέφει όλες τις παραχωρήσεις ενός χρήστη.
     * @param user Ο κωδικός του χρήστη
     * @return Οι παραχωρήσεις του χρήστη
     */
    List<Transfer> findAll(int user);

    /**
     * Διαγράφει όλες τις λίστες.
     */
    void deleteAll();
}

