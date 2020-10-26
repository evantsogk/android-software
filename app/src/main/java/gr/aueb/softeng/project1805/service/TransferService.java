package gr.aueb.softeng.project1805.service;

import java.util.ArrayList;
import java.util.Calendar;

import gr.aueb.softeng.project1805.dao.TransferDAO;
import gr.aueb.softeng.project1805.dao.UserDAO;
import gr.aueb.softeng.project1805.domain.ServiceType;
import gr.aueb.softeng.project1805.domain.Transfer;
import gr.aueb.softeng.project1805.domain.TransferType;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.TransferDAOMemory;
import gr.aueb.softeng.project1805.memorydao.UserDAOMemory;
import gr.aueb.softeng.project1805.utils.SystemDate;

/**
 * Η υπηρεσία παραχώρησης data, χρόνου ομιλίας και SMS
 */
public class TransferService {
    private Calendar date;
    private TransferDAO transferDAO;
    private ArrayList<User> users;

    /**
     * Κατασκευαστής που δημιουργεί μια λίστα με τους υπάρχοντες χρήστες,
     * μια λίστα με παραχωρήσεις και βρίσκει την ημερομηνία του συστήματος.
     */
    public TransferService() {
        UserDAO userDAO = new UserDAOMemory();
        users = (ArrayList<User>) userDAO.findAll();
        transferDAO=new TransferDAOMemory();
        date=SystemDate.getSystemDate();
    }

    /**
     * Αναλαμβάνει την εκτέλεση μιας παραχώρησης από έναν χρήστη σε έναν άλλο.
     * Συγκεκριμένα ελέγχει αν το υπόλοιπο του τύπου υπηρεσίας που θέλει να στείλει είναι επαρκές.
     * Αν ναι καταγράφει την παραχώρηση σε αυτόν ως τύπος παραχώρησης outgoing και στον χρήστη στον
     * οποίο στέλνεται ως incoming. Τέλος ενημερώνει κατάλληλα το υπόλοιπο του χρήστη.
     * @param user Ο χρήστης
     * @param receiverNum Ο αριθμός τηλεφώνου στον οποίο στέλνεται η παραχώρηση
     * @param serviceType Ο τύπος της υπηρεσίας που παραχωρείται
     * @param quantity Η ποσότητα που παραχωρείται
     * @return Την παραχώρηση
     */
    public Transfer transfer(User user, String receiverNum, ServiceType serviceType, int quantity) {
        if (sufficientRemaining(user, serviceType, quantity)) {

            for (User u : users) {
                if (u.getPhoneNum().equals(receiverNum) && !receiverNum.equals(user.getPhoneNum())) {
                    Transfer outgoing = new Transfer(date, user.getPhoneNum(), receiverNum, quantity, serviceType, TransferType.Outgoing);
                    Transfer incoming = new Transfer(date, user.getPhoneNum(), receiverNum, quantity, serviceType, TransferType.Incoming);

                    user.addTransfer(outgoing);
                    transferDAO.save(outgoing, user.getUid());
                    updateRemaining(user, false, outgoing.getServiceType(), outgoing.getQuantity());

                    transferDAO.save(incoming, u.getUid());
                    return outgoing;
                }
            }
        }
        return null;
    }

    /**
     * Ψάχνει στη λίστα των παραχωρήσεων του χρήστη αν υπάρχουν incoming παραχωρήσεις.
     * Αν αυτές δεν έχουν ληφθεί τότε τις λαμβάνει και ενημερώνει το αντίστοιχο υπόλοιπο.
     * @param user Ο χρήστης
     * @return Τη λίστα με τις παραχωρήσεις που λήφθηκαν.
     */
    public ArrayList<Transfer> receive(User user) {
        ArrayList<Transfer> transfers = (ArrayList<Transfer>) transferDAO.findAll(user.getUid());
        ArrayList<Transfer> incoming = new ArrayList<>();

        for (int i=transfers.size()-1; i>=0; i--) {
            if (transfers.get(i).getTransferType()==TransferType.Incoming && ! transfers.get(i).getReceived()) {
                incoming.add(transfers.get(i));
                transfers.get(i).received();
                updateRemaining(user, true, transfers.get(i).getServiceType(), transfers.get(i).getQuantity());
            }
            else if (transfers.get(i).getTransferType()==TransferType.Incoming && transfers.get(i).getReceived()) {
                break;
            }
        }
        return incoming;
    }

    /**
     * Ενημερώνει το υπόλοιπο του service type του χρήστη ανάλογα με μια προκαθορισμένη ποσότητα.
     * Πιο συγκεκριμένα την προσθέτει αν το add είναι true, αλλιώς την αφαιρεί.
     * @param user Ο χρήστης
     * @param add Δείχνει αν η ποσότητα προσίθεται ή αφαιρείται
     * @param serviceType Ο τύπος υπηρεσίας
     * @param quantity Η ποσότητα
     */
    public void updateRemaining(User user, boolean add, ServiceType serviceType, int quantity) {
        if (serviceType==ServiceType.Data) {
            if (add) {
                user.setRemainingData(user.getRemainingData()+quantity);
            }
            else {
                user.setRemainingData(user.getRemainingData()-quantity);
            }
        }
        else if (serviceType==ServiceType.TalkTime) {
            if (add) {
                user.setRemainingTalkTime(user.getRemainingTalkTime()+quantity);
            }
            else {
                user.setRemainingTalkTime(user.getRemainingTalkTime()-quantity);
            }
        }
        else {
            if (add) {
                user.setRemainingSMS(user.getRemainingSMS()+quantity);
            }
            else {
                user.setRemainingSMS(user.getRemainingSMS()-quantity);
            }
        }
        if (!add) {
            new PackageRemaining().updatePackageRemaining(user, serviceType, quantity);
        }
    }

    /**
     * Ελέγχει αν το υπόλοιπο του τύπου υπηρεσίας του χρήστη είναι μεγαλύτερο ή ίσο από μια προκαθορισμένη ποσότητα
     * Επιστρέφει (@code true} αν είναι μεγαλύτερο ή ίσο, αλλιώς επιστρέφει {@code false}
     * @param user Ο χρήστης
     * @param serviceType Ο τύπος υπηρεσίας
     * @param quantity Η προκαθορισμένη ποσότητα
     * @return {@code true}  αν το υπόλοιπο του τύπου υπηρεσίας του χρήστη είναι μεγαλύτερο ή ίσο από την ποσότητα
     */
    public boolean sufficientRemaining(User user, ServiceType serviceType, int quantity) {
        if (serviceType==ServiceType.Data) {
            if (user.getRemainingData()>=quantity) return true;
        }
        else if (serviceType==ServiceType.TalkTime) {
            if (user.getRemainingTalkTime()>=quantity) return true;
        }
        else {
            if (user.getRemainingSMS()>=quantity) return true;
        }
        return false;
    }
}

