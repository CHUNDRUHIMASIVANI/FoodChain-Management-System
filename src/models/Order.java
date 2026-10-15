package models;

public class Order {

    private int orderId;
    private int customerId;
    private int productId;
    private int quantity;
    private String orderDate;
    private String status;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Order(
            int orderId,
            int customerId,
            int productId,
            int quantity,
            String orderDate,
            String status) {

        this.orderId = orderId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.status = status;
    }

    // ==========================================
    // GET ORDER ID
    // ==========================================

    public int getOrderId() {

        return orderId;
    }

    // ==========================================
    // GET CUSTOMER ID
    // ==========================================

    public int getCustomerId() {

        return customerId;
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
    // GET ORDER DATE
    // ==========================================

    public String getOrderDate() {

        return orderDate;
    }

    // ==========================================
    // GET STATUS
    // ==========================================

    public String getStatus() {

        return status;
    }

    // ==========================================
    // UPDATE QUANTITY
    // ==========================================

    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }

    // ==========================================
    // UPDATE ORDER DATE
    // ==========================================

    public void setOrderDate(
            String orderDate) {

        this.orderDate = orderDate;
    }

    // ==========================================
    // UPDATE STATUS
    // ==========================================

    public void setStatus(
            String status) {

        this.status = status;
    }

    // ==========================================
    // DISPLAY ORDER
    // ==========================================

    @Override
    public String toString() {

        return "Order ID: " + orderId
                + ", Customer ID: " + customerId
                + ", Product ID: " + productId
                + ", Quantity: " + quantity
                + ", Order Date: " + orderDate
                + ", Status: " + status;
    }
}