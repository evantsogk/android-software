package gr.aueb.softeng.project1805.domain;

import java.io.Serializable;
import java.util.Calendar;

/**
 * To ενεργό πακέτο.
 *
 */
public class ActivePackage implements Serializable{
    private int packageId;
    private Calendar activationDate;
    private boolean isActive;
    private int remainingQuantity;

    /**
     * Ο προκαθορισμένος κατασκευαστής.
     */
    public ActivePackage() {}

    /**
     * Βοηθητικός κατασκευαστής που αρχικοποιεί
     * τα βασικά στοιχεία του ενεργού πακέτου.
     * @param packageId Id ενεργού πακέτου
     * @param activationDate Ημερομηνία ενεργοποίησης πακέτου
     * @param isActive true αν είναι ενεργό false αν όχι
     * @param remainingQuantity Υπόλοιπο πακέτου
     */
    public ActivePackage(int packageId, Calendar activationDate, boolean isActive, int remainingQuantity) {
        this.packageId=packageId;
        this.activationDate=(Calendar) activationDate.clone();
        this.isActive=isActive;
        this.remainingQuantity=remainingQuantity;
    }

    /**
     * Επιστρέφει το id του ενεργού πακέτου. Το id ενεργού πακέτου
     * προσδιορίζει μοναδικά κάθε ενεργό πακέτο.
     * @return Το id του ενεργού πακέτου
     */
    public int getPackageId() {
        return packageId;
    }

    /**
     * Θέτει το id του ενεργού πακέτου.Το id του ενεργού πακέτου
     * προσδιορίζει μοναδικά κάθε ενεργό πακέτο.
     * @param packageId Το id του ενεργού πακέτου
     */
    public void setPackageId(int packageId) {
        this.packageId=packageId;
    }

    /**
     * Επιστρέφει την ημερομηνία ενεργοποίησης του πακέτου.
     * @return την ημερομηνία ενεργοποίησης πακέτου
     */
    public Calendar getActivationDate() {
        return (Calendar) activationDate.clone();
    }

    /**
     * Θέτει την ημερομηνία ενεργοποίησης του πακέτου.
     * @param activationDate Η ημερομηνία ενεργοποίησης του πακέτου
     */
    public void setActivationDate(Calendar activationDate) {
        this.activationDate=(Calendar) activationDate.clone();
    }

    /**
     * Επιστρέφει {@code true} αν το πακέτο είναι ενεργό.
     * Eπιστρέφει {@code false} αν το πακέτο δεν είναι ενεργό.
     * @return {@code true} αν το πακέτο είναι ενεργό
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Θέτει αν είναι ενεργό το πακέτο.
     * @param isActive έχει την τιμή {@code true} αν είναι ενεργο και {@code false} αν δεν είναι
     */
    public void setActive(boolean isActive) {
        this.isActive=isActive;
    }

    /**
     * Επιστρέφει το υπόλοιπο του πακέτου.
     * @return το υπόλοιπο του πακέτου.
     */
    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    /**
     * Θέτει το υπόλοιπο του πακέτου.
     * @param quantity το υπόλοιπο του πακέτου
     */
    public void setRemainingQuantity(int quantity) {
        this.remainingQuantity=quantity;
    }
}

