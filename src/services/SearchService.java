package services;

import java.util.ArrayList;

import models.Product;

public class SearchService {

    // ==========================================
    // SEARCH PRODUCT BY NAME
    // ==========================================

    public ArrayList<Product> searchByName(
            ArrayList<Product> products,
            String keyword) {

        ArrayList<Product> results =
                new ArrayList<>();

        if (products == null ||
                keyword == null) {

            return results;
        }

        keyword = keyword.trim();

        if (keyword.isEmpty()) {
            return results;
        }

        for (Product product : products) {

            if (product == null) {
                continue;
            }

            String productName =
                    product.getProductName();

            if (productName == null) {
                continue;
            }

            if (productName
                    .toLowerCase()
                    .contains(
                            keyword.toLowerCase())) {

                results.add(product);
            }
        }

        return results;
    }

    // ==========================================
    // DISPLAY SEARCH RESULTS
    // ==========================================

    public void displayResults(
            ArrayList<Product> results) {

        if (results == null ||
                results.isEmpty()) {

            System.out.println(
                    "No matching products found.");

            return;
        }

        System.out.println();
        System.out.println(
                "----------- SEARCH RESULTS -----------");

        for (Product product : results) {

            System.out.println(product);
        }
    }

    // ==========================================
    // CHECK PRODUCT NAME MATCH
    // ==========================================

    public boolean matchesName(
            Product product,
            String keyword) {

        if (product == null ||
                keyword == null) {

            return false;
        }

        if (product.getProductName() == null) {
            return false;
        }

        return product.getProductName()
                .toLowerCase()
                .contains(
                        keyword.toLowerCase());
    }
}