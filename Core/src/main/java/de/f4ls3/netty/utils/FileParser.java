package de.f4ls3.netty.utils;

import java.io.File;
import java.util.Scanner;

public class FileParser {

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
