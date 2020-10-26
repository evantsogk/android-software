package gr.aueb.softeng.project1805.memorydao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gr.aueb.softeng.project1805.dao.StatisticsDAO;
import gr.aueb.softeng.project1805.utils.SystemDate;

public class StatisticsDAOMemory implements StatisticsDAO {
    private static ArrayList<ArrayList<float[]>> statistics = new ArrayList<>();

    /**
     * Προσθέτει μια λίστα που αντιστοιχεί σε έναν καινούριο χρήστη.
     */
    public void addUser() {
        statistics.add(new ArrayList<float[]>());
    }

    /**
     * Προσθέτει έναν πίνακα float[] στη λίστα ενός χρήστη, ο οποίος αντιστοιχεί σε καινούριο μήνα.
     * @param user Ο κωδικός του χρήστη
     */
    public void addMonth(int user) {
        if (statistics.get(user).isEmpty() || SystemDate.getSystemDate().get(Calendar.MONTH)>statistics.get(user).get(statistics.get(user).size()-1)[1]) {
            statistics.get(user).add(new float[6]);
            save(user, 0, SystemDate.getSystemDate().get(Calendar.YEAR));
            save(user, 1, SystemDate.getSystemDate().get(Calendar.MONTH));
        }
    }

    /**
     * Προσθέτει έναν πίνακα float[] στη λίστα ενός χρήστη, ο οποίος αντιστοιχεί σε καινούριο μήνα, χωρίς ελέγχους.
     * @param user Ο κωδικός του χρήστη
     */
    public void addMonthSimple(int user) {
        statistics.get(user).add(new float[6]);
    }

    /**
     * Αποθηκεύει τιμές στον πίνακα στατιστικών ενός χρήστη.
     * @param user Ο κωδικός του χρήστη
     * @param index 0-1 (χρόνος, μήνας), 2-5 (κόστος, data, ομιλία, sms)
     * @param value Η τιμή
     */
    public void save(int user, int index, float value) {
        if (index>=2 && index<=5) value+=statistics.get(user).get(statistics.get(user).size()-1)[index];
        statistics.get(user).get(statistics.get(user).size()-1)[index]=value;
    }

    /**
     * Διαγράφει έναν πίνακα στατιστικών ενός μήνα από τη λίστα ενός χρήστη.
     * @param monthStatistics Ο πίνακας στατιστικών
     * @param user Ο κωδικός του χρήστη
     */
    public void delete (float[] monthStatistics, int user) {
        statistics.get(user).remove(monthStatistics);
    }

    /**
     * Επιστρέφει όλα τα στατιστικά ενός χρήστη.
     * @param user Ο κωδικός του χρήστη
     * @return Τα στατιστικά του χρήστη
     */
    public List<float[]> findAll(int user) {
        ArrayList<float[]> result = new ArrayList<>();
        result.addAll(statistics.get(user));
        return result;
    }

    /**
     * Διαγράφει όλες τις λίστες.
     */
    public void deleteAll() {
        statistics = new ArrayList<>();
    }
}

