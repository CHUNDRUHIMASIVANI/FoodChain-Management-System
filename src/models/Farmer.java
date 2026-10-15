package models;

public class Farmer {

    private int farmerId;
    private String farmerName;
    private String location;
    private String contactNumber;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Farmer(
            int farmerId,
            String farmerName,
            String location,
            String contactNumber) {

        this.farmerId = farmerId;
        this.farmerName = farmerName;
        this.location = location;
        this.contactNumber = contactNumber;
    }

    // ==========================================
    // GET FARMER ID
    // ==========================================

    public int getFarmerId() {

        return farmerId;
    }

    // ==========================================
    // GET FARMER NAME
    // ==========================================

    public String getFarmerName() {

        return farmerName;
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
    // UPDATE FARMER NAME
    // ==========================================

    public void setFarmerName(
            String farmerName) {

        this.farmerName = farmerName;
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
    // DISPLAY FARMER
    // ==========================================

    @Override
    public String toString() {

        return "Farmer ID: " + farmerId
                + ", Name: " + farmerName
                + ", Location: " + location
                + ", Contact: " + contactNumber;
    }
}