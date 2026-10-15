package models;

public class Shipment {

    private int shipmentId;
    private int orderId;
    private int warehouseId;
    private String shipmentDate;
    private String deliveryDate;
    private String status;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Shipment(
            int shipmentId,
            int orderId,
            int warehouseId,
            String shipmentDate,
            String deliveryDate,
            String status) {

        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.warehouseId = warehouseId;
        this.shipmentDate = shipmentDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
    }

    // ==========================================
    // GET SHIPMENT ID
    // ==========================================

    public int getShipmentId() {

        return shipmentId;
    }

    // ==========================================
    // GET ORDER ID
    // ==========================================

    public int getOrderId() {

        return orderId;
    }

    // ==========================================
    // GET WAREHOUSE ID
    // ==========================================

    public int getWarehouseId() {

        return warehouseId;
    }

    // ==========================================
    // GET SHIPMENT DATE
    // ==========================================

    public String getShipmentDate() {

        return shipmentDate;
    }

    // ==========================================
    // GET DELIVERY DATE
    // ==========================================

    public String getDeliveryDate() {

        return deliveryDate;
    }

    // ==========================================
    // GET STATUS
    // ==========================================

    public String getStatus() {

        return status;
    }

    // ==========================================
    // UPDATE WAREHOUSE
    // ==========================================

    public void setWarehouseId(
            int warehouseId) {

        this.warehouseId = warehouseId;
    }

    // ==========================================
    // UPDATE SHIPMENT DATE
    // ==========================================

    public void setShipmentDate(
            String shipmentDate) {

        this.shipmentDate = shipmentDate;
    }

    // ==========================================
    // UPDATE DELIVERY DATE
    // ==========================================

    public void setDeliveryDate(
            String deliveryDate) {

        this.deliveryDate = deliveryDate;
    }

    // ==========================================
    // UPDATE STATUS
    // ==========================================

    public void setStatus(
            String status) {

        this.status = status;
    }

    // ==========================================
    // DISPLAY SHIPMENT
    // ==========================================

    @Override
    public String toString() {

        return "Shipment ID: " + shipmentId
                + ", Order ID: " + orderId
                + ", Warehouse ID: " + warehouseId
                + ", Shipment Date: " + shipmentDate
                + ", Delivery Date: " + deliveryDate
                + ", Status: " + status;
    }
}