package app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.DatabaseConnection;

import models.Farmer;
import models.Supplier;
import models.Product;
import models.Warehouse;
import models.DistributionCenter;
import models.QualityReport;
import models.Customer;
import models.Order;
import models.Shipment;
import models.Inventory;

import services.ProductService;
import services.SupplierService;
import services.InventoryService;
import services.DistributionService;
import services.SearchService;
import services.CorrectionService;
import services.DocumentService;
import services.MatchingService;
import services.ConstraintService;
import services.AnalyticsService;

public class FoodChainApp {

    // =====================================================
    // SCANNER
    // =====================================================

    private static Scanner scanner = new Scanner(System.in);

    // =====================================================
    // SERVICES
    // =====================================================

    private static ProductService productService =
            new ProductService();

    private static SupplierService supplierService =
            new SupplierService();

    private static InventoryService inventoryService =
            new InventoryService();

    private static DistributionService distributionService =
            new DistributionService();

    private static SearchService searchService =
            new SearchService();

    private static CorrectionService correctionService =
            new CorrectionService();

    private static DocumentService documentService =
            new DocumentService();

    private static MatchingService matchingService =
            new MatchingService();

    private static ConstraintService constraintService =
            new ConstraintService();

    private static AnalyticsService analyticsService =
            new AnalyticsService();

    // =====================================================
    // OTHER DATA
    // =====================================================

    private static ArrayList<Farmer> farmers =
            new ArrayList<>();

    private static ArrayList<Customer> customers =
            new ArrayList<>();

    private static ArrayList<Order> orders =
            new ArrayList<>();

    private static ArrayList<Shipment> shipments =
            new ArrayList<>();

    private static ArrayList<QualityReport> qualityReports =
            new ArrayList<>();

    // =====================================================
    // LOGIN DATA
    // =====================================================

    private static ArrayList<String> usernames =
            new ArrayList<>();

    private static ArrayList<String> passwords =
            new ArrayList<>();

    // =====================================================
    // MAIN
    // =====================================================

