package org.spyne.libs;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Generic_Lib {

    public static Map readConfigFile(String filePath){
        Map<String, String> configMap = new HashMap<>();
        Properties properties = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            // Load properties file
            properties.load(fileInputStream);

            // Iterate through properties and put them into the HashMap
            for (String key : properties.stringPropertyNames()) {
                configMap.put(key, properties.getProperty(key));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return configMap;
    }

    public static void writeResult_CSV(String filePath, List<String[]> data) {
        File file = new File(filePath);

        // Delete the file if it exists
        if (file.exists()) {
            try {
                Files.delete(Path.of(filePath));
                System.out.println("Existing file deleted: " + filePath);
            } catch (IOException e) {
                System.err.println("Failed to delete the existing file: " + e.getMessage());
            }
        }

        // Create a new CSV file and add items from the list
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            writer.writeAll(data);
            System.out.println("New file created and data added: " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to create the new CSV file: " + e.getMessage());
        }
    }
}
