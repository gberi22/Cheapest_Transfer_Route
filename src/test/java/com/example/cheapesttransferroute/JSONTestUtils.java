package com.example.cheapesttransferroute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONTestUtils {

    public static String loadJSON(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/json/" + fileName)));
    }
}
