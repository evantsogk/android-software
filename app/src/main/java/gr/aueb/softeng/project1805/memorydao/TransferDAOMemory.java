package gr.aueb.softeng.project1805.memorydao;

import java.util.ArrayList;
import java.util.List;

import gr.aueb.softeng.project1805.dao.TransferDAO;
import gr.aueb.softeng.project1805.domain.Transfer;

public class TransferDAOMemory implements TransferDAO {

    private static ArrayList<ArrayList<Transfer>> entities=new ArrayList<>();

    /**
     * Προσθέτει μια λίστα που αντιστοιχεί σε έναν καινούριο χρήστη.
     */
    public void AddUser() {
        entities.add(new ArrayList<Transfer>());
    }

    /**
     * Αποθηκεύει μια παραχώρηση στη λίστα ενός χρήστη.
     * @param entity Η παραχώρηση
     * @param user Ο κωδικός του χρήστη
     */
    public void save(Transfer entity, int user) {
        entities.get(user).add(entity);
    }

    /**
     * Διαγράφει μια παραχώρηση από τη λίστα ενός χρήστη
     * @param entity Η παραχώρηση
     * @param user Ο κωδικός του χρήστη
     */
    public void delete(Transfer entity, int user) {
        entities.get(user).remove(entity);
    }

    /**
     * Επιστρέφει όλες τις παραχωρήσεις ενός χρήστη.
     * @param user Ο κωδικός του χρήστη
     * @return Οι παραχωρήσεις του χρήστη
     */
    public List<Transfer> findAll(int user) {
        ArrayList<Transfer> result = new ArrayList<>();
        result.addAll(entities.get(user));
        return result;
    }

    /**
     * Διαγράφει όλες τις λίστες.
     */
    public void deleteAll() {
        entities=new ArrayList<>();
    }
}

