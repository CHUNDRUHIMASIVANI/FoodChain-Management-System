package gui;

import data.DatabaseConnection;

import javax.swing.*;

import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;



import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.sql.*;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.Comparator;

import java.util.List;

import java.util.HashMap;

import java.util.Map;

import java.util.ArrayDeque;

import dsa.m1_string.KMP;

import dsa.m1_string.Zfunction;

import dsa.m1_string.RabinKarp;

import dsa.m1_string.AhoCorasick;

import dsa.m2_suffix.SuffixArray;

import dsa.m2_suffix.SAIS;

import dsa.m2_suffix.LCPKasai;

import dsa.m2_suffix.SuffixTree;

import dsa.m2_suffix.SuffixAutomation;

import dsa.m3_dynamic.Levenshtein;

import dsa.m3_dynamic.DamerauLevenshtein;

import dsa.m3_dynamic.BitMaskDP;

import dsa.m3_dynamic.MatrixChainMultiplication;

import dsa.m3_dynamic.OptimalBST;import dsa.m4_network.BipartiteMatching;
import dsa.m4_network.Dinic;
import dsa.m4_network.EdmondsKarp;
import dsa.m4_network.FordFulkerson;
import dsa.m4_network.MaxFlowMinCut;

import dsa.m5_np.CliqueToIndependentSet;
import dsa.m5_np.IndependentSetToVertexCover;
import dsa.m5_np.SAT;
import dsa.m5_np.SetCover;
import dsa.m5_np.ThreeSAT;
import dsa.m5_np.ThreeSATToClique;
import dsa.m5_np.VertexCoverApproximation;

import dsa.m6_random_parallel.RandomizedQuickSort;
import dsa.m6_random_parallel.MillerRabin;
import dsa.m6_random_parallel.ReservoirSampling;
import dsa.m6_random_parallel.ParallelReduce;
import dsa.m6_random_parallel.BlellochScan;
import dsa.m6_random_parallel.BrentTheorem;


public class DashboardFrame extends JFrame {

    private final String username;

    private final String role;

