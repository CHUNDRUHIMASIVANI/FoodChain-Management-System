package services;

import java.util.ArrayList;

import models.Product;
import models.Supplier;

public class MatchingService {

    // ==========================================
    // FIND SUPPLIERS FOR A PRODUCT CATEGORY
    // ==========================================

    public ArrayList<Supplier> findMatchingSuppliers(
            ArrayList<Supplier> suppliers,
            String location) {

        ArrayList<Supplier> results =
                new ArrayList<>();

        if (suppliers == null ||
                location == null ||
                location.trim().isEmpty()) {

            return results;
        }

        String searchLocation =
                location.trim().toLowerCase();

        for (Supplier supplier : suppliers) {

            if (supplier == null ||
                    supplier.getLocation() == null) {

                continue;
            }

            if (supplier.getLocation()
                    .toLowerCase()
                    .contains(searchLocation)) {

                results.add(supplier);
            }
        }

        return results;
    }

    // ==========================================
    // DISPLAY MATCHING SUPPLIERS
    // ==========================================

    public void displayMatchingSuppliers(
            ArrayList<Supplier> suppliers) {

        if (suppliers == null ||
                suppliers.isEmpty()) {

            System.out.println(
                    "No matching suppliers found.");

            return;
        }

        System.out.println();
        System.out.println(
                "--------- MATCHING SUPPLIERS ---------");

        for (Supplier supplier : suppliers) {

            System.out.println(supplier);
        }
    }

    // ==========================================
    // MATCH PRODUCT WITH SUPPLIER LOCATION
    // ==========================================

    public boolean canSupply(
            Product product,
            Supplier supplier) {

        if (product == null ||
                supplier == null) {

            return false;
        }

        if (supplier.getLocation() == null) {
            return false;
        }

        return !supplier.getLocation()
                .trim()
                .isEmpty();
    }

    // ==========================================
    // FIND FIRST AVAILABLE SUPPLIER
    // ==========================================

    public Supplier findFirstSupplier(
            ArrayList<Supplier> suppliers) {

        if (suppliers == null ||
                suppliers.isEmpty()) {

            return null;
        }

        for (Supplier supplier : suppliers) {

            if (supplier != null) {
                return supplier;
            }
        }

        return null;
    }
}