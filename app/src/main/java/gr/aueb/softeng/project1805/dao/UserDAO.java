package gr.aueb.softeng.project1805.dao;

import java.util.List;

import gr.aueb.softeng.project1805.domain.User;

/**
 * Η διεπαφή DAO για την κλάση {@link User}
 */
public interface UserDAO {

    /**
     * Αποθηκεύει έναν χρήστη.
     * @param entity Ο χρήστης
     */
    void save(User entity);

    /**
     * Διαγράφει έναν χρήστη.
     * @param entity Ο χρήστης
     */
    void delete(User entity);

    /**
     * Επιστρέφει όλους τους χρήστες.
     * @return Οι χρήστες
     */
    List<User> findAll();

    /**
     * Βρίσκει έναν χρήστη με βάση τον κωδικό του.
     * @param userId Ο κωδικός του χρήστη
     * @return Ο χρήστης που βρέθηκε ή null
     */
    User find(int userId);

    /**
     * Επιστρέφει τον επόμενο κωδικό που μπορεί να αποδοθεί σε έναν χρήστη.
     * @return Ο κωδικός του χρήστη
     */
    int nextId();

    /**
     * Διαγράφει όλους τους χρήστες.
     */
    void deleteAll();
}

