package gr.aueb.softeng.project1805.domain;

import java.util.Calendar;

/**
 * To SMS service usage (κληρονομεί την ServiceUsage)
 */
public class SMS extends ServiceUsage {
    private String receiverPhoneNum;

    /**
     * Ο προκαθορισμένος κατασκευαστής.
     */
    public SMS() {}

    /**
     * Βοηθητικός κατασκευαστής που αρχικοποιεί
     * τα βασικά στοιχεία του SMS.
     * @param date Ημερομηνία κατανάλωσης SMS
     * @param receiverPhoneNum Ο αριθμός τηλεφώνου στον οποίο στάλθηκε το SMS
     */
    public SMS(Calendar date, String receiverPhoneNum) {
        super(date);
        this.receiverPhoneNum=receiverPhoneNum;
    }

    /**
     * Επιστρέφει τον αριθμό τηλεφώνου στον οποίο στάλθηκε το SMS.
     * @return O αριθμός τηλεφώνου στον οποίο στάλθηκε το SMS.
     */
    public String getReceiverPhoneNum() {
        return receiverPhoneNum;
    }

    /**
     * Θέτει τον αριθμό τηλεφώνου στον οποίο στάλθηκε το SMS.
     * @param receiverPhoneNum O αριθμός τηλεφώνου στον οποίο στάλθηκε το SMS
     */
    public void setReceiverPhoneNum(String receiverPhoneNum) {
        this.receiverPhoneNum=receiverPhoneNum;
    }
}

