package models;

public class Customer {

    private int customerId;
    private String customerName;
    private String address;
    private String contactNumber;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Customer(
            int customerId,
            String customerName,
            String address,
            String contactNumber) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    // ==========================================
    // GET CUSTOMER ID
    // ==========================================

    public int getCustomerId() {

        return customerId;
    }

    // ==========================================
    // GET CUSTOMER NAME
    // ==========================================

    public String getCustomerName() {

        return customerName;
    }

    // ==========================================
    // GET ADDRESS
    // ==========================================

    public String getAddress() {

        return address;
    }

    // ==========================================
    // GET CONTACT NUMBER
    // ==========================================

    public String getContactNumber() {

        return contactNumber;
    }

    // ==========================================
    // UPDATE CUSTOMER NAME
    // ==========================================

    public void setCustomerName(
            String customerName) {

        this.customerName = customerName;
    }

    // ==========================================
    // UPDATE ADDRESS
    // ==========================================

    public void setAddress(
            String address) {

        this.address = address;
    }

    // ==========================================
    // UPDATE CONTACT NUMBER
    // ==========================================

    public void setContactNumber(
            String contactNumber) {

        this.contactNumber = contactNumber;
    }

    // ==========================================
    // DISPLAY CUSTOMER
    // ==========================================

    @Override
    public String toString() {

        return "Customer ID: " + customerId
                + ", Name: " + customerName
                + ", Address: " + address
                + ", Contact: " + contactNumber;
    }
}