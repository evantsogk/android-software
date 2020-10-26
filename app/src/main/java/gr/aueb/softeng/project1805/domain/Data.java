package gr.aueb.softeng.project1805.domain;

import java.util.Calendar;

/**
 * To data service usage (κληρονομεί την ServiceUsage)
 */
public class Data extends ServiceUsage {
    private int dataVolume;

    /**
     * Ο προκαθορισμένος κατασκευαστής.
     */
    public Data() {}

    /**
     * Βοηθητικός κατασκευαστής που αρχικοποιεί
     * τα βασικά στοιχεία του data.
     * @param date Ημερομηνία κατανάλωσης data
     * @param dataVolume Ποσότητα κατανάλωσης data
     */
    public Data(Calendar date, int dataVolume) {
        super(date);
        this.dataVolume=dataVolume;
    }

    /**
     * Επιστρέφει την ποσότητα κατανάλωσης data.
     * @return H ποσότητα κατανάλωσης data
     */
    public int getDataVolume() {
        return dataVolume;
    }

    /**
     * Θέτει την ποσότητα κατανάλωσης data.
     * @param dataVolume H ποσότητα κατανάλωσης data
     */
    public void setDataVolume(int dataVolume) {
        this.dataVolume=dataVolume;
    }
}