    public DashboardFrame(String username, String role) {

        this.username = username;

        this.role = role;

        setTitle("FoodChain - Dashboard");

        setSize(1100, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildUI();

    }

    // =========================================================

    // DASHBOARD UI

    // =========================================================

    private void buildUI() {

        JPanel main = new JPanel(new BorderLayout(15, 15));

        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel header = new JPanel(new BorderLayout());

        JLabel title = new JLabel("FOODCHAIN");

        title.setFont(new Font("Arial", Font.BOLD, 30));

        JLabel welcome = new JLabel(

                "Welcome, " + username + " (" + role + ")"

        );

        welcome.setFont(new Font("Arial", Font.PLAIN, 16));

        header.add(title, BorderLayout.WEST);

        header.add(welcome, BorderLayout.EAST);

        main.add(header, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(0, 3, 12, 12));

        if (role.equalsIgnoreCase("ADMIN")) {

            String[] adminButtons = {

                    "Farmer Management",

                    "Supplier Management",

                    "Product Management",

                    "Customer Management",

                    "Order Management",

                    "Marketing",

                    "Warehouse Management",

                    "Distribution Center Management",

                    "Inventory Management",

                    "Quality Reports",

                    "Shipment Management",

                    "Analytics",

                    "DSA Search",

                    "DSA Algorithms",

                    "Logout"

            };

            for (String name : adminButtons) {

                JButton button = new JButton(name);

                button.setFont(new Font("Arial", Font.BOLD, 14));

                button.addActionListener(e -> openAdminModule(name));

                buttons.add(button);

            }

        } else {

            String[] userButtons = {

                    "View Products",

                    "Search Products",

                    "View Suppliers",

                    "View Inventory",

                    "Place Order",

                    "My Orders",

                    "Cancel Order",

                    "Track Shipment",

                    "View Quality Reports",

                    "Marketing",

                    "Correct Product Name",

                    "Logout"

            };

            for (String name : userButtons) {

                JButton button = new JButton(name);

                button.setFont(new Font("Arial", Font.BOLD, 14));

                button.addActionListener(e -> openUserModule(name));

                buttons.add(button);

            }

        }

        main.add(new JScrollPane(buttons), BorderLayout.CENTER);

        setContentPane(main);

    }

    // =========================================================

    // ADMIN MODULES

    // =========================================================

    private void openAdminModule(String module) {

        switch (module) {

            case "Farmer Management":

                farmerManagement();

                break;

            case "Supplier Management":

                supplierManagement();

                break;

            case "Product Management":

                productManagement();

                break;

            case "Customer Management":

                customerManagement();

                break;

            case "Order Management":

                orderManagement();

                break;

            case "Marketing":

                marketing();

                break;

            case "Warehouse Management":

                warehouseManagement();

                break;

            case "Distribution Center Management":

                distributionManagement();

                break;

            case "Inventory Management":

                inventoryManagement();

                break;

            case "Quality Reports":

                qualityReports();

                break;

            case "Shipment Management":

                shipmentManagement();

                break;

            case "Analytics":

                analytics();

                break;

            case "DSA Search":

                dsaSearch();

                break;

            case "DSA Algorithms":

                dsaAlgorithms();

                break;

            case "Logout":

                logout();

                break;

        }

    }

    // =========================================================

    // USER MODULES

    // =========================================================

    private void openUserModule(String module) {

        switch (module) {

            case "View Products":

                showProducts();

                break;

            case "Search Products":

                searchProducts();

                break;

            case "View Suppliers":

                showSuppliers();

                break;

            case "View Inventory":

                showInventory();

                break;

            case "Place Order":

                placeOrder();

                break;

            case "My Orders":

                myOrders();

                break;

            case "Cancel Order":

                cancelOrder();

                break;

            case "Track Shipment":

                trackShipment();

                break;

            case "View Quality Reports":

                qualityReports();

                break;

            case "Marketing":

                marketing();

                break;

            case "Correct Product Name":

                correctProductName();

                break;

            case "Logout":

                logout();

                break;

        }

    }

    // =========================================================

    // 1. FARMER MANAGEMENT

    // =========================================================

    private void farmerManagement() {

        String[] options = {

                "View Farmers",

                "Add Farmer",

                "Update Farmer",

                "Delete Farmer"

        };

        String choice = (String) JOptionPane.showInputDialog(

                this,

                "Select Farmer Operation",

                "Farmer Management",

                JOptionPane.PLAIN_MESSAGE,

                null,

                options,

                options[0]

        );

        if (choice == null) return;

        if (choice.equals("View Farmers")) {

            showQuery(

                    "SELECT farmer_id, farmer_name, location, contact_number " +

                    "FROM farmers ORDER BY farmer_id",

                    "Farmer List"

            );

        } else if (choice.equals("Add Farmer")) {

            JTextField id = new JTextField();

            JTextField name = new JTextField();

            JTextField location = new JTextField();

            JTextField contact = new JTextField();

            Object[] fields = {

                    "Farmer ID:", id,

                    "Name:", name,

                    "Location:", location,

                    "Contact:", contact

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Add Farmer",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "INSERT INTO farmers " +

                        "(farmer_id, farmer_name, location, contact_number) " +

                        "VALUES (?, ?, ?, ?)",

                        id.getText(),

                        name.getText(),

                        location.getText(),

                        contact.getText()

                );

            }

        } else if (choice.equals("Update Farmer")) {

            JTextField id = new JTextField();

            JTextField name = new JTextField();

            JTextField location = new JTextField();

            JTextField contact = new JTextField();

            Object[] fields = {

                    "Farmer ID:", id,

                    "New Name:", name,

                    "New Location:", location,

                    "New Contact:", contact

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Update Farmer",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "UPDATE farmers SET farmer_name=?, location=?, " +

                        "contact_number=? WHERE farmer_id=?",

                        name.getText(),

                        location.getText(),

                        contact.getText(),

                        id.getText()

                );

            }

        } else {

            String id = JOptionPane.showInputDialog(

                    this, "Enter Farmer ID:"

            );

            if (id != null) {

                execute(

                        "DELETE FROM farmers WHERE farmer_id=?",

                        id

                );

            }

        }

    }

    // =========================================================

    // 2. SUPPLIER MANAGEMENT

    // =========================================================

    private void supplierManagement() {

        String[] options = {

                "View Suppliers",

                "Add Supplier",

                "Update Supplier",

                "Delete Supplier"

        };

        String choice = (String) JOptionPane.showInputDialog(

                this,

                "Select Supplier Operation",

                "Supplier Management",

                JOptionPane.PLAIN_MESSAGE,

                null,

                options,

                options[0]

        );

        if (choice == null) return;

        if (choice.equals("View Suppliers")) {

            showQuery(

                    "SELECT supplier_id, supplier_name, location, " +

                    "contact_number FROM suppliers ORDER BY supplier_id",

                    "Supplier List"

            );

        } else if (choice.equals("Add Supplier")) {

            JTextField id = new JTextField();

            JTextField name = new JTextField();

            JTextField location = new JTextField();

            JTextField contact = new JTextField();

            Object[] fields = {

                    "Supplier ID:", id,

                    "Name:", name,

                    "Location:", location,

                    "Contact:", contact

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Add Supplier",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "INSERT INTO suppliers " +

                        "(supplier_id, supplier_name, location, contact_number) " +

                        "VALUES (?, ?, ?, ?)",

                        id.getText(),

                        name.getText(),

                        location.getText(),

                        contact.getText()

                );

            }

        } else if (choice.equals("Update Supplier")) {

            JTextField id = new JTextField();

            JTextField name = new JTextField();

            JTextField location = new JTextField();

            JTextField contact = new JTextField();

            Object[] fields = {

                    "Supplier ID:", id,

                    "New Name:", name,

                    "New Location:", location,

                    "New Contact:", contact

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Update Supplier",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "UPDATE suppliers SET supplier_name=?, location=?, " +

                        "contact_number=? WHERE supplier_id=?",

                        name.getText(),

                        location.getText(),

                        contact.getText(),

                        id.getText()

                );

            }

        } else {

            String id = JOptionPane.showInputDialog(

                    this, "Supplier ID:"

            );

            if (id != null) {

                execute(

                        "DELETE FROM suppliers WHERE supplier_id=?",

                        id

                );

            }

        }

    }

    // =========================================================

    // 3. PRODUCT MANAGEMENT

    // =========================================================

    private void productManagement() {

        String[] options = {

                "View Products",

                "Add Product",

                "Update Product",

                "Delete Product"

        };

        String choice = (String) JOptionPane.showInputDialog(

                this,

                "Select Product Operation",

                "Product Management",

                JOptionPane.PLAIN_MESSAGE,

                null,

                options,

                options[0]

        );

        if (choice == null) return;

        if (choice.equals("View Products")) {

            showProducts();

        } else if (choice.equals("Add Product")) {

            JTextField id = new JTextField();

            JTextField name = new JTextField();

            JTextField category = new JTextField();

            JTextField price = new JTextField();

            JTextField quantity = new JTextField();

            Object[] fields = {

                    "Product ID:", id,

                    "Product Name:", name,

                    "Category:", category,

                    "Price:", price,

                    "Quantity:", quantity

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Add Product",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "INSERT INTO products " +

                        "(product_id, product_name, category, price, quantity) " +

                        "VALUES (?, ?, ?, ?, ?)",

                        id.getText(),

                        name.getText(),

                        category.getText(),

                        price.getText(),

                        quantity.getText()

                );

            }

        } else if (choice.equals("Update Product")) {

            JTextField id = new JTextField();

            JTextField name = new JTextField();

            JTextField category = new JTextField();

            JTextField price = new JTextField();

            JTextField quantity = new JTextField();

            Object[] fields = {

                    "Product ID:", id,

                    "New Name:", name,

                    "New Category:", category,

                    "New Price:", price,

                    "New Quantity:", quantity

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Update Product",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "UPDATE products SET product_name=?, category=?, " +

                        "price=?, quantity=? WHERE product_id=?",

                        name.getText(),

                        category.getText(),

                        price.getText(),

                        quantity.getText(),

                        id.getText()

                );

            }

        } else {

            String id = JOptionPane.showInputDialog(

                    this, "Product ID:"

            );

            if (id != null) {

                execute(

                        "DELETE FROM products WHERE product_id=?",

                        id

                );

            }

        }

    }

    // =========================================================

    // 4. CUSTOMER MANAGEMENT

    // =========================================================

    private void customerManagement() {

        String[] options = {

                "View Customers",

                "Update Customer",

                "Delete Customer"

        };

        String choice = (String) JOptionPane.showInputDialog(

                this,

                "Select Customer Operation",

                "Customer Management",

                JOptionPane.PLAIN_MESSAGE,

                null,

                options,

                options[0]

        );

        if (choice == null) return;

        if (choice.equals("View Customers")) {

            showQuery(

                    "SELECT customer_id, customer_name, address, " +

                    "contact_number FROM customers ORDER BY customer_id",

                    "Customers"

            );

        } else if (choice.equals("Update Customer")) {

            JTextField id = new JTextField();

            JTextField name = new JTextField();

            JTextField address = new JTextField();

            JTextField contact = new JTextField();

            Object[] fields = {

                    "Customer ID:", id,

                    "Name:", name,

                    "Address:", address,

                    "Contact:", contact

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Update Customer",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "UPDATE customers SET customer_name=?, address=?, " +

                        "contact_number=? WHERE customer_id=?",

                        name.getText(),

                        address.getText(),

                        contact.getText(),

                        id.getText()

                );

            }

        } else {

            String id = JOptionPane.showInputDialog(

                    this, "Customer ID:"

            );

            if (id != null) {

                execute(

                        "DELETE FROM customers WHERE customer_id=?",

                        id

                );

            }

        }

    }

    // =========================================================

    // 5. ORDER MANAGEMENT

    // =========================================================

    private void orderManagement() {

        String[] options = {

                "View All Orders",

                "Update Order Status",

                "Cancel Order"

        };

        String choice = (String) JOptionPane.showInputDialog(

                this,

                "Select Order Operation",

                "Order Management",

                JOptionPane.PLAIN_MESSAGE,

                null,

                options,

                options[0]

        );

        if (choice == null) return;

        if (choice.equals("View All Orders")) {

            showQuery(

                    "SELECT order_id, customer_id, product_id, quantity, " +

                    "order_date, status FROM orders ORDER BY order_id",

                    "All Orders"

            );

        } else if (choice.equals("Update Order Status")) {

            JTextField id = new JTextField();

            String[] statuses = {

                    "Pending",

                    "Processing",

                    "Shipped",

                    "Delivered",

                    "Cancelled"

            };

            JComboBox<String> status =

                    new JComboBox<>(statuses);

            Object[] fields = {

                    "Order ID:", id,

                    "Status:", status

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Update Order",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "UPDATE orders SET status=? WHERE order_id=?",

                        status.getSelectedItem(),

                        id.getText()

                );

                execute(

                        "UPDATE shipments SET status=? WHERE order_id=?",

                        status.getSelectedItem(),

                        id.getText()

                );

            }

        } else {

            String id = JOptionPane.showInputDialog(

                    this, "Order ID:"

            );

            if (id != null) {

                execute(

                        "UPDATE orders SET status='Cancelled' " +

                        "WHERE order_id=?",

                        id

                );

                execute(

                        "UPDATE shipments SET status='Cancelled' " +

                        "WHERE order_id=?",

                        id

                );

            }

        }

    }

    // =========================================================

    // 6. MARKETING

    // =========================================================

    private void marketing() {

        showQuery(

                "SELECT product_id, product_name, category, price, quantity " +

                "FROM products ORDER BY category, product_name",

                "FOODCHAIN MARKETING"

        );

    }

    // =========================================================

    // 7. WAREHOUSE MANAGEMENT

    // =========================================================

    private void warehouseManagement() {

        String[] options = {

                "View Warehouses",

                "Add Warehouse",

                "Update Warehouse",

                "Delete Warehouse"

        };

        String choice = (String) JOptionPane.showInputDialog(

                this,

                "Warehouse Operation",

                "Warehouse Management",

                JOptionPane.PLAIN_MESSAGE,

                null,

                options,

                options[0]

        );

        if (choice == null) return;

        if (choice.equals("View Warehouses")) {

            showQuery(

                    "SELECT warehouse_id, warehouse_name, location, capacity " +

                    "FROM warehouses ORDER BY warehouse_id",

                    "Warehouses"

            );

        } else if (choice.equals("Add Warehouse")) {

            JTextField id = new JTextField();

            JTextField name = new JTextField();

            JTextField location = new JTextField();

            JTextField capacity = new JTextField();

            Object[] fields = {

                    "Warehouse ID:", id,

                    "Name:", name,

                    "Location:", location,

                    "Capacity:", capacity

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Add Warehouse",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "INSERT INTO warehouses " +

                        "(warehouse_id, warehouse_name, location, capacity) " +

                        "VALUES (?, ?, ?, ?)",

                        id.getText(),

                        name.getText(),

                        location.getText(),

                        capacity.getText()

                );

            }

        } else if (choice.equals("Update Warehouse")) {

            JTextField id = new JTextField();

            JTextField name = new JTextField();

            JTextField location = new JTextField();

            JTextField capacity = new JTextField();

            Object[] fields = {

                    "Warehouse ID:", id,

                    "Name:", name,

                    "Location:", location,

                    "Capacity:", capacity

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Update Warehouse",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "UPDATE warehouses SET warehouse_name=?, " +

                        "location=?, capacity=? WHERE warehouse_id=?",

                        name.getText(),

                        location.getText(),

                        capacity.getText(),

                        id.getText()

                );

            }

        } else {

            String id = JOptionPane.showInputDialog(

                    this, "Warehouse ID:"

            );

            if (id != null) {

                execute(

                        "DELETE FROM warehouses WHERE warehouse_id=?",

                        id

                );

            }

        }

    }

    // =========================================================

    // 8. DISTRIBUTION CENTERS

    // =========================================================

    private void distributionManagement() {

        String[] options = {

                "View Distribution Centers",

                "Add Distribution Center",

                "Update Distribution Center",

                "Delete Distribution Center"

        };

        String choice = (String) JOptionPane.showInputDialog(

                this,

                "Distribution Center Operation",

                "Distribution Center Management",

                JOptionPane.PLAIN_MESSAGE,

                null,

                options,

                options[0]

        );

        if (choice == null) return;

        if (choice.equals("View Distribution Centers")) {

            showQuery(

                    "SELECT distribution_center_id, " +

                    "distribution_center_name, location, capacity " +

                    "FROM distribution_centers " +

                    "ORDER BY distribution_center_id",

                    "Distribution Centers"

            );

        } else if (choice.equals("Add Distribution Center")) {

            JTextField id = new JTextField();

            JTextField name = new JTextField();

            JTextField location = new JTextField();

            JTextField capacity = new JTextField();

            Object[] fields = {

                    "ID:", id,

                    "Name:", name,

                    "Location:", location,

                    "Capacity:", capacity

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Add Distribution Center",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "INSERT INTO distribution_centers " +

                        "(distribution_center_id, " +

                        "distribution_center_name, location, capacity) " +

                        "VALUES (?, ?, ?, ?)",

                        id.getText(),

                        name.getText(),

                        location.getText(),

                        capacity.getText()

                );

            }

        } else if (choice.equals("Update Distribution Center")) {

            JTextField id = new JTextField();

            JTextField name = new JTextField();

            JTextField location = new JTextField();

            JTextField capacity = new JTextField();

            Object[] fields = {

                    "ID:", id,

                    "Name:", name,

                    "Location:", location,

                    "Capacity:", capacity

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Update Distribution Center",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "UPDATE distribution_centers SET " +

                        "distribution_center_name=?, location=?, " +

                        "capacity=? WHERE distribution_center_id=?",

                        name.getText(),

                        location.getText(),

                        capacity.getText(),

                        id.getText()

                );

            }

        } else {

            String id = JOptionPane.showInputDialog(

                    this, "Distribution Center ID:"

            );

            if (id != null) {

                execute(

                        "DELETE FROM distribution_centers " +

                        "WHERE distribution_center_id=?",

                        id

                );

            }

        }

    }

    // =========================================================

    // 9. INVENTORY MANAGEMENT

    // =========================================================

    private void inventoryManagement() {

        String[] options = {

                "View Inventory",

                "Add Inventory",

                "Update Inventory",

                "Add Stock",

                "Remove Stock",

                "Delete Inventory"

        };

        String choice = (String) JOptionPane.showInputDialog(

                this,

                "Inventory Operation",

                "Inventory Management",

                JOptionPane.PLAIN_MESSAGE,

                null,

                options,

                options[0]

        );

        if (choice == null) return;

        if (choice.equals("View Inventory")) {

            showInventory();

        } else if (choice.equals("Add Inventory")) {

            JTextField id = new JTextField();

            JTextField warehouse = new JTextField();

            JTextField product = new JTextField();

            JTextField quantity = new JTextField();

            JTextField minimum = new JTextField();

            Object[] fields = {

                    "Inventory ID:", id,

                    "Warehouse ID:", warehouse,

                    "Product ID:", product,

                    "Quantity:", quantity,

                    "Minimum Stock:", minimum

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Add Inventory",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "INSERT INTO inventory " +

                        "(inventory_id, warehouse_id, product_id, " +

                        "quantity, minimum_stock) VALUES (?, ?, ?, ?, ?)",

                        id.getText(),

                        warehouse.getText(),

                        product.getText(),

                        quantity.getText(),

                        minimum.getText()

                );

            }

        } else if (choice.equals("Update Inventory")) {

            JTextField id = new JTextField();

            JTextField warehouse = new JTextField();

            JTextField product = new JTextField();

            JTextField quantity = new JTextField();

            JTextField minimum = new JTextField();

            Object[] fields = {

                    "Inventory ID:", id,

                    "Warehouse ID:", warehouse,

                    "Product ID:", product,

                    "Quantity:", quantity,

                    "Minimum Stock:", minimum

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Update Inventory",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "UPDATE inventory SET warehouse_id=?, " +

                        "product_id=?, quantity=?, minimum_stock=? " +

                        "WHERE inventory_id=?",

                        warehouse.getText(),

                        product.getText(),

                        quantity.getText(),

                        minimum.getText(),

                        id.getText()

                );

            }

        } else if (choice.equals("Add Stock")) {

            stockChange(true);

        } else if (choice.equals("Remove Stock")) {

            stockChange(false);

        } else {

            String id = JOptionPane.showInputDialog(

                    this, "Inventory ID:"

            );

            if (id != null) {

                execute(

                        "DELETE FROM inventory WHERE inventory_id=?",

                        id

                );

            }

        }

    }

    private void stockChange(boolean add) {

        JTextField product = new JTextField();

        JTextField quantity = new JTextField();

        Object[] fields = {

                "Product ID:", product,

                "Quantity:", quantity

        };

        if (JOptionPane.showConfirmDialog(

                this,

                fields,

                add ? "Add Stock" : "Remove Stock",

                JOptionPane.OK_CANCEL_OPTION

        ) != JOptionPane.OK_OPTION) {

            return;

        }

        String operator = add ? "+" : "-";

        execute(

                "UPDATE inventory SET quantity = quantity " +

                operator + " ? WHERE product_id=?",

                quantity.getText(),

                product.getText()

        );

        execute(

                "UPDATE products SET quantity = quantity " +

                operator + " ? WHERE product_id=?",

                quantity.getText(),

                product.getText()

        );

    }

    // =========================================================

    // 10. QUALITY REPORTS

    // =========================================================

    private void qualityReports() {

        ensureQualityTable();

        String[] options = {

                "View Reports",

                "Add Report",

                "Update Report",

                "Delete Report"

        };

        String choice = (String) JOptionPane.showInputDialog(

                this,

                "Quality Report Operation",

                "Quality Reports",

                JOptionPane.PLAIN_MESSAGE,

                null,

                options,

                options[0]

        );

        if (choice == null) return;

        if (choice.equals("View Reports")) {

            showQuery(

                    "SELECT report_id, product_id, report_date, " +

                    "quality_status, description " +

                    "FROM quality_reports ORDER BY report_id",

                    "Quality Reports"

            );

        } else if (choice.equals("Add Report")) {

            JTextField id = new JTextField();

            JTextField product = new JTextField();

            JTextField date = new JTextField(

                    LocalDate.now().toString()

            );

            JTextField status = new JTextField();

            JTextField description = new JTextField();

            Object[] fields = {

                    "Report ID:", id,

                    "Product ID:", product,

                    "Date:", date,

                    "Quality Status:", status,

                    "Description:", description

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Add Quality Report",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "INSERT INTO quality_reports " +

                        "(report_id, product_id, report_date, " +

                        "quality_status, description) VALUES (?, ?, ?, ?, ?)",

                        id.getText(),

                        product.getText(),

                        date.getText(),

                        status.getText(),

                        description.getText()

                );

            }

        } else if (choice.equals("Update Report")) {

            JTextField id = new JTextField();

            JTextField product = new JTextField();

            JTextField date = new JTextField();

            JTextField status = new JTextField();

            JTextField description = new JTextField();

            Object[] fields = {

                    "Report ID:", id,

                    "Product ID:", product,

                    "Date:", date,

                    "Quality Status:", status,

                    "Description:", description

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Update Quality Report",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "UPDATE quality_reports SET product_id=?, " +

                        "report_date=?, quality_status=?, description=? " +

                        "WHERE report_id=?",

                        product.getText(),

                        date.getText(),

                        status.getText(),

                        description.getText(),

                        id.getText()

                );

            }

        } else {

            String id = JOptionPane.showInputDialog(

                    this, "Report ID:"

            );

            if (id != null) {

                execute(

                        "DELETE FROM quality_reports WHERE report_id=?",

                        id

                );

            }

        }

    }

    private void ensureQualityTable() {

        try (Connection con = DatabaseConnection.getConnection();

             Statement st = con.createStatement()) {

            st.executeUpdate(

                    "CREATE TABLE IF NOT EXISTS quality_reports (" +

                    "report_id INT PRIMARY KEY," +

                    "product_id INT," +

                    "report_date VARCHAR(30)," +

                    "quality_status VARCHAR(100)," +

                    "description VARCHAR(500))"

            );

        } catch (SQLException e) {

            showError(e);

        }

    }

    // =========================================================

    // 11. SHIPMENT MANAGEMENT

    // =========================================================

    private void shipmentManagement() {

        String[] options = {

                "View Shipments",

                "Update Shipment",

                "Delete Shipment"

        };

        String choice = (String) JOptionPane.showInputDialog(

                this,

                "Shipment Operation",

                "Shipment Management",

                JOptionPane.PLAIN_MESSAGE,

                null,

                options,

                options[0]

        );

        if (choice == null) return;

        if (choice.equals("View Shipments")) {

            showQuery(

                    "SELECT shipment_id, order_id, warehouse_id, " +

                    "shipment_date, delivery_date, status " +

                    "FROM shipments ORDER BY shipment_id",

                    "Shipments"

            );

        } else if (choice.equals("Update Shipment")) {

            JTextField id = new JTextField();

            JTextField warehouse = new JTextField();

            JTextField shipmentDate = new JTextField();

            JTextField deliveryDate = new JTextField();

            JTextField status = new JTextField();

            Object[] fields = {

                    "Shipment ID:", id,

                    "Warehouse ID:", warehouse,

                    "Shipment Date:", shipmentDate,

                    "Delivery Date:", deliveryDate,

                    "Status:", status

            };

            if (JOptionPane.showConfirmDialog(

                    this, fields, "Update Shipment",

                    JOptionPane.OK_CANCEL_OPTION

            ) == JOptionPane.OK_OPTION) {

                execute(

                        "UPDATE shipments SET warehouse_id=?, " +

                        "shipment_date=?, delivery_date=?, status=? " +

                        "WHERE shipment_id=?",

                        warehouse.getText(),

                        shipmentDate.getText(),

                        deliveryDate.getText(),

                        status.getText(),

                        id.getText()

                );

            }

        } else {

            String id = JOptionPane.showInputDialog(

                    this, "Shipment ID:"

            );

            if (id != null) {

                execute(

                        "DELETE FROM shipments WHERE shipment_id=?",

                        id

                );

            }

        }

    }

    // =========================================================

    // 12. ANALYTICS

    // =========================================================

    private void analytics() {

        String[] options = {

                "Product Analytics",

                "Inventory Analytics",

                "Order Analytics"

        };

        String choice = (String) JOptionPane.showInputDialog(

                this,

                "Select Analytics",

                "Analytics",

                JOptionPane.PLAIN_MESSAGE,

                null,

                options,

                options[0]

        );

        if (choice == null) return;

        if (choice.equals("Product Analytics")) {

            showQuery(

                    "SELECT category, COUNT(*) AS product_count, " +

                    "SUM(quantity) AS total_quantity, " +

                    "AVG(price) AS average_price " +

                    "FROM products GROUP BY category",

                    "Product Analytics"

            );

        } else if (choice.equals("Inventory Analytics")) {

            showQuery(

                    "SELECT warehouse_id, " +

                    "COUNT(*) AS inventory_items, " +

                    "SUM(quantity) AS total_stock, " +

                    "SUM(minimum_stock) AS minimum_required " +

                    "FROM inventory GROUP BY warehouse_id",

                    "Inventory Analytics"

            );

        } else {

            showQuery(

                    "SELECT status, COUNT(*) AS order_count " +

                    "FROM orders GROUP BY status",

                    "Order Analytics"

            );

        }

    }

    // =========================================================

    // KMP STRING SEARCH

    // =========================================================

    private void kmpProductSearch() {

        String keyword = JOptionPane.showInputDialog(

                this,

                "Enter product name/category keyword:",

                "KMP Product Search",

                JOptionPane.PLAIN_MESSAGE

        );

        if (keyword == null || keyword.trim().isEmpty()) return;

        String pattern = keyword.trim().toLowerCase();

        List<ProductData> products = loadProducts();

        StringBuilder result = new StringBuilder();

        int matches = 0;

        for (ProductData p : products) {

            boolean nameMatch = kmpSearch(p.name.toLowerCase(), pattern);

            boolean categoryMatch = kmpSearch(p.category.toLowerCase(), pattern);

            if (nameMatch || categoryMatch) {

                matches++;

                result.append("ID: ").append(p.id)

                        .append("\nName: ").append(p.name)

                        .append("\nCategory: ").append(p.category)

                        .append("\nPrice: ").append(p.price)

                        .append("\nQuantity: ").append(p.quantity)

                        .append("\n-------------------------\n");

            }

        }

        if (matches == 0) {

            JOptionPane.showMessageDialog(

                    this,

                    "No product found using KMP for: " + keyword,

                    "KMP Search Result",

                    JOptionPane.INFORMATION_MESSAGE

            );

        } else {

            JOptionPane.showMessageDialog(

                    this,

                    "KMP matched " + matches + " product(s).\n\n" + result,

                    "KMP Search Result",

                    JOptionPane.INFORMATION_MESSAGE

            );

        }

    }

    private boolean kmpSearch(String text, String pattern) {

        if (pattern.length() == 0) return true;

        int[] lps = buildLPS(pattern);

        int i = 0;

        int j = 0;

        while (i < text.length()) {

            if (text.charAt(i) == pattern.charAt(j)) {

                i++;

                j++;

                if (j == pattern.length()) return true;

            } else if (j > 0) {

                j = lps[j - 1];

            } else {

                i++;

            }

        }

        return false;

    }

    private int[] buildLPS(String pattern) {

        int[] lps = new int[pattern.length()];

        int len = 0;

        int i = 1;

        while (i < pattern.length()) {

            if (pattern.charAt(i) == pattern.charAt(len)) {

                lps[i] = ++len;

                i++;

            } else if (len > 0) {

                len = lps[len - 1];

            } else {

                lps[i] = 0;

                i++;

            }

        }

        return lps;

    }

    // =========================================================

    // 13. DSA SEARCH

    // =========================================================

    private void dsaSearch() {

        String[] options = {

                "KMP Product Search",

                "Binary Search by Product ID",

                "Document Search"

        };

        String choice = (String) JOptionPane.showInputDialog(

                this,

                "Select DSA Search",

                "DSA Search",

                JOptionPane.PLAIN_MESSAGE,

                null,

                options,

                options[0]

        );

        if (choice == null) return;

        if (choice.equals("KMP Product Search")) {

            kmpProductSearch();

        } else if (choice.equals("Binary Search by Product ID")) {

            String input = JOptionPane.showInputDialog(

                    this,

                    "Enter Product ID:"

            );

            if (input == null) return;

            try {

                int target = Integer.parseInt(input);

                List<ProductData> products =

                        loadProducts();

                products.sort(

                        Comparator.comparingInt(p -> p.id)

                );

                int low = 0;

                int high = products.size() - 1;

                ProductData found = null;

                while (low <= high) {

                    int mid = (low + high) / 2;

                    if (products.get(mid).id == target) {

                        found = products.get(mid);

                        break;

                    }

                    if (products.get(mid).id < target) {

                        low = mid + 1;

                    } else {

                        high = mid - 1;

                    }

                }

                if (found == null) {

                    JOptionPane.showMessageDialog(

                            this,

                            "Product not found."

                    );

                } else {

                    JOptionPane.showMessageDialog(

                            this,

                            "Product Found\n\n" +

                            "ID: " + found.id +

                            "\nName: " + found.name +

                            "\nCategory: " + found.category +

                            "\nPrice: " + found.price +

                            "\nQuantity: " + found.quantity

                    );

                }

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(

                        this,

                        "Enter a valid Product ID."

                );

            }

        } else {

            String keyword = JOptionPane.showInputDialog(

                    this,

                    "Document keyword:"

            );

            if (keyword == null) return;

            showQuery(

                    "SELECT product_id, product_name, category " +

                    "FROM products WHERE product_name LIKE ? " +

                    "OR category LIKE ?",

                    "Document/Product Search",

                    "%" + keyword + "%",

                    "%" + keyword + "%"

            );

        }

    }

    // =========================================================

    // 14. DSA ALGORITHMS

    // =========================================================

    private void dsaAlgorithms() {


String[] options = {
    "M1 - String Algorithms",
    "M2 - Suffix Structures",
    "M3 - Dynamic Programming",
    "M4 - Network Flow",
    "M5 - NP-Completeness",
    "M6 - Randomized & Parallel",
    "Back"
};

        String choice = (String) JOptionPane.showInputDialog(
                this,
                "Select DSA Module",
                "DSA Algorithms",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == null || choice.equals("Back")) return;

        if (choice.equals("M1 - String Algorithms")) {
            m1StringAlgorithms();
        } else if (choice.equals("M2 - Suffix Structures")) {
            m2SuffixStructures();
        } else if (choice.equals("M3 - Dynamic Programming")) {
            m3DynamicProgramming();
       } else if (choice.equals("M4 - Network Flow")) {
            m4NetworkFlow();
        } else if (choice.equals("M5 - NP-Completeness")) {
            m5NPCompleteness();
       } else if (choice.equals("M6 - Randomized & Parallel")) {
            m6RandomizedParallel();
}

    }




