package gr.aueb.softeng.project1805.bank;

/**
 * Βοηθητική κλάση για λογαριασμό τράπεζας.
 */
public class Account {
    private String card_num;
    private double balance;

    /**
     * Προκαθορισμένος κατασκευαστής.
     */
    public Account() {}

    /**
     * Βοηθητικός κατασκευαστής που αρχικοποιεί έναν λογαριασμό.
     * @param card_num Αριθμός κάρτας
     * @param balance Υπόλοιπο λογαριασμού
     */
    public Account(String card_num, double balance) {
        this.card_num=card_num;
        this.balance=balance;
    }

    /**
     * Επιστρέφερι τον αριθμό κάρτας.
     * @return card_num Ο αριθμός κάρτας
     */
    public String getCard_num() {
        return card_num;
    }

    /**
     * Θέτει τον αριθμό κάρτας.
     * @param card_num Ο αριθμός κάρτας
     */
    public void setCard_num(String card_num) {
        this.card_num=card_num;
    }

    /**
     * Επιστρέφει το υπόλοιπο.
     * @return balance Το υπόλοιπο
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Θέτει το υπόλοιπο.
     * @param balance Το υπόλοιπο
     */
    public void setBalance(double balance) {
        this.balance=balance;
    }
}

