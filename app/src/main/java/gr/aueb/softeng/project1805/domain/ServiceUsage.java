package gr.aueb.softeng.project1805.domain;

import java.io.Serializable;
import java.util.Calendar;

/**
 * To service usage.
 *
 */
public class ServiceUsage implements Serializable{
    private Calendar date;

    /**
     * Ο προκαθορισμένος κατασκευαστής.
     */
    ServiceUsage() {}

    /**
    *Βοηθητικός κατασκευαστής που αρχικοποιεί
    *τα βασικά στοιχεία του service usage.
    *@param date Η ημερομηνία κατανάλωσης της υπηρεσίας
     */
    ServiceUsage(Calendar date) {
        this.date=(Calendar) date.clone();
    }

    /**
     * Επιστρέφει την ημερομηνία κατανάλωσης της υπηρεσίας.
     * @return την ημερομηνία κατανάλωσης της υπηρεσίας
     */
    public Calendar getDate() {
        return (Calendar) date.clone();
    }

    /**
     * Θέτει την ημερομηνία κατανάλωσης της υπηρεσίας.
     * @param date Η ημερομηνία κατανάλωσης της υπηρεσίας
     */
    public void setDate(Calendar date) {
        this.date=(Calendar) date.clone();
    }
}

