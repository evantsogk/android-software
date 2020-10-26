package gr.aueb.softeng.project1805.domain;


import java.io.Serializable;
import java.util.Calendar;

/**
 * Η παραχώρηση.
 *
 */
public class Transfer implements Serializable{
    private Calendar date;
    private String senderNum, receiverNum;
    private int quantity;
    private ServiceType serviceType;
    private TransferType transferType;
    private boolean received;

    /**
     * Ο προκαθορισμένος κατασκευαστής.
     */
    public Transfer() {}

    /**
     * Βοηθητικός κατασκευαστής που αρχικοποιεί τα βασικά στοιχεία της παραχώρησης
     * @param date Hμερομηνία που έγινε η παραχώρηση
     * @param senderNum Αριθμός τηλεφώνου που κάνει την παραχώρηση
     * @param receiverNum Αριθμός τηλεφώνου που δέχεται την παραχώρηση
     * @param quantity Ποσότητα υπηρεσίας που παραχωρείται
     * @param serviceType Τύπος υπηρεσίας που παραχωρείται
     * @param transferType Τύπος παραχώρησης(εισερχόμενη/εξερχόμενη)
     */
    public Transfer(Calendar date, String senderNum, String receiverNum, int quantity, ServiceType serviceType, TransferType transferType) {
        this.date = (Calendar) date.clone();
        this.senderNum=senderNum;
        this.receiverNum=receiverNum;
        this.quantity =quantity;
        this.serviceType=serviceType;
        this.transferType=transferType;
        received=false;
    }

    /**
     * Επιστρέφει την ημερομηνία που έγινε η παραχώρηση.
     * @return Η ημερομηνία της παραχώρησης
     */
    public Calendar getDate() {
        return (Calendar) date.clone();
    }

    /**
     * Θέτει την ημερομηνία της παραχώρησης
     * @param date Η ημερομηνία της παραχώρησης
     */
    public void setDate(Calendar date) {
        this.date=(Calendar) date.clone();
    }

    /**
     * Επιστρέφει τον αριθμό τηλεφώνου που κάνει την παραχώρηση.
     * @return Ο αριθμός τηλεφώνου που κάνει την παραχώρηση
     */
    public String getSenderNum() {
        return senderNum;
    }

    /**
     * Θέτει τον αριθμό τηλεφώνου που κάνει την παραχώρηση.
     * @param senderNum Ο αριθμός τηλεφώνου που κάνει την παραχώρηση.
     */
    public void setSenderNum(String senderNum) {
        this.senderNum=senderNum;
    }

    /**
     * Επιστρέφει τον αριθμό τηλεφώνου που δέχεται την παραχώρηση.
     * @return Ο αριθμός τηλεφώνου που δέχεται την παραχώρηση
     */
    public String getReceiverNum() {
        return receiverNum;
    }

    /**
     * Θέτει τον αριθμό τηλεφώνου που δέχεται την παραχώρηση.
     * @param receiverNum Ο αριθμός τηλεφώνου που δέχεται την παραχώρηση
     */
    public void setReceiverNum(String receiverNum) {
        this.receiverNum=receiverNum;
    }

    /**
     * Επιστρέφει τη ποσότητα της παραχώρησης.
     * @return Η ποσότητα της παραχώρησης
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Θέτει την ποσότητα της παραχώρησης.
     * @param quantity Η ποσότητα τη παραχώρησης
     */
    public void setQuantity(int quantity) {
        this.quantity=quantity;
    }

    /**
     * Επιστρέφει {@code true} αν η παραχώρηση έχει ληφθεί, {@code false} αν δεν έχει ληφθεί
     * @return {@code true} αν η παραχώρηση έχει ληφθεί
     */
    public boolean getReceived() {return received;}

    /**
     * Θέτει true αν έχει ληφθεί η παραχώρηση.
     */
    public void received() {received=true;}

    /**
     * Eπιστρέφει το service type της παραχώρησης.
     * @return Το service type της παραχώρησης
     */
    public ServiceType getServiceType() {
        return serviceType;
    }

    /**
     * Θέτει το service type της παραχώρησης.
     * @param serviceType Το service type της παραχώρησης
     */
    public void setServiceType(ServiceType serviceType) {
        this.serviceType=serviceType;
    }

    /**
     * Επιστρέφει τον τύπο της παραχώρησης(εισερχόμενη/εξερχόμενη).
     * @return Ο τύπος της παραχώρησης
     */
    public TransferType getTransferType() {
        return transferType;
    }

    /**
     * Θέτει τον τύπο της παραχώρησης(εισερχόμενη/εξερχόμενη)
     * @param transferType Ο τύπος της παραχώρησης
     */
    public void setTransferType(TransferType transferType) {
        this.transferType=transferType;
    }
}

