package models;

public class Product {

    private int productId;
    private String productName;
    private String category;
    private double price;
    private int quantity;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Product(
            int productId,
            String productName,
            String category,
            double price,
            int quantity) {

        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    // ==========================================
    // GET PRODUCT ID
    // ==========================================

    public int getProductId() {

        return productId;
    }

    // ==========================================
    // GET PRODUCT NAME
    // ==========================================

    public String getProductName() {

        return productName;
    }

    // ==========================================
    // GET CATEGORY
    // ==========================================

    public String getCategory() {

        return category;
    }

    // ==========================================
    // GET PRICE
    // ==========================================

    public double getPrice() {

        return price;
    }

    // ==========================================
    // GET QUANTITY
    // ==========================================

    public int getQuantity() {

        return quantity;
    }

    // ==========================================
    // UPDATE PRODUCT NAME
    // ==========================================

    public void setProductName(
            String productName) {

        this.productName = productName;
    }

    // ==========================================
    // UPDATE CATEGORY
    // ==========================================

    public void setCategory(
            String category) {

        this.category = category;
    }

    // ==========================================
    // UPDATE PRICE
    // ==========================================

    public void setPrice(
            double price) {

        this.price = price;
    }

    // ==========================================
    // UPDATE QUANTITY
    // ==========================================

    public void setQuantity(
            int quantity) {

        this.quantity = quantity;
    }

    // ==========================================
    // DISPLAY PRODUCT
    // ==========================================

    @Override
    public String toString() {

        return "Product ID: " + productId
                + ", Name: " + productName
                + ", Category: " + category
                + ", Price: " + price
                + ", Quantity: " + quantity;
    }
}