private void m4NetworkFlow() {
    String[] options = {
        "Match Suppliers to Warehouses (MySQL)",
        "Ford-Fulkerson",
        "Edmonds-Karp",
        "Dinic",
        "Bipartite Matching",
        "Max-Flow Min-Cut",
        "Back"
    };

    String choice = (String) JOptionPane.showInputDialog(
        this,
        "Select a network-flow algorithm:",
        "M4 - Network Flow",
        JOptionPane.PLAIN_MESSAGE,
        null,
        options,
        options[0]
    );

    if (choice == null || choice.equals("Back")) {
        return;
    }

    switch (choice) {
        case "Match Suppliers to Warehouses (MySQL)":
        case "Bipartite Matching":
            matchSuppliersToWarehouses();
            break;

        case "Ford-Fulkerson":
            runFordFulkerson();
            break;

        case "Edmonds-Karp":
            runEdmondsKarp();
            break;

        case "Dinic":
            runDinic();
            break;

        case "Max-Flow Min-Cut":
            runMaxFlowMinCut();
            break;
        case "View Warehouse Capacities (MySQL)": {
            String sql =
                "SELECT warehouse_id, warehouse_name, capacity " +
                "FROM warehouses ORDER BY warehouse_id";

            StringBuilder output =
                new StringBuilder("WAREHOUSE CAPACITIES\n\n");

            try (
                java.sql.Connection con =
                    data.DatabaseConnection.getConnection();
                java.sql.PreparedStatement ps =
                    con.prepareStatement(sql);
                java.sql.ResultSet rs = ps.executeQuery()
            ) {
                boolean found = false;

                while (rs.next()) {
                    found = true;

                    output.append("Warehouse ID: ")
                        .append(rs.getInt("warehouse_id"))
                        .append("\nName: ")
                        .append(rs.getString("warehouse_name"))
                        .append("\nCapacity: ")
                        .append(rs.getInt("capacity"))
                        .append("\n\n");
                }

                if (!found) {
                    output.append("No warehouses found.");
                }

                javax.swing.JTextArea area =
                    new javax.swing.JTextArea(output.toString(), 18, 40);

                area.setEditable(false);

                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    new javax.swing.JScrollPane(area)
                );

            } catch (java.sql.SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + ex.getMessage()
                );
            }

            break;
        }

        default:
            JOptionPane.showMessageDialog(
                this, "Invalid M4 selection."
            );
    }
}


private void runFordFulkerson() {
    dsa.m4_network.FordFulkerson a =
        new dsa.m4_network.FordFulkerson(4);
    a.addEdge(0, 1, 10);
    a.addEdge(1, 2, 7);
    a.addEdge(2, 3, 5);
    int flow = a.maxFlow(0, 3);
    showM4Result("Ford-Fulkerson", flow);
}

private void runEdmondsKarp() {
    dsa.m4_network.EdmondsKarp a =
        new dsa.m4_network.EdmondsKarp(4);
    a.addEdge(0, 1, 10);
    a.addEdge(1, 2, 7);
    a.addEdge(2, 3, 5);
    int flow = a.maxFlow(0, 3);
    showM4Result("Edmonds-Karp", flow);
}

private void runDinic() {
    dsa.m4_network.Dinic a =
        new dsa.m4_network.Dinic(4);
    a.addEdge(0, 1, 10);
    a.addEdge(1, 2, 7);
    a.addEdge(2, 3, 5);
    int flow = a.maxFlow(0, 3);
    showM4Result("Dinic", flow);
}

private void runMaxFlowMinCut() {
    dsa.m4_network.MaxFlowMinCut a =
        new dsa.m4_network.MaxFlowMinCut(4);
    a.addEdge(0, 1, 10);
    a.addEdge(1, 2, 7);
    a.addEdge(2, 3, 5);
    int flow = a.maxFlow(0, 3);
    showM4Result("Max-Flow Min-Cut", flow);
}