    public static void main(String[] args) {

        loadLoginData();
        loadSampleData();

        System.out.println();
        System.out.println("==========================================");
        System.out.println("              FOODCHAIN");
        System.out.println("   FOOD SUPPLY & DISTRIBUTION PLATFORM");
        System.out.println("==========================================");

        while (true) {

            System.out.println();
            System.out.println("--------------- LOGIN ----------------");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Register User");
            System.out.println("4. Exit");
            System.out.println("--------------------------------------");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    adminLogin();
                    break;

                case 2:
                    userLogin();
                    break;

                case 3:
                    registerUser();
                    break;

                case 4:
                    System.out.println(
                            "Thank you for using FoodChain.");
                    scanner.close();
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // LOGIN DATA
    // =====================================================

    private static void loadLoginData() {

        // Default admin
        usernames.add("admin");
        passwords.add("admin123");

        // Default user
        usernames.add("user");
        passwords.add("user123");
    }

    // =====================================================
    // SAMPLE DATA
    // =====================================================

    private static void loadSampleData() {


        // ---------------- SUPPLIERS ----------------

        supplierService.addSupplier(
                new Supplier(
                        1,
                        "Fresh Suppliers",
                        "Vijayawada",
                        "9000011111"));

        supplierService.addSupplier(
                new Supplier(
                        2,
                        "Agro Suppliers",
                        "Guntur",
                        "9000022222"));

        // ---------------- PRODUCTS ----------------

        productService.addProduct(
                new Product(
                        101,
                        "Fresh Rice",
                        "Grains",
                        60.0,
                        500));

        productService.addProduct(
                new Product(
                        102,
                        "Brown Rice",
                        "Grains",
                        80.0,
                        300));

        productService.addProduct(
                new Product(
                        103,
                        "Fresh Apple",
                        "Fruits",
                        120.0,
                        200));

        productService.addProduct(
                new Product(
                        104,
                        "Tomato",
                        "Vegetables",
                        40.0,
                        250));

        // ---------------- WAREHOUSES ----------------

        distributionService.addWarehouse(
                new Warehouse(
                        1,
                        "Main Warehouse",
                        "Vijayawada",
                        1000));

        distributionService.addWarehouse(
                new Warehouse(
                        2,
                        "Central Warehouse",
                        "Guntur",
                        1500));

        // ---------------- DISTRIBUTION CENTERS ----------------

        distributionService.addDistributionCenter(
                new DistributionCenter(
                        1,
                        "Vijayawada DC",
                        "Vijayawada",
                        2000));

        distributionService.addDistributionCenter(
                new DistributionCenter(
                        2,
                        "Guntur DC",
                        "Guntur",
                        2500));

        // ---------------- INVENTORY ----------------

        inventoryService.addInventory(
                new Inventory(
                        1,
                        1,
                        101,
                        500,
                        100));

        inventoryService.addInventory(
                new Inventory(
                        2,
                        1,
                        102,
                        300,
                        75));

        inventoryService.addInventory(
                new Inventory(
                        3,
                        2,
                        103,
                        200,
                        50));

        inventoryService.addInventory(
                new Inventory(
                        4,
                        2,
                        104,
                        250,
                        60));

        // ---------------- CUSTOMERS ----------------

        customers.add(new Customer(
                1,
                "Demo Customer",
                "Nandigama",
                "9123456789"));

        // ---------------- QUALITY REPORTS ----------------

        qualityReports.add(
                new QualityReport(
                        1,
                        101,
                        "2026-09-20",
                        "Good",
                        "Fresh rice passed quality inspection."));

        qualityReports.add(
                new QualityReport(
                        2,
                        103,
                        "2026-09-20",
                        "Excellent",
                        "Fresh apples passed quality inspection."));

        // ---------------- DOCUMENTS ----------------

        documentService.addDocument(
                "Fresh Rice - High quality food grain.");

        documentService.addDocument(
                "Brown Rice - Healthy grain product.");

        documentService.addDocument(
                "Apple - Fresh fruit product.");

        documentService.addDocument(
                "Tomato - Fresh vegetable product.");
    }

    // =====================================================
    // ADMIN LOGIN
    // =====================================================

   
    private static void adminLogin() {

        System.out.println();
        System.out.println("=========== ADMIN LOGIN ===========");

        String username = readString("Username: ");
        String password = readString("Password: ");

        String sql = "SELECT password FROM users "
                + "WHERE username = ? AND role = 'ADMIN'";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()
                        && result.getString("password").equals(password)) {

                    System.out.println("Admin login successful.");
                    adminMenu();

                } else {
                    System.out.println("Invalid admin username or password.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Unable to process admin login.");
            System.out.println("Database Error: " + e.getMessage());
        }
    }
   
    // =====================================================
    // USER LOGIN
    // =====================================================

    private static void userLogin() {

        System.out.println();

        System.out.println("============ USER LOGIN ============");

        String username =
                readString("Username: ");

        String password =
                readString("Password: ");

        String sql =
                "SELECT password FROM users "
                + "WHERE username = ? AND role = 'USER'";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet result =
                         statement.executeQuery()) {

                if (result.next()) {

                    String storedPassword =
                            result.getString("password");

                    if (storedPassword.equals(password)) {

                        System.out.println(
                                "User login successful.");

                        userMenu(username);

                    } else {

                        System.out.println(
                                "Invalid username or password.");
                    }

                } else {

                    System.out.println(
                            "Invalid username or password.");
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to process user login.");

            System.out.println(
                    "Database Error: " + e.getMessage());
        }
    }
    // =====================================================
    // REGISTER USER
    // =====================================================

    private static void registerUser() {

        System.out.println();

        System.out.println("========== USER REGISTRATION ==========");

        String username =
                readString("Create username: ");

        if (username.isEmpty()) {

            System.out.println(
                    "Username cannot be empty.");

            return;
        }

        String password =
                readString("Create password: ");

        if (password.isEmpty()) {

            System.out.println(
                    "Password cannot be empty.");

            return;
        }

        String checkSql =
                "SELECT user_id FROM users WHERE username = ?";
        String customerIdSql =
                "SELECT COALESCE(MAX(customer_id), 0) + 1 AS next_id "
                + "FROM customers";

        String userSql =
                "INSERT INTO users "
                + "(username, password, role) "
                + "VALUES (?, ?, ?)";

        String customerSql =
                "INSERT INTO customers "
                + "(customer_id, customer_name, address, contact_number) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            // ==========================================
            // CHECK USERNAME IN MYSQL
            // ==========================================

            try (PreparedStatement checkStatement =
                         connection.prepareStatement(checkSql)) {

                checkStatement.setString(1, username);

                try (ResultSet result =
                             checkStatement.executeQuery()) {

                    if (result.next()) {

                        System.out.println(
                                "Username already exists.");

                        return;
                    }
                }
            }

            // ==========================================
            // GET NEXT CUSTOMER ID
            // ==========================================

            int customerId;

            try (PreparedStatement customerIdStatement =
                         connection.prepareStatement(customerIdSql);
                 ResultSet result =
                         customerIdStatement.executeQuery()) {

                result.next();

                customerId =
                        result.getInt("next_id");
            }

            // ==========================================
            // INSERT USER
            // ==========================================

            try (PreparedStatement userStatement =
                         connection.prepareStatement(userSql)) {

                userStatement.setString(1, username);
                userStatement.setString(2, password);
                userStatement.setString(3, "USER");

                userStatement.executeUpdate();
            }

            // ==========================================
            // INSERT CUSTOMER
            // ==========================================

            try (PreparedStatement customerStatement =
                         connection.prepareStatement(customerSql)) {

                customerStatement.setInt(1, customerId);
                customerStatement.setString(2, username);
                customerStatement.setString(3, "Not Provided");
                customerStatement.setString(4, "Not Provided");

                customerStatement.executeUpdate();
            }

            // ==========================================
            // KEEP CURRENT JAVA SESSION DATA
            // ==========================================

            usernames.add(username);
            passwords.add(password);

            customers.add(
                    new Customer(
                            customerId,
                            username,
                            "Not Provided",
                            "Not Provided"
                    )
            );

            System.out.println(
                    "Registration successful.");

            System.out.println(
                    "Your Customer ID is: "
                    + customerId);

        } catch (SQLException e) {

            System.out.println(
                    "Registration failed.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // FIND USER
    // =====================================================

    private static int findUser(String username) {

        for (int i = 0;
                i < usernames.size();
                i++) {

            if (usernames.get(i)
                    .equals(username)) {

                return i;
            }
        }

        return -1;
    }

    // =====================================================
    // ADMIN MENU
    // =====================================================

    private static void adminMenu() {

        while (true) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("              ADMIN DASHBOARD");
            System.out.println("==========================================");

            System.out.println("1. Farmer Management");
            System.out.println("2. Supplier Management");
            System.out.println("3. Product Management");
            System.out.println("4. Customer Management");
            System.out.println("5. Order Management");
            System.out.println("6. Marketing");
            System.out.println("7. Warehouse Management");
            System.out.println("8. Distribution Center Management");
            System.out.println("9. Inventory Management");
            System.out.println("10. Quality Reports");
            System.out.println("11. Shipment Management");
            System.out.println("12. Analytics");
            System.out.println("13. DSA Search");
            System.out.println("14. DSA Algorithms");
            System.out.println("15. Logout");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    farmerMenu();
                    break;

                case 2:
                    supplierMenu();
                    break;

                case 3:
                    productMenu();
                    break;

                case 4:
                    customerMenu();
                    break;

                case 5:
                    orderMenu();
                    break;

                case 6:
                    marketingMenu();
                    break;

                case 7:
                    warehouseMenu();
                    break;

                case 8:
                    distributionCenterMenu();
                    break;

                case 9:
                    inventoryMenu();
                    break;

                case 10:
                    qualityReportMenu();
                    break;

                case 11:
                    shipmentMenu();
                    break;

                case 12:
                    analyticsMenu();
                    break;

                case 13:
                    dsaSearchMenu();
                    break;

                case 14:
                    dsaAlgorithmMenu();
                    break;

                case 15:
                    System.out.println(
                            "Admin logged out.");

                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // USER MENU
    // =====================================================

    private static void userMenu(String username) {

        int customerId =
                getCustomerIdByUsername(username);

        while (true) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("               USER DASHBOARD");
            System.out.println("==========================================");

            System.out.println("1. View Products");
            System.out.println("2. Search Products");
            System.out.println("3. View Suppliers");
            System.out.println("4. View Inventory");
            System.out.println("5. Place Order");
            System.out.println("6. My Orders");
            System.out.println("7. Cancel Order");
            System.out.println("8. Track Shipment");
            System.out.println("9. View Quality Reports");
            System.out.println("10. Marketing");
            System.out.println("11. Correct Product Name");
            System.out.println("12. Logout");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    productService.displayProducts();
                    break;

                case 2:
                    searchProducts();
                    break;

                case 3:
                    supplierService.displaySuppliers();
                    break;

                case 4:
                    inventoryService.displayInventory();
                    break;

                case 5:
                    placeOrder(customerId);
                    break;

                case 6:
                    viewMyOrders(customerId);
                    break;

                case 7:
                    cancelOrder(customerId);
                    break;

                case 8:
                    trackShipment(customerId);
                    break;

                case 9:
                    displayQualityReports();
                    break;

                case 10:
                    marketingMenuUser();
                    break;

                case 11:
                    correctProductName();
                    break;

                case 12:
                    System.out.println(
                            "User logged out.");

                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // FARMER MENU
    // =====================================================

    private static void farmerMenu() {

        while (true) {

            System.out.println();
            System.out.println("========== FARMER MANAGEMENT ==========");
            System.out.println("1. Add Farmer");
            System.out.println("2. View Farmers");
            System.out.println("3. Update Farmer");
            System.out.println("4. Delete Farmer");
            System.out.println("5. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    addFarmer();
                    break;

                case 2:
                    displayFarmers();
                    break;

                case 3:
                    updateFarmer();
                    break;

                case 4:
                    deleteFarmer();
                    break;

                case 5:
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // ADD FARMER
    // =====================================================

    private static void addFarmer() {

        int id =
                readInt("Farmer ID: ");

        String name =
                readString("Farmer Name: ");

        String location =
                readString("Location: ");

        String contact =
                readString("Contact Number: ");

        String checkSql =
                "SELECT farmer_id "
                + "FROM farmers "
                + "WHERE farmer_id = ?";

        String insertSql =
                "INSERT INTO farmers "
                + "(farmer_id, farmer_name, location, contact_number) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            try (PreparedStatement statement =
                         connection.prepareStatement(checkSql)) {

                statement.setInt(1, id);

                try (ResultSet result =
                             statement.executeQuery()) {

                    if (result.next()) {

                        System.out.println(
                                "Farmer ID already exists.");

                        return;
                    }
                }
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(insertSql)) {

                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setString(3, location);
                statement.setString(4, contact);

                statement.executeUpdate();

                System.out.println(
                        "Farmer added successfully.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to add farmer.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // VIEW FARMERS
    // =====================================================

    private static void displayFarmers() {

        String sql = "SELECT farmer_id, farmer_name, location, contact_number "
                + "FROM farmers";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            boolean found = false;

            System.out.println();
            System.out.println("----------- FARMER LIST -----------");

            while (result.next()) {

                found = true;

                Farmer farmer = new Farmer(
                        result.getInt("farmer_id"),
                        result.getString("farmer_name"),
                        result.getString("location"),
                        result.getString("contact_number")
                );

                System.out.println(farmer);
            }

            if (!found) {
                System.out.println("No farmers available.");
            }

        } catch (SQLException e) {

            System.out.println("Unable to load farmers.");
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // =====================================================
    // FIND FARMER
    // =====================================================
    private static Farmer findFarmer(int id) {

        String sql = "SELECT farmer_id, farmer_name, location, contact_number "
                + "FROM farmers WHERE farmer_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    return new Farmer(
                            result.getInt("farmer_id"),
                            result.getString("farmer_name"),
                            result.getString("location"),
                            result.getString("contact_number")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());
        }

        return null;
    }

    // =====================================================
    // UPDATE FARMER
    // =====================================================

    private static void updateFarmer() {

        int id =
                readInt("Enter Farmer ID: ");

        String name =
                readString("New Name: ");

        String location =
                readString("New Location: ");

        String contact =
                readString("New Contact: ");

        String sql =
                "UPDATE farmers "
                + "SET farmer_name = ?, "
                + "location = ?, "
                + "contact_number = ? "
                + "WHERE farmer_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, location);
            statement.setString(3, contact);
            statement.setInt(4, id);

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated > 0) {

                System.out.println(
                        "Farmer updated successfully.");

            } else {

                System.out.println(
                        "Farmer not found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to update farmer.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // DELETE FARMER
    // =====================================================
    private static void deleteFarmer() {

        int id =
                readInt("Enter Farmer ID: ");

        String sql =
                "DELETE FROM farmers "
                + "WHERE farmer_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted > 0) {

                System.out.println(
                        "Farmer deleted successfully.");

            } else {

                System.out.println(
                        "Farmer not found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to delete farmer.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    } // =====================================================
    // SUPPLIER MENU
    // =====================================================

    private static void supplierMenu() {

        while (true) {

            System.out.println();
            System.out.println("========= SUPPLIER MANAGEMENT =========");
            System.out.println("1. Add Supplier");
            System.out.println("2. View Suppliers");
            System.out.println("3. Update Supplier");
            System.out.println("4. Delete Supplier");
            System.out.println("5. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    addSupplier();
                    break;

                case 2:
                    supplierService.displaySuppliers();
                    break;

                case 3:
                    updateSupplier();
                    break;

                case 4:
                    deleteSupplier();
                    break;

                case 5:
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // ADD SUPPLIER
    // =====================================================

    private static void addSupplier() {

        int id =
                readInt("Supplier ID: ");

        String name =
                readString("Supplier Name: ");

        String location =
                readString("Location: ");

        String contact =
                readString("Contact Number: ");

        String checkSql =
                "SELECT supplier_id "
                + "FROM suppliers "
                + "WHERE supplier_id = ?";

        String insertSql =
                "INSERT INTO suppliers "
                + "(supplier_id, supplier_name, location, contact_number) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            try (PreparedStatement statement =
                         connection.prepareStatement(checkSql)) {

                statement.setInt(1, id);

                try (ResultSet result =
                             statement.executeQuery()) {

                    if (result.next()) {
                        System.out.println(
                                "Supplier ID already exists.");
                        return;
                    }
                }
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(insertSql)) {

                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setString(3, location);
                statement.setString(4, contact);

                statement.executeUpdate();

                System.out.println(
                        "Supplier added successfully.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to add supplier.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // UPDATE SUPPLIER
    // =====================================================
    private static void updateSupplier() {

        int id =
                readInt("Supplier ID: ");

        String name =
                readString("New Name: ");

        String location =
                readString("New Location: ");

        String contact =
                readString("New Contact: ");

        String sql =
                "UPDATE suppliers "
                + "SET supplier_name = ?, "
                + "location = ?, "
                + "contact_number = ? "
                + "WHERE supplier_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, location);
            statement.setString(3, contact);
            statement.setInt(4, id);

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated > 0) {

                System.out.println(
                        "Supplier updated successfully.");

            } else {

                System.out.println(
                        "Supplier not found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to update supplier.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
        // =====================================================
    // DELETE SUPPLIER
    // =====================================================
    private static void deleteSupplier() {

        int id =
                readInt("Supplier ID: ");

        String sql =
                "DELETE FROM suppliers "
                + "WHERE supplier_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted > 0) {

                System.out.println(
                        "Supplier deleted successfully.");

            } else {

                System.out.println(
                        "Supplier not found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to delete supplier.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }

    // =====================================================
    // PRODUCT MENU
    // =====================================================

    private static void productMenu() {

        while (true) {

            System.out.println();
            System.out.println("========== PRODUCT MANAGEMENT ==========");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    addProduct();
                    break;

                case 2:
                    productService.displayProducts();
                    break;

                case 3:
                    updateProduct();
                    break;

                case 4:
                    deleteProduct();
                    break;

                case 5:
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // ADD PRODUCT
    // =====================================================

    private static void addProduct() {

        int id =
                readInt("Product ID: ");

        String name =
                readString("Product Name: ");

        String category =
                readString("Category: ");

        double price =
                readDouble("Price: ");

        int quantity =
                readInt("Quantity: ");

        if (price < 0 || quantity < 0) {
            System.out.println(
                    "Price and quantity cannot be negative.");
            return;
        }

        String checkSql =
                "SELECT product_id "
                + "FROM products "
                + "WHERE product_id = ?";

        String insertSql =
                "INSERT INTO products "
                + "(product_id, product_name, category, price, quantity) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            try (PreparedStatement statement =
                         connection.prepareStatement(checkSql)) {

                statement.setInt(1, id);

                try (ResultSet result =
                             statement.executeQuery()) {

                    if (result.next()) {
                        System.out.println(
                                "Product ID already exists.");
                        return;
                    }
                }
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(insertSql)) {

                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setString(3, category);
                statement.setDouble(4, price);
                statement.setInt(5, quantity);

                statement.executeUpdate();

                System.out.println(
                        "Product added successfully.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to add product.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }    // =====================================================
    // UPDATE PRODUCT
    // =====================================================

    private static void updateProduct() {

        int id =
                readInt("Product ID: ");

        String name =
                readString("New Product Name: ");

        String category =
                readString("New Category: ");

        double price =
                readDouble("New Price: ");

        int quantity =
                readInt("New Quantity: ");

        if (price < 0 || quantity < 0) {
            System.out.println(
                    "Price and quantity cannot be negative.");
            return;
        }

        String sql =
                "UPDATE products "
                + "SET product_name = ?, "
                + "category = ?, "
                + "price = ?, "
                + "quantity = ? "
                + "WHERE product_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, category);
            statement.setDouble(3, price);
            statement.setInt(4, quantity);
            statement.setInt(5, id);

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated > 0) {

                System.out.println(
                        "Product updated successfully.");

            } else {

                System.out.println(
                        "Product not found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to update product.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }    // =====================================================
    // DELETE PRODUCT
    // =====================================================

    private static void deleteProduct() {

        int id =
                readInt("Product ID: ");

        String sql =
                "DELETE FROM products "
                + "WHERE product_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted > 0) {

                System.out.println(
                        "Product deleted successfully.");

            } else {

                System.out.println(
                        "Product not found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to delete product.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // CUSTOMER MENU
    // =====================================================

    private static void customerMenu() {

        while (true) {

            System.out.println();
            System.out.println("========= CUSTOMER MANAGEMENT =========");
            System.out.println("1. View Customers");
            System.out.println("2. Update Customer");
            System.out.println("3. Delete Customer");
            System.out.println("4. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    displayCustomers();
                    break;

                case 2:
                    updateCustomer();
                    break;

                case 3:
                    deleteCustomer();
                    break;

                case 4:
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // VIEW CUSTOMERS
    // =====================================================

    private static void displayCustomers() {

        String sql = "SELECT customer_id, customer_name, address, contact_number "
                + "FROM customers ORDER BY customer_id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            boolean found = false;

            System.out.println();
            System.out.println(
                    "----------- CUSTOMER LIST -----------");

            while (result.next()) {

                found = true;

                Customer customer = new Customer(
                        result.getInt("customer_id"),
                        result.getString("customer_name"),
                        result.getString("address"),
                        result.getString("contact_number")
                );

                System.out.println(customer);
            }

            if (!found) {
                System.out.println(
                        "No customers available.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to load customers.");

            System.out.println(
                    "Database Error: " + e.getMessage());
        }
    }

    // =====================================================
    // FIND CUSTOMER
    // =====================================================

    private static Customer findCustomer(int customerId) {

        String sql = "SELECT customer_id, customer_name, address, contact_number "
                + "FROM customers WHERE customer_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    return new Customer(
                            result.getInt("customer_id"),
                            result.getString("customer_name"),
                            result.getString("address"),
                            result.getString("contact_number")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Database Error: " + e.getMessage());
        }

        return null;
    }

    // =====================================================
    // UPDATE CUSTOMER
    // =====================================================

    private static void updateCustomer() {

        int id = readInt("Customer ID: ");

        Customer customer = findCustomer(id);

        if (customer == null) {

            System.out.println(
                    "Customer not found.");

            return;
        }

        String name =
                readString("New Name: ");

        String address =
                readString("New Address: ");

        String contact =
                readString("New Contact: ");

        String sql = "UPDATE customers "
                + "SET customer_name = ?, "
                + "address = ?, "
                + "contact_number = ? "
                + "WHERE customer_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, contact);
            statement.setInt(4, id);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 1) {

                System.out.println(
                        "Customer updated successfully.");

            } else {

                System.out.println(
                        "Customer update failed.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to update customer.");

            System.out.println(
                    "Database Error: " + e.getMessage());
        }
    }
    // =====================================================
    // DELETE CUSTOMER
    // =====================================================

    private static void deleteCustomer() {

        int id = readInt("Customer ID: ");

        Customer customer = findCustomer(id);

        if (customer == null) {

            System.out.println(
                    "Customer not found.");

            return;
        }

        String sql =
                "DELETE FROM customers WHERE customer_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted == 1) {

                System.out.println(
                        "Customer deleted successfully.");

            } else {

                System.out.println(
                        "Customer deletion failed.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to delete customer.");

            System.out.println(
                    "Database Error: " + e.getMessage());

            System.out.println(
                    "The customer may have related orders.");
        }
    }

    // =====================================================
    // WAREHOUSE MENU
    // =====================================================

    private static void warehouseMenu() {

        while (true) {

            System.out.println();
            System.out.println("========= WAREHOUSE MANAGEMENT =========");
            System.out.println("1. Add Warehouse");
            System.out.println("2. View Warehouses");
            System.out.println("3. Update Warehouse");
            System.out.println("4. Delete Warehouse");
            System.out.println("5. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    addWarehouse();
                    break;

                case 2:
                    distributionService
                            .displayWarehouses();
                    break;

                case 3:
                    updateWarehouse();
                    break;

                case 4:
                    deleteWarehouse();
                    break;

                case 5:
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // ADD WAREHOUSE
    // =====================================================

    private static void addWarehouse() {

        int id =
                readInt("Warehouse ID: ");

        String name =
                readString("Warehouse Name: ");

        String location =
                readString("Location: ");

        int capacity =
                readInt("Capacity: ");

        if (capacity < 0) {
            System.out.println(
                    "Capacity cannot be negative.");
            return;
        }

        String checkSql =
                "SELECT warehouse_id "
                + "FROM warehouses "
                + "WHERE warehouse_id = ?";

        String insertSql =
                "INSERT INTO warehouses "
                + "(warehouse_id, warehouse_name, location, capacity) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            // Check duplicate ID
            try (PreparedStatement statement =
                         connection.prepareStatement(checkSql)) {

                statement.setInt(1, id);

                try (ResultSet result =
                             statement.executeQuery()) {

                    if (result.next()) {
                        System.out.println(
                                "Warehouse ID already exists.");
                        return;
                    }
                }
            }

            // Insert warehouse
            try (PreparedStatement statement =
                         connection.prepareStatement(insertSql)) {

                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setString(3, location);
                statement.setInt(4, capacity);

                statement.executeUpdate();

                System.out.println(
                        "Warehouse added successfully.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to add warehouse.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // UPDATE WAREHOUSE
    // =====================================================

    private static void updateWarehouse() {

        int id =
                readInt("Warehouse ID: ");

        String name =
                readString("New Name: ");

        String location =
                readString("New Location: ");

        int capacity =
                readInt("New Capacity: ");

        if (capacity < 0) {
            System.out.println(
                    "Capacity cannot be negative.");
            return;
        }

        String sql =
                "UPDATE warehouses "
                + "SET warehouse_name = ?, "
                + "location = ?, "
                + "capacity = ? "
                + "WHERE warehouse_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, location);
            statement.setInt(3, capacity);
            statement.setInt(4, id);

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated > 0) {

                System.out.println(
                        "Warehouse updated successfully.");

            } else {

                System.out.println(
                        "Warehouse not found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to update warehouse.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }

    // =====================================================
    // DELETE WAREHOUSE
    // =====================================================

    private static void deleteWarehouse() {

        int id =
                readInt("Warehouse ID: ");

        String sql =
                "DELETE FROM warehouses "
                + "WHERE warehouse_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted > 0) {

                System.out.println(
                        "Warehouse deleted successfully.");

            } else {

                System.out.println(
                        "Warehouse not found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to delete warehouse.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // DISTRIBUTION CENTER MENU
    // =====================================================

    private static void distributionCenterMenu() {

        while (true) {

            System.out.println();
            System.out.println(
                    "===== DISTRIBUTION CENTER MANAGEMENT =====");

            System.out.println("1. Add Center");
            System.out.println("2. View Centers");
            System.out.println("3. Update Center");
            System.out.println("4. Delete Center");
            System.out.println("5. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    addDistributionCenter();
                    break;

                case 2:
                    distributionService
                            .displayDistributionCenters();
                    break;

                case 3:
                    updateDistributionCenter();
                    break;

                case 4:
                    deleteDistributionCenter();
                    break;

                case 5:
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // ADD DISTRIBUTION CENTER
    // =====================================================
    private static void addDistributionCenter() {

        int id =
                readInt("Center ID: ");

        String name =
                readString("Center Name: ");

        String location =
                readString("Location: ");

        int capacity =
                readInt("Capacity: ");

        if (capacity < 0) {
            System.out.println(
                    "Capacity cannot be negative.");
            return;
        }

        String checkSql =
                "SELECT center_id "
                + "FROM distribution_centers "
                + "WHERE center_id = ?";

        String insertSql =
                "INSERT INTO distribution_centers "
                + "(center_id, center_name, location, capacity) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            try (PreparedStatement statement =
                         connection.prepareStatement(checkSql)) {

                statement.setInt(1, id);

                try (ResultSet result =
                             statement.executeQuery()) {

                    if (result.next()) {
                        System.out.println(
                                "Center ID already exists.");
                        return;
                    }
                }
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(insertSql)) {

                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setString(3, location);
                statement.setInt(4, capacity);

                statement.executeUpdate();

                System.out.println(
                        "Distribution center added successfully.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to add distribution center.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }   // =====================================================
    // UPDATE DISTRIBUTION CENTER
    // =====================================================

    private static void updateDistributionCenter() {

        int id =
                readInt("Center ID: ");

        String name =
                readString("New Name: ");

        String location =
                readString("New Location: ");

        int capacity =
                readInt("New Capacity: ");

        if (capacity < 0) {
            System.out.println(
                    "Capacity cannot be negative.");
            return;
        }

        String sql =
                "UPDATE distribution_centers "
                + "SET center_name = ?, "
                + "location = ?, "
                + "capacity = ? "
                + "WHERE center_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, location);
            statement.setInt(3, capacity);
            statement.setInt(4, id);

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated > 0) {

                System.out.println(
                        "Distribution center updated.");

            } else {

                System.out.println(
                        "Center not found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to update distribution center.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // DELETE DISTRIBUTION CENTER
    // =====================================================
    private static void deleteDistributionCenter() {

        int id =
                readInt("Center ID: ");

        String sql =
                "DELETE FROM distribution_centers "
                + "WHERE center_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted > 0) {

                System.out.println(
                        "Distribution center deleted.");

            } else {

                System.out.println(
                        "Center not found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to delete distribution center.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
      // =====================================================
    // INVENTORY MENU
    // =====================================================

    private static void inventoryMenu() {

        while (true) {

            System.out.println();
            System.out.println("========= INVENTORY MANAGEMENT =========");
            System.out.println("1. Add Inventory");
            System.out.println("2. View Inventory");
            System.out.println("3. Update Inventory");
            System.out.println("4. Add Stock");
            System.out.println("5. Remove Stock");
            System.out.println("6. Delete Inventory");
            System.out.println("7. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    addInventory();
                    break;

                case 2:

                    displayInventoryFromDatabase();

                    break;
                case 3:
                    updateInventory();
                    break;

                case 4:
                    addStock();
                    break;

                case 5:
                    removeStock();
                    break;

                case 6:
                    deleteInventory();
                    break;

                case 7:
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }
    
    private static void displayInventoryFromDatabase() {

        String sql =
                "SELECT inventory_id, warehouse_id, product_id, "
                + "quantity, minimum_stock "
                + "FROM inventory "
                + "ORDER BY inventory_id";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result =
                     statement.executeQuery()) {

            boolean found = false;

            System.out.println();
            System.out.println(
                    "----------- INVENTORY LIST -----------");

            while (result.next()) {

                found = true;

                Inventory inventory = new Inventory(
                        result.getInt("inventory_id"),
                        result.getInt("warehouse_id"),
                        result.getInt("product_id"),
                        result.getInt("quantity"),
                        result.getInt("minimum_stock")
                );

                System.out.println(inventory);
            }

            if (!found) {

                System.out.println(
                        "No inventory available.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to load inventory.");

            System.out.println(
                    "Database Error: " + e.getMessage());
        }
    }

    // =====================================================
    // ADD INVENTORY
    // =====================================================

    private static void addInventory() {

        int inventoryId =
                readInt("Inventory ID: ");

        int warehouseId =
                readInt("Warehouse ID: ");

        int productId =
                readInt("Product ID: ");

        int quantity =
                readInt("Quantity: ");

        int minimumStock =
                readInt("Minimum Stock: ");

        if (quantity < 0 || minimumStock < 0) {

            System.out.println(
                    "Unable to add inventory.");

            return;
        }

        String sql =
                "INSERT INTO inventory "
                + "(inventory_id, warehouse_id, product_id, "
                + "quantity, minimum_stock) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, inventoryId);
            statement.setInt(2, warehouseId);
            statement.setInt(3, productId);
            statement.setInt(4, quantity);
            statement.setInt(5, minimumStock);

            int rowsInserted =
                    statement.executeUpdate();

            if (rowsInserted > 0) {

                System.out.println(
                        "Inventory added successfully.");

            } else {

                System.out.println(
                        "Unable to add inventory.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to add inventory.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // UPDATE INVENTORY
    // =====================================================

    private static void updateInventory() {

        int id =
                readInt("Inventory ID: ");

        int warehouseId =
                readInt("New Warehouse ID: ");

        int productId =
                readInt("New Product ID: ");

        int quantity =
                readInt("New Quantity: ");

        int minimumStock =
                readInt("New Minimum Stock: ");

        if (quantity < 0 || minimumStock < 0) {

            System.out.println(
                    "Inventory not found or invalid data.");

            return;
        }

        String warehouseSql =
                "SELECT warehouse_id FROM warehouses "
                + "WHERE warehouse_id = ?";

        String productSql =
                "SELECT product_id FROM products "
                + "WHERE product_id = ?";

        String updateSql =
                "UPDATE inventory "
                + "SET warehouse_id = ?, "
                + "product_id = ?, "
                + "quantity = ?, "
                + "minimum_stock = ? "
                + "WHERE inventory_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            // ==========================================
            // CHECK WAREHOUSE
            // ==========================================

            try (PreparedStatement statement =
                         connection.prepareStatement(warehouseSql)) {

                statement.setInt(1, warehouseId);

                try (ResultSet result =
                             statement.executeQuery()) {

                    if (!result.next()) {

                        System.out.println(
                                "Warehouse does not exist.");

                        return;
                    }
                }
            }

            // ==========================================
            // CHECK PRODUCT
            // ==========================================

            try (PreparedStatement statement =
                         connection.prepareStatement(productSql)) {

                statement.setInt(1, productId);

                try (ResultSet result =
                             statement.executeQuery()) {

                    if (!result.next()) {

                        System.out.println(
                                "Product does not exist.");

                        return;
                    }
                }
            }

            // ==========================================
            // UPDATE INVENTORY
            // ==========================================

            try (PreparedStatement statement =
                         connection.prepareStatement(updateSql)) {

                statement.setInt(1, warehouseId);
                statement.setInt(2, productId);
                statement.setInt(3, quantity);
                statement.setInt(4, minimumStock);
                statement.setInt(5, id);

                int rowsUpdated =
                        statement.executeUpdate();

                if (rowsUpdated == 1) {

                    System.out.println(
                            "Inventory updated successfully.");

                } else {

                    System.out.println(
                            "Inventory not found or invalid data.");
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to update inventory.");

            System.out.println(
                    "Database Error: " + e.getMessage());
        }
    }

    // =====================================================
    // ADD STOCK
    // =====================================================

    private static void addStock() {

        int productId =
                readInt("Product ID: ");

        int quantity =
                readInt("Quantity to add: ");

        if (quantity <= 0) {

            System.out.println(
                    "Unable to add stock.");

            return;
        }

        String sql =
                "UPDATE inventory "
                + "SET quantity = quantity + ? "
                + "WHERE product_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, quantity);
            statement.setInt(2, productId);

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated > 0) {

                System.out.println(
                        "Stock added successfully.");

            } else {

                System.out.println(
                        "Unable to add stock.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to add stock.");

            System.out.println(
                    "Database Error: " + e.getMessage());
        }
    }
    // =====================================================
    // REMOVE STOCK
    // =====================================================

    private static void removeStock() {

        int productId =
                readInt("Product ID: ");

        int quantity =
                readInt("Quantity to remove: ");

        if (quantity <= 0) {

            System.out.println(
                    "Unable to remove stock.");

            return;
        }

        String checkSql =
                "SELECT quantity "
                + "FROM inventory "
                + "WHERE product_id = ?";

        String updateSql =
                "UPDATE inventory "
                + "SET quantity = quantity - ? "
                + "WHERE product_id = ? "
                + "AND quantity >= ?";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            int currentQuantity = -1;

            try (PreparedStatement statement =
                         connection.prepareStatement(checkSql)) {

                statement.setInt(1, productId);

                try (ResultSet result =
                             statement.executeQuery()) {

                    if (result.next()) {
                        currentQuantity =
                                result.getInt("quantity");
                    }
                }
            }

            if (currentQuantity == -1) {

                System.out.println(
                        "Inventory not found.");

                return;
            }

            if (quantity > currentQuantity) {

                System.out.println(
                        "Unable to remove stock.");

                System.out.println(
                        "Available stock: "
                        + currentQuantity);

                return;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(updateSql)) {

                statement.setInt(1, quantity);
                statement.setInt(2, productId);
                statement.setInt(3, quantity);

                int rowsUpdated =
                        statement.executeUpdate();

                if (rowsUpdated > 0) {

                    System.out.println(
                            "Stock removed successfully.");

                } else {

                    System.out.println(
                            "Unable to remove stock.");
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to remove stock.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // DELETE INVENTORY
    // =====================================================

    private static void deleteInventory() {

        int id =
                readInt("Inventory ID: ");

        String sql =
                "DELETE FROM inventory "
                + "WHERE inventory_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted > 0) {

                System.out.println(
                        "Inventory deleted successfully.");

            } else {

                System.out.println(
                        "Inventory not found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to delete inventory.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }

    // =====================================================
    // QUALITY REPORT MENU
    // =====================================================

    private static void qualityReportMenu() {

        while (true) {

            System.out.println();
            System.out.println("========== QUALITY REPORTS ==========");
            System.out.println("1. Add Report");
            System.out.println("2. View Reports");
            System.out.println("3. Update Report");
            System.out.println("4. Delete Report");
            System.out.println("5. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    addQualityReport();
                    break;

                case 2:
                    displayQualityReports();
                    break;

                case 3:
                    updateQualityReport();
                    break;

                case 4:
                    deleteQualityReport();
                    break;

                case 5:
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // ADD QUALITY REPORT
    // =====================================================
    private static void addQualityReport() {

        int id =
                readInt("Report ID: ");

        int productId =
                readInt("Product ID: ");

        if (!productService.productExists(productId)) {
            System.out.println(
                    "Product does not exist.");
            return;
        }

        String date =
                readString("Report Date: ");

        String status =
                readString("Quality Status: ");

        String description =
                readString("Description: ");

        String checkSql =
                "SELECT report_id "
                + "FROM quality_reports "
                + "WHERE report_id = ?";

        String insertSql =
                "INSERT INTO quality_reports "
                + "(report_id, product_id, report_date, "
                + "quality_status, description) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            try (PreparedStatement statement =
                         connection.prepareStatement(checkSql)) {

                statement.setInt(1, id);

                try (ResultSet result =
                             statement.executeQuery()) {

                    if (result.next()) {
                        System.out.println(
                                "Report ID already exists.");
                        return;
                    }
                }
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(insertSql)) {

                statement.setInt(1, id);
                statement.setInt(2, productId);
                statement.setString(3, date);
                statement.setString(4, status);
                statement.setString(5, description);

                statement.executeUpdate();

                System.out.println(
                        "Quality report added.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to add quality report.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
        // =====================================================
    // VIEW QUALITY REPORTS
    // =====================================================

    private static void displayQualityReports() {

        String sql =
                "SELECT report_id, product_id, report_date, "
                + "quality_status, description "
                + "FROM quality_reports "
                + "ORDER BY report_id";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result =
                     statement.executeQuery()) {

            boolean found = false;

            System.out.println();
            System.out.println(
                    "--------- QUALITY REPORTS ---------");

            while (result.next()) {

                found = true;

                QualityReport report =
                        new QualityReport(
                                result.getInt("report_id"),
                                result.getInt("product_id"),
                                result.getString("report_date"),
                                result.getString("quality_status"),
                                result.getString("description")
                        );

                System.out.println(report);
            }

            if (!found) {
                System.out.println(
                        "No quality reports available.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to load quality reports.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // FIND QUALITY REPORT
    // =====================================================

    private static QualityReport findQualityReport(
            int id) {

        for (QualityReport report :
                qualityReports) {

            if (report.getReportId() == id) {
                return report;
            }
        }

        return null;
    }

    // =====================================================
    // UPDATE QUALITY REPORT
    // =====================================================
    private static void updateQualityReport() {

        int id =
                readInt("Report ID: ");

        int productId =
                readInt("New Product ID: ");

        if (!productService.productExists(productId)) {
            System.out.println(
                    "Product does not exist.");
            return;
        }

        String date =
                readString("New Date: ");

        String status =
                readString("New Quality Status: ");

        String description =
                readString("New Description: ");

        String sql =
                "UPDATE quality_reports "
                + "SET product_id = ?, "
                + "report_date = ?, "
                + "quality_status = ?, "
                + "description = ? "
                + "WHERE report_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, productId);
            statement.setString(2, date);
            statement.setString(3, status);
            statement.setString(4, description);
            statement.setInt(5, id);

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated > 0) {

                System.out.println(
                        "Quality report updated.");

            } else {

                System.out.println(
                        "Report not found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to update quality report.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    } // =====================================================
    // DELETE QUALITY REPORT
    // =====================================================

    private static void deleteQualityReport() {

        int id =
                readInt("Report ID: ");

        String sql =
                "DELETE FROM quality_reports "
                + "WHERE report_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted > 0) {

                System.out.println(
                        "Quality report deleted.");

            } else {

                System.out.println(
                        "Report not found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to delete quality report.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // ORDER MENU - ADMIN
    // =====================================================

    private static void orderMenu() {

        while (true) {

            System.out.println();
            System.out.println("============ ORDER MANAGEMENT ============");
            System.out.println("1. View All Orders");
            System.out.println("2. Update Order Status");
            System.out.println("3. Delete / Cancel Order");
            System.out.println("4. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    displayAllOrders();
                    break;

                case 2:
                    updateOrderStatus();
                    break;

                case 3:
                    adminCancelOrder();
                    break;

                case 4:
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // DISPLAY ALL ORDERS
    // =====================================================

    private static void displayAllOrders() {

        String sql =
                "SELECT order_id, customer_id, product_id, "
                + "quantity, order_date, status "
                + "FROM orders "
                + "ORDER BY order_id";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result =
                     statement.executeQuery()) {

            boolean found = false;

            System.out.println();
            System.out.println(
                    "------------- ALL ORDERS -------------");

            while (result.next()) {

                found = true;

                Order order =
                        new Order(
                                result.getInt("order_id"),
                                result.getInt("customer_id"),
                                result.getInt("product_id"),
                                result.getInt("quantity"),
                                result.getString("order_date"),
                                result.getString("status")
                        );

                System.out.println(order);
            }

            if (!found) {

                System.out.println(
                        "No orders available.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to load orders.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // FIND ORDER
    // =====================================================

    private static Order findOrder(int orderId) {

        for (Order order : orders) {

            if (order.getOrderId() == orderId) {
                return order;
            }
        }

        return null;
    }

    // =====================================================
    // UPDATE ORDER STATUS
    // =====================================================

    private static void updateOrderStatus() {

        int orderId =
                readInt("Order ID: ");

        String checkSql =
                "SELECT status "
                + "FROM orders "
                + "WHERE order_id = ?";

        String updateOrderSql =
                "UPDATE orders "
                + "SET status = ? "
                + "WHERE order_id = ?";

        String updateShipmentSql =
                "UPDATE shipments "
                + "SET status = ? "
                + "WHERE order_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            String currentStatus = null;

            // Check whether order exists
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 checkSql)) {

                statement.setInt(1, orderId);

                try (ResultSet result =
                             statement.executeQuery()) {

                    if (result.next()) {

                        currentStatus =
                                result.getString("status");

                    } else {

                        System.out.println(
                                "Order not found.");

                        return;
                    }
                }
            }

            System.out.println(
                    "Current Status: "
                    + currentStatus);

            String status =
                    readString("New Status: ");

            // Update order
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 updateOrderSql)) {

                statement.setString(1, status);
                statement.setInt(2, orderId);

                statement.executeUpdate();
            }

            // Update related shipment
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 updateShipmentSql)) {

                statement.setString(1, status);
                statement.setInt(2, orderId);

                statement.executeUpdate();
            }

            System.out.println(
                    "Order status updated.");

        } catch (SQLException e) {

            System.out.println(
                    "Unable to update order status.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // ADMIN CANCEL ORDER
    // =====================================================
    private static void adminCancelOrder() {

        int orderId =
                readInt("Order ID: ");

        String findOrderSql =
                "SELECT product_id, quantity, status "
                + "FROM orders "
                + "WHERE order_id = ?";

        String updateOrderSql =
                "UPDATE orders "
                + "SET status = 'Cancelled' "
                + "WHERE order_id = ?";

        String updateShipmentSql =
                "UPDATE shipments "
                + "SET status = 'Cancelled' "
                + "WHERE order_id = ?";

        String restoreStockSql =
                "UPDATE inventory "
                + "SET quantity = quantity + ? "
                + "WHERE product_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                int productId;
                int quantity;
                String currentStatus;

                // Find order
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     findOrderSql)) {

                    statement.setInt(1, orderId);

                    try (ResultSet result =
                                 statement.executeQuery()) {

                        if (!result.next()) {

                            System.out.println(
                                    "Order not found.");

                            connection.rollback();
                            return;
                        }

                        productId =
                                result.getInt("product_id");

                        quantity =
                                result.getInt("quantity");

                        currentStatus =
                                result.getString("status");
                    }
                }

                // Check current status
                if (currentStatus.equalsIgnoreCase(
                        "Delivered")) {

                    System.out.println(
                            "Delivered order cannot be cancelled.");

                    connection.rollback();
                    return;
                }

                if (currentStatus.equalsIgnoreCase(
                        "Cancelled")) {

                    System.out.println(
                            "Order is already cancelled.");

                    connection.rollback();
                    return;
                }

                // Restore inventory
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     restoreStockSql)) {

                    statement.setInt(1, quantity);
                    statement.setInt(2, productId);

                    int rowsUpdated =
                            statement.executeUpdate();

                    if (rowsUpdated == 0) {

                        System.out.println(
                                "Inventory not found.");

                        connection.rollback();
                        return;
                    }
                }

                // Cancel order
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     updateOrderSql)) {

                    statement.setInt(1, orderId);
                    statement.executeUpdate();
                }

                // Cancel shipment
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     updateShipmentSql)) {

                    statement.setInt(1, orderId);
                    statement.executeUpdate();
                }

                connection.commit();

                System.out.println(
                        "Order cancelled.");

                System.out.println(
                        "Inventory has been restored.");

                System.out.println(
                        "Shipment status updated.");

            } catch (SQLException e) {

                connection.rollback();

                System.out.println(
                        "Unable to cancel order.");

                System.out.println(
                        "Database Error: "
                        + e.getMessage());
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to connect to database.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // PLACE ORDER
    // =====================================================

    private static void placeOrder(int customerId) {

        if (findCustomer(customerId) == null) {

            System.out.println(
                    "Customer account not found.");

            return;
        }

        productService.displayProducts();

        int productId =
                readInt("Enter Product ID: ");

        Product product =
                productService.findProductById(productId);

        if (product == null) {

            System.out.println(
                    "Product does not exist.");

            return;
        }

        int quantity =
                readInt("Enter Quantity: ");

        if (quantity <= 0) {

            System.out.println(
                    "Quantity must be greater than zero.");

            return;
        }

        String inventorySql =
                "SELECT inventory_id, warehouse_id, "
                + "product_id, quantity, minimum_stock "
                + "FROM inventory "
                + "WHERE product_id = ?";

        String updateInventorySql =
                "UPDATE inventory "
                + "SET quantity = quantity - ? "
                + "WHERE product_id = ? "
                + "AND quantity - ? >= minimum_stock";

        String nextOrderIdSql =
                "SELECT COALESCE(MAX(order_id), 0) + 1 "
                + "AS next_id FROM orders";

        String nextShipmentIdSql =
                "SELECT COALESCE(MAX(shipment_id), 0) + 1 "
                + "AS next_id FROM shipments";

        String orderSql =
                "INSERT INTO orders "
                + "(order_id, customer_id, product_id, "
                + "quantity, order_date, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        String shipmentSql =
                "INSERT INTO shipments "
                + "(shipment_id, order_id, warehouse_id, "
                + "shipment_date, delivery_date, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                Inventory inventory = null;

                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     inventorySql)) {

                    statement.setInt(1, productId);

                    try (ResultSet result =
                                 statement.executeQuery()) {

                        if (result.next()) {

                            inventory = new Inventory(
                                    result.getInt("inventory_id"),
                                    result.getInt("warehouse_id"),
                                    result.getInt("product_id"),
                                    result.getInt("quantity"),
                                    result.getInt("minimum_stock")
                            );
                        }
                    }
                }

                if (inventory == null) {

                    System.out.println(
                            "Inventory not found.");

                    connection.rollback();

                    return;
                }

                if (quantity > inventory.getQuantity()) {

                    System.out.println(
                            "Insufficient inventory.");

                    System.out.println(
                            "Order was NOT created.");

                    connection.rollback();

                    return;
                }

                // Check constraints
                if (!constraintService.isValidOrderQuantity(
                        product,
                        inventory,
                        quantity)) {

                    System.out.println(
                            "Order quantity violates constraints.");

                    connection.rollback();

                    return;
                }

                // Check minimum stock
                if (!constraintService.maintainsMinimumStock(
                        inventory,
                        quantity)) {

                    System.out.println(
                            "Order would reduce stock below minimum level.");

                    System.out.println(
                            "Order was NOT created.");

                    connection.rollback();

                    return;
                }

                // Generate Order ID
                int orderId = 0;

                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     nextOrderIdSql);
                     ResultSet result =
                             statement.executeQuery()) {

                    if (result.next()) {
                        orderId =
                                result.getInt("next_id");
                    }
                }

                // Generate Shipment ID
                int shipmentId = 0;

                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     nextShipmentIdSql);
                     ResultSet result =
                             statement.executeQuery()) {

                    if (result.next()) {
                        shipmentId =
                                result.getInt("next_id");
                    }
                }

                String today =
                        LocalDate.now().toString();

                // Remove stock from MySQL
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     updateInventorySql)) {

                    statement.setInt(1, quantity);
                    statement.setInt(2, productId);
                    statement.setInt(3, quantity);

                    int rowsUpdated =
                            statement.executeUpdate();

                    if (rowsUpdated == 0) {

                        System.out.println(
                                "Unable to update inventory.");

                        connection.rollback();

                        return;
                    }
                }

                // Insert Order
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     orderSql)) {

                    statement.setInt(1, orderId);
                    statement.setInt(2, customerId);
                    statement.setInt(3, productId);
                    statement.setInt(4, quantity);
                    statement.setString(5, today);
                    statement.setString(6, "Pending");

                    statement.executeUpdate();
                }

                // Insert Shipment
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     shipmentSql)) {

                    statement.setInt(1, shipmentId);
                    statement.setInt(2, orderId);
                    statement.setInt(
                            3,
                            inventory.getWarehouseId());
                    statement.setString(4, today);
                    statement.setString(5, "Pending");
                    statement.setString(
                            6,
                            "Not Delivered");

                    statement.executeUpdate();
                }

                connection.commit();

                // Keep current Java session synchronized
                Order order =
                        new Order(
                                orderId,
                                customerId,
                                productId,
                                quantity,
                                today,
                                "Pending");

                orders.add(order);

                Shipment shipment =
                        new Shipment(
                                shipmentId,
                                orderId,
                                inventory.getWarehouseId(),
                                today,
                                "Not Delivered",
                                "Pending");

                shipments.add(shipment);

                System.out.println();
                System.out.println(
                        "======================================");
                System.out.println(
                        "        ORDER CREATED SUCCESSFULLY");
                System.out.println(
                        "======================================");
                System.out.println(
                        "Order ID: " + orderId);
                System.out.println(
                        "Product: "
                        + product.getProductName());
                System.out.println(
                        "Quantity: " + quantity);
                System.out.println(
                        "Status: Pending");
                System.out.println(
                        "Shipment ID: " + shipmentId);
                System.out.println(
                        "Inventory updated successfully.");

            } catch (SQLException e) {

                connection.rollback();

                System.out.println(
                        "Unable to create order.");

                System.out.println(
                        "Database Error: "
                        + e.getMessage());
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to connect to database.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // GET NEXT ORDER ID
    // =====================================================

    private static int getNextOrderId() {

        int max = 0;

        for (Order order : orders) {

            if (order.getOrderId() > max) {

                max = order.getOrderId();
            }
        }

        return max + 1;
    }

    // =====================================================
    // GET NEXT SHIPMENT ID
    // =====================================================

    private static int getNextShipmentId() {

        int max = 0;

        for (Shipment shipment : shipments) {

            if (shipment.getShipmentId() > max) {

                max = shipment.getShipmentId();
            }
        }

        return max + 1;
    }

    // =====================================================
    // GET NEXT CUSTOMER ID
    // =====================================================

    private static int getNextCustomerId() {

        int max = 0;

        for (Customer customer : customers) {

            if (customer.getCustomerId() > max) {

                max = customer.getCustomerId();
            }
        }

        return max + 1;
    }

    // =====================================================
    // GET CUSTOMER ID BY USERNAME
    // =====================================================

    private static int getCustomerIdByUsername(
            String username) {

        String sql =
                "SELECT customer_id "
                + "FROM customers "
                + "WHERE customer_name = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet result =
                         statement.executeQuery()) {

                if (result.next()) {

                    return result.getInt(
                            "customer_id");
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to find customer account.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }

        return -1;
    }

    // =====================================================
    // VIEW MY ORDERS
    // =====================================================

    private static void viewMyOrders(int customerId) {

        String sql =
                "SELECT order_id, customer_id, product_id, "
                + "quantity, order_date, status "
                + "FROM orders "
                + "WHERE customer_id = ? "
                + "ORDER BY order_id";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            try (ResultSet result =
                         statement.executeQuery()) {

                boolean found = false;

                System.out.println();
                System.out.println(
                        "------------- MY ORDERS -------------");

                while (result.next()) {

                    found = true;

                    Order order =
                            new Order(
                                    result.getInt("order_id"),
                                    result.getInt("customer_id"),
                                    result.getInt("product_id"),
                                    result.getInt("quantity"),
                                    result.getString("order_date"),
                                    result.getString("status")
                            );

                    System.out.println(order);
                }

                if (!found) {

                    System.out.println(
                            "You have no orders.");
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to load your orders.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }

    // =====================================================
    // CANCEL USER ORDER
    // =====================================================

    private static void cancelOrder(int customerId) {

        int orderId =
                readInt("Enter Order ID: ");

        String findOrderSql =
                "SELECT customer_id, product_id, quantity, status "
                + "FROM orders "
                + "WHERE order_id = ?";

        String restoreStockSql =
                "UPDATE inventory "
                + "SET quantity = quantity + ? "
                + "WHERE product_id = ?";

        String updateOrderSql =
                "UPDATE orders "
                + "SET status = 'Cancelled' "
                + "WHERE order_id = ?";

        String updateShipmentSql =
                "UPDATE shipments "
                + "SET status = 'Cancelled' "
                + "WHERE order_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                int orderCustomerId;
                int productId;
                int quantity;
                String status;

                // Find order
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     findOrderSql)) {

                    statement.setInt(1, orderId);

                    try (ResultSet result =
                                 statement.executeQuery()) {

                        if (!result.next()) {

                            System.out.println(
                                    "Order not found.");

                            connection.rollback();
                            return;
                        }

                        orderCustomerId =
                                result.getInt("customer_id");

                        productId =
                                result.getInt("product_id");

                        quantity =
                                result.getInt("quantity");

                        status =
                                result.getString("status");
                    }
                }

                // Check ownership
                if (orderCustomerId != customerId) {

                    System.out.println(
                            "You are not authorized to cancel this order.");

                    connection.rollback();
                    return;
                }

                // Check delivered status
                if (status.equalsIgnoreCase(
                        "Delivered")) {

                    System.out.println(
                            "Delivered order cannot be cancelled.");

                    connection.rollback();
                    return;
                }

                // Check already cancelled
                if (status.equalsIgnoreCase(
                        "Cancelled")) {

                    System.out.println(
                            "Order is already cancelled.");

                    connection.rollback();
                    return;
                }

                // Restore inventory
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     restoreStockSql)) {

                    statement.setInt(1, quantity);
                    statement.setInt(2, productId);

                    int rowsUpdated =
                            statement.executeUpdate();

                    if (rowsUpdated == 0) {

                        System.out.println(
                                "Inventory not found.");

                        connection.rollback();
                        return;
                    }
                }

                // Cancel order
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     updateOrderSql)) {

                    statement.setInt(1, orderId);

                    statement.executeUpdate();
                }

                // Cancel shipment
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     updateShipmentSql)) {

                    statement.setInt(1, orderId);

                    statement.executeUpdate();
                }

                connection.commit();

                System.out.println(
                        "Order cancelled successfully.");

                System.out.println(
                        "Inventory has been restored.");

            } catch (SQLException e) {

                connection.rollback();

                System.out.println(
                        "Unable to cancel order.");

                System.out.println(
                        "Database Error: "
                        + e.getMessage());
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to connect to database.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }    // =====================================================
    // SHIPMENT MENU
    // =====================================================

    private static void shipmentMenu() {

        while (true) {

            System.out.println();
            System.out.println("========= SHIPMENT MANAGEMENT =========");
            System.out.println("1. View Shipments");
            System.out.println("2. Update Shipment");
            System.out.println("3. Delete Shipment");
            System.out.println("4. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    displayShipments();
                    break;

                case 2:
                    updateShipment();
                    break;

                case 3:
                    deleteShipment();
                    break;

                case 4:
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // VIEW SHIPMENTS
    // =====================================================

    private static void displayShipments() {

        String sql =
                "SELECT shipment_id, order_id, warehouse_id, "
                + "shipment_date, delivery_date, status "
                + "FROM shipments "
                + "ORDER BY shipment_id";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result =
                     statement.executeQuery()) {

            boolean found = false;

            System.out.println();
            System.out.println(
                    "----------- SHIPMENT LIST -----------");

            while (result.next()) {

                found = true;

                Shipment shipment =
                        new Shipment(
                                result.getInt("shipment_id"),
                                result.getInt("order_id"),
                                result.getInt("warehouse_id"),
                                result.getString("shipment_date"),
                                result.getString("delivery_date"),
                                result.getString("status")
                        );

                System.out.println(shipment);
            }

            if (!found) {
                System.out.println(
                        "No shipments available.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to load shipments.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }

    // =====================================================
    // FIND SHIPMENT
    // =====================================================

    private static Shipment findShipment(
            int shipmentId) {

        for (Shipment shipment :
                shipments) {

            if (shipment.getShipmentId()
                    == shipmentId) {

                return shipment;
            }
        }

        return null;
    }

    // =====================================================
    // FIND SHIPMENT BY ORDER
    // =====================================================

    private static Shipment findShipmentByOrderId(
            int orderId) {

        for (Shipment shipment :
                shipments) {

            if (shipment.getOrderId()
                    == orderId) {

                return shipment;
            }
        }

        return null;
    }

    // =====================================================
    // UPDATE SHIPMENT
    // =====================================================

    private static void updateShipment() {

        int id =
                readInt("Shipment ID: ");

        String findShipmentSql =
                "SELECT order_id "
                + "FROM shipments "
                + "WHERE shipment_id = ?";

        String updateShipmentSql =
                "UPDATE shipments "
                + "SET warehouse_id = ?, "
                + "shipment_date = ?, "
                + "delivery_date = ?, "
                + "status = ? "
                + "WHERE shipment_id = ?";

        String updateOrderSql =
                "UPDATE orders "
                + "SET status = ? "
                + "WHERE order_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                int orderId;

                // Find shipment
                try (PreparedStatement statement =
                             connection.prepareStatement(findShipmentSql)) {

                    statement.setInt(1, id);

                    try (ResultSet result =
                                 statement.executeQuery()) {

                        if (!result.next()) {

                            System.out.println(
                                    "Shipment not found.");

                            connection.rollback();
                            return;
                        }

                        orderId =
                                result.getInt("order_id");
                    }
                }

                int warehouseId =
                        readInt("New Warehouse ID: ");

                if (!distributionService
                        .warehouseExists(warehouseId)) {

                    System.out.println(
                            "Warehouse does not exist.");

                    connection.rollback();
                    return;
                }

                String shipmentDate =
                        readString("Shipment Date: ");

                String deliveryDate =
                        readString("Delivery Date: ");

                String status =
                        readString("Status: ");

                // Update shipment in MySQL
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     updateShipmentSql)) {

                    statement.setInt(1, warehouseId);
                    statement.setString(2, shipmentDate);
                    statement.setString(3, deliveryDate);
                    statement.setString(4, status);
                    statement.setInt(5, id);

                    statement.executeUpdate();
                }

                // Keep order status synchronized
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     updateOrderSql)) {

                    statement.setString(1, status);
                    statement.setInt(2, orderId);

                    statement.executeUpdate();
                }

                connection.commit();

                System.out.println(
                        "Shipment updated successfully.");

                System.out.println(
                        "Order status synchronized.");

            } catch (SQLException e) {

                connection.rollback();

                System.out.println(
                        "Unable to update shipment.");

                System.out.println(
                        "Database Error: "
                        + e.getMessage());
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to connect to database.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }    // =====================================================
    // DELETE SHIPMENT
    // =====================================================

    private static void deleteShipment() {

        int id =
                readInt("Shipment ID: ");

        String sql =
                "DELETE FROM shipments "
                + "WHERE shipment_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted == 0) {

                System.out.println(
                        "Shipment not found.");

                return;
            }

            System.out.println(
                    "Shipment deleted successfully.");

        } catch (SQLException e) {

            System.out.println(
                    "Unable to delete shipment.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // TRACK SHIPMENT
    // =====================================================

    private static void trackShipment(int customerId) {

        int orderId =
                readInt("Enter Order ID: ");

        String sql =
                "SELECT s.shipment_id, s.order_id, "
                + "s.warehouse_id, s.shipment_date, "
                + "s.delivery_date, s.status "
                + "FROM shipments s "
                + "JOIN orders o ON s.order_id = o.order_id "
                + "WHERE s.order_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            try (ResultSet result =
                         statement.executeQuery()) {

                if (!result.next()) {

                    System.out.println(
                            "Shipment not found.");

                    return;
                }

                System.out.println();
                System.out.println(
                        "----------- SHIPMENT TRACKING -----------");

                System.out.println(
                        "Shipment ID: "
                        + result.getInt("shipment_id"));

                System.out.println(
                        "Order ID: "
                        + result.getInt("order_id"));

                System.out.println(
                        "Warehouse ID: "
                        + result.getInt("warehouse_id"));

                System.out.println(
                        "Shipment Date: "
                        + result.getString("shipment_date"));

                System.out.println(
                        "Delivery Date: "
                        + result.getString("delivery_date"));

                System.out.println(
                        "Status: "
                        + result.getString("status"));
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to track shipment.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }    // =====================================================
    // MARKETING - ADMIN
    // =====================================================

    private static void marketingMenu() {

        while (true) {

            System.out.println();
            System.out.println("============= MARKETING =============");
            System.out.println("1. View Products for Marketing");
            System.out.println("2. Add Product");
            System.out.println("3. Update Product");
            System.out.println("4. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    displayMarketingProducts();
                    break;

                case 2:
                    addProduct();
                    break;

                case 3:
                    updateProduct();
                    break;

                case 4:
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // MARKETING - USER
    // =====================================================

    private static void marketingMenuUser() {

        displayMarketingProducts();
    }

    // =====================================================
    // DISPLAY MARKETING PRODUCTS
    // =====================================================
    private static void displayMarketingProducts() {

        String sql =
                "SELECT product_id, product_name, category, "
                + "price, quantity "
                + "FROM products "
                + "ORDER BY product_id";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result =
                     statement.executeQuery()) {

            boolean found = false;

            System.out.println();
            System.out.println(
                    "========== FOODCHAIN MARKETING ==========");

            while (result.next()) {

                found = true;

                System.out.println();
                System.out.println(
                        "Product: "
                        + result.getString("product_name"));

                System.out.println(
                        "Category: "
                        + result.getString("category"));

                System.out.println(
                        "Price: ₹"
                        + result.getDouble("price"));

                System.out.println(
                        "Available Quantity: "
                        + result.getInt("quantity"));

                System.out.println(
                        "----------------------------------------");
            }

            if (!found) {
                System.out.println(
                        "No products available.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to load marketing products.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }

    // =====================================================
    // SEARCH PRODUCTS
    // =====================================================

    private static void searchProducts() {

        String keyword =
                readString("Search product name: ");

        String sql =
                "SELECT product_id, product_name, category, "
                + "price, quantity "
                + "FROM products "
                + "WHERE product_name LIKE ? "
                + "ORDER BY product_id";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    "%" + keyword + "%");

            try (ResultSet result =
                         statement.executeQuery()) {

                boolean found = false;

                System.out.println();
                System.out.println(
                        "========== SEARCH RESULTS ==========");

                while (result.next()) {

                    found = true;

                    Product product =
                            new Product(
                                    result.getInt("product_id"),
                                    result.getString("product_name"),
                                    result.getString("category"),
                                    result.getDouble("price"),
                                    result.getInt("quantity")
                            );

                    System.out.println(product);
                }

                if (!found) {
                    System.out.println(
                            "No matching products found.");
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to search products.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());
        }
    }
    // =====================================================
    // CORRECT PRODUCT NAME
    // =====================================================
    private static void correctProductName() {

        String input =
                readString("Enter product name/spelling: ");

        ArrayList<Product> products =
                new ArrayList<>();

        String sql =
                "SELECT product_id, product_name, category, "
                + "price, quantity "
                + "FROM products "
                + "ORDER BY product_id";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result =
                     statement.executeQuery()) {

            while (result.next()) {

                products.add(
                        new Product(
                                result.getInt("product_id"),
                                result.getString("product_name"),
                                result.getString("category"),
                                result.getDouble("price"),
                                result.getInt("quantity")
                        )
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to load products.");

            System.out.println(
                    "Database Error: "
                    + e.getMessage());

            return;
        }

        Product closest =
                correctionService.findClosestProduct(
                        products,
                        input);

        correctionService.displayCorrection(
                input,
                closest);
    }
        // =====================================================
    // ANALYTICS MENU
    // =====================================================

    private static void analyticsMenu() {

        while (true) {

            System.out.println();
            System.out.println("============== ANALYTICS ==============");
            System.out.println("1. Product Analytics");
            System.out.println("2. Inventory Analytics");
            System.out.println("3. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    analyticsService
                            .displayProductAnalytics(
                                    productService
                                            .getProducts());
                    break;

                case 2:
                    analyticsService
                            .displayInventoryAnalytics(
                                    inventoryService
                                            .getInventoryList());
                    break;

                case 3:
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // DSA SEARCH MENU
    // =====================================================

    private static void dsaSearchMenu() {

        while (true) {

            System.out.println();
            System.out.println("============= DSA SEARCH =============");
            System.out.println("1. Product Search");
            System.out.println("2. Product Name Correction");
            System.out.println("3. Document Search");
            System.out.println("4. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    searchProducts();
                    break;

                case 2:
                    correctProductName();
                    break;

                case 3:
                    searchDocuments();
                    break;

                case 4:
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // DOCUMENT SEARCH
    // =====================================================
    private static void searchDocuments() {

        String keyword =
                readString("Document keyword: ");

        ArrayList<String> results =
                documentService.searchDocuments(
                        keyword);

        documentService
                .displaySearchResults(results);
    }
    // =====================================================
    // DSA ALGORITHM MENU
    // =====================================================

    private static void dsaAlgorithmMenu() {

        while (true) {

            System.out.println();
            System.out.println("============ DSA ALGORITHMS ============");

            System.out.println("1. M1 - String Algorithms");
            System.out.println("2. M2 - Suffix Structures");
            System.out.println("3. M3 - Dynamic Programming");
            System.out.println("4. M4 - Network Flow");
            System.out.println("5. M5 - NP-Completeness");
            System.out.println("6. M6 - Randomized & Parallel");
            System.out.println("7. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    displayM1();
                    break;

                case 2:
                    displayM2();
                    break;

                case 3:
                    displayM3();
                    break;

                case 4:
                    displayM4();
                    break;

                case 5:
                    displayM5();
                    break;

                case 6:
                    displayM6();
                    break;

                case 7:
                    return;

                default:
                    System.out.println(
                            "Invalid choice.");
            }
        }
    }

    // =====================================================
    // M1
    // =====================================================

    private static void displayM1() {

        System.out.println();
        System.out.println(
                "========== M1 STRING ALGORITHMS ==========");

        System.out.println(
                "1. KMP");

        System.out.println(
                "2. Z-Function");

        System.out.println(
                "3. Rabin-Karp");

        System.out.println(
                "4. Aho-Corasick");

        System.out.println();
        System.out.println(
                "These algorithms are used for fast");
        System.out.println(
                "pattern and product/document searching.");
    }

    // =====================================================
    // M2
    // =====================================================

    private static void displayM2() {

        System.out.println();
        System.out.println(
                "========== M2 SUFFIX STRUCTURES ==========");

        System.out.println(
                "1. Suffix Array");

        System.out.println(
                "2. SA-IS");

        System.out.println(
                "3. LCP / Kasai");

        System.out.println(
                "4. Suffix Tree");

        System.out.println(
                "5. Suffix Automaton");
    }

    // =====================================================
    // M3
    // =====================================================

    private static void displayM3() {

        System.out.println();
        System.out.println(
                "====== M3 DYNAMIC PROGRAMMING ======");

        System.out.println(
                "1. Levenshtein Distance");

        System.out.println(
                "2. Damerau-Levenshtein");

        System.out.println(
                "3. Bitmask DP");

        System.out.println(
                "4. Matrix Chain Multiplication");

        System.out.println(
                "5. Optimal BST");

        System.out.println();
        System.out.println(
                "Levenshtein is currently used for");
        System.out.println(
                "product-name correction.");
    }

    // =====================================================
    // M4
    // =====================================================


private static void displayM4() {

    while (true) {

        System.out.println();
        System.out.println("========== M4 NETWORK FLOW ==========");
        System.out.println("1. Ford-Fulkerson");
        System.out.println("2. Edmonds-Karp");
        System.out.println("3. Dinic");
        System.out.println("4. Bipartite Matching");
        System.out.println("5. Max-Flow Min-Cut");
        System.out.println("6. Back");

        int choice = readInt("Enter choice: ");

        switch (choice) {

            case 1: {
                dsa.m4_network.FordFulkerson ff =
                        new dsa.m4_network.FordFulkerson(4);

                ff.addEdge(0, 1, 10);
                ff.addEdge(1, 2, 7);
                ff.addEdge(2, 3, 5);

                System.out.println("\n--- Ford-Fulkerson ---");
                System.out.println("Source: 0 | Sink: 3");
                System.out.println("Maximum flow: "
                        + ff.maxFlow(0, 3));
                ff.displayFlow();
                break;
            }

            case 2: {
                dsa.m4_network.EdmondsKarp ek =
                        new dsa.m4_network.EdmondsKarp(4);

                ek.addEdge(0, 1, 10);
                ek.addEdge(1, 2, 7);
                ek.addEdge(2, 3, 5);

                System.out.println("\n--- Edmonds-Karp ---");
                System.out.println("Source: 0 | Sink: 3");
                System.out.println("Maximum flow: "
                        + ek.maxFlow(0, 3));
                ek.displayFlow();
                break;
            }

            case 3: {
                dsa.m4_network.Dinic dinic =
                        new dsa.m4_network.Dinic(4);

                dinic.addEdge(0, 1, 10);
                dinic.addEdge(1, 2, 7);
                dinic.addEdge(2, 3, 5);

                System.out.println("\n--- Dinic ---");
                System.out.println("Source: 0 | Sink: 3");
                System.out.println("Maximum flow: "
                        + dinic.maxFlow(0, 3));
                dinic.displayFlow();
                break;
            }

            case 4: {
                dsa.m4_network.BipartiteMatching bm =
                        new dsa.m4_network.BipartiteMatching(3, 3);

                bm.addConnection(0, 0);
                bm.addConnection(0, 1);
                bm.addConnection(1, 1);
                bm.addConnection(2, 2);

                System.out.println("\n--- Bipartite Matching ---");
                System.out.println("Suppliers: 3 | Warehouses: 3");
                System.out.println("Maximum matching: "
                        + bm.maximumMatching());
                bm.displayMatching();
                break;
            }

            case 5: {
                dsa.m4_network.MaxFlowMinCut mc =
                        new dsa.m4_network.MaxFlowMinCut(4);

                mc.addEdge(0, 1, 10);
                mc.addEdge(1, 2, 7);
                mc.addEdge(2, 3, 5);

                System.out.println("\n--- Max-Flow Min-Cut ---");
                System.out.println("Maximum flow: "
                        + mc.maxFlow(0, 3));
                mc.displayFlow();
                mc.displayMinCut(0);
                break;
            }

            case 6:
                return;

            default:
                System.out.println("Invalid choice. Try again.");
        }

        System.out.println();
    }
}

    // =====================================================
    // M5
    // =====================================================

    private static void displayM5() {

        System.out.println();
        System.out.println(
                "======= M5 NP-COMPLETENESS =======");

        System.out.println(
                "1. SAT");

        System.out.println(
                "2. 3-SAT");

        System.out.println(
                "3. 3-SAT to CLIQUE");

        System.out.println(
                "4. CLIQUE to Independent Set");

        System.out.println(
                "5. Independent Set to Vertex Cover");

        System.out.println(
                "6. Vertex Cover Approximation");
    }

    // =====================================================
    // M6
    // =====================================================

    private static void displayM6() {

        System.out.println();
        System.out.println(
                "===== M6 RANDOMIZED & PARALLEL =====");

        System.out.println(
                "1. Randomized QuickSort");

        System.out.println(
                "2. Reservoir Sampling");

        System.out.println(
                "3. Miller-Rabin");

        System.out.println(
                "4. Blelloch Scan");

        System.out.println(
                "5. Parallel Reduce");

        System.out.println(
                "6. Brent's Theorem");
    }

    // =====================================================
    // INPUT - INTEGER
    // =====================================================

    private static int readInt(
            String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine().trim());

            } catch (Exception e) {

                System.out.println(
                        "Please enter a valid integer.");
            }
        }
    }

    // =====================================================
    // INPUT - DOUBLE
    // =====================================================

    private static double readDouble(
            String message) {

        while (true) {

            try {

                System.out.print(message);

                return Double.parseDouble(
                        scanner.nextLine().trim());

            } catch (Exception e) {

                System.out.println(
                        "Please enter a valid number.");
            }
        }
    }

    // =====================================================
    // INPUT - STRING
    // =====================================================

    private static String readString(
            String message) {

        System.out.print(message);

        return scanner.nextLine().trim();
    }
}