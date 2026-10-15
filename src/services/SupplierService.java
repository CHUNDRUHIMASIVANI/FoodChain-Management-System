package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.DatabaseConnection;
import models.Supplier;

public class SupplierService {

    private ArrayList<Supplier> suppliers;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public SupplierService() {

        suppliers = new ArrayList<>();

        loadSuppliersFromDatabase();
    }

    // ==========================================
    // LOAD SUPPLIERS FROM MYSQL
    // ==========================================

    private void loadSuppliersFromDatabase() {

        String sql = "SELECT supplier_id, supplier_name, location, "
                   + "contact_number FROM suppliers ORDER BY supplier_id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int supplierId =
                        resultSet.getInt("supplier_id");

                String supplierName =
                        resultSet.getString("supplier_name");

                String location =
                        resultSet.getString("location");

                String contactNumber =
                        resultSet.getString("contact_number");

                Supplier supplier = new Supplier(
                        supplierId,
                        supplierName,
                        location,
                        contactNumber
                );

                suppliers.add(supplier);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Warning: Could not load suppliers from MySQL."
            );

            e.printStackTrace();
        }
    }

    // ==========================================
    // ADD SUPPLIER
    // ==========================================

    public boolean addSupplier(Supplier supplier) {

        if (supplier == null) {
            return false;
        }

        // Supplier ID must be unique
        if (findSupplierById(
                supplier.getSupplierId()) != null) {

            return false;
        }

        String sql = "INSERT INTO suppliers "
                   + "(supplier_id, supplier_name, location, contact_number) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    supplier.getSupplierId()
            );

            statement.setString(
                    2,
                    supplier.getSupplierName()
            );

            statement.setString(
                    3,
                    supplier.getLocation()
            );

            statement.setString(
                    4,
                    supplier.getContactNumber()
            );

            int rowsInserted =
                    statement.executeUpdate();

            if (rowsInserted == 1) {

                suppliers.add(supplier);

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error: Could not add supplier to MySQL."
            );

            e.printStackTrace();
        }

        return false;
    }

    // ==========================================
    // VIEW SUPPLIERS
    // ==========================================

    public void displaySuppliers() {

        if (suppliers.isEmpty()) {

            System.out.println(
                    "No suppliers available.");

            return;
        }

        System.out.println();

        System.out.println(
                "----------- SUPPLIER LIST -----------");

        for (Supplier supplier : suppliers) {

            System.out.println(supplier);
        }
    }

    // ==========================================
    // FIND SUPPLIER BY ID
    // ==========================================

    public Supplier findSupplierById(int supplierId) {

        for (Supplier supplier : suppliers) {

            if (supplier.getSupplierId() == supplierId) {

                return supplier;
            }
        }

        return null;
    }

    // ==========================================
    // UPDATE SUPPLIER
    // ==========================================

    public boolean updateSupplier(
            int supplierId,
            String supplierName,
            String location,
            String contactNumber) {

        Supplier supplier =
                findSupplierById(supplierId);

        if (supplier == null) {
            return false;
        }

        String sql = "UPDATE suppliers "
                   + "SET supplier_name = ?, "
                   + "location = ?, "
                   + "contact_number = ? "
                   + "WHERE supplier_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    supplierName
            );

            statement.setString(
                    2,
                    location
            );

            statement.setString(
                    3,
                    contactNumber
            );

            statement.setInt(
                    4,
                    supplierId
            );

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated == 1) {

                supplier.setSupplierName(
                        supplierName
                );

                supplier.setLocation(
                        location
                );

                supplier.setContactNumber(
                        contactNumber
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error: Could not update supplier in MySQL."
            );

            e.printStackTrace();
        }

        return false;
    }

    // ==========================================
    // DELETE SUPPLIER
    // ==========================================

    public boolean deleteSupplier(int supplierId) {

        Supplier supplier =
                findSupplierById(supplierId);

        if (supplier == null) {
            return false;
        }

        String sql =
                "DELETE FROM suppliers WHERE supplier_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    supplierId
            );

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted == 1) {

                suppliers.remove(supplier);

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error: Could not delete supplier from MySQL."
            );

            e.printStackTrace();
        }

        return false;
    }

    // ==========================================
    // CHECK SUPPLIER EXISTS
    // ==========================================

    public boolean supplierExists(int supplierId) {

        return findSupplierById(supplierId) != null;
    }

    // ==========================================
    // GET ALL SUPPLIERS
    // ==========================================

    public ArrayList<Supplier> getSuppliers() {

        return suppliers;
    }

    // ==========================================
    // GET SUPPLIER COUNT
    // ==========================================

    public int getSupplierCount() {

        return suppliers.size();
    }
}