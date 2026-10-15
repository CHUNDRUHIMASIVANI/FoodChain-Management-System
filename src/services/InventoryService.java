package services;

import java.util.ArrayList;

import models.Inventory;

public class InventoryService {

    private ArrayList<Inventory> inventoryList;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public InventoryService() {

        inventoryList = new ArrayList<>();
    }

    // ==========================================
    // ADD INVENTORY
    // ==========================================

    public boolean addInventory(
            Inventory inventory) {

        if (inventory == null) {
            return false;
        }

        // Inventory ID must be unique
        if (findByInventoryId(
                inventory.getInventoryId()) != null) {

            return false;
        }

        // Quantity cannot be negative
        if (inventory.getQuantity() < 0) {
            return false;
        }

        // Minimum stock cannot be negative
        if (inventory.getMinimumStock() < 0) {
            return false;
        }

        inventoryList.add(inventory);

        return true;
    }

    // ==========================================
    // VIEW INVENTORY
    // ==========================================

    public void displayInventory() {

        if (inventoryList.isEmpty()) {

            System.out.println(
                    "No inventory available.");

            return;
        }

        System.out.println();
        System.out.println(
                "----------- INVENTORY LIST -----------");

        for (Inventory inventory : inventoryList) {

            System.out.println(inventory);
        }
    }

    // ==========================================
    // FIND BY INVENTORY ID
    // ==========================================

    public Inventory findByInventoryId(
            int inventoryId) {

        for (Inventory inventory : inventoryList) {

            if (inventory.getInventoryId()
                    == inventoryId) {

                return inventory;
            }
        }

        return null;
    }

    // ==========================================
    // FIND BY PRODUCT ID
    // ==========================================

    public Inventory findByProductId(
            int productId) {

        for (Inventory inventory : inventoryList) {

            if (inventory.getProductId()
                    == productId) {

                return inventory;
            }
        }

        return null;
    }

    // ==========================================
    // UPDATE COMPLETE INVENTORY
    // ==========================================

    public boolean updateInventory(
            int inventoryId,
            int warehouseId,
            int productId,
            int quantity,
            int minimumStock) {

        Inventory inventory =
                findByInventoryId(inventoryId);

        if (inventory == null) {
            return false;
        }

        if (quantity < 0 ||
                minimumStock < 0) {

            return false;
        }

        inventory.setWarehouseId(
                warehouseId);

        inventory.setProductId(
                productId);

        inventory.setQuantity(
                quantity);

        inventory.setMinimumStock(
                minimumStock);

        return true;
    }

    // ==========================================
    // UPDATE ONLY QUANTITY
    // ==========================================

    public boolean updateQuantity(
            int productId,
            int quantity) {

        Inventory inventory =
                findByProductId(productId);

        if (inventory == null) {
            return false;
        }

        if (quantity < 0) {
            return false;
        }

        inventory.setQuantity(quantity);

        return true;
    }

    // ==========================================
    // ADD STOCK
    // ==========================================

    public boolean addStock(
            int productId,
            int quantity) {

        if (quantity <= 0) {
            return false;
        }

        Inventory inventory =
                findByProductId(productId);

        if (inventory == null) {
            return false;
        }

        inventory.setQuantity(
                inventory.getQuantity()
                + quantity);

        return true;
    }

    // ==========================================
    // REMOVE STOCK
    // ==========================================

    public boolean removeStock(
            int productId,
            int quantity) {

        if (quantity <= 0) {
            return false;
        }

        Inventory inventory =
                findByProductId(productId);

        if (inventory == null) {
            return false;
        }

        // Cannot remove more than available
        // stock
        if (quantity >
                inventory.getQuantity()) {

            return false;
        }

        inventory.setQuantity(
                inventory.getQuantity()
                - quantity);

        return true;
    }

    // ==========================================
    // CHECK ENOUGH STOCK
    // ==========================================

    public boolean hasEnoughStock(
            int productId,
            int requestedQuantity) {

        if (requestedQuantity <= 0) {
            return false;
        }

        Inventory inventory =
                findByProductId(productId);

        if (inventory == null) {
            return false;
        }

        return inventory.getQuantity()
                >= requestedQuantity;
    }

    // ==========================================
    // CHECK LOW STOCK
    // ==========================================

    public boolean isLowStock(
            int productId) {

        Inventory inventory =
                findByProductId(productId);

        if (inventory == null) {
            return false;
        }

        return inventory.getQuantity()
                < inventory.getMinimumStock();
    }

    // ==========================================
    // DELETE INVENTORY
    // ==========================================

    public boolean deleteInventory(
            int inventoryId) {

        Inventory inventory =
                findByInventoryId(inventoryId);

        if (inventory == null) {
            return false;
        }

        inventoryList.remove(inventory);

        return true;
    }

    // ==========================================
    // CHECK INVENTORY EXISTS
    // ==========================================

    public boolean inventoryExists(
            int inventoryId) {

        return findByInventoryId(
                inventoryId) != null;
    }

    // ==========================================
    // GET ALL INVENTORY
    // ==========================================

    public ArrayList<Inventory>
    getInventoryList() {

        return inventoryList;
    }

    // ==========================================
    // GET INVENTORY COUNT
    // ==========================================

    public int getInventoryCount() {

        return inventoryList.size();
    }
}