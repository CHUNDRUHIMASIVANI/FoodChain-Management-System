package services;

import models.Warehouse;
import models.Product;
import models.Inventory;

public class ConstraintService {

    // ==========================================
    // CHECK WAREHOUSE CAPACITY
    // ==========================================

    public boolean hasEnoughCapacity(
            Warehouse warehouse,
            int requiredQuantity) {

        if (warehouse == null) {
            return false;
        }

        if (requiredQuantity < 0) {
            return false;
        }

        return warehouse.getCapacity()
                >= requiredQuantity;
    }

    // ==========================================
    // CHECK PRODUCT QUANTITY
    // ==========================================

    public boolean isValidProductQuantity(
            Product product,
            int requiredQuantity) {

        if (product == null) {
            return false;
        }

        if (requiredQuantity <= 0) {
            return false;
        }

        return product.getQuantity()
                >= requiredQuantity;
    }

    // ==========================================
    // CHECK INVENTORY CONSTRAINT
    // ==========================================

    public boolean isValidInventoryQuantity(
            Inventory inventory,
            int requiredQuantity) {

        if (inventory == null) {
            return false;
        }

        if (requiredQuantity <= 0) {
            return false;
        }

        return inventory.getQuantity()
                >= requiredQuantity;
    }

    // ==========================================
    // CHECK MINIMUM STOCK
    // ==========================================

    public boolean maintainsMinimumStock(
            Inventory inventory,
            int quantityToRemove) {

        if (inventory == null) {
            return false;
        }

        if (quantityToRemove < 0) {
            return false;
        }

        int remainingQuantity =
                inventory.getQuantity()
                - quantityToRemove;

        return remainingQuantity
                >= inventory.getMinimumStock();
    }

    // ==========================================
    // CHECK ALL BASIC CONSTRAINTS
    // ==========================================

    public boolean isValidOrderQuantity(
            Product product,
            Inventory inventory,
            int quantity) {

        if (product == null ||
                inventory == null) {

            return false;
        }

        if (quantity <= 0) {
            return false;
        }

        if (inventory.getQuantity()
                < quantity) {

            return false;
        }

        return true;
    }
}