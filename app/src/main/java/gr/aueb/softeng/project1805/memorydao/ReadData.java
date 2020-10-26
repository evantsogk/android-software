package gr.aueb.softeng.project1805.memorydao;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

import java.io.InputStream;
import java.util.Calendar;

import gr.aueb.softeng.project1805.bank.Account;
import gr.aueb.softeng.project1805.bank.AccountDAO;
import gr.aueb.softeng.project1805.bank.AccountDAOMemory;
import gr.aueb.softeng.project1805.dao.ActivePackageDAO;
import gr.aueb.softeng.project1805.dao.PackageDAO;
import gr.aueb.softeng.project1805.dao.ServiceUsageDAO;
import gr.aueb.softeng.project1805.dao.StatisticsDAO;
import gr.aueb.softeng.project1805.dao.TransferDAO;
import gr.aueb.softeng.project1805.dao.UserDAO;
import gr.aueb.softeng.project1805.domain.ActivePackage;
import gr.aueb.softeng.project1805.domain.Call;
import gr.aueb.softeng.project1805.domain.Data;
import gr.aueb.softeng.project1805.domain.SMS;
import gr.aueb.softeng.project1805.domain.ServiceType;
import gr.aueb.softeng.project1805.domain.ServiceUsage;
import gr.aueb.softeng.project1805.domain.Transfer;
import gr.aueb.softeng.project1805.domain.TransferType;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.domain.Package;
import gr.aueb.softeng.project1805.utils.SystemDate;

public class ReadData {

    private UserDAO userDAO=new UserDAOMemory();
    private PackageDAO packageDAO=new PackageDAOMemory();
    private ActivePackageDAO activePackageDAO=new ActivePackageDAOMemory();
    private TransferDAO transferDAO=new TransferDAOMemory();
    private ServiceUsageDAO serviceUsageDAO=new ServiceUsageDAOMemory();
    private StatisticsDAO statistics=new StatisticsDAOMemory();
    private AccountDAO accountDAO=new AccountDAOMemory();

