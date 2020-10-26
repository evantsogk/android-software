package gr.aueb.softeng.project1805.memorydao;

import java.util.ArrayList;
import java.util.List;

import gr.aueb.softeng.project1805.dao.ServiceUsageDAO;
import gr.aueb.softeng.project1805.domain.Call;
import gr.aueb.softeng.project1805.domain.SMS;
import gr.aueb.softeng.project1805.domain.ServiceUsage;
import gr.aueb.softeng.project1805.domain.Data;

public class ServiceUsageDAOMemory implements ServiceUsageDAO {

    private static ArrayList<ArrayList<ServiceUsage>> entities=new ArrayList<>();

    /**
     * Προσθέτει μια λίστα που αντιστοιχεί σε έναν καινούριο χρήστη.
     */
    public void addUser() {
        entities.add(new ArrayList<ServiceUsage>());
    }

    /**
     * Αποθηκεύει ένα ServiceUsage στη λίστα ενός χρήστη
     * @param entity Το ServiceUsage
     * @param user  Ο κωδικός του χρήστη
     */
    public void save(ServiceUsage entity, int user) {
        entities.get(user).add(entity);
    }

    /**
     * Διαγράφει ένα ServiceUsage από τη λίστα ενός χρήστη.
     * @param entity Το ServiceUsage
     * @param user Ο κωδικός του χρήστη
     */
    public void delete(ServiceUsage entity, int user) {
        entities.get(user).remove(entity);
    }

    /**
     * Επιστρέφει όλα τα service usages ενός χρήστη.
     * @param user Ο κωδικός του χρήστη.
     * @return Τα service usages
     */
    public List<ServiceUsage> findAll(int user) {
        ArrayList<ServiceUsage> result = new ArrayList<>();
        result.addAll(entities.get(user));
        return result;
    }

    /**
     * Επιστρέφει όλα τα service usages ενός χρήστη που αφορούν κατανάλωση data.
     * @param user Ο κωδικός του χρήστη.
     * @return Τα service usages
     */
    public List<ServiceUsage> findData(int user) {
        ArrayList<ServiceUsage> result = new ArrayList<>();
        for(ServiceUsage serviceUsage : entities.get(user)) {
            if (serviceUsage instanceof Data) {
                result.add(serviceUsage);
            }
        }
        return result;
    }

    /**
     * Επιστρέφει όλα τα service usages ενός χρήστη που αφορούν κατανάλωση λεπτών ομιλίας.
     * @param user Ο κωδικός του χρήστη.
     * @return Τα service usages
     */
    public List<ServiceUsage> findCalls(int user) {
        ArrayList<ServiceUsage> result = new ArrayList<>();
        for(ServiceUsage serviceUsage : entities.get(user)) {
            if (serviceUsage instanceof Call) {
                result.add(serviceUsage);
            }
        }
        return result;
    }

    /**
     * Επιστρέφει όλα τα service usages ενός χρήστη που αφορούν κατανάλωση sms.
     * @param user Ο κωδικός του χρήστη.
     * @return Τα service usages
     */
    public List<ServiceUsage> findSMS(int user) {
        ArrayList<ServiceUsage> result = new ArrayList<>();
        for(ServiceUsage serviceUsage : entities.get(user)) {
            if (serviceUsage instanceof SMS) {
                result.add(serviceUsage);
            }
        }
        return result;
    }

    /**
     * Διαγράφει όλες τις λίστες.
     */
    public void deleteAll() {
        entities = new ArrayList<>();
    }

}

