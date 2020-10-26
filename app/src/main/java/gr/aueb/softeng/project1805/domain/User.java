package gr.aueb.softeng.project1805.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Ο χρήστης.
 *
 */
public class User implements Serializable {
    private String phoneNum, username, password;
    private int uid, remainingData, remainingTalkTime, remainingSMS;
    private List<Transfer> transfers = new ArrayList<>();
    private List<ActivePackage> packages = new ArrayList<>();
    private List<ServiceUsage> services = new ArrayList<>();

    /**
     * Ο προκαθορισμένος κατασκευαστής.
     */
    public User() {}

    /**
     * Βοηθητικός κατασκευαστής που αρχικοποιεί
     * τα βασικά στοιχεία του χρήστη.
     * @param uid Id χρήστη
     * @param phoneNum Αριθμός τηλεφώνου χρήστη
     * @param username Όνομα χρήστη
     * @param password Κωδικός χρήστη
     */
    public User(int uid, String phoneNum, String username, String password) {
        this.uid=uid;
        this.phoneNum = phoneNum;
        this.username = username;
        this.password = password;
        remainingData = 0;
        remainingTalkTime = 0;
        remainingSMS = 0;
    }

    /**
     * Επιστρέφει το id χρήστη. Το id χρήστη
     * προσδιορίζει μοναδικά κάθε χρήστη.
     * @return Το id χρήστη
     */
    public int getUid(){
        return uid;
    }

    /**
     * Θέτει το id χρήστη.Το id χρήστη
     * προσδιορίζει μοναδικά κάθε χρήστη.
     * @param uid Το Id χρήστη
     */
    public void setUid(int uid) {
        this.uid=uid;
    }

    /**
     * Επιστρέφει τον αριθμό τηλεφώνου χρήστη.
     * @return Ο αριθμος τηλεφώνου χρήστη
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * Θέτει τον αριθμο τηλεφώνου χρήστη
     * @param phoneNum Ο αριθμός τηλεφώνου χρήστη
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum=phoneNum;
    }

    /**
     * Επιστρέφει το όνομα χρήστη
     * @return το όνομα χρήστη
     */

    public String getUsername() {
        return username;
    }

    /**
     * Θέτει το όνομαχρήστη
     * @param username Το όνομα χρήστη
     */
    public void setUserName(String username) {
        this.username=username;
    }

    /**
     * Επιστρέφει τον κωδικό του χρήστη
     * @return τον κωδικό χρήστη
     */
    public String getPassword() {
        return password;
    }

    /**
     * Θέτει τον κωδικό του χρήστη
     * @param password Ο κωδικός του χρήστη
     */
    public void setPassword(String password) {
        this.password=password;
    }

    /**
     * Επιστρέφει το υπόλοιπο data του χρήστη
     * @return το υπόλοιπο data του χρήστη
     */
    public int getRemainingData() {
        return remainingData;
    }

    /**
     * Θέτει το υπόλοιπο data του χρήστη
     * @param remainingData το υπόλοιπο data του χρήστη
     */
    public void setRemainingData(int remainingData) {
        this.remainingData=remainingData;
    }

    /**
     * Επιστρέφει τον υπόλοιπο χρόνο ομιλίας του χρήστη
     * @return τον υπόλοιπο χρόνο ομιλίας του χρήστη
     */
    public int getRemainingTalkTime() {
        return remainingTalkTime;
    }

    /**
     * Θέτει τον υπόλοιπο χρόνο ομιλίας του χρήστη
     * @param remainingTalkTime τον υπόλοιπο χρόνο ομιλίας του χρήστη
     */
    public void setRemainingTalkTime(int remainingTalkTime) {
        this.remainingTalkTime=remainingTalkTime;
    }

    /**
     * Επιστρέφει τον υπόλοιπο SMS του χρήστη
     * @return τον υπόλοιπο SMS του χρήστη
     */
    public int getRemainingSMS() {
        return remainingSMS;
    }

    /**
     * Θέτει τον υπόλοιπο SMS του χρήστη
     * @param remainingSMS τον υπόλοιπο SMS του χρήστη
     */
    public void setRemainingSMS(int remainingSMS) {
        this.remainingSMS=remainingSMS;
    }

