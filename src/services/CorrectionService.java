package services;

import java.util.ArrayList;

import models.Product;
import dsa.m3_dynamic.Levenshtein;

public class CorrectionService {

    // ==========================================
    // FIND CLOSEST PRODUCT NAME
    // ==========================================

    public Product findClosestProduct(
            ArrayList<Product> products,
            String input) {

        if (products == null ||
                products.isEmpty() ||
                input == null ||
                input.trim().isEmpty()) {

            return null;
        }

        input = input.trim();

        Product closestProduct = null;

        int minimumDistance =
                Integer.MAX_VALUE;

        for (Product product : products) {

            if (product == null ||
                    product.getProductName() == null) {

                continue;
            }

            int distance =
                    Levenshtein.distance(
                            input.toLowerCase(),
                            product.getProductName()
                                    .toLowerCase());

            if (distance < minimumDistance) {

                minimumDistance = distance;

                closestProduct = product;
            }
        }

        return closestProduct;
    }

    // ==========================================
    // GET CORRECTION DISTANCE
    // ==========================================

    public int getCorrectionDistance(
            String input,
            Product product) {

        if (input == null ||
                product == null ||
                product.getProductName() == null) {

            return -1;
        }

        return Levenshtein.distance(
                input.toLowerCase(),
                product.getProductName()
                        .toLowerCase());
    }

    // ==========================================
    // DISPLAY CORRECTION
    // ==========================================

    public void displayCorrection(
            String input,
            Product product) {

        if (product == null) {

            System.out.println(
                    "No similar product found.");

            return;
        }

        int distance =
                getCorrectionDistance(
                        input,
                        product);

        System.out.println();
        System.out.println(
                "Did you mean: "
                + product.getProductName()
                + "?");

        System.out.println(
                "Edit Distance: "
                + distance);
    }
}