package services;

import java.util.ArrayList;

public class DocumentService {

    private ArrayList<String> documents;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public DocumentService() {

        documents = new ArrayList<>();
    }

    // ==========================================
    // ADD DOCUMENT
    // ==========================================

    public boolean addDocument(String document) {

        if (document == null ||
                document.trim().isEmpty()) {

            return false;
        }

        documents.add(document);

        return true;
    }

    // ==========================================
    // VIEW DOCUMENTS
    // ==========================================

    public void displayDocuments() {

        if (documents.isEmpty()) {

            System.out.println(
                    "No documents available.");

            return;
        }

        System.out.println();
        System.out.println(
                "----------- DOCUMENT LIST -----------");

        for (int i = 0; i < documents.size(); i++) {

            System.out.println(
                    (i + 1) + ". "
                    + documents.get(i));
        }
    }

    // ==========================================
    // SEARCH DOCUMENT
    // ==========================================

    public ArrayList<String> searchDocuments(
            String keyword) {

        ArrayList<String> results =
                new ArrayList<>();

        if (keyword == null ||
                keyword.trim().isEmpty()) {

            return results;
        }

        keyword = keyword.trim().toLowerCase();

        for (String document : documents) {

            if (document.toLowerCase()
                    .contains(keyword)) {

                results.add(document);
            }
        }

        return results;
    }

    // ==========================================
    // DISPLAY SEARCH RESULTS
    // ==========================================

    public void displaySearchResults(
            ArrayList<String> results) {

        if (results == null ||
                results.isEmpty()) {

            System.out.println(
                    "No matching documents found.");

            return;
        }

        System.out.println();
        System.out.println(
                "--------- DOCUMENT SEARCH RESULTS ---------");

        for (String document : results) {

            System.out.println(document);
        }
    }

    // ==========================================
    // GET DOCUMENT COUNT
    // ==========================================

    public int getDocumentCount() {

        return documents.size();
    }

    // ==========================================
    // GET ALL DOCUMENTS
    // ==========================================

    public ArrayList<String> getDocuments() {

        return documents;
    }

    // ==========================================
    // DELETE DOCUMENT
    // ==========================================

    public boolean deleteDocument(int index) {

        if (index < 0 ||
                index >= documents.size()) {

            return false;
        }

        documents.remove(index);

        return true;
    }
}