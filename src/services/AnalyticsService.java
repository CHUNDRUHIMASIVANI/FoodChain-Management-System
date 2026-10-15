package services;

import java.util.ArrayList;

import models.Product;
import models.Inventory;

public class AnalyticsService {

    // ==========================================
    // TOTAL PRODUCTS
    // ==========================================

    public int getTotalProducts(
            ArrayList<Product> products) {

        if (products == null) {
            return 0;
        }

        return products.size();
    }

    // ==========================================
    // TOTAL PRODUCT QUANTITY
    // ==========================================

    public int getTotalProductQuantity(
            ArrayList<Product> products) {

        if (products == null) {
            return 0;
        }

        int total = 0;

        for (Product product : products) {

            if (product != null) {

                total += product.getQuantity();
            }
        }

        return total;
    }

    // ==========================================
    // TOTAL INVENTORY QUANTITY
    // ==========================================

    public int getTotalInventoryQuantity(
            ArrayList<Inventory> inventoryList) {

        if (inventoryList == null) {
            return 0;
        }

        int total = 0;

        for (Inventory inventory : inventoryList) {

            if (inventory != null) {

                total += inventory.getQuantity();
            }
        }

        return total;
    }

    // ==========================================
    // COUNT LOW STOCK PRODUCTS
    // ==========================================

    public int countLowStock(
            ArrayList<Inventory> inventoryList) {

        if (inventoryList == null) {
            return 0;
        }

        int count = 0;

        for (Inventory inventory : inventoryList) {

            if (inventory != null &&
                    inventory.getQuantity()
                    < inventory.getMinimumStock()) {

                count++;
            }
        }

        return count;
    }

    // ==========================================
    // DISPLAY PRODUCT ANALYTICS
    // ==========================================

    public void displayProductAnalytics(
            ArrayList<Product> products) {

        if (products == null ||
                products.isEmpty()) {

            System.out.println(
                    "No product data available.");

            return;
        }

        int totalProducts =
                getTotalProducts(products);

        int totalQuantity =
                getTotalProductQuantity(products);

        System.out.println();
        System.out.println(
                "========== PRODUCT ANALYTICS ==========");

        System.out.println(
                "Total Products: "
                + totalProducts);

        System.out.println(
                "Total Product Quantity: "
                + totalQuantity);
    }

    // ==========================================
    // DISPLAY INVENTORY ANALYTICS
    // ==========================================

    public void displayInventoryAnalytics(
            ArrayList<Inventory> inventoryList) {

        if (inventoryList == null ||
                inventoryList.isEmpty()) {

            System.out.println(
                    "No inventory data available.");

            return;
        }

        int totalQuantity =
                getTotalInventoryQuantity(
                        inventoryList);

        int lowStockCount =
                countLowStock(
                        inventoryList);

        System.out.println();
        System.out.println(
                "========= INVENTORY ANALYTICS =========");

        System.out.println(
                "Total Inventory Quantity: "
                + totalQuantity);

        System.out.println(
                "Low Stock Items: "
                + lowStockCount);
    }
}