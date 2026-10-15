package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.DatabaseConnection;
import models.Product;

public class ProductService {

    private ArrayList<Product> products;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public ProductService() {

        products = new ArrayList<>();

        loadProductsFromDatabase();
    }

    // ==========================================
    // LOAD PRODUCTS FROM MYSQL
    // ==========================================

    private void loadProductsFromDatabase() {

        String sql = "SELECT product_id, product_name, category, price, quantity "
                   + "FROM products ORDER BY product_id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String category = resultSet.getString("category");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                Product product = new Product(
                        productId,
                        productName,
                        category,
                        price,
                        quantity
                );

                products.add(product);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Warning: Could not load products from MySQL."
            );

            e.printStackTrace();
        }
    }

    // ==========================================
    // ADD PRODUCT
    // ==========================================

    public boolean addProduct(Product product) {

        if (product == null) {
            return false;
        }

        // Product ID must be unique
        if (findProductById(product.getProductId()) != null) {
            return false;
        }

        // Quantity cannot be negative
        if (product.getQuantity() < 0) {
            return false;
        }

        // Price cannot be negative
        if (product.getPrice() < 0) {
            return false;
        }

        String sql = "INSERT INTO products "
                   + "(product_id, product_name, category, price, quantity) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, product.getProductId());
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getCategory());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getQuantity());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted == 1) {

                products.add(product);

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error: Could not add product to MySQL."
            );

            e.printStackTrace();
        }

        return false;
    }

    // ==========================================
    // VIEW PRODUCTS
    // ==========================================

    public void displayProducts() {

        if (products.isEmpty()) {

            System.out.println(
                    "No products available.");

            return;
        }

        System.out.println();

        System.out.println(
                "----------- PRODUCT LIST -----------");

        for (Product product : products) {

            System.out.println(product);
        }
    }

    // ==========================================
    // FIND PRODUCT BY ID
    // ==========================================

    public Product findProductById(int productId) {

        for (Product product : products) {

            if (product.getProductId() == productId) {

                return product;
            }
        }

        return null;
    }

    // ==========================================
    // UPDATE COMPLETE PRODUCT
    // ==========================================

    public boolean updateProduct(
            int productId,
            String productName,
            String category,
            double price,
            int quantity) {

        Product product = findProductById(productId);

        if (product == null) {
            return false;
        }

        if (price < 0 || quantity < 0) {
            return false;
        }

        String sql = "UPDATE products "
                   + "SET product_name = ?, category = ?, "
                   + "price = ?, quantity = ? "
                   + "WHERE product_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, productName);
            statement.setString(2, category);
            statement.setDouble(3, price);
            statement.setInt(4, quantity);
            statement.setInt(5, productId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 1) {

                product.setProductName(productName);
                product.setCategory(category);
                product.setPrice(price);
                product.setQuantity(quantity);

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error: Could not update product in MySQL."
            );

            e.printStackTrace();
        }

        return false;
    }

    // ==========================================
    // UPDATE ONLY QUANTITY
    // ==========================================

    public boolean updateQuantity(
            int productId,
            int quantity) {

        Product product = findProductById(productId);

        if (product == null) {
            return false;
        }

        if (quantity < 0) {
            return false;
        }

        String sql = "UPDATE products "
                   + "SET quantity = ? "
                   + "WHERE product_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, quantity);
            statement.setInt(2, productId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 1) {

                product.setQuantity(quantity);

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error: Could not update product quantity in MySQL."
            );

            e.printStackTrace();
        }

        return false;
    }

    // ==========================================
    // DELETE PRODUCT
    // ==========================================

    public boolean deleteProduct(int productId) {

        Product product = findProductById(productId);

        if (product == null) {
            return false;
        }

        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted == 1) {

                products.remove(product);

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error: Could not delete product from MySQL."
            );

            System.out.println(
                    "The product may be used by inventory, orders, "
                    + "or quality reports."
            );

            e.printStackTrace();
        }

        return false;
    }

    // ==========================================
    // CHECK PRODUCT EXISTS
    // ==========================================

    public boolean productExists(int productId) {

        return findProductById(productId) != null;
    }

    // ==========================================
    // GET ALL PRODUCTS
    // ==========================================

    public ArrayList<Product> getProducts() {

        return products;
    }

    // ==========================================
    // GET PRODUCT COUNT
    // ==========================================

    public int getProductCount() {

        return products.size();
    }
}