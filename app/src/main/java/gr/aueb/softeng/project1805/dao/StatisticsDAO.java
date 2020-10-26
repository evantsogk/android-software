package gr.aueb.softeng.project1805.dao;

import java.util.List;

/**
 * Η διεπαφή DAO για τα στατιστικά του χρήστη.
 */
public interface StatisticsDAO {

    /**
     * Προσθέτει μια λίστα που αντιστοιχεί σε έναν καινούριο χρήστη.
     */
    void addUser();

    /**
     * Προσθέτει έναν πίνακα float[] στη λίστα ενός χρήστη, ο οποίος αντιστοιχεί σε καινούριο μήνα.
     * @param user Ο κωδικός του χρήστη
     */
    void addMonth(int user);

    void addMonthSimple(int user);

    /**
     * Αποθηκεύει τιμές στον πίνακα στατιστικών ενός χρήστη.
     * @param user Ο κωδικός του χρήστη
     * @param index 0-1 (χρόνος, μήνας), 2-5 (κόστος, data, ομιλία, sms)
     * @param value Η τιμή
     */
    void save(int user, int index, float value);

    /**
     * Διαγράφει έναν πίνακα στατιστικών ενός μήνα από τη λίστα ενός χρήστη.
     * @param monthStatistics Ο πίνακας στατιστικών
     * @param user Ο κωδικός του χρήστη
     */
    void delete (float[] monthStatistics, int user);

    /**
     * Επιστρέφει όλα τα στατιστικά ενός χρήστη.
     * @param user Ο κωδικός του χρήστη
     * @return Τα στατιστικά του χρήστη
     */
    List<float[]> findAll(int user);

    /**
     * Διαγράφει όλες τις λίστες.
     */
    void deleteAll();
}