    public void readUsers(InputStream file) {

        try {

            DocumentBuilder builder=DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
            Document doc=builder.parse(file, null);

            doc.getDocumentElement().normalize();

            NodeList list=doc.getElementsByTagName("user");

            int uid;
            String phone_num;
            String username;
            String password;
            int remaining_data;
            int remaining_talktime;
            int remaining_sms;
            User user;

            for (int i=0; i<list.getLength(); i++) {
                Node n=list.item(i);

                if (n.getNodeType()==Node.ELEMENT_NODE) {
                    Element eElement=(Element) n;

                    uid=Integer.parseInt(eElement.getElementsByTagName("uid").item(0).getTextContent());
                    phone_num=eElement.getElementsByTagName("user_phone").item(0).getTextContent();
                    username=eElement.getElementsByTagName("username").item(0).getTextContent();
                    password=eElement.getElementsByTagName("password").item(0).getTextContent();
                    remaining_data=Integer.parseInt(eElement.getElementsByTagName("data").item(0).getTextContent());
                    remaining_talktime=Integer.parseInt(eElement.getElementsByTagName("talktime").item(0).getTextContent());
                    remaining_sms=Integer.parseInt(eElement.getElementsByTagName("sms").item(0).getTextContent());
                    user=new User(uid, phone_num, username, password);
                    user.setRemainingData(remaining_data);
                    user.setRemainingTalkTime(remaining_talktime);
                    user.setRemainingSMS(remaining_sms);
                    userDAO.save(user);

                    activePackageDAO.addUser();
                    transferDAO.AddUser();
                    serviceUsageDAO.addUser();
                    statistics.addUser();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readPackages(InputStream file) {

        int packageId;
        String name;
        int duration;
        float price;
        String serviceType;
        int quantity;
        Package pack;

        try {

            DocumentBuilder builder=DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
            Document doc=builder.parse(file, null);

            doc.getDocumentElement().normalize();

            NodeList list=doc.getElementsByTagName("package");

            for (int i=0; i<list.getLength(); i++) {
                Node n = list.item(i);

                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) n;

                    packageId=Integer.parseInt(eElement.getElementsByTagName("package_id").item(0).getTextContent());
                    name=eElement.getElementsByTagName("package_name").item(0).getTextContent();
                    duration=Integer.parseInt(eElement.getElementsByTagName("duration").item(0).getTextContent());
                    price=Float.parseFloat(eElement.getElementsByTagName("price").item(0).getTextContent());
                    serviceType=eElement.getElementsByTagName("service_type").item(0).getTextContent();
                    quantity=Integer.parseInt(eElement.getElementsByTagName("quantity").item(0).getTextContent());

                    if(serviceType.equals("Data")){
                        pack=new Package(packageId,name,duration,price, ServiceType.Data,quantity);
                    }else if(serviceType.equals("TalkTime")){
                        pack=new Package(packageId,name,duration,price,ServiceType.TalkTime,quantity);
                    }else{
                        pack=new Package(packageId,name,duration,price,ServiceType.SMS,quantity);
                    }

                    packageDAO.save(pack);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readActivePackages(InputStream file) {

        try {

            DocumentBuilder builder=DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
            Document doc=builder.parse(file, null);

            doc.getDocumentElement().normalize();


            NodeList list=doc.getElementsByTagName("active_packages");
            NodeList user_packages_list = ((Element)list.item(0)).getElementsByTagName("user_packages");


            for (int i=0; i<user_packages_list.getLength(); i++) {

                NodeList packages_list=user_packages_list.item(i).getChildNodes();

                int id, year, month, day;
                String isActive;
                int remaining_quantity;
                ActivePackage activePackage;
                Calendar  calendar = SystemDate.getSystemDate();

                for (int j=0; j<packages_list.getLength(); j++) {

                    Node n=packages_list.item(j);
                    if (n.getNodeType()==Node.ELEMENT_NODE) {

                        Element eElement=(Element) n;

                        id=Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent());
                        year=Integer.parseInt(eElement.getElementsByTagName("year").item(0).getTextContent());
                        month=Integer.parseInt(eElement.getElementsByTagName("month").item(0).getTextContent());
                        month--;
                        day=Integer.parseInt(eElement.getElementsByTagName("day").item(0).getTextContent());
                        isActive=eElement.getElementsByTagName("isActive").item(0).getTextContent();
                        remaining_quantity=Integer.parseInt(eElement.getElementsByTagName("remaining_quantity").item(0).getTextContent());

                        calendar.set(year, month, day);
                        Boolean isActive1;
                        if (isActive.equals("true")) isActive1=true;
                        else isActive1=false;

                        activePackage=new ActivePackage(id, calendar, isActive1, remaining_quantity);
                        activePackageDAO.save(activePackage, i);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readTransfers(InputStream file) {

        try {
            DocumentBuilder builder=DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
            Document doc=builder.parse(file, null);

            doc.getDocumentElement().normalize();


            NodeList list=doc.getElementsByTagName("transfers");
            NodeList user_transfers_list = ((Element)list.item(0)).getElementsByTagName("user_transfers");


            for (int i=0; i<user_transfers_list.getLength(); i++) {

                NodeList transfers_list=user_transfers_list.item(i).getChildNodes();

                int year,month, day;
                String senderNum, receiverNum;
                int quantity;
                String serviceType, transferType;
                String received;
                Calendar calendar = SystemDate.getSystemDate();
                Transfer transfer;

                for (int j=0; j<transfers_list.getLength(); j++) {

                    Node n=transfers_list.item(j);
                    if (n.getNodeType()==Node.ELEMENT_NODE) {

                        Element eElement=(Element) n;

                        year=Integer.parseInt(eElement.getElementsByTagName("year").item(0).getTextContent());
                        month=Integer.parseInt(eElement.getElementsByTagName("month").item(0).getTextContent());
                        month--;
                        day=Integer.parseInt(eElement.getElementsByTagName("day").item(0).getTextContent());
                        senderNum=eElement.getElementsByTagName("sender_num").item(0).getTextContent();
                        receiverNum=eElement.getElementsByTagName("receiver_num").item(0).getTextContent();
                        quantity=Integer.parseInt(eElement.getElementsByTagName("quantity").item(0).getTextContent());
                        serviceType=eElement.getElementsByTagName("serviceType").item(0).getTextContent();
                        transferType=eElement.getElementsByTagName("transferType").item(0).getTextContent();
                        received=eElement.getElementsByTagName("received").item(0).getTextContent();

                        calendar.set(year, month, day);
                        ServiceType service_type;
                        if (serviceType.equals("Data")) service_type=ServiceType.Data;
                        else if (serviceType.equals("TalkTime")) service_type=ServiceType.TalkTime;
                        else service_type=ServiceType.SMS;
                        TransferType transfer_type;
                        if (transferType.equals("incoming")) transfer_type=TransferType.Incoming;
                        else transfer_type=TransferType.Outgoing;
                        Boolean received1;
                        if (received.equals("true")) received1=true;
                        else received1=false;

                        transfer=new Transfer(calendar, senderNum, receiverNum, quantity, service_type, transfer_type);
                        if (received1) transfer.received();
                        transferDAO.save(transfer, i);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readServiceUsages(InputStream file) {

        try {

            DocumentBuilder builder=DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
            Document doc=builder.parse(file, null);

            doc.getDocumentElement().normalize();


            NodeList list=doc.getElementsByTagName("usages");
            NodeList user_usage_list = ((Element)list.item(0)).getElementsByTagName("user_usage");


            for (int i=0; i<user_usage_list.getLength(); i++) {

                NodeList usage_list=user_usage_list.item(i).getChildNodes();

                int year, month, day;
                String service_type;
                int data_volume;
                String receiver_phone, called_phone;
                int duration;
                Calendar calendar = SystemDate.getSystemDate();
                Data data;
                Call call;
                SMS sms;

                for (int j=0; j<usage_list.getLength(); j++) {

                    Node n=usage_list.item(j);
                    if (n.getNodeType()==Node.ELEMENT_NODE) {

                        Element eElement=(Element) n;

                        year=Integer.parseInt(eElement.getElementsByTagName("year").item(0).getTextContent());
                        month=Integer.parseInt(eElement.getElementsByTagName("month").item(0).getTextContent());
                        month--;
                        day=Integer.parseInt(eElement.getElementsByTagName("day").item(0).getTextContent());
                        service_type=eElement.getElementsByTagName("service_type").item(0).getTextContent();

                        calendar.set(year, month, day);

                        ServiceType serviceType;
                        if (service_type.equals("Data")) {
                            data_volume=Integer.parseInt(eElement.getElementsByTagName("data_volume").item(0).getTextContent());
                            serviceType=ServiceType.Data;

                            data=new Data(calendar, data_volume);
                            serviceUsageDAO.save(data, i);
                        }
                        else if (service_type.equals("TalkTime")) {
                            called_phone=eElement.getElementsByTagName("called_phone").item(0).getTextContent();
                            duration=Integer.parseInt(eElement.getElementsByTagName("duration").item(0).getTextContent());
                            serviceType=ServiceType.TalkTime;

                            call=new Call(calendar, duration, called_phone);
                            serviceUsageDAO.save(call, i);
                        }
                        else {
                            receiver_phone=eElement.getElementsByTagName("receiver_phone").item(0).getTextContent();
                            serviceType=ServiceType.SMS;

                            sms = new SMS(calendar, receiver_phone);
                            serviceUsageDAO.save(sms, i);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readStatistics(InputStream file) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
            Document doc = builder.parse(file, null);

            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("statistics");
            NodeList user_statistics_list = ((Element)list.item(0)).getElementsByTagName("user_statistics");

            for (int i=0; i<user_statistics_list.getLength(); i++){
                NodeList statistics_list=user_statistics_list.item(i).getChildNodes();

                float year;
                float month;
                float price;
                float data;
                float talktime;
                float sms;

                statistics.addUser();
                for (int j=0; j<statistics_list.getLength(); j++) {
                    Node n=statistics_list.item(j);
                    if (n.getNodeType()==Node.ELEMENT_NODE) {
                        Element eElement=(Element) n;

                        year=Float.parseFloat(eElement.getElementsByTagName("year").item(0).getTextContent());
                        month=Float.parseFloat(eElement.getElementsByTagName("month").item(0).getTextContent());
                        month--;
                        price=Float.parseFloat(eElement.getElementsByTagName("price").item(0).getTextContent());
                        data=Float.parseFloat(eElement.getElementsByTagName("data").item(0).getTextContent());
                        talktime=Float.parseFloat(eElement.getElementsByTagName("talktime").item(0).getTextContent());
                        sms=Float.parseFloat(eElement.getElementsByTagName("sms").item(0).getTextContent());
                        statistics.addMonthSimple(i);
                        statistics.save(i,0,year);
                        statistics.save(i,1,month);
                        statistics.save(i,2,price);
                        statistics.save(i,3,data);
                        statistics.save(i,4,talktime);
                        statistics.save(i,5,sms);

                    }

                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void readAccounts(InputStream file){

        try {

            DocumentBuilder builder=DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
            Document doc=builder.parse(file, null);

            doc.getDocumentElement().normalize();

            NodeList list=doc.getElementsByTagName("account");

            String card_num;
            double balance;
            Account account;


            for (int i=0; i<list.getLength(); i++) {
                Node n=list.item(i);

                if (n.getNodeType()==Node.ELEMENT_NODE) {
                    Element eElement=(Element) n;

                    card_num=eElement.getElementsByTagName("card_num").item(0).getTextContent();
                    balance=Double.parseDouble(eElement.getElementsByTagName("balance").item(0).getTextContent());

                    account=new Account(card_num,balance);
                    accountDAO.save(account);

                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public User getUser(int user) {
        User loggedInUser = getUserDAO().find(user);

        for (ActivePackage activePackage : getActivePackageDAO().findAllPackages(user)) {
            loggedInUser.addPackage(activePackage);
        }

        for (Transfer transfer : getTransferDAO().findAll(user)) {
            loggedInUser.addTransfer(transfer);
        }

        for (ServiceUsage serviceUsage : getServiceUsageDAO().findAll(user)) {
            loggedInUser.addService(serviceUsage);
        }
        return loggedInUser;
    }


    /**
     * Επιστρέφει την εξωτερική λίστα με τους χρήστες.
     * @return Η εξωτερική λίστα με τους χρήστες
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * Επιστρέφει την εξωτερική λίστα με τα πακέτα.
     * @return Η εξωτερική λίστα με τα πακέτα
     */
    public PackageDAO getPackageDAO() {
        return packageDAO;
    }

    /**
     * Επιστρέφει την εξωτερική λίστα με τα ενεργά πακέτα των χρηστών
     * @return Η εξωτερική λίστα με τα ενερά πακέτα των χρηστών
     */
    public ActivePackageDAO getActivePackageDAO() {
        return activePackageDAO;
    }

    /**
     * Επιστρέφει την εξωτερική λίστα με τις παραχωρήσεις των χρηστών.
     * @return Η εξωτερική λίστα με τις παραχωρήσεις των χρηστών
     */
    public TransferDAO getTransferDAO() {
        return transferDAO;
    }

    /**
     * Επιστρέφει την εξωτερική λίστα με τα service usages των χρηστών.
     * @return Η εξωτερική λίστα με τα service usages των χρηστων
     */
    public ServiceUsageDAO getServiceUsageDAO() {
        return serviceUsageDAO;
    }

    /**
     * Επιστρέφει την εξωτερική λίστα με τα στατιστικά στοιχεία των χρηστών.
     * @return Η εξωτερική λίστα με τα στατιστικά στοιχεία των χρηστών
     */
    public StatisticsDAO getStatisticsDAO() {
        return statistics;
    }

    /**
     * Επιστρέφει την εξωτερική λίστα με τους λογαριασμούς.
     * @return Η εξωτερική λίστα με τους λογαριασμούς
     */
    public AccountDAO getAccountDAO() { return accountDAO; }

    public void eraseData() {
        getAccountDAO().deleteAll();
        getActivePackageDAO().deleteAll();
        getPackageDAO().deleteAll();
        getServiceUsageDAO().deleteAll();
        getStatisticsDAO().deleteAll();
        getTransferDAO().deleteAll();

        for (User user : getUserDAO().findAll()) {
            activePackageDAO.addUser();
            transferDAO.AddUser();
            serviceUsageDAO.addUser();
            statistics.addUser();
        }

        getUserDAO().deleteAll();
    }
}