    /**
     * Επιστέφει την λίστα των παραχωρήσεων του χρήστη(εξερχόμενες και εισερχόμενες).
     * Η λίστα είναι αντίγραφο της λίστας των παραχωρήσεων.
     * @return Η λίστα των παραχωρήσεων
     */
    public List<Transfer> getTransfers() {
        return new ArrayList<>(transfers);
    }

    /**
     * Προσθέτει μία παραχώρηση στη λίστα των παραχωρήσεων του χρήστη.
     * @param transfer Η παραχώρηση που προστίθεται
     */
    public void addTransfer(Transfer transfer) {
        if (transfer!=null) {
            transfers.add(transfer);
        }
    }
    /**
     * Αφαιρει μία παραχώρηση από την λίστα των παραχωρήσεων του χρήστη.
     * @param transfer Η παραχώρηση που αφαιρείται
     */
    public void removeTransfer(Transfer transfer) {
        if (transfer!=null) {
            transfers.remove(transfer);
        }
    }
    /**
     * Μη ενθυλακωμένη λίστα των παραχωρήσεων του χρήστη.
     * @return Η λίστα των παραχωρήσεων του χρήστη
     */
    public List<Transfer> friendTransfers() {
        return transfers;
    }

    /**
     * Επιστέφει τη λίστα των ενεργών πακέτων του χρήστη(είτε έχουν λήξει είτε δεν έχουν λήξει ακόμα).
     * Η λίστα είναι αντίγραφο της λίστας των ενεργών πακέτων.
     * @return Η λίστα των ενεργών πακέτων
     */
    public List<ActivePackage> getPackages() {
        return new ArrayList<>(packages);
    }

    /**
     * Επιστέφει τη λίστα των ενεργών πακέτων του χρήστη τα οποία είναι ακόμα ενεργά(δεν έχουν λήξει).
     * @return Η λίστα με τα ενεργά πακέτα του χρήστη που δεν έχουν λήξει ακόμα
     */
    public List<ActivePackage> findAllActive() {
        List<ActivePackage> result = new ArrayList<>();
        for (ActivePackage activePackage : packages) {
            if (activePackage.isActive()) {
                result.add(activePackage);
            }
        }
        return result;
    }

    /**
     * Προσθέτει ένα ενεργό πακέτο στη λίστα των ενεργών πακέτων του χρήστη.
     * @param pack Το ενεργό που προστίθεται
     */
    public void addPackage(ActivePackage pack) {
        if (pack!=null) {
            packages.add(pack);
        }
    }

    /**
     * Αφαιρει ένα ενεργό πακέτο από την λίστα των ενεργών πακέτων του χρήστη.
     * @param pack Το ενεργό πακέτο που αφαιρείται
     */
    public void removePackage(ActivePackage pack) {
        if (pack!=null) {
            packages.remove(pack);
        }
    }

    /**
     * Μη ενθυλακωμένη λίστα των ενεργών πακέτων του χρήστη.
     * @return Η λίστα των παραχωρήσεων του χρήστη
     */
    public List<ActivePackage> friendPackages() {
        return packages;
    }

    /**
     * Επιστέφει τη λίστα των service usage του χρήστη.
     * Η λίστα είναι αντίγραφο της λίστας των service usage.
     * @return Η λίστα των service usage
     */
    public List<ServiceUsage> getServices() {
        return new ArrayList<>(services);
    }

    /**
     * Προσθέτει ένα service usage στη λίστα των service usage του χρήστη.
     * @param service Το service usage που προστίθεται
     */
    public void addService(ServiceUsage service) {
        if (service!=null) {
            services.add(service);
        }
    }

    /**
     * Αφαιρει ένα service usage από την λίστα των service usage  του χρήστη.
     * @param service Το service usage που αφαιρείται
     */
    public void removeService(ServiceUsage service) {
        if (service!=null) {
            services.remove(service);
        }
    }

    /**
     * Μη ενθυλακωμένη λίστα των service usage του χρήστη.
     * @return Η λίστα των service usage του χρήστη
     */
    public List<ServiceUsage> friendServices() {
        return services;
    }
}

