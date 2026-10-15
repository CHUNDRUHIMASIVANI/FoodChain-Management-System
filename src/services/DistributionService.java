package services;

import java.util.ArrayList;

import models.Warehouse;
import models.DistributionCenter;

public class DistributionService {

    private ArrayList<Warehouse> warehouses;
    private ArrayList<DistributionCenter> centers;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public DistributionService() {

        warehouses = new ArrayList<>();
        centers = new ArrayList<>();
    }

    // ==========================================
    // WAREHOUSE - ADD
    // ==========================================

    public boolean addWarehouse(Warehouse warehouse) {

        if (warehouse == null) {
            return false;
        }

        if (warehouse.getCapacity() < 0) {
            return false;
        }

        if (findWarehouseById(
                warehouse.getWarehouseId()) != null) {

            return false;
        }

        warehouses.add(warehouse);

        return true;
    }

    // ==========================================
    // WAREHOUSE - VIEW
    // ==========================================

    public void displayWarehouses() {

        if (warehouses.isEmpty()) {

            System.out.println(
                    "No warehouses available.");

            return;
        }

        System.out.println();
        System.out.println(
                "----------- WAREHOUSE LIST -----------");

        for (Warehouse warehouse : warehouses) {

            System.out.println(warehouse);
        }
    }

    // ==========================================
    // WAREHOUSE - FIND
    // ==========================================

    public Warehouse findWarehouseById(
            int warehouseId) {

        for (Warehouse warehouse : warehouses) {

            if (warehouse.getWarehouseId()
                    == warehouseId) {

                return warehouse;
            }
        }

        return null;
    }

    // ==========================================
    // WAREHOUSE - UPDATE
    // ==========================================

    public boolean updateWarehouse(
            int warehouseId,
            String warehouseName,
            String location,
            int capacity) {

        Warehouse warehouse =
                findWarehouseById(warehouseId);

        if (warehouse == null) {
            return false;
        }

        if (capacity < 0) {
            return false;
        }

        warehouse.setWarehouseName(
                warehouseName);

        warehouse.setLocation(
                location);

        warehouse.setCapacity(
                capacity);

        return true;
    }

    // ==========================================
    // WAREHOUSE - DELETE
    // ==========================================

    public boolean deleteWarehouse(
            int warehouseId) {

        Warehouse warehouse =
                findWarehouseById(warehouseId);

        if (warehouse == null) {
            return false;
        }

        warehouses.remove(warehouse);

        return true;
    }

    // ==========================================
    // WAREHOUSE - CHECK EXISTS
    // ==========================================

    public boolean warehouseExists(
            int warehouseId) {

        return findWarehouseById(
                warehouseId) != null;
    }

    // ==========================================
    // GET ALL WAREHOUSES
    // ==========================================

    public ArrayList<Warehouse> getWarehouses() {

        return warehouses;
    }

    // ==========================================
    // GET WAREHOUSE COUNT
    // ==========================================

    public int getWarehouseCount() {

        return warehouses.size();
    }

    // ==========================================
    // DISTRIBUTION CENTER - ADD
    // ==========================================

    public boolean addDistributionCenter(
            DistributionCenter center) {

        if (center == null) {
            return false;
        }

        if (center.getCapacity() < 0) {
            return false;
        }

        if (findCenterById(
                center.getCenterId()) != null) {

            return false;
        }

        centers.add(center);

        return true;
    }

    // ==========================================
    // DISTRIBUTION CENTER - VIEW
    // ==========================================

    public void displayDistributionCenters() {

        if (centers.isEmpty()) {

            System.out.println(
                    "No distribution centers available.");

            return;
        }

        System.out.println();
        System.out.println(
                "------ DISTRIBUTION CENTER LIST ------");

        for (DistributionCenter center : centers) {

            System.out.println(center);
        }
    }

    // ==========================================
    // DISTRIBUTION CENTER - FIND
    // ==========================================

    public DistributionCenter findCenterById(
            int centerId) {

        for (DistributionCenter center : centers) {

            if (center.getCenterId()
                    == centerId) {

                return center;
            }
        }

        return null;
    }

    // ==========================================
    // DISTRIBUTION CENTER - UPDATE
    // ==========================================

    public boolean updateDistributionCenter(
            int centerId,
            String centerName,
            String location,
            int capacity) {

        DistributionCenter center =
                findCenterById(centerId);

        if (center == null) {
            return false;
        }

        if (capacity < 0) {
            return false;
        }

        center.setCenterName(centerName);

        center.setLocation(location);

        center.setCapacity(capacity);

        return true;
    }

    // ==========================================
    // DISTRIBUTION CENTER - DELETE
    // ==========================================

    public boolean deleteDistributionCenter(
            int centerId) {

        DistributionCenter center =
                findCenterById(centerId);

        if (center == null) {
            return false;
        }

        centers.remove(center);

        return true;
    }

    // ==========================================
    // DISTRIBUTION CENTER - CHECK EXISTS
    // ==========================================

    public boolean distributionCenterExists(
            int centerId) {

        return findCenterById(centerId)
                != null;
    }

    // ==========================================
    // GET ALL DISTRIBUTION CENTERS
    // ==========================================

    public ArrayList<DistributionCenter>
    getCenters() {

        return centers;
    }

    // ==========================================
    // GET DISTRIBUTION CENTER COUNT
    // ==========================================

    public int getCenterCount() {

        return centers.size();
    }
}