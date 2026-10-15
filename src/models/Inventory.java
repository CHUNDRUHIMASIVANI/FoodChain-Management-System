package models;

public class Inventory {

    private int inventoryId;
    private int warehouseId;
    private int productId;
    private int quantity;
    private int minimumStock;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Inventory(
            int inventoryId,
            int warehouseId,
            int productId,
            int quantity,
            int minimumStock) {

        this.inventoryId = inventoryId;
        this.warehouseId = warehouseId;
        this.productId = productId;
        this.quantity = quantity;
        this.minimumStock = minimumStock;
    }

    // ==========================================
    // GET INVENTORY ID
    // ==========================================

    public int getInventoryId() {

        return inventoryId;
    }

    // ==========================================
    // GET WAREHOUSE ID
    // ==========================================

    public int getWarehouseId() {

        return warehouseId;
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
    // GET MINIMUM STOCK
    // ==========================================

    public int getMinimumStock() {

        return minimumStock;
    }

    // ==========================================
    // UPDATE WAREHOUSE ID
    // ==========================================

    public void setWarehouseId(
            int warehouseId) {

        this.warehouseId = warehouseId;
    }

    // ==========================================
    // UPDATE PRODUCT ID
    // ==========================================

    public void setProductId(
            int productId) {

        this.productId = productId;
    }

    // ==========================================
    // UPDATE QUANTITY
    // ==========================================

    public void setQuantity(
            int quantity) {

        this.quantity = quantity;
    }

    // ==========================================
    // UPDATE MINIMUM STOCK
    // ==========================================

    public void setMinimumStock(
            int minimumStock) {

        this.minimumStock = minimumStock;
    }

    // ==========================================
    // DISPLAY INVENTORY
    // ==========================================

    @Override
    public String toString() {

        return "Inventory ID: " + inventoryId
                + ", Warehouse ID: " + warehouseId
                + ", Product ID: " + productId
                + ", Quantity: " + quantity
                + ", Minimum Stock: " + minimumStock;
    }
}