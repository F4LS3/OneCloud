package de.f4ls3.netty.utils;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;

public class FileParser {

    private Gson gson = new Gson();

    public String parseFile(File file) {
        try {
            if (file.getName().endsWith(".json")) {
                StringBuilder builder = new StringBuilder();
                Scanner scanner = new Scanner(file);

                while(scanner.hasNext()) builder.append(scanner.nextLine());
                return builder.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
