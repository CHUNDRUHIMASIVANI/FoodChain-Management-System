package models;

public class SupplyEvent {

    private int eventId;
    private int productId;
    private int quantity;
    private String eventType;
    private String eventDate;
    private String location;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public SupplyEvent(
            int eventId,
            int productId,
            int quantity,
            String eventType,
            String eventDate,
            String location) {

        this.eventId = eventId;
        this.productId = productId;
        this.quantity = quantity;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.location = location;
    }

    // ==========================================
    // GET EVENT ID
    // ==========================================

    public int getEventId() {

        return eventId;
    }

    // ==========================================
    // GET PRODUCT ID
    // ==========================================

    public int getProductId() {

        return productId;
    }

    // ==========================================
    // GET QUANTITY
    // ==========================================

    public int getQuantity() {

        return quantity;
    }

    // ==========================================
    // GET EVENT TYPE
    // ==========================================

    public String getEventType() {

        return eventType;
    }

    // ==========================================
    // GET EVENT DATE
    // ==========================================

    public String getEventDate() {

        return eventDate;
    }

    // ==========================================
    // GET LOCATION
    // ==========================================

    public String getLocation() {

        return location;
    }

    // ==========================================
    // UPDATE PRODUCT ID
    // ==========================================

    public void setProductId(int productId) {

        this.productId = productId;
    }

    // ==========================================
    // UPDATE QUANTITY
    // ==========================================

    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }

    // ==========================================
    // UPDATE EVENT TYPE
    // ==========================================

    public void setEventType(
            String eventType) {

        this.eventType = eventType;
    }

    // ==========================================
    // UPDATE EVENT DATE
    // ==========================================

    public void setEventDate(
            String eventDate) {

        this.eventDate = eventDate;
    }

    // ==========================================
    // UPDATE LOCATION
    // ==========================================

    public void setLocation(
            String location) {

        this.location = location;
    }

    // ==========================================
    // DISPLAY SUPPLY EVENT
    // ==========================================

    @Override
    public String toString() {

        return "Event ID: " + eventId
                + ", Product ID: " + productId
                + ", Quantity: " + quantity
                + ", Event Type: " + eventType
                + ", Event Date: " + eventDate
                + ", Location: " + location;
    }
}