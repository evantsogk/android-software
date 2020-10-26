package gr.aueb.softeng.project1805.service;

import java.util.ArrayList;

import gr.aueb.softeng.project1805.dao.ActivePackageDAO;
import gr.aueb.softeng.project1805.dao.ServiceUsageDAO;
import gr.aueb.softeng.project1805.dao.StatisticsDAO;
import gr.aueb.softeng.project1805.dao.TransferDAO;
import gr.aueb.softeng.project1805.dao.UserDAO;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.ActivePackageDAOMemory;
import gr.aueb.softeng.project1805.memorydao.ServiceUsageDAOMemory;
import gr.aueb.softeng.project1805.memorydao.StatisticsDAOMemory;
import gr.aueb.softeng.project1805.memorydao.TransferDAOMemory;
import gr.aueb.softeng.project1805.memorydao.UserDAOMemory;

/**
 * Η υπηρεσία ταυτοποίησης του χρήστη
 */
public class UserAuthentication {
    private UserDAO userDAO;
    private ArrayList<User> users;

    /**
     * Κατασκευαστής που δημιουργεί μια νέα λίστα με τους ήδη εγγεγραμμένους χρήστες
     */
    public UserAuthentication() {
        userDAO = new UserDAOMemory();
        users = (ArrayList<User>) userDAO.findAll();
    }

    /**
     * Αναλαμβάνει την είσοδο του χρήστη στην εφαρμογή.
     * Ελέγχει αν ο συνδυασμός του username και του password που έδωσε ο χρήστης υπάρχει στη λίστα με τους χρήστες.
     * Αν υπάρχει επιστρέφει τον χρήστη αλλιώς επιστρέφει {@code null}.
     * @param username Το username του χρήστη
     * @param password Ο κωδικός του χρήστη
     * @return Τον χρήστη
     */
    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Αναλαμβάνει την εγγραφή του χρήστη.
     * Πιο συγκεκριμένα ελέγχει αν το username ή ο αριθμός τηλεφώνου που δίνει ο χρήστης υπάρχουν στη λίστα με τους χρήστες.
     * Αν δεν υπάρχουν τότε δημιουργεί τον χρήστη και καταγράφει τα στοιχεία του και καλεί την login, αλλίως επιστρέφει null.
     * @param username Το username που δίνει ο χρήστης
     * @param password Ο κωδικός που δίνει ο χρήστης για την εγγραφή
     * @param phoneNum Ο αριθμος τηλεφώνου που δίνει ο χρήστης
     * @return Τον χρήστη
     */
    public User signUp(String username, String password, String phoneNum) {
        for (User user : users) {
            if (user.getUsername().equals(username) || user.getPhoneNum().equals(phoneNum)) {
                return null;
            }
        }

        userDAO.save(new User(userDAO.nextId(), phoneNum, username, password));

        users = (ArrayList<User>) userDAO.findAll();

        ActivePackageDAO activePackageDAO=new ActivePackageDAOMemory();
        activePackageDAO.addUser();

        ServiceUsageDAO serviceUsageDAO=new ServiceUsageDAOMemory();
        serviceUsageDAO.addUser();

        TransferDAO transferDAO=new TransferDAOMemory();
        transferDAO.AddUser();

        StatisticsDAO statisticsDAO=new StatisticsDAOMemory();
        statisticsDAO.addUser();
        statisticsDAO.addMonth(users.get(users.size()-1).getUid());

        return login(username, password);
    }
}

