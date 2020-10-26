package gr.aueb.softeng.project1805.dao;

import java.util.List;

import gr.aueb.softeng.project1805.domain.ServiceUsage;

/**
 * Η διεπαφή DAO για την κλάση {@link ServiceUsage}
 */
public interface ServiceUsageDAO {

    /**
     * Προσθέτει μια λίστα που αντιστοιχεί σε έναν καινούριο χρήστη.
     */
    void addUser();

    /**
     * Αποθηκεύει ένα ServiceUsage στη λίστα ενός χρήστη
     * @param entity Το ServiceUsage
     * @param user  Ο κωδικός του χρήστη
     */
    void save(ServiceUsage entity, int user);

    /**
     * Διαγράφει ένα ServiceUsage από τη λίστα ενός χρήστη.
     * @param entity Το ServiceUsage
     * @param user Ο κωδικός του χρήστη
     */
    void delete(ServiceUsage entity, int user);

    /**
     * Επιστρέφει όλα τα service usages ενός χρήστη.
     * @param user Ο κωδικός του χρήστη.
     * @return Τα service usages
     */
    List<ServiceUsage> findAll(int user);

    /**
     * Επιστρέφει όλα τα service usages ενός χρήστη που αφορούν κατανάλωση data.
     * @param user Ο κωδικός του χρήστη.
     * @return Τα service usages
     */
    List<ServiceUsage> findData(int user);

    /**
     * Επιστρέφει όλα τα service usages ενός χρήστη που αφορούν κατανάλωση λεπτών ομιλίας.
     * @param user Ο κωδικός του χρήστη.
     * @return Τα service usages
     */
    List<ServiceUsage> findCalls(int user);

    /**
     * Επιστρέφει όλα τα service usages ενός χρήστη που αφορούν κατανάλωση sms.
     * @param user Ο κωδικός του χρήστη.
     * @return Τα service usages
     */
    List<ServiceUsage> findSMS(int user);

    /**
     * Διαγράφει όλες τις λίστες.
     */
    void deleteAll();
}

