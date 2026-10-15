package models;

public class DistributionCenter {

    private int centerId;
    private String centerName;
    private String location;
    private int capacity;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public DistributionCenter(
            int centerId,
            String centerName,
            String location,
            int capacity) {

        this.centerId = centerId;
        this.centerName = centerName;
        this.location = location;
        this.capacity = capacity;
    }

    // ==========================================
    // GET CENTER ID
    // ==========================================

    public int getCenterId() {

        return centerId;
    }

    // ==========================================
    // GET CENTER NAME
    // ==========================================

    public String getCenterName() {

        return centerName;
    }

    // ==========================================
    // GET LOCATION
    // ==========================================

    public String getLocation() {

        return location;
    }

    // ==========================================
    // GET CAPACITY
    // ==========================================

    public int getCapacity() {

        return capacity;
    }

    // ==========================================
    // UPDATE CENTER NAME
    // ==========================================

    public void setCenterName(
            String centerName) {

        this.centerName = centerName;
    }

    // ==========================================
    // UPDATE LOCATION
    // ==========================================

    public void setLocation(
            String location) {

        this.location = location;
    }

    // ==========================================
    // UPDATE CAPACITY
    // ==========================================

    public void setCapacity(
            int capacity) {

        this.capacity = capacity;
    }

    // ==========================================
    // DISPLAY DISTRIBUTION CENTER
    // ==========================================

    @Override
    public String toString() {

        return "Distribution Center ID: "
                + centerId
                + ", Name: "
                + centerName
                + ", Location: "
                + location
                + ", Capacity: "
                + capacity;
    }
}