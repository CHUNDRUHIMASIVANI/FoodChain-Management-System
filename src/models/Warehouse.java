package models;

public class Warehouse {

    private int warehouseId;
    private String warehouseName;
    private String location;
    private int capacity;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Warehouse(
            int warehouseId,
            String warehouseName,
            String location,
            int capacity) {

        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.location = location;
        this.capacity = capacity;
    }

    // ==========================================
    // GET WAREHOUSE ID
    // ==========================================

    public int getWarehouseId() {

        return warehouseId;
    }

    // ==========================================
    // GET WAREHOUSE NAME
    // ==========================================

    public String getWarehouseName() {

        return warehouseName;
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
    // UPDATE WAREHOUSE NAME
    // ==========================================

    public void setWarehouseName(
            String warehouseName) {

        this.warehouseName = warehouseName;
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
    // DISPLAY WAREHOUSE
    // ==========================================

    @Override
    public String toString() {

        return "Warehouse ID: " + warehouseId
                + ", Name: " + warehouseName
                + ", Location: " + location
                + ", Capacity: " + capacity;
    }
}