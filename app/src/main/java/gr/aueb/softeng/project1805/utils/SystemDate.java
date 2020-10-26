package gr.aueb.softeng.project1805.utils;

import java.util.Calendar;

/**
 * Βοηθητική κλάση για τη λήψη της ημερομηνίας του συστήματος.
 * Η κλάση επιτρέπει να υποκατασταθεί η ημερομηνία του συστήματος με μία
 * προκαθορισμένη ημερομηνία.
 */
public class SystemDate {
    private static Calendar systemDate;

    /**
     * Θέτει μία συγκεκριμένη ημερομηνία ως την ημερομηνία του συστήματος.
     * Η ημερομηνία αυτή επιστρέφεται από την {@link SystemDate#getSystemDate()} ()}.
     * Εάν αντί για προκαθορισμένης ημερομηνίας τεθεί
     * {@code null} τότε επιστρέφεται
     * η πραγματική ημερομηνία του συστήματος
     * @param date Η ημερομηνία η οποία επιστρέφεται ως ημερομηνία του συστήματος
     */
    public static void setSystemDate(Calendar date) {
        systemDate = date;
    }

    /**
     * Θέτει την ημερομηνία του συστήματος ως {@code null}
     */
    public static void removeSystemDate() {
        systemDate = null;
    }

    /**
     * Eπιστρέφει την κανονική ημερομηνία του συστήματος αν δεν τη έχουμε θέσει εμείς και είνι null.
     * Αλλιώ επιστρέφει την ημερομηνία που έχουμε θέσει εμείς ως ημερομηνία του συστήματος.
     * @return Η ημερομηνία του συστήματος
     */
    public static Calendar getSystemDate() {
        if (systemDate==null) {
            return Calendar.getInstance();
        }
        else {
            return (Calendar) systemDate.clone();
        }
    }
}

