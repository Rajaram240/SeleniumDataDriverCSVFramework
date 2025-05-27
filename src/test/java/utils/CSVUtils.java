package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap; // To maintain insertion order for column mapping

public class CSVUtils {

    private static String filePath;
    // To store the CSV data in memory
    private static List<Map<String, String>> csvData = new ArrayList<>();
    private static List<String> headers = new ArrayList<>();

    // You might choose a different delimiter if your data contains commas
    private static final String DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String QUOTE = "\""; // For handling commas in data

    /**
     * Loads CSV data from the specified file into memory.
     * Assumes the first row is a header row.
     *
     * @param path The path to the CSV file.
     * @throws IOException If an I/O error occurs.
     */
    public static void loadCSV(String path) throws IOException {
        filePath = path;
        csvData.clear(); // Clear any previous data
        headers.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read header
            String line = br.readLine();
            if (line != null) {
                headers = parseCsvLine(line); // Parse header to get column names
            } else {
                throw new IOException("CSV file is empty or has no header: " + filePath);
            }

            // Read data rows
            while ((line = br.readLine()) != null) {
                List<String> values = parseCsvLine(line);
                Map<String, String> row = new LinkedHashMap<>(); // Use LinkedHashMap to preserve order
                for (int i = 0; i < headers.size() && i < values.size(); i++) {
                    row.put(headers.get(i), values.get(i));
                }
                csvData.add(row);
            }
            System.out.println("CSV file loaded successfully: " + filePath);
        }
    }

    /**
     * Parses a single CSV line, handling quoted commas.
     * @param line The CSV line to parse.
     * @return A list of string values.
     */
    private static List<String> parseCsvLine(String line) {
        List<String> values = new ArrayList<>();
        // Simple regex to split by comma, ignoring commas inside double quotes
        // This regex is a common way to handle basic CSV parsing
        String[] parsed = line.split(DELIMITER + "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        for (String value : parsed) {
            values.add(value.trim().replaceAll("^\"|\"$", "")); // Remove leading/trailing quotes and trim
        }
        return values;
    }


    /**
     * Writes the in-memory CSV data back to the file.
     * This will overwrite the existing file.
     *
     * @throws IOException If an I/O error occurs.
     */
    public static void writeCSV() throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalStateException("CSV file path not loaded. Call loadCSV() first.");
        }
        if (headers.isEmpty()) {
            throw new IllegalStateException("No headers found. CSV data might be empty.");
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            // Write header
            pw.println(String.join(DELIMITER, headers));

            // Write data rows
            for (Map<String, String> row : csvData) {
                List<String> values = new ArrayList<>();
                for (String header : headers) {
                    String value = row.getOrDefault(header, "");
                    // Quote values that contain delimiter or new line
                    if (value.contains(DELIMITER) || value.contains(NEW_LINE_SEPARATOR)) {
                        values.add(QUOTE + value.replace(QUOTE, QUOTE + QUOTE) + QUOTE); // Handle inner quotes
                    } else {
                        values.add(value);
                    }
                }
                pw.println(String.join(DELIMITER, values));
            }
            System.out.println("CSV file saved successfully: " + filePath);
        }
    }

    /**
     * Gets data for a specific cell by row index (0-based for data rows, excluding header) and column name.
     *
     * @param rowIndex   0-based index of the data row (0 for the first data row after header).
     * @param columnName The name of the column (from the header).
     * @return The cell data as a String, or empty string if not found.
     */
    public static String getCellData(int rowIndex, String columnName) {
        if (rowIndex < 0 || rowIndex >= csvData.size()) {
            return "";
        }
        Map<String, String> row = csvData.get(rowIndex);
        return row.getOrDefault(columnName, "");
    }

    /**
     * Sets data for a specific cell by row index (0-based for data rows) and column name.
     * Modifies the in-memory data. Call writeCSV() to save to file.
     *
     * @param rowIndex   0-based index of the data row.
     * @param columnName The name of the column to update.
     * @param data       The data to set.
     */
    public static void setCellData(int rowIndex, String columnName, String data) {
        // Ensure the row exists, or create it if needed (less common for writing results)
        if (rowIndex >= csvData.size()) {
            // For now, let's just ensure the row exists. Extend this if you need to create rows.
            while (csvData.size() <= rowIndex) {
                csvData.add(new LinkedHashMap<>()); // Add empty maps until row exists
            }
        }
        Map<String, String> row = csvData.get(rowIndex);
        row.put(columnName, data);

        // Ensure the column header exists
        if (!headers.contains(columnName)) {
            headers.add(columnName);
            // Reorder headers if necessary or keep new columns at the end
        }
    }

    /**
     * Gets the number of data rows (excluding header).
     * @return The number of data rows.
     */
    public static int getRowCount() {
        return csvData.size();
    }

    /**
     * Gets the list of header names.
     * @return A list of header strings.
     */
    public static List<String> getHeaders() {
        return new ArrayList<>(headers);
    }
}