package gr.aueb.softeng.project1805.service;

import gr.aueb.softeng.project1805.dao.ServiceUsageDAO;
import gr.aueb.softeng.project1805.dao.StatisticsDAO;
import gr.aueb.softeng.project1805.domain.Call;
import gr.aueb.softeng.project1805.domain.Data;
import gr.aueb.softeng.project1805.domain.SMS;
import gr.aueb.softeng.project1805.domain.ServiceType;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.ServiceUsageDAOMemory;
import gr.aueb.softeng.project1805.memorydao.StatisticsDAOMemory;
import gr.aueb.softeng.project1805.utils.SystemDate;

/**
 * H υπηρεσία παρακολούθησης των γεγονότων κλήσεων, κατανάλωσης data και SMS
 */
public class MonitorMobileEvents {
    private ServiceUsageDAO serviceUsageDAO;
    private StatisticsDAO statisticsDAO;
    private User user;

    /**
     * Βοηθητικός κατασκευαστής που δημιουργεί νέες λίστες για τα service usage και statistics.
     * @param user Ο χρήστης του οποίου παρακολουθούνται τα γεγονότα κλήσεων, καταναλωσης data και SMS
     */
    public MonitorMobileEvents(User user) {
        serviceUsageDAO=new ServiceUsageDAOMemory();
        statisticsDAO=new StatisticsDAOMemory();

        this.user=user;
    }

    /**
     * Αναλαμβάνει την ενημέρωση του υπολοίπου data του χρήστη.
     * Συγκεκριμένα αφαιρεί την ποσότητα data πρώτα από το υπόλοιπο καποιου(ή κάποιων) διαθέσιμων πακέτων τύπου Data(αν υπάρχουν)
     * και στη συνέχεια μείωνει αναλόγως το υπόλοιπο data του χρήστη.
     * Επιστρέφει το αντικείμενο Data που φτιάχνεται εφόσον γίνει η ενημέρωση. Αλλίως επιστρέφει {@code null}.
     * @param dataVolume Η ποσότητα data
     * @return Το αντικείμενο data
     */
    public Data newData(int dataVolume) {

        if (user.getRemainingData()>=dataVolume) {
            Data data = new Data(SystemDate.getSystemDate(), dataVolume);
            serviceUsageDAO.save(data, user.getUid());

            new PackageRemaining().updatePackageRemaining(user, ServiceType.Data, dataVolume);
            user.setRemainingData(user.getRemainingData() - dataVolume);

            statisticsDAO.save(user.getUid(), 3, dataVolume);

            return data;
        }
        return null;
    }

    /**
     * Αναλαμβάνει την ενημέρωση του υπολοίπου χρόνου ομιλίας του χρήστη.
     * Συγκεκριμένα αφαιρεί τη διάρκεια χρόνου ομιλίας πρώτα από το υπόλοιπο καποιου(ή κάποιων) διαθέσιμων πακέτων τύπου TalkTime(αν υπάρχουν)
     * και στη συνέχεια μείωνει αναλόγως το υπόλοιπο χρόνου ομιλίας του χρήστη.
     * Επιστρέφει το αντικείμενο Call που φτιάχνεται εφόσον γίνει η ενημέρωση. Αλλίως επιστρέφει {@code null}.
     * @param duration Η διάρκεια χρόνου ομιλίας
     * @param calledPhone Ο αριθμός τηλεφώνου που καλείται
     * @return Το αντικείμενο Call
     */
    public Call newCall(int duration, String calledPhone) {

        if (user.getRemainingTalkTime()>=duration) {
            Call call=new Call(SystemDate.getSystemDate(), duration, calledPhone);
            serviceUsageDAO.save(call, user.getUid());

            new PackageRemaining().updatePackageRemaining(user, ServiceType.TalkTime, duration);
            user.setRemainingTalkTime(user.getRemainingTalkTime() - duration);

            statisticsDAO.save(user.getUid(), 4, duration);

            return call;
        }
        return null;
    }

    /**
     * Αναλαμβάνει την ενημέρωση του υπολοίπου SMS του χρήστη.
     * Συγκεκριμένα μειώνει κατά 1 πρώτα το υπόλοιπο κάποιου διαθέσιμου πακέτου τύπου SMS(αν υπάρχει)
     * και στη συνέχεια μειώνει αναλόγως το υπόλοιπο χρόνου ομιλίας του χρήστη.
     * Επιστρέφει το αντικείμενο SMS που φτιάχνεται εφόσον γίνει η ενημέρωση. Αλλίως επιστρέφει {@code null}
     * @param receiverPhone Ο αριθμός τηλεφώνου στον οποίο στέλνεται το SMS
     * @return Το αντικείμενο
     */
    public SMS newSMS(String receiverPhone) {

        if (user.getRemainingSMS() >= 1) {
            SMS sms = new SMS(SystemDate.getSystemDate(), receiverPhone);
            serviceUsageDAO.save(sms, user.getUid());

            new PackageRemaining().updatePackageRemaining(user, ServiceType.SMS, 1);
            user.setRemainingSMS(user.getRemainingSMS() - 1);

            statisticsDAO.save(user.getUid(), 5, 1);

            return sms;
        }
        return null;
    }
}