private void showM4Result(String algorithm, int flow) {
    JOptionPane.showMessageDialog(
        this,
        "Algorithm: " + algorithm
            + "\nMaximum flow: " + flow
            + "\n\nSource: 0"
            + "\nDestination: 3",
        "M4 Algorithm Result",
        JOptionPane.INFORMATION_MESSAGE
    );
}


private void matchSuppliersToWarehouses() {

    java.util.List<String> supplierNames =
        new java.util.ArrayList<>();
    java.util.List<String> supplierLocations =
        new java.util.ArrayList<>();

    java.util.List<String> warehouseNames =
        new java.util.ArrayList<>();
    java.util.List<String> warehouseLocations =
        new java.util.ArrayList<>();

    try (Connection con = DatabaseConnection.getConnection()) {

        // Load real suppliers from MySQL
        String sql =
            "SELECT supplier_name, location "
            + "FROM suppliers ORDER BY supplier_id";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                supplierNames.add(rs.getString("supplier_name"));
                supplierLocations.add(rs.getString("location"));
            }
        }

        // Load real warehouses from MySQL
        sql =
            "SELECT warehouse_name, location "
            + "FROM warehouses ORDER BY warehouse_id";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                warehouseNames.add(rs.getString("warehouse_name"));
                warehouseLocations.add(rs.getString("location"));
            }
        }

        if (supplierNames.isEmpty() || warehouseNames.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "No matching possible.\n"
                + "Please add suppliers and warehouses first.",
                "M4 - Supplier Matching",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Build the bipartite graph from matching locations
        dsa.m4_network.BipartiteMatching matcher =
            new dsa.m4_network.BipartiteMatching(
                supplierNames.size(),
                warehouseNames.size()
            );

        for (int s = 0; s < supplierNames.size(); s++) {
            String supplierLocation = supplierLocations.get(s);

            if (supplierLocation == null
                    || supplierLocation.trim().isEmpty()) {
                continue;
            }

            for (int w = 0; w < warehouseNames.size(); w++) {
                String warehouseLocation = warehouseLocations.get(w);

                if (warehouseLocation != null
                        && supplierLocation.trim().equalsIgnoreCase(
                            warehouseLocation.trim())) {

                    matcher.addConnection(s, w);
                }
            }
        }

        int totalMatches = matcher.maximumMatching();

        StringBuilder output = new StringBuilder();

        output.append("SUPPLIER - WAREHOUSE MATCHING\n\n");
        output.append("Suppliers loaded: ")
              .append(supplierNames.size()).append("\n");
        output.append("Warehouses loaded: ")
              .append(warehouseNames.size()).append("\n");
        output.append("Total matches: ")
              .append(totalMatches).append("\n\n");

        for (int w = 0; w < warehouseNames.size(); w++) {
            int s = matcher.getMatchedSupplier(w);

            if (s >= 0) {
                output.append("Supplier: ")
                      .append(supplierNames.get(s)).append("\n");
                output.append("Warehouse: ")
                      .append(warehouseNames.get(w)).append("\n");
                output.append("Location: ")
                      .append(warehouseLocations.get(w))
                      .append("\n\n");
            }
        }

        output.append(
            "Rule: supplier and warehouse locations must match."
        );

        javax.swing.JTextArea area =
            new javax.swing.JTextArea(output.toString(), 18, 40);

        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JOptionPane.showMessageDialog(
            this,
            new javax.swing.JScrollPane(area),
            "M4 - Database Matching Results",
            JOptionPane.INFORMATION_MESSAGE
        );

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            this,
            "Unable to match suppliers and warehouses:\n"
                + e.getMessage(),
            "M4 Database Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}



private void m5NPCompleteness() {

    String[] options = {
        "SAT (Brute Force)",
        "3-SAT (Brute Force)",
        "Vertex Cover (Brute Force)",
        "Clique Problem (Brute Force)",
        "Hamiltonian Cycle (Brute Force)",
        "Set Cover (Brute Force)",
        "Clique to Independent Set",
        "3-SAT to CLIQUE Reduction",
        "Vertex Cover (Approximation)",
        "Back"
    };

    String choice = (String) JOptionPane.showInputDialog(
        this,
        "Select an NP-completeness demonstration:",
        "M5 - NP-Completeness",
        JOptionPane.PLAIN_MESSAGE,
        null,
        options,
        options[0]
    );

    if (choice == null || choice.equals("Back")) {
        return;
    }
    if (choice.equals("SAT (Brute Force)")) {

        satDemo();

    } else if (choice.equals("3-SAT (Brute Force)")) {

        threeSatDemo();

    } else if (choice.equals("Vertex Cover (Brute Force)")) {

        vertexCoverDemo();

    } else if (choice.equals("Clique Problem (Brute Force)")) {

        cliqueDemo();

    } else if (choice.equals("Hamiltonian Cycle (Brute Force)")) {

        hamiltonianCycleDemo();

    } else if (choice.equals("Set Cover (Brute Force)")) {

        SetCover.runFoodChainDemo();
    }
    }
private void satDemo() {
    int[][] clauses = {
        {1, -2},
        {-1, 3},
        {2, 3}
    };

    int solution = -1;
    int n = 3;

    for (int mask = 0; mask < (1 << n); mask++) {
        boolean allSatisfied = true;

        for (int[] clause : clauses) {
            boolean clauseSatisfied = false;

            for (int literal : clause) {
                int variable = Math.abs(literal) - 1;
                boolean value = (mask & (1 << variable)) != 0;

                if (literal < 0) {
                    value = !value;
                }

                if (value) {
                    clauseSatisfied = true;
                    break;
                }
            }

            if (!clauseSatisfied) {
                allSatisfied = false;
                break;
            }
        }

        if (allSatisfied) {
            solution = mask;
            break;
        }
    }

    String result = "SAT - Brute Force\n\n";

    if (solution >= 0) {
        result += "Satisfiable: YES\n";
        result += "x1 = " + ((solution & 1) != 0) + "\n";
        result += "x2 = " + ((solution & 2) != 0) + "\n";
        result += "x3 = " + ((solution & 4) != 0);
    } else {
        result += "Satisfiable: NO";
    }

    result += "\n\nTime Complexity: O(2^n * L)";

    JOptionPane.showMessageDialog(
        this, result, "SAT Result",
        JOptionPane.INFORMATION_MESSAGE
    );
}

private void threeSatDemo() {
    int[][] clauses = {
        {1, 2, -3},
        {-1, 2, 3},
        {1, -2, 3}
    };

    int solution = -1;
    int n = 3;

    for (int mask = 0; mask < (1 << n); mask++) {
        boolean allSatisfied = true;

        for (int[] clause : clauses) {
            boolean clauseSatisfied = false;

            for (int literal : clause) {
                int variable = Math.abs(literal) - 1;
                boolean value = (mask & (1 << variable)) != 0;

                if (literal < 0) {
                    value = !value;
                }

                if (value) {
                    clauseSatisfied = true;
                    break;
                }
            }

            if (!clauseSatisfied) {
                allSatisfied = false;
                break;
            }
        }

        if (allSatisfied) {
            solution = mask;
            break;
        }
    }

    String result = "3-SAT - Brute Force\n\n";

    if (solution >= 0) {
        result += "Satisfiable: YES\n";
        result += "x1 = " + ((solution & 1) != 0) + "\n";
        result += "x2 = " + ((solution & 2) != 0) + "\n";
        result += "x3 = " + ((solution & 4) != 0);
    } else {
        result += "Satisfiable: NO";
    }

    result += "\n\nTime Complexity: O(2^n * L)";

    JOptionPane.showMessageDialog(
        this, result, "3-SAT Result",
        JOptionPane.INFORMATION_MESSAGE
    );
}

private void vertexCoverDemo() {
    int[][] edges = {
        {0, 1},
        {0, 2},
        {1, 2},
        {1, 3},
        {2, 4},
        {3, 4}
    };

    int vertices = 5;
    int bestMask = -1;
    int bestSize = vertices + 1;

    for (int mask = 0; mask < (1 << vertices); mask++) {
        int size = Integer.bitCount(mask);

        if (size >= bestSize) {
            continue;
        }

        boolean coversAll = true;

        for (int[] edge : edges) {
            boolean firstSelected =
                (mask & (1 << edge[0])) != 0;

            boolean secondSelected =
                (mask & (1 << edge[1])) != 0;

            if (!firstSelected && !secondSelected) {
                coversAll = false;
                break;
            }
        }

        if (coversAll) {
            bestSize = size;
            bestMask = mask;
        }
    }

    String result = "Vertex Cover - Brute Force\n\n"
        + "Number of vertices: " + vertices + "\n"
        + "Number of edges: " + edges.length + "\n";

    if (bestMask >= 0) {
        result += "Minimum cover size: " + bestSize + "\n"
            + "Selected vertices: ";

        boolean first = true;

        for (int i = 0; i < vertices; i++) {
            if ((bestMask & (1 << i)) != 0) {
                if (!first) {
                    result += ", ";
                }

                result += i;
                first = false;
            }
        }
    }

    result += "\n\nTime Complexity: O(2^V * (V + E))";

    JOptionPane.showMessageDialog(
        this, result, "Vertex Cover Result",
        JOptionPane.INFORMATION_MESSAGE
    );
}private void cliqueDemo() {
    int[][] edges = {
            {0, 1},
            {0, 2},
            {1, 2},
            {1, 3},
            {2, 3},
            {3, 4}
        };

        int vertices = 5;
        int bestMask = 0;
        int bestSize = 0;

        for (int mask = 1; mask < (1 << vertices); mask++) {
            int size = Integer.bitCount(mask);

            if (size <= bestSize) {
                continue;
            }

            boolean isClique = true;

            for (int i = 0; i < vertices && isClique; i++) {
                for (int j = i + 1; j < vertices; j++) {
                    boolean selectedI = (mask & (1 << i)) != 0;
                    boolean selectedJ = (mask & (1 << j)) != 0;

                    if (selectedI && selectedJ) {
                        boolean connected = false;

                        for (int[] edge : edges) {
                            if ((edge[0] == i && edge[1] == j)
                                    || (edge[0] == j && edge[1] == i)) {
                                connected = true;
                                break;
                            }
                        }

                        if (!connected) {
                            isClique = false;
                            break;
                        }
                    }
                }
            }

            if (isClique) {
                bestSize = size;
                bestMask = mask;
            }
        }

        StringBuilder result = new StringBuilder(
            "CLIQUE PROBLEM - BRUTE FORCE\n\n"
        );

        result.append("Vertices: ").append(vertices).append("\n");
        result.append("Edges: ").append(edges.length).append("\n");
        result.append("Maximum clique size: ").append(bestSize).append("\n");
        result.append("Clique vertices: ");

        boolean first = true;

        for (int i = 0; i < vertices; i++) {
            if ((bestMask & (1 << i)) != 0) {
                if (!first) {
                    result.append(", ");
                }

                result.append(i);
                first = false;
            }
        }

        result.append("\n\nTime Complexity: O(2^V * V^2)");

        JOptionPane.showMessageDialog(
            this,
            result.toString(),
            "Clique Result",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
private void hamiltonianCycleDemo() {
    int[][] graph = {
        {0, 1, 1, 0},
        {1, 0, 1, 1},
        {1, 1, 0, 1},
        {0, 1, 1, 0}
    };

    int n = graph.length;
    int[] path = new int[n];

    for (int i = 0; i < n; i++) {
        path[i] = -1;
    }

    path[0] = 0;

    if (hamiltonianUtil(graph, path, 1)) {
        StringBuilder result =
            new StringBuilder("HAMILTONIAN CYCLE\n\nCycle found:\n");

        for (int i = 0; i < n; i++) {
            result.append(path[i]).append(" -> ");
        }

        result.append(path[0]);
        result.append("\n\nTime Complexity: O(V!)");

        JOptionPane.showMessageDialog(
            this,
            result.toString(),
            "Hamiltonian Cycle Result",
            JOptionPane.INFORMATION_MESSAGE
        );
    } else {
        JOptionPane.showMessageDialog(
            this,
            "No Hamiltonian cycle exists.\n\nTime Complexity: O(V!)",
            "Hamiltonian Cycle Result",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}

private boolean hamiltonianUtil(int[][] graph, int[] path, int position) {
    int n = graph.length;

    if (position == n) {
        return graph[path[position - 1]][path[0]] == 1;
    }

    for (int vertex = 1; vertex < n; vertex++) {
        if (isSafeHamiltonian(vertex, graph, path, position)) {
            path[position] = vertex;

            if (hamiltonianUtil(graph, path, position + 1)) {
                return true;
            }

            path[position] = -1;
        }
    }

    return false;
}

private boolean isSafeHamiltonian(
        int vertex, int[][] graph, int[] path, int position) {

    if (graph[path[position - 1]][vertex] == 0) {
        return false;
    }

    for (int i = 0; i < position; i++) {
        if (path[i] == vertex) {
            return false;
        }
    }

    return true;
}


private void m6RandomizedParallel() {

    String[] options = {
        "Randomized Quick Sort",
        "Monte Carlo",
        "Miller-Rabin",
        "Parallel Processing",
        "Parallel Reduction",
        "Reservoir Sampling",
        "Blelloch Scan",
        "Brent's Theorem",
        "Back"
    };

    String choice = (String) JOptionPane.showInputDialog(
        this,
        "Select M6 Algorithm",
        "M6 - Randomized & Parallel",
        JOptionPane.PLAIN_MESSAGE,
        null,
        options,
        options[0]
    );

    if (choice == null || choice.equals("Back")) {
        return;
    }

    if (choice.equals("Randomized Quick Sort")) {
        RandomizedQuickSort.runFoodChainDemo();
        
        }
    else if (choice.equals("Miller-Rabin")) {
        MillerRabin.runDemo();
    }
    else if (choice.equals("Reservoir Sampling")) {
        ReservoirSampling.runFoodChainDemo();
    }   
    else if (choice.equals("Blelloch Scan")) {
        BlellochScan.runDemo();
    }
    else if (choice.equals("Brent's Theorem")) {
        BrentTheorem.runDemo();
    }
    else if (choice.equals("Monte Carlo")) {
        monteCarloFoodChainDemo();
    }
       else if (choice.equals("Parallel Reduction")) {
        ParallelReduce.runFoodChainDemo();
    }
       else if (choice.equals("Parallel Processing")) {
    	    parallelProcessingFoodChainDemo();
    	}
}

private void parallelProcessingFoodChainDemo() {

    String sql =
        "SELECT warehouse_id, quantity FROM inventory";

    try (
        java.sql.Connection con =
            data.DatabaseConnection.getConnection();

        java.sql.PreparedStatement ps =
            con.prepareStatement(sql);

        java.sql.ResultSet rs =
            ps.executeQuery()
    ) {
        java.util.List<String> inventoryRows =
            new java.util.ArrayList<>();

        while (rs.next()) {
            inventoryRows.add(
                rs.getInt("warehouse_id") + ","
                + rs.getInt("quantity")
            );
        }

        if (inventoryRows.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "No inventory records found.",
                "FoodChain Parallel Processing",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        java.util.Map<Integer, Integer> warehouseTotals =
            inventoryRows.parallelStream()
                .map(row -> row.split(","))
                .collect(
                    java.util.stream.Collectors.groupingBy(
                        parts -> Integer.parseInt(parts[0]),
                        java.util.stream.Collectors.summingInt(
                            parts -> Integer.parseInt(parts[1])
                        )
                    )
                );

        StringBuilder result = new StringBuilder();

        result.append("FOODCHAIN — PARALLEL INVENTORY ANALYSIS\n\n");
        result.append("Inventory records processed: ")
              .append(inventoryRows.size()).append("\n\n");
        result.append("Total quantity by warehouse:\n");

        warehouseTotals.entrySet().stream()
            .sorted(java.util.Map.Entry.comparingByKey())
            .forEach(entry ->
                result.append("Warehouse ")
                      .append(entry.getKey())
                      .append(": ")
                      .append(entry.getValue())
                      .append(" units\n")
            );

        int totalQuantity = warehouseTotals.values()
            .stream()
            .mapToInt(Integer::intValue)
            .sum();

        result.append("\nTotal inventory quantity: ")
              .append(totalQuantity);

        javax.swing.JOptionPane.showMessageDialog(
            this,
            result.toString(),
            "FoodChain Parallel Processing",
            javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

    } catch (java.sql.SQLException ex) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Could not load inventory:\n" + ex.getMessage(),
            "Database Error",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}
private void monteCarloFoodChainDemo() {

    String sql = "SELECT shipment_id, status FROM shipments";

    try (
        java.sql.Connection con =
                data.DatabaseConnection.getConnection();

        java.sql.PreparedStatement ps =
                con.prepareStatement(sql);

        java.sql.ResultSet rs =
                ps.executeQuery()
    ) {
        java.util.List<Boolean> shipmentResults =
                new java.util.ArrayList<>();

        while (rs.next()) {
            String status = rs.getString("status");

            shipmentResults.add(
                status != null &&
                status.equalsIgnoreCase("Delivered")
            );
        }

        if (shipmentResults.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "No shipment records found.",
                "FoodChain Delivery Analysis",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        java.util.Random random = new java.util.Random();

        int totalSamples = 10000;
        int deliveredSamples = 0;

        for (int i = 0; i < totalSamples; i++) {
            int index = random.nextInt(shipmentResults.size());

            if (shipmentResults.get(index)) {
                deliveredSamples++;
            }
        }

        double estimatedPercentage =
                100.0 * deliveredSamples / totalSamples;

        long deliveredCount = shipmentResults.stream()
                .filter(Boolean::booleanValue)
                .count();

        double actualPercentage =
                100.0 * deliveredCount / shipmentResults.size();

        String message =
                "FOODCHAIN — MONTE CARLO SHIPMENT ANALYSIS\n\n"
                + "Total shipments: " + shipmentResults.size() + "\n"
                + "Simulation samples: " + totalSamples + "\n"
                + "Estimated delivered percentage: "
                + String.format("%.2f%%", estimatedPercentage) + "\n"
                + "Actual delivered percentage: "
                + String.format("%.2f%%", actualPercentage) + "\n\n"
                + "This simulation estimates the proportion of shipments "
                + "whose status is Delivered.";

        javax.swing.JOptionPane.showMessageDialog(
            this,
            message,
            "FoodChain Monte Carlo Analysis",
            javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

    } catch (java.sql.SQLException ex) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Could not load shipment data:\n" + ex.getMessage(),
            "Database Error",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}
    // =========================================================
    // M1 - STRING ALGORITHMS
    // =========================================================

    private void m1StringAlgorithms() {

        String[] options = {
                "Naive String Search",
                "KMP String Search",
                "Z-Function",
                "Rabin-Karp",
                "Aho-Corasick",
                "Back"
        };

        String choice = (String) JOptionPane.showInputDialog(
                this,
                "Select M1 Algorithm",
                "M1 - String Algorithms",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == null || choice.equals("Back")) return;

        switch (choice) {
            case "Naive String Search":
                naiveStringSearchDemo();
                break;
            case "KMP String Search":
                kmpStringSearchDemo();
                break;
            case "Z-Function":
                zFunctionDemo();
                break;
            case "Rabin-Karp":
                rabinKarpDemo();
                break;
            case "Aho-Corasick":
                ahoCorasickDemo();
                break;
        }
    }

    private String[] getStringSearchInput(String title) {

        String text = JOptionPane.showInputDialog(
                this,
                "Enter text:",
                title,
                JOptionPane.PLAIN_MESSAGE
        );

        if (text == null) return null;

        String pattern = JOptionPane.showInputDialog(
                this,
                "Enter pattern:",
                title,
                JOptionPane.PLAIN_MESSAGE
        );

        if (pattern == null) return null;
        return new String[]{text, pattern};
    }

    private void naiveStringSearchDemo() {

        String[] input = getStringSearchInput("Naive String Search");
        if (input == null) return;

        String text = input[0];
        String pattern = input[1];
        int foundAt = naiveSearch(text, pattern);

        showStringResult(
                "Naive String Search",
                text,
                pattern,
                foundAt,
                "Time Complexity: O(n × m)\n" +
                "Space Complexity: O(1)"
        );
    }

    private int naiveSearch(String text, String pattern) {

        if (pattern.length() == 0) return 0;

        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            int j = 0;
            while (j < pattern.length() &&
                    text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }
            if (j == pattern.length()) return i;
        }
        return -1;
    }

    private void kmpStringSearchDemo() {

        String[] input = getStringSearchInput("KMP String Search");
        if (input == null) return;

        String text = input[0];
        String pattern = input[1];

        int foundAt = KMP.search(
                text.toLowerCase(),
                pattern.toLowerCase()
        );

        showStringResult(
                "KMP String Search",
                text,
                pattern,
                foundAt,
                "Time Complexity: O(n + m)\n" +
                "Space Complexity: O(m)\n" +
                "Uses the LPS table from dsa.m1_string.KMP."
        );
    }

    private void zFunctionDemo() {

        String[] input = getStringSearchInput("Z-Function");
        if (input == null) return;

        String text = input[0];
        String pattern = input[1];

        int foundAt = Zfunction.search(
                text.toLowerCase(),
                pattern.toLowerCase()
        );

        showStringResult(
                "Z-Function",
                text,
                pattern,
                foundAt,
                "Time Complexity: O(n + m)\n" +
                "Space Complexity: O(n + m)\n" +
                "Uses dsa.m1_string.Zfunction."
        );
    }

    private void rabinKarpDemo() {

        String[] input = getStringSearchInput("Rabin-Karp");
        if (input == null) return;

        String text = input[0];
        String pattern = input[1];

        int foundAt = RabinKarp.search(
                text.toLowerCase(),
                pattern.toLowerCase()
        );

        showStringResult(
                "Rabin-Karp",
                text,
                pattern,
                foundAt,
                "Average/Expected Time: O(n + m)\n" +
                "Worst Case: O(n × m)\n" +
                "Space Complexity: O(1)\n" +
                "Uses dsa.m1_string.RabinKarp."
        );
    }

    private void ahoCorasickDemo() {

        String text = JOptionPane.showInputDialog(
                this,
                "Enter text:",
                "Aho-Corasick",
                JOptionPane.PLAIN_MESSAGE
        );

        if (text == null) return;

        String patternInput = JOptionPane.showInputDialog(
                this,
                "Enter patterns separated by commas:\nExample: rice,apple,mango",
                "Aho-Corasick",
                JOptionPane.PLAIN_MESSAGE
        );

        if (patternInput == null) return;

        String[] patterns = patternInput.toLowerCase().split("\\s*,\\s*");
        AhoNode root = buildAhoAutomaton(patterns);

        List<String> matches = ahoSearch(
                root,
                text.toLowerCase()
        );
        

        StringBuilder result = new StringBuilder();
        if (matches == null || matches.isEmpty()) {
            result.append("No pattern found.");
        } else {
            for (String match : matches) {
                result.append("Found: ")
                        .append(match)
                        .append("\n");
            }
        }
        JOptionPane.showMessageDialog(
                this,
                "Aho-Corasick Result\n\n" + result +
                "\nTime Complexity: O(n + total pattern length + matches)\n" +
                "Space Complexity: O(total pattern length)",
                "Aho-Corasick",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    private static class AhoNode {
        Map<Character, AhoNode> children = new HashMap<>();
        AhoNode fail;
        List<String> outputs = new ArrayList<>();
    }

    private AhoNode buildAhoAutomaton(String[] patterns) {
        AhoNode root = new AhoNode();

        for (String pattern : patterns) {
            if (pattern.isEmpty()) continue;

            AhoNode current = root;

            for (char c : pattern.toCharArray()) {
                current.children.putIfAbsent(c, new AhoNode());
                current = current.children.get(c);
            }

            current.outputs.add(pattern);
        }

        root.fail = root;

        ArrayDeque<AhoNode> queue = new ArrayDeque<>();

        for (AhoNode child : root.children.values()) {
            child.fail = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            AhoNode current = queue.remove();

            for (Map.Entry<Character, AhoNode> entry :
                    current.children.entrySet()) {

                char c = entry.getKey();
                AhoNode child = entry.getValue();
                AhoNode fallback = current.fail;

                while (fallback != root &&
                        !fallback.children.containsKey(c)) {
                    fallback = fallback.fail;
                }

                if (fallback.children.containsKey(c) &&
                        fallback.children.get(c) != child) {
                    child.fail = fallback.children.get(c);
                } else {
                    child.fail = root;
                }

                child.outputs.addAll(child.fail.outputs);
                queue.add(child);
            }
        }

        return root;
    }

    private List<String> ahoSearch(AhoNode root, String text) {
        List<String> matches = new ArrayList<>();
        AhoNode current = root;

        for (char c : text.toCharArray()) {

            while (current != root &&
                    !current.children.containsKey(c)) {
                current = current.fail;
            }

            if (current.children.containsKey(c)) {
                current = current.children.get(c);
            } else {
                current = root;
            }

            matches.addAll(current.outputs);
        }

        return matches;
    }

    private void showStringResult(
            String algorithm,
            String text,
            String pattern,
            int foundAt,
            String details) {

        String result = foundAt >= 0
                ? "Pattern found at index: " + foundAt
                : "Pattern not found.";

        JOptionPane.showMessageDialog(
                this,
                algorithm + "\n\n" +
                "Text: " + text + "\n" +
                "Pattern: " + pattern + "\n\n" +
                result + "\n\n" + details,
                algorithm,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // M2 - SUFFIX STRUCTURES
    // =========================================================

    private void m2SuffixStructures() {

        String[] options = {
                "Suffix Array",
                "SA-IS",
                "LCP / Kasai",
                "Suffix Tree",
                "Suffix Automaton",
                "Back"
        };

        String choice = (String) JOptionPane.showInputDialog(
                this,
                "Select M2 Algorithm",
                "M2 - Suffix Structures",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == null || choice.equals("Back")) return;

        switch (choice) {
            case "Suffix Array":
                suffixArrayDemo();
                break;
            case "SA-IS":
                saisDemo();
                break;
            case "LCP / Kasai":
                lcpKasaiDemo();
                break;
            case "Suffix Tree":
                suffixTreeDemo();
                break;
            case "Suffix Automaton":
                suffixAutomatonDemo();
                break;
        }
    }

    private String askText(String title) {
        String text = JOptionPane.showInputDialog(
                this,
                "Enter text:",
                title,
                JOptionPane.PLAIN_MESSAGE
        );
        if (text == null) return null;
        return text;
    }

    private void suffixArrayDemo() {

        String text = askText("Suffix Array");
        if (text == null) return;

        SuffixArray suffixArray = new SuffixArray(text);
        int[] array = suffixArray.getSuffixArray();

        StringBuilder result = new StringBuilder();
        result.append("Suffix Array\n\n");

        for (int i = 0; i < array.length; i++) {
            result.append(i)
                    .append(" -> position ")
                    .append(array[i])
                    .append(" -> ")
                    .append(suffixArray.getSuffix(i))
                    .append("\n");
        }

        result.append("\nTime: construction uses sorting of suffixes\n")
                .append("Search: binary search over suffixes.");

        JOptionPane.showMessageDialog(
                this,
                result.toString(),
                "Suffix Array",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void saisDemo() {

        String text = askText("SA-IS");
        if (text == null) return;

        int[] array = SAIS.buildSuffixArray(text);
        StringBuilder result = new StringBuilder();

        result.append("SA-IS Suffix Array\n\n");

        for (int i = 0; i < array.length; i++) {
            result.append(i)
                    .append(" -> ")
                    .append(array[i])
                    .append(" -> ")
                    .append(text.substring(array[i]))
                    .append("\n");
        }

        result.append("\nSA-IS builds the suffix array using S/L typing,\n")
                .append("LMS positions and induced sorting.");

        JOptionPane.showMessageDialog(
                this,
                result.toString(),
                "SA-IS",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void lcpKasaiDemo() {

        String text = askText("LCP / Kasai");
        if (text == null) return;

        int[] sa = SAIS.buildSuffixArray(text);
        int[] rank = new int[text.length()];
        int[] lcp = new int[text.length()];

        for (int i = 0; i < sa.length; i++) {
            rank[sa[i]] = i;
        }

        int k = 0;
        for (int i = 0; i < text.length(); i++) {
            int r = rank[i];
            if (r == 0) continue;

            int j = sa[r - 1];
            while (i + k < text.length() &&
                    j + k < text.length() &&
                    text.charAt(i + k) == text.charAt(j + k)) {
                k++;
            }

            lcp[r] = k;
            if (k > 0) k--;
        }

        StringBuilder result = new StringBuilder("LCP / Kasai\n\n");
        for (int i = 0; i < lcp.length; i++) {
            result.append("SA[")
                    .append(i)
                    .append("] = ")
                    .append(sa[i])
                    .append(" | LCP = ")
                    .append(lcp[i])
                    .append("\n");
        }

        result.append("\nTime Complexity: O(n) after suffix array\n")
                .append("Space Complexity: O(n)");

        JOptionPane.showMessageDialog(
                this,
                result.toString(),
                "LCP / Kasai",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void suffixTreeDemo() {

        String text = askText("Suffix Tree");
        if (text == null) return;

        StringBuilder result = new StringBuilder();
        result.append("Suffix Tree\n\n");
        result.append("All suffixes represented by starting positions:\n\n");

        for (int i = 0; i < text.length(); i++) {
            result.append(i)
                    .append(" -> ")
                    .append(text.substring(i))
                    .append("\n");
        }

        result.append("\nThis demo shows the suffix structure of the text.");

        JOptionPane.showMessageDialog(
                this,
                result.toString(),
                "Suffix Tree",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void suffixAutomatonDemo() {

        String text = askText("Suffix Automaton");
        if (text == null) return;

        int distinct = countDistinctSubstrings(text);

        JOptionPane.showMessageDialog(
                this,
                "Suffix Automaton Demo\n\n" +
                "Text: " + text + "\n" +
                "Distinct substrings: " + distinct + "\n\n" +
                "The suffix automaton represents all substrings\n" +
                "using a compact state-transition structure.",
                "Suffix Automaton",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private int countDistinctSubstrings(String text) {

        int n = text.length();
        if (n == 0) return 0;

        class State {
            int link = -1;
            int length = 0;
            Map<Character, Integer> next = new HashMap<>();
        }

        ArrayList<State> states = new ArrayList<>();
        states.add(new State());
        int last = 0;

        for (char c : text.toCharArray()) {
            int current = states.size();
            states.add(new State());
            states.get(current).length = states.get(last).length + 1;

            int p = last;
            while (p != -1 && !states.get(p).next.containsKey(c)) {
                states.get(p).next.put(c, current);
                p = states.get(p).link;
            }

            if (p == -1) {
                states.get(current).link = 0;
            } else {
                int q = states.get(p).next.get(c);
                if (states.get(p).length + 1 == states.get(q).length) {
                    states.get(current).link = q;
                } else {
                    int clone = states.size();
                    State cloneState = new State();
                    cloneState.length = states.get(p).length + 1;
                    cloneState.link = states.get(q).link;
                    cloneState.next.putAll(states.get(q).next);
                    states.add(cloneState);

                    while (p != -1 &&
                            states.get(p).next.getOrDefault(c, -1) == q) {
                        states.get(p).next.put(c, clone);
                        p = states.get(p).link;
                    }

                    states.get(q).link = clone;
                    states.get(current).link = clone;
                }
            }
            last = current;
        }

        long count = 0;
        for (int i = 1; i < states.size(); i++) {
            count += states.get(i).length -
                    states.get(states.get(i).link).length;
        }

        return (int) count;
    }

    // =========================================================
    // M3 - DYNAMIC PROGRAMMING
    // =========================================================

    private void m3DynamicProgramming() {

        String[] options = {
                "Levenshtein Distance",
                "Damerau-Levenshtein",
                "Bitmask DP",
                "Matrix Chain Multiplication",
                "Optimal BST",
                "Back"
        };

        String choice = (String) JOptionPane.showInputDialog(
                this,
                "Select M3 Algorithm",
                "M3 - Dynamic Programming",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == null || choice.equals("Back")) return;

        switch (choice) {
            case "Levenshtein Distance":
                levenshteinDemo();
                break;
            case "Damerau-Levenshtein":
                damerauLevenshteinDemo();
                break;
            case "Bitmask DP":
                bitmaskDPDemo();
                break;
            case "Matrix Chain Multiplication":
                matrixChainDemo();
                break;
            case "Optimal BST":
                optimalBSTDemo();
                break;
        }
    }

    private void levenshteinDemo() {

        String a = JOptionPane.showInputDialog(this, "Enter first string:");
        if (a == null) return;

        String b = JOptionPane.showInputDialog(this, "Enter second string:");
        if (b == null) return;

        int distance = levenshtein(a, b);

        JOptionPane.showMessageDialog(
                this,
                "Levenshtein Distance\n\n" +
                "First: " + a + "\n" +
                "Second: " + b + "\n" +
                "Distance: " + distance + "\n\n" +
                "Time Complexity: O(n × m)\n" +
                "Space Complexity: O(n × m)",
                "Levenshtein",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void damerauLevenshteinDemo() {

        String a = JOptionPane.showInputDialog(this, "Enter first string:");
        if (a == null) return;

        String b = JOptionPane.showInputDialog(this, "Enter second string:");
        if (b == null) return;

        int distance = damerauDistance(a, b);

        JOptionPane.showMessageDialog(
                this,
                "Damerau-Levenshtein Distance\n\n" +
                "First: " + a + "\n" +
                "Second: " + b + "\n" +
                "Distance: " + distance + "\n\n" +
                "Supports insertion, deletion, substitution and\n" +
                "adjacent transposition.\n\n" +
                "Time Complexity: O(n × m)\n" +
                "Space Complexity: O(n × m)",
                "Damerau-Levenshtein",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private int damerauDistance(String a, String b) {

        int n = a.length();
        int m = b.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) dp[i][0] = i;
        for (int j = 0; j <= m; j++) dp[0][j] = j;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;

                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1,
                                dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );

                if (i > 1 && j > 1 &&
                        a.charAt(i - 1) == b.charAt(j - 2) &&
                        a.charAt(i - 2) == b.charAt(j - 1)) {
                    dp[i][j] = Math.min(
                            dp[i][j],
                            dp[i - 2][j - 2] + 1
                    );
                }
            }
        }

        return dp[n][m];
    }

    private void bitmaskDPDemo() {

        String input = JOptionPane.showInputDialog(
                this,
                "Enter a comma-separated distance matrix row-wise.\n" +
                "Example for 3 cities: 0,10,15,10,0,20,15,20,0"
        );

        if (input == null) return;

        try {
            String[] parts = input.split(",");
            int count = parts.length;
            int n = (int) Math.sqrt(count);

            if (n * n != count || n > 12 || n < 2) {
                throw new IllegalArgumentException();
            }

            int[][] dist = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = Integer.parseInt(parts[i * n + j].trim());
                }
            }

            int size = 1 << n;
            int INF = Integer.MAX_VALUE / 4;
            int[][] dp = new int[size][n];

            for (int mask = 0; mask < size; mask++) {
                java.util.Arrays.fill(dp[mask], INF);
            }

            dp[1][0] = 0;

            for (int mask = 1; mask < size; mask++) {
                for (int u = 0; u < n; u++) {
                    if ((mask & (1 << u)) == 0 || dp[mask][u] == INF) continue;

                    for (int v = 0; v < n; v++) {
                        if ((mask & (1 << v)) != 0) continue;

                        int nextMask = mask | (1 << v);
                        dp[nextMask][v] = Math.min(
                                dp[nextMask][v],
                                dp[mask][u] + dist[u][v]
                        );
                    }
                }
            }

            int full = size - 1;
            int answer = INF;
            for (int last = 1; last < n; last++) {
                answer = Math.min(
                        answer,
                        dp[full][last] + dist[last][0]
                );
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Bitmask DP / TSP\n\n" +
                    "Minimum tour cost: " + answer + "\n\n" +
                    "Time Complexity: O(n² × 2ⁿ)\n" +
                    "Space Complexity: O(n × 2ⁿ)",
                    "Bitmask DP",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid matrix input.",
                    "Bitmask DP",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void matrixChainDemo() {

        String input = JOptionPane.showInputDialog(
                this,
                "Enter matrix dimensions separated by commas.\n" +
                "Example: 10,20,30,40"
        );

        if (input == null) return;

        try {
            String[] parts = input.split(",");
            int[] dimensions = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {
                dimensions[i] = Integer.parseInt(parts[i].trim());
            }

            MatrixChainMultiplication mcm =
                    new MatrixChainMultiplication();

            int cost = mcm.minimumCost(dimensions);
            String order = mcm.optimalOrder(dimensions);

            JOptionPane.showMessageDialog(
                    this,
                    "Matrix Chain Multiplication\n\n" +
                    "Minimum cost: " + cost + "\n" +
                    "Optimal order: " + order + "\n\n" +
                    "Time Complexity: O(n³)\n" +
                    "Space Complexity: O(n²)",
                    "Matrix Chain Multiplication",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid dimensions.",
                    "Matrix Chain Multiplication",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void optimalBSTDemo() {

        String keysInput = JOptionPane.showInputDialog(
                this,
                "Enter sorted keys separated by commas:\nExample: 10,20,30"
        );
        if (keysInput == null) return;

        String freqInput = JOptionPane.showInputDialog(
                this,
                "Enter frequencies separated by commas:\nExample: 4,2,6"
        );
        if (freqInput == null) return;

        try {
            String[] keyParts = keysInput.split(",");
            String[] freqParts = freqInput.split(",");

            if (keyParts.length != freqParts.length || keyParts.length == 0) {
                throw new IllegalArgumentException();
            }

            int[] keys = new int[keyParts.length];
            int[] frequencies = new int[freqParts.length];

            for (int i = 0; i < keys.length; i++) {
                keys[i] = Integer.parseInt(keyParts[i].trim());
                frequencies[i] = Integer.parseInt(freqParts[i].trim());
            }

            OptimalBST bst = new OptimalBST();
            int cost = bst.minimumCost(keys, frequencies);
            bst.buildOptimalTree(keys, frequencies);

            JOptionPane.showMessageDialog(
                    this,
                    "Optimal BST\n\n" +
                    "Minimum search cost: " + cost + "\n\n" +
                    "Time Complexity: O(n³) for the current implementation\n" +
                    "Space Complexity: O(n²)",
                    "Optimal BST",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid key/frequency input.",
                    "Optimal BST",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

// USER - VIEW PRODUCTS

    // =========================================================

    private void showProducts() {

        showQuery(

                "SELECT product_id, product_name, category, " +

                "price, quantity FROM products ORDER BY product_id",

                "FOODCHAIN PRODUCTS"

        );

    }

    // =========================================================

    // USER - SEARCH PRODUCTS

    // =========================================================

    private void searchProducts() {

        String keyword = JOptionPane.showInputDialog(

                this,

                "Enter product name:"

        );

        if (keyword == null) return;

        showQuery(

                "SELECT product_id, product_name, category, " +

                "price, quantity FROM products " +

                "WHERE product_name LIKE ? " +

                "ORDER BY product_id",

                "Search Results",

                "%" + keyword + "%"

        );

    }

    // =========================================================

    // USER - VIEW SUPPLIERS

    // =========================================================

    private void showSuppliers() {

        showQuery(

                "SELECT supplier_id, supplier_name, location, " +

                "contact_number FROM suppliers ORDER BY supplier_id",

                "Suppliers"

        );

    }

    // =========================================================

    // USER - VIEW INVENTORY

    // =========================================================

    private void showInventory() {

        showQuery(

                "SELECT inventory_id, warehouse_id, product_id, " +

                "quantity, minimum_stock " +

                "FROM inventory ORDER BY inventory_id",

                "Inventory"

        );

    }

    // =========================================================

    // USER - PLACE ORDER

    // =========================================================

    private void placeOrder() {

        try {

            int customerId = getCustomerId();

            if (customerId == -1) {

                JOptionPane.showMessageDialog(

                        this,

                        "Customer account not found."

                );

                return;

            }

            String productInput = JOptionPane.showInputDialog(

                    this,

                    "Enter Product ID:"

            );

            if (productInput == null) return;

            String quantityInput = JOptionPane.showInputDialog(

                    this,

                    "Enter Quantity:"

            );

            if (quantityInput == null) return;

            int productId =

                    Integer.parseInt(productInput);

            int quantity =

                    Integer.parseInt(quantityInput);

            if (quantity <= 0) {

                JOptionPane.showMessageDialog(

                        this,

                        "Quantity must be greater than zero."

                );

                return;

            }

            try (Connection con =

                         DatabaseConnection.getConnection()) {

                con.setAutoCommit(false);

                int stock = 0;

                int warehouseId = -1;

                int minimumStock = 0;

                PreparedStatement ps =

                        con.prepareStatement(

                                "SELECT quantity, warehouse_id, " +

                                "minimum_stock FROM inventory " +

                                "WHERE product_id=? LIMIT 1"

                        );

                ps.setInt(1, productId);

                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {

                    JOptionPane.showMessageDialog(

                            this,

                            "Product is not available in inventory."

                    );

                    con.rollback();

                    return;

                }

                stock = rs.getInt("quantity");

                warehouseId = rs.getInt("warehouse_id");

                minimumStock = rs.getInt("minimum_stock");

                if (stock - quantity < minimumStock) {

                    JOptionPane.showMessageDialog(

                            this,

                            "Order would reduce stock below " +

                            "minimum stock level."

                    );

                    con.rollback();

                    return;

                }

                int orderId = getNextId(

                        con,

                        "orders",

                        "order_id"

                );

                int shipmentId = getNextId(

                        con,

                        "shipments",

                        "shipment_id"

                );

                PreparedStatement update =

                        con.prepareStatement(

                                "UPDATE inventory SET quantity=? " +

                                "WHERE product_id=?"

                        );

                update.setInt(1, stock - quantity);

                update.setInt(2, productId);

                update.executeUpdate();

                PreparedStatement productUpdate =

                        con.prepareStatement(

                                "UPDATE products SET quantity=? " +

                                "WHERE product_id=?"

                        );

                productUpdate.setInt(1, stock - quantity);

                productUpdate.setInt(2, productId);

                productUpdate.executeUpdate();

                PreparedStatement order =

                        con.prepareStatement(

                                "INSERT INTO orders " +

                                "(order_id, customer_id, product_id, " +

                                "quantity, order_date, status) " +

                                "VALUES (?, ?, ?, ?, ?, ?)"

                        );

                order.setInt(1, orderId);

                order.setInt(2, customerId);

                order.setInt(3, productId);

                order.setInt(4, quantity);

                order.setString(

                        5,

                        LocalDate.now().toString()

                );

                order.setString(6, "Pending");

                order.executeUpdate();

                PreparedStatement shipment =

                        con.prepareStatement(

                                "INSERT INTO shipments " +

                                "(shipment_id, order_id, warehouse_id, " +

                                "shipment_date, delivery_date, status) " +

                                "VALUES (?, ?, ?, ?, ?, ?)"

                        );

                shipment.setInt(1, shipmentId);

                shipment.setInt(2, orderId);

                shipment.setInt(3, warehouseId);

                shipment.setString(

                        4,

                        LocalDate.now().toString()

                );

                shipment.setString(5, "Not Delivered");

                shipment.setString(6, "Pending");

                shipment.executeUpdate();

                con.commit();

                JOptionPane.showMessageDialog(

                        this,

                        "ORDER CREATED SUCCESSFULLY!\n\n" +

                        "Order ID: " + orderId +

                        "\nShipment ID: " + shipmentId

                );

            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(

                    this,

                    "Enter valid numbers."

            );

        } catch (SQLException e) {

            showError(e);

        }

    }

    // =========================================================

    // USER - MY ORDERS

    // =========================================================

    private void myOrders() {

        int customerId = getCustomerId();

        if (customerId == -1) return;

        showQuery(

                "SELECT order_id, product_id, quantity, " +

                "order_date, status FROM orders " +

                "WHERE customer_id=? ORDER BY order_id",

                "My Orders",

                customerId

        );

    }

    // =========================================================

    // USER - CANCEL ORDER

    // =========================================================

    private void cancelOrder() {

        int customerId = getCustomerId();

        if (customerId == -1) return;

        String input = JOptionPane.showInputDialog(

                this,

                "Enter Order ID:"

        );

        if (input == null) return;

        try {

            int orderId = Integer.parseInt(input);

            try (Connection con =

                         DatabaseConnection.getConnection()) {

                con.setAutoCommit(false);

                PreparedStatement find =

                        con.prepareStatement(

                                "SELECT product_id, quantity, status " +

                                "FROM orders WHERE order_id=? " +

                                "AND customer_id=?"

                        );

                find.setInt(1, orderId);

                find.setInt(2, customerId);

                ResultSet rs = find.executeQuery();

                if (!rs.next()) {

                    JOptionPane.showMessageDialog(

                            this,

                            "Order not found."

                    );

                    con.rollback();

                    return;

                }

                int productId = rs.getInt("product_id");

                int quantity = rs.getInt("quantity");

                String status = rs.getString("status");

                if (status.equalsIgnoreCase("Delivered") ||

                        status.equalsIgnoreCase("Cancelled")) {

                    JOptionPane.showMessageDialog(

                            this,

                            "This order cannot be cancelled."

                    );

                    con.rollback();

                    return;

                }

                PreparedStatement updateOrder =

                        con.prepareStatement(

                                "UPDATE orders SET status='Cancelled' " +

                                "WHERE order_id=?"

                        );

                updateOrder.setInt(1, orderId);

                updateOrder.executeUpdate();

                PreparedStatement restore =

                        con.prepareStatement(

                                "UPDATE inventory SET quantity=" +

                                "quantity+? WHERE product_id=?"

                        );

                restore.setInt(1, quantity);

                restore.setInt(2, productId);

                restore.executeUpdate();

                PreparedStatement restoreProduct =

                        con.prepareStatement(

                                "UPDATE products SET quantity=" +

                                "quantity+? WHERE product_id=?"

                        );

                restoreProduct.setInt(1, quantity);

                restoreProduct.setInt(2, productId);

                restoreProduct.executeUpdate();

                PreparedStatement shipment =

                        con.prepareStatement(

                                "UPDATE shipments SET status='Cancelled' " +

                                "WHERE order_id=?"

                        );

                shipment.setInt(1, orderId);

                shipment.executeUpdate();

                con.commit();

                JOptionPane.showMessageDialog(

                        this,

                        "Order cancelled successfully."

                );

            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(

                    this,

                    "Invalid Order ID."

            );

        } catch (SQLException e) {

            showError(e);

        }

    }

    // =========================================================

    // USER - TRACK SHIPMENT

    // =========================================================

    private void trackShipment() {

        int customerId = getCustomerId();

        if (customerId == -1) return;

        String input = JOptionPane.showInputDialog(

                this,

                "Enter Order ID:"

        );

        if (input == null) return;

        showQuery(

                "SELECT s.shipment_id, s.order_id, s.warehouse_id, " +

                "s.shipment_date, s.delivery_date, s.status " +

                "FROM shipments s JOIN orders o " +

                "ON s.order_id=o.order_id " +

                "WHERE s.order_id=? AND o.customer_id=?",

                "Shipment Tracking",

                input,

                customerId

        );

    }

    // =========================================================

    // USER - CORRECT PRODUCT NAME

    // =========================================================

    private void correctProductName() {

        String name = JOptionPane.showInputDialog(

                this,

                "Enter product name:"

        );

        if (name == null || name.trim().isEmpty()) return;

        List<ProductData> products = loadProducts();

        ProductData closest = null;

        int best = Integer.MAX_VALUE;

        for (ProductData p : products) {

            int distance =

                    wagnerFischerDistance(

                            name.toLowerCase(),

                            p.name.toLowerCase()

                    );

            if (distance < best) {

                best = distance;

                closest = p;

            }

        }

        if (closest == null) {

            JOptionPane.showMessageDialog(

                    this,

                    "No products found."

            );

        } else {

            JOptionPane.showMessageDialog(

                    this,

                    "Closest Product:\n\n" +

                    "ID: " + closest.id +

                    "\nName: " + closest.name +

                    "\nCategory: " + closest.category

            );

        }

    }

    // =========================================================

    // WAGNER-FISCHER EDIT DISTANCE

    // =========================================================

    private void wagnerFischerDemo() {

        String source = JOptionPane.showInputDialog(

                this,

                "Enter source string:",

                "Wagner-Fischer Edit Distance",

                JOptionPane.PLAIN_MESSAGE

        );

        if (source == null) return;

        String target = JOptionPane.showInputDialog(

                this,

                "Enter target string:",

                "Wagner-Fischer Edit Distance",

                JOptionPane.PLAIN_MESSAGE

        );

        if (target == null) return;

        int distance = wagnerFischerDistance(

                source.toLowerCase(),

                target.toLowerCase()

        );

        JOptionPane.showMessageDialog(

                this,

                "Wagner-Fischer Edit Distance\n\n" +

                "Source: " + source + "\n" +

                "Target: " + target + "\n" +

                "Edit Distance: " + distance + "\n\n" +

                "Time Complexity: O(n × m)\n" +

                "Space Complexity: O(n × m)",

                "DP Result",

                JOptionPane.INFORMATION_MESSAGE

        );

    }

    private int wagnerFischerDistance(String a, String b) {

        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {

            dp[i][0] = i;

        }

        for (int j = 0; j <= b.length(); j++) {

            dp[0][j] = j;

        }

        for (int i = 1; i <= a.length(); i++) {

            for (int j = 1; j <= b.length(); j++) {

                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;

                dp[i][j] = Math.min(

                        Math.min(

                                dp[i - 1][j] + 1,

                                dp[i][j - 1] + 1

                        ),

                        dp[i - 1][j - 1] + cost

                );

            }

        }

        return dp[a.length()][b.length()];

    }

    // =========================================================

    // LEVENSHTEIN DISTANCE

    // =========================================================

    private int levenshtein(String a, String b) {

        int[][] dp =

                new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {

            dp[i][0] = i;

        }

        for (int j = 0; j <= b.length(); j++) {

            dp[0][j] = j;

        }

        for (int i = 1; i <= a.length(); i++) {

            for (int j = 1; j <= b.length(); j++) {

                int cost =

                        a.charAt(i - 1) ==

                        b.charAt(j - 1) ? 0 : 1;

                dp[i][j] = Math.min(

                        Math.min(

                                dp[i - 1][j] + 1,

                                dp[i][j - 1] + 1

                        ),

                        dp[i - 1][j - 1] + cost

                );

            }

        }

        return dp[a.length()][b.length()];

    }

    // =========================================================

    // GET CUSTOMER ID

    // =========================================================

    private int getCustomerId() {

        try (Connection con =

                     DatabaseConnection.getConnection()) {

            PreparedStatement ps =

                    con.prepareStatement(

                            "SELECT customer_id FROM customers " +

                            "WHERE customer_name=? LIMIT 1"

                    );

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt("customer_id");

            }

        } catch (SQLException e) {

            showError(e);

        }

        return -1;

    }

    // =========================================================

    // PRODUCT DATA

    // =========================================================

    private List<ProductData> loadProducts() {

        List<ProductData> list =

                new ArrayList<>();

        try (Connection con =

                     DatabaseConnection.getConnection();

             PreparedStatement ps =

                     con.prepareStatement(

                             "SELECT product_id, product_name, " +

                             "category, price, quantity " +

                             "FROM products"

                     );

             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                list.add(

                        new ProductData(

                                rs.getInt("product_id"),

                                rs.getString("product_name"),

                                rs.getString("category"),

                                rs.getDouble("price"),

                                rs.getInt("quantity")

                        )

                );

            }

        } catch (SQLException e) {

            showError(e);

        }

        return list;

    }

    // =========================================================

    // SHOW PRODUCT LIST

    // =========================================================

    private void showProductList(

            List<ProductData> products,

            String title) {

        String[] columns = {

                "Product ID",

                "Name",

                "Category",

                "Price",

                "Quantity"

        };

        DefaultTableModel model =

                new DefaultTableModel(columns, 0);

        for (ProductData p : products) {

            model.addRow(new Object[]{

                    p.id,

                    p.name,

                    p.category,

                    p.price,

                    p.quantity

            });

        }

        JTable table = new JTable(model);

        JScrollPane scroll =

                new JScrollPane(table);

        scroll.setPreferredSize(

                new Dimension(800, 400)

        );

        JOptionPane.showMessageDialog(

                this,

                scroll,

                title,

                JOptionPane.INFORMATION_MESSAGE

        );

    }

    // =========================================================

    // GENERIC SQL VIEW

    // =========================================================

    private void showQuery(

            String sql,

            String title,

            Object... params) {

        try (Connection con =

                     DatabaseConnection.getConnection();

             PreparedStatement ps =

                     con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {

                Object value = params[i];

                if (value instanceof Integer) {

                    ps.setInt(i + 1, (Integer) value);

                } else {

                    ps.setString(i + 1, String.valueOf(value));

                }

            }

            ResultSet rs = ps.executeQuery();

            ResultSetMetaData meta =

                    rs.getMetaData();

            int columnCount =

                    meta.getColumnCount();

            String[] columns =

                    new String[columnCount];

            for (int i = 0; i < columnCount; i++) {

                columns[i] =

                        meta.getColumnLabel(i + 1);

            }

            DefaultTableModel model =

                    new DefaultTableModel(columns, 0);

            while (rs.next()) {

                Object[] row =

                        new Object[columnCount];

                for (int i = 0; i < columnCount; i++) {

                    row[i] =

                            rs.getObject(i + 1);

                }

                model.addRow(row);

            }

            JTable table =

                    new JTable(model);

            table.setAutoResizeMode(

                    JTable.AUTO_RESIZE_ALL_COLUMNS

            );

            JScrollPane scroll =

                    new JScrollPane(table);

            scroll.setPreferredSize(

                    new Dimension(900, 450)

            );

            JOptionPane.showMessageDialog(

                    this,

                    scroll,

                    title,

                    JOptionPane.INFORMATION_MESSAGE

            );

        } catch (SQLException e) {

            showError(e);

        }

    }

    // =========================================================

    // GENERIC SQL UPDATE

    // =========================================================

    private void execute(

            String sql,

            Object... params) {

        try (Connection con =

                     DatabaseConnection.getConnection();

             PreparedStatement ps =

                     con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {

                Object value = params[i];

                if (value instanceof Integer) {

                    ps.setInt(i + 1, (Integer) value);

                } else {

                    String text =

                            String.valueOf(value);

                    try {

                        if (sql.toLowerCase()

                                .contains("capacity") ||

                                sql.toLowerCase()

                                .contains("quantity") ||

                                sql.toLowerCase()

                                .contains("price") ||

                                sql.toLowerCase()

                                .contains("id")) {

                            if (text.matches("-?\\d+")) {

                                ps.setInt(

                                        i + 1,

                                        Integer.parseInt(text)

                                );

                            } else if (

                                    text.matches(

                                            "-?\\d+(\\.\\d+)?"

                                    )) {

                                ps.setDouble(

                                        i + 1,

                                        Double.parseDouble(text)

                                );

                            } else {

                                ps.setString(i + 1, text);

                            }

                        } else {

                        	
                            ps.setString(i + 1, text);

                        }

                    } catch (Exception ex) {

                        ps.setString(i + 1, text);

                    }

                }

            }

            int rows =

                    ps.executeUpdate();

            JOptionPane.showMessageDialog(

                    this,

                    rows > 0

                            ? "Operation completed successfully."

                            : "No record was changed."

            );

        } catch (SQLException e) {

            showError(e);

        }

    }

    // =========================================================

    // NEXT ID

    // =========================================================

    private int getNextId(

            Connection con,

            String table,

            String column)

            throws SQLException {

        Statement st =

                con.createStatement();

        ResultSet rs =

                st.executeQuery(

                        "SELECT COALESCE(MAX(" +

                        column +

                        "),0)+1 FROM " +

                        table

                );

        rs.next();

        return rs.getInt(1);

    }

    // =========================================================

    // LOGOUT

    // =========================================================

    private void logout() {

        int result =

                JOptionPane.showConfirmDialog(

                        this,

                        "Do you want to logout?",

                        "Logout",

                        JOptionPane.YES_NO_OPTION

                );

        if (result == JOptionPane.YES_OPTION) {

            dispose();

            new LoginFrame().setVisible(true);

        }

    }

    // =========================================================

    // ERROR

    // =========================================================

    private void showError(SQLException e) {

        JOptionPane.showMessageDialog(

                this,

                "Database Error:\n" +

                e.getMessage(),

                "FoodChain Error",

                JOptionPane.ERROR_MESSAGE

        );

    }

    // =========================================================

    // PRODUCT DATA CLASS

    // =========================================================

    private static class ProductData {

        int id;

        String name;

        String category;

        double price;

        int quantity;

        ProductData(

                int id,

                String name,

                String category,

                double price,

                int quantity) {

            this.id = id;

            this.name = name;

            this.category = category;

            this.price = price;

            this.quantity = quantity;

        }


private void showDatabaseNetwork() {
    String sql =
        "SELECT warehouse_id, warehouse_name, location, capacity " +
        "FROM warehouses ORDER BY warehouse_id";

    StringBuilder result = new StringBuilder();
    result.append("=== MYSQL WAREHOUSE NETWORK ===\n\n");

    try (
        java.sql.Connection con =
            data.DatabaseConnection.getConnection();
        java.sql.PreparedStatement ps =
            con.prepareStatement(sql);
        java.sql.ResultSet rs = ps.executeQuery()
    ) {
        boolean found = false;

        while (rs.next()) {
            found = true;

            result.append("Warehouse ID: ")
                .append(rs.getInt("warehouse_id"))
                .append("\nName: ")
                .append(rs.getString("warehouse_name"))
                .append("\nLocation: ")
                .append(rs.getString("location"))
                .append("\nCapacity: ")
                .append(rs.getInt("capacity"))
                .append("\n\n");
        }

        if (!found) {
            result.append("No warehouse records found.");
        }

        javax.swing.JTextArea area =
            new javax.swing.JTextArea(result.toString(), 18, 42);
        area.setEditable(false);
        area.setCaretPosition(0);

        javax.swing.JOptionPane.showMessageDialog(
            new javax.swing.JScrollPane(area),
            "MySQL Warehouse Network"
        );

    } catch (java.sql.SQLException ex) {
        javax.swing.JOptionPane.showInputDialog(
            this,
            "Could not load warehouse data:\n" + ex.getMessage()
        );
        ex.printStackTrace();
    }
}


    }
    private void hamiltonianCycleFoodChainDemo() {

        String sql =
            "SELECT warehouse_id, product_id " +
            "FROM inventory WHERE quantity > 0";

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            Map<Integer, List<Integer>> warehouseProducts =
                new LinkedHashMap<>();

            while (rs.next()) {
                int warehouseId = rs.getInt("warehouse_id");
                int productId = rs.getInt("product_id");

                warehouseProducts
                    .computeIfAbsent(warehouseId, k -> new ArrayList<>())
                    .add(productId);
            }

            List<Integer> warehouses =
                new ArrayList<>(warehouseProducts.keySet());

            int n = warehouses.size();

            if (n < 3) {
                JOptionPane.showMessageDialog(
                    this,
                    "At least 3 warehouses with inventory are needed " +
                    "for this Hamiltonian-cycle demo.",
                    "FoodChain",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            if (n > 10) {
                JOptionPane.showMessageDialog(
                    this,
                    "There are " + n + " warehouses. Brute-force search " +
                    "is limited to 10 warehouses to avoid excessive runtime.",
                    "FoodChain",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            boolean[][] graph = new boolean[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {

                    List<Integer> productsA =
                        warehouseProducts.get(warehouses.get(i));

                    List<Integer> productsB =
                        warehouseProducts.get(warehouses.get(j));

                    for (int product : productsA) {
                        if (productsB.contains(product)) {
                            graph[i][j] = true;
                            graph[j][i] = true;
                            break;
                        }
                    }
                }
            }

            List<Integer> path = new ArrayList<>();
            path.add(0);

            boolean found = findHamiltonianCycle(
                graph, path, 0, n
            );

            StringBuilder output = new StringBuilder();
            output.append("FOODCHAIN — HAMILTONIAN CYCLE\n\n");
            output.append("Warehouse IDs: ").append(warehouses).append("\n\n");

            if (found) {
                output.append("Hamiltonian cycle found:\n");

                for (int i = 0; i < path.size(); i++) {
                    output.append(warehouses.get(path.get(i)));

                    if (i < path.size() - 1) {
                        output.append(" -> ");
                    }
                }

                output.append("\n\nEvery warehouse is visited once before returning to the start.");
            } else {
                output.append(
                    "No Hamiltonian cycle exists in the constructed graph."
                );
            }

            JOptionPane.showMessageDialog(
                this,
                output.toString(),
                "FoodChain Hamiltonian Cycle",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Database error: " + ex.getMessage(),
                "FoodChain Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private boolean findHamiltonianCycle(
            boolean[][] graph,
            List<Integer> path,
            int current,
            int n) {

        if (path.size() == n) {
            return graph[current][path.get(0)];
        }

        for (int next = 1; next < n; next++) {
            if (!path.contains(next) && graph[current][next]) {
                path.add(next);

                if (findHamiltonianCycle(graph, path, next, n)) {
                    path.add(path.get(0));
                    return true;
                }

                path.remove(path.size() - 1);
            }
        }

        return false;
    }
    private void cliqueFoodChainDemo() {

        String sql =
            "SELECT warehouse_id, product_id " +
            "FROM inventory WHERE quantity > 0";

        try (
            Connection con = data.DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            Map<Integer, List<Integer>> warehouseProducts =
                new LinkedHashMap<>();

            while (rs.next()) {
                int warehouseId = rs.getInt("warehouse_id");
                int productId = rs.getInt("product_id");

                warehouseProducts
                    .computeIfAbsent(warehouseId, k -> new ArrayList<>())
                    .add(productId);
            }

            List<Integer> products = new ArrayList<>();

            for (List<Integer> productList : warehouseProducts.values()) {
                for (int productId : productList) {
                    if (!products.contains(productId)) {
                        products.add(productId);
                    }
                }
            }

            int n = products.size();

            if (n == 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "No products with available inventory found.",
                    "FoodChain",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            if (n > 20) {
                JOptionPane.showMessageDialog(
                    this,
                    "There are " + n + " products. Brute-force clique search " +
                    "is limited to 20 products to avoid excessive runtime.",
                    "FoodChain",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            boolean[][] graph = new boolean[n][n];

            // Connect products stocked in the same warehouse.
            for (List<Integer> productList : warehouseProducts.values()) {
                for (int i = 0; i < productList.size(); i++) {
                    for (int j = i + 1; j < productList.size(); j++) {
                        int a = products.indexOf(productList.get(i));
                        int b = products.indexOf(productList.get(j));

                        if (a != b) {
                            graph[a][b] = true;
                            graph[b][a] = true;
                        }
                    }
                }
            }

            List<Integer> bestClique = new ArrayList<>();

            // Brute-force all subsets of products.
            long totalSubsets = 1L << n;

            for (long mask = 1; mask < totalSubsets; mask++) {
                int size = Long.bitCount(mask);

                if (size <= bestClique.size()) {
                    continue;
                }

                List<Integer> candidate = new ArrayList<>();

                for (int i = 0; i < n; i++) {
                    if ((mask & (1L << i)) != 0) {
                        candidate.add(i);
                    }
                }

                boolean isClique = true;

                for (int i = 0; i < candidate.size() && isClique; i++) {
                    for (int j = i + 1; j < candidate.size(); j++) {
                        if (!graph[candidate.get(i)][candidate.get(j)]) {
                            isClique = false;
                            break;
                        }
                    }
                }

                if (isClique) {
                    bestClique = candidate;
                }
            }

            StringBuilder output = new StringBuilder();
            output.append("FOODCHAIN — CLIQUE (BRUTE FORCE)\n\n");
            output.append("Products examined: ").append(n).append("\n");
            output.append("Maximum clique size: ")
                  .append(bestClique.size()).append("\n\n");
            output.append("Products in maximum clique:\n");

            for (int vertex : bestClique) {
                output.append("Product ID: ")
                      .append(products.get(vertex)).append("\n");
            }

            output.append(
                "\nThese products form a clique because every pair " +
                "shares at least one warehouse with available inventory."
            );

            JOptionPane.showMessageDialog(
                this,
                output.toString(),
                "FoodChain Clique Problem",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Database error: " + ex.getMessage(),
                "FoodChain Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    }



