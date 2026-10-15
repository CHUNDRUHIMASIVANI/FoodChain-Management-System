package models;

public class Supplier {

    private int supplierId;
    private String supplierName;
    private String location;
    private String contactNumber;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Supplier(
            int supplierId,
            String supplierName,
            String location,
            String contactNumber) {

        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.location = location;
        this.contactNumber = contactNumber;
    }

    // ==========================================
    // GET SUPPLIER ID
    // ==========================================

    public int getSupplierId() {

        return supplierId;
    }

    // ==========================================
    // GET SUPPLIER NAME
    // ==========================================

    public String getSupplierName() {

        return supplierName;
    }

    // ==========================================
    // GET LOCATION
    // ==========================================

    public String getLocation() {

        return location;
    }

    // ==========================================
    // GET CONTACT NUMBER
    // ==========================================

    public String getContactNumber() {

        return contactNumber;
    }

    // ==========================================
    // UPDATE SUPPLIER NAME
    // ==========================================

    public void setSupplierName(
            String supplierName) {

        this.supplierName = supplierName;
    }

    // ==========================================
    // UPDATE LOCATION
    // ==========================================

    public void setLocation(
            String location) {

        this.location = location;
    }

    // ==========================================
    // UPDATE CONTACT NUMBER
    // ==========================================

    public void setContactNumber(
            String contactNumber) {

        this.contactNumber = contactNumber;
    }

    // ==========================================
    // DISPLAY SUPPLIER
    // ==========================================

    @Override
    public String toString() {

        return "Supplier ID: " + supplierId
                + ", Name: " + supplierName
                + ", Location: " + location
                + ", Contact: " + contactNumber;
    }
}