package models;

public class QualityReport {

    private int reportId;
    private int productId;
    private String reportDate;
    private String qualityStatus;
    private String description;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public QualityReport(
            int reportId,
            int productId,
            String reportDate,
            String qualityStatus,
            String description) {

        this.reportId = reportId;
        this.productId = productId;
        this.reportDate = reportDate;
        this.qualityStatus = qualityStatus;
        this.description = description;
    }

    // ==========================================
    // GET REPORT ID
    // ==========================================

    public int getReportId() {

        return reportId;
    }

    // ==========================================
    // GET PRODUCT ID
    // ==========================================

    public int getProductId() {

        return productId;
    }

    // ==========================================
    // GET REPORT DATE
    // ==========================================

    public String getReportDate() {

        return reportDate;
    }

    // ==========================================
    // GET QUALITY STATUS
    // ==========================================

    public String getQualityStatus() {

        return qualityStatus;
    }

    // ==========================================
    // GET DESCRIPTION
    // ==========================================

    public String getDescription() {

        return description;
    }

    // ==========================================
    // UPDATE PRODUCT ID
    // ==========================================

    public void setProductId(int productId) {

        this.productId = productId;
    }

    // ==========================================
    // UPDATE REPORT DATE
    // ==========================================

    public void setReportDate(String reportDate) {

        this.reportDate = reportDate;
    }

    // ==========================================
    // UPDATE QUALITY STATUS
    // ==========================================

    public void setQualityStatus(
            String qualityStatus) {

        this.qualityStatus = qualityStatus;
    }

    // ==========================================
    // UPDATE DESCRIPTION
    // ==========================================

    public void setDescription(
            String description) {

        this.description = description;
    }

    // ==========================================
    // DISPLAY QUALITY REPORT
    // ==========================================

    @Override
    public String toString() {

        return "Report ID: " + reportId
                + ", Product ID: " + productId
                + ", Date: " + reportDate
                + ", Quality Status: " + qualityStatus
                + ", Description: " + description;
    }
}