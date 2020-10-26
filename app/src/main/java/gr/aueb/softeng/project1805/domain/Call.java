package gr.aueb.softeng.project1805.domain;

import java.util.Calendar;

/**
 * To call service usage (κληρονομεί την ServiceUsage)
 */
public class Call extends ServiceUsage{
    private int duration;
    private String calledPhone;

    /**
     * Ο προκαθορισμένος κατασκευαστής.
     */
    public Call() {}

    /**
     * Βοηθητικός κατασκευαστής που αρχικοποιεί
     * τα βασικά στοιχεία του Call.
     * @param date Ημερομηνία κατανάλωσης χρόνου ομιλίας
     * @param duration Διάρκεια κατανάλωσης χρόνου ομιλίας
     * @param calledPhone Αριθμός τηλεφώνου που καλέστηκε
     */
    public Call(Calendar date, int duration, String calledPhone) {
        super(date);
        this.duration=duration;
        this.calledPhone=calledPhone;
    }

    /**
     * Επιστρέφει την διάρκεια κατανάλωσης χρόνου ομιλίας.
     * @return H διάρκεια κατανάλωσης χρόνου ομιλίας
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Θέτει την διάρκεια κατανάλωσης χρόνου ομιλίας.
     * @param duration H διάρκεια κατανάλωσης χρόνου ομιλίας.
     */
    public void setDuration(int duration) {
        this.duration=duration;
    }

    /**
     * Επιστρέφει τον αριθμό τηλεφώνου που καλέστηκε.
     * @return Ο αριθμός τηλεφώνου που καλέστηκε
     */
    public String getCalledPhone() {
        return calledPhone;
    }

    /**
     * Θέτει τον αριθμό τηλεφώνου που καλέστηκε.
     * @param calledPhone Ο αριθμός τηλεφω΄νου που καλέστηκε
     */
    public void setCalledPhone(String calledPhone) {
        this.calledPhone=calledPhone;
    }
}

