package gr.aueb.softeng.project1805.memorydao;

import java.util.ArrayList;
import java.util.List;

import gr.aueb.softeng.project1805.dao.ActivePackageDAO;
import gr.aueb.softeng.project1805.domain.ActivePackage;

public class ActivePackageDAOMemory implements ActivePackageDAO {

    private static ArrayList<ArrayList<ActivePackage>> entities=new ArrayList<>();

    /**
     * Προσθέτει μια λίστα που αντιστοιχεί σε έναν καινούριο χρήστη.
     */
    public void addUser() {
        entities.add(new ArrayList<ActivePackage>());
    }

    /**
     * Αποθηκεύει ένα ενεργό πακέτο στη λίστα ενός χρήστη.
     * @param entity Το ενεργό πακέτο
     * @param user Ο κωδικός του χρήστη
     */
    public void save(ActivePackage entity, int user) {
        entities.get(user).add(entity);
    }

    /**
     * Διαγράφει ένα ενεργό πακέτο από τη λίστα ενός χρήστη.
     * @param entity Το ενεργό πακέτο
     * @param user Ο κωδικός του χρήστη
     */
    public  void delete(ActivePackage entity, int user) {
        entities.get(user).remove(entity);
    }

    /**
     * Επιστρέφει όλα τα πακέτα που έχει αγοράσει ένας χρήστης.
     * @param user Ο χρήστης
     * @return Τα πακέτα
     */
    public List<ActivePackage> findAllPackages(int user) {
        ArrayList<ActivePackage> result = new ArrayList<>();
        result.addAll(entities.get(user));
        return result;
    }

    /**
     * Επιστρέφει όλα τα πακέτα που έχει αγοράσει ένας χρήστης και τα οποία είναι ακόμα ενεργά.
     * @param user Ο κωδικός του χρήστη
     * @return Τα ενεργά πακέτα
     */
    public List<ActivePackage> findAllActive(int user) {
        ArrayList<ActivePackage> result = new ArrayList<>();
        for(ActivePackage activePackage : entities.get(user)) {
            if (activePackage.isActive()) {
                result.add(activePackage);
            }
        }
        return result;
    }

    /**
     * Βρίσκει ένα ενεργό πακέτο ενός χρήστη με βάση τον κωδικό του.
     * @param packageId Ο κωδικός του πακέτου
     * @param user Ο κωδικός του χρήστη
     * @return Το ενεργό πακέτο που βρέθηκε ή null
     */
    public ActivePackage find(int packageId, int user) {
        for(ActivePackage activePackage : entities.get(user)) {
            if (activePackage.getPackageId() == packageId && activePackage.isActive()) {
                return activePackage;
            }
        }
        return null;

    }

    /**
     * Διαγράφει όλες τις λίστες.
     */
    public void deleteAll() {
        entities=new ArrayList<>();
    }
}

