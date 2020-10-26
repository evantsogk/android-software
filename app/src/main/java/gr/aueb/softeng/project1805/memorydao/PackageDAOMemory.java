package gr.aueb.softeng.project1805.memorydao;

import java.util.ArrayList;
import java.util.List;
import gr.aueb.softeng.project1805.dao.PackageDAO;
import gr.aueb.softeng.project1805.domain.Package;

public class PackageDAOMemory implements PackageDAO {

    private static ArrayList<Package> entities = new ArrayList<>();

    /**
     * Αποθηκεύει ένα πακέτο.
     * @param entity Το πακέτο
     */
    public void save(Package entity) {
        entities.add(entity);
    }

    /**
     * Διαγράφει ένα πακέτο.
     * @param entity Το πακέτο
     */
    public void delete(Package entity) {
        entities.remove(entity);
    }

    /**
     * Επιστρέφει όλα τα πακέτα.
     * @return Τα πακέτα
     */
    public List<Package> findAll() {
        ArrayList<Package> result = new ArrayList<>();
        result.addAll(entities);
        return result;
    }

    /**
     * Βρίσκει ένα πακέτο με βάση τον κωδικό του.
     * @param packageId Ο κωδικός του πακέτου
     * @return Το πακέτο που βρέθηκε ή null
     */
    public Package find(int packageId) {
        for(Package pack : entities) {
            if (pack.getPackageId() == packageId) {
                return pack;
            }
        }
        return null;
    }

    /**
     * Επιστρέφει τον επόμενο κωδικό που μπορεί να αποδοθεί σε ένα πακέτο.
     * @return Ο κωδικός του πακέτου
     */
    public int nextId() {
        return (entities.size() > 0 ? entities.get(entities.size()-1).getPackageId()+1 : 0);
    }

    /**
     * Διαγράφει όλα τα πακέτα.
     */
    public void deleteAll() {
        entities = new ArrayList<>();
    }
}

