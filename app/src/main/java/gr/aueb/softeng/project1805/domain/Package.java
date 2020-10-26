package gr.aueb.softeng.project1805.domain;

/**
 * To πακέτο.
 *
 */
public class Package {
    private int packageId;
    private String name;
    private int duration;
    private float price;
    private ServiceType serviceType;
    private int quantity;

    /**
     * Ο προκαθορισμένος κατασκευαστής.
     */
    public Package() {}

    /**
     * Βοηθητικός κατασκευαστής που αρχικοποιεί
     * τα βασικά στοιχεία του πακέτου.
     * @param packageId Id πακέτου
     * @param name Όνομα πακέτου
     * @param duration Διάρκεια πακέτου
     * @param price Τιμή πακέτου
     * @param serviceType Ο τύπος του service ου πακέτου. Παίρνει τις τιμές Data, TalkTime, SMS
     * @param quantity Η παρεχόμενη ποσότητα του service του πακέτου
     */
    public Package (int packageId, String name, int duration, float price, ServiceType serviceType, int quantity) {
        this.packageId=packageId;
        this.name=name;
        this.duration=duration;
        this.price=price;
        this.serviceType=serviceType;
        this.quantity=quantity;
    }

    /**
     * Επιστρέφει το id του πακέτου. Το id πακέτου
     * προσδιορίζει μοναδικά κάθε πακέτο.
     * @return Το id του πακέτου
     */
    public int getPackageId() {
        return packageId;
    }

    /**
     * Θέτει το id  πακέτου.Το id του πακέτου
     * προσδιορίζει μοναδικά κάθε πακέτο.
     * @param packageId Το id του πακέτου
     */
    public void setPackageId(int packageId) {
        this.packageId=packageId;
    }

    /**
     * Επιστρέφει το όνομα του πακέτου.
     * @return το όνομα του πακέτου
     */
    public String getName() {
        return name;
    }

    /**
     * Θέτει το όνομα του πακέτου.
     * @param name Το όνομα του πακέτου
     */
    public void setName(String name) {
        this.name=name;
    }

    /**
     * Επιστρέφει τη διάρκεια  του πακέτου.
     * @return τη διάρκεια του πακέτου
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Θέτει τη διάρκεια του πακέτου.
     * @param duration Η διάρκεια του πακέτου
     */
    public void setDuration(int duration) {
        this.duration=duration;
    }

    /**
     * Επιστρέφει την τιμή  του πακέτου.
     * @return την τιμή του πακέτου
     */
    public float getPrice() {
        return price;
    }

    /**
     * Θέτει την τιμήτου πακέτου.
     * @param price Η τιμή του πακέτου
     */
    public void setPrice(float price) {
        this.price=price;
    }

    /**
     * Επιστρέφει τo service type  του πακέτου.
     * @return τo service type του πακέτου
     */
    public ServiceType getServiceType() {
        return serviceType;
    }

    /**
     * Θέτει το service type του πακέτου.
     * @param  serviceType To service type  του πακέτου
     */
    public void setServiceType(ServiceType serviceType) {
        this.serviceType=serviceType;
    }

    /**
     * Επιστρέφει την ποσότητα του πακέτου.
     * @return Η ποσότητα του πακέτου
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Θέτει την ποσότητα του πακέτου.
     * @param  quantity Η ποσότητα του πακέτου
     */
    public void setQuantity(int quantity) {
        this.quantity=quantity;
    }

}

