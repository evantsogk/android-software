package gr.aueb.softeng.project1805.memorydao;

import java.util.ArrayList;
import java.util.List;

import gr.aueb.softeng.project1805.dao.UserDAO;
import gr.aueb.softeng.project1805.domain.User;

public class UserDAOMemory implements UserDAO {

    private static ArrayList<User> entities = new ArrayList<>();

    /**
     * Αποθηκεύει έναν χρήστη.
     * @param entity Ο χρήστης
     */
    public void save(User entity) {
        entities.add(entity);
    }

    /**
     * Διαγράφει έναν χρήστη.
     * @param entity Ο χρήστης
     */
    public void delete(User entity) {
        entities.remove(entity);
    }

    /**
     * Επιστρέφει όλους τους χρήστες.
     * @return Οι χρήστες
     */
    public List<User> findAll() {
        ArrayList<User> result = new ArrayList<>();
        result.addAll(entities);
        return result;
    }

    /**
     * Βρίσκει έναν χρήστη με βάση τον κωδικό του.
     * @param userId Ο κωδικός του χρήστη
     * @return Ο χρήστης που βρέθηκε ή null
     */
    public User find(int userId) {
        for(User user : entities) {
            if (user.getUid() == userId) {
                return user;
            }
        }
        return null;
    }

    /**
     * Επιστρέφει τον επόμενο κωδικό που μπορεί να αποδοθεί σε έναν χρήστη.
     * @return Ο κωδικός του χρήστη
     */
    public int nextId() {
        return (entities.size() > 0 ? entities.get(entities.size()-1).getUid()+1 : 0);
    }

    /**
     * Διαγράφει όλους τους χρήστες.
     */
    public void deleteAll() {
        entities = new ArrayList<>();
    }
}

