package domain;

/**
 *
 * @author Pyzyryab <zerodaycode.eu>
 */
public class Client {

    private int clientID;
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;
    private double balance;

    // Overloaded constructors for handle the different CRUD operations
    public Client() {
    }

    public Client(int clientID) {
        this.clientID = clientID;
    }

    public Client(String name, String lastName, String email, String phoneNumber, double balance) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
    }

    public Client(int clientID, String name, String lastName, String email, String phoneNumber, double balance) {
        this.clientID = clientID;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
    }

    // Getters and setter mapping each private class member
    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Client {" + "clientID=" + clientID + ", name=" + name + ", lastName=" + lastName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", balance=" + balance + '}';
    }

}
