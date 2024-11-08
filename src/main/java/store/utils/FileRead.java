package store.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileRead {

    public static List<String> readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        List<String> contents = readFileContents(br);

        br.close();
        contents.removeFirst();
        return contents;
    }

    private static List<String> readFileContents(BufferedReader br) throws IOException {
        List<String> contents = new ArrayList<>();
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            contents.add(line);
        }
        return contents;
    }
}
