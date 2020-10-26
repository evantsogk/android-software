package gr.aueb.softeng.project1805.bank;

import java.util.ArrayList;
import java.util.List;

public class AccountDAOMemory implements AccountDAO {

    private static ArrayList<Account> entities = new ArrayList<>();

    /**
     * Αποθηκεύει έναν λογαριασμό.
     * @param entity Ο λογαριασμός
     */
    public void save(Account entity) {
        entities.add(entity);
    }

    /**
     * Διαγράφει έναν λογαριασμό.
     * @param entity Ο λογαριασμός
     */
    public void delete(Account entity) {
        entities.remove(entity);
    }

    /**
     * Επιστρέφει όλους τους λογαριασμούς.
     * @return Οι λογαριασμοί
     */
    public List<Account> findAll() {
        ArrayList<Account> result = new ArrayList<>();
        result.addAll(entities);
        return result;
    }

    /**
     * Βρίσκει έναν λογαριασμό με βάση τον αριθμό κάρτας.
     * @param card_num Ο αριθμός κάρτας
     * @return Ο λογαριασμός που βρέθηκε ή null
     */
    public Account find(String card_num) {
        for(Account acc : entities) {
            if (acc.getCard_num().equals(card_num)) {
                return acc;
            }
        }
        return null;
    }

    /**
     * Διαγράφει όλους τους λογαριασμούς.
     */
    public void deleteAll() {
        entities = new ArrayList<>();
    }
}

