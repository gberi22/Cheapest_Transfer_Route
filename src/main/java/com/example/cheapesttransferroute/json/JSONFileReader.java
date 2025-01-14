package com.example.cheapesttransferroute.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class for reading JSON files from the specified directory
 */
public class JSONFileReader {
    /**
     * @param fileName is the name of JSON file to load
     * @return the content of the file as a string
     * @throws IOException if I/O error occurs while reading the file
     */
    public static String loadJSON(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/json/" + fileName)));
    }
}
