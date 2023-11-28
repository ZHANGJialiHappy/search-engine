package searchengine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileManager {
    private List<Page> pages;
    private String filename;
    private Map<String, Map<Page, Double>> invertedIndex;
    private Set<String> allWords;

    public FileManager() throws IOException {
        filename = Files.readString(Paths.get("config.txt")).strip();
        readFile();
        createdInvertedIndex();
    }

    byte[] getFile(String filename) {
        try {
            return Files.readAllBytes(Paths.get(filename));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private void readFile() throws IOException {
        try {
            pages = new ArrayList<>();
            allWords = new HashSet<>();
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
            in.close();
            String url = "";
            String title = "";
            Map<String, Double> content = new HashMap<>();

            int pageLine = 0;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith("*PAGE")) {
                    if (title != "" && !content.isEmpty()) {
                        Page page = new Page(url, title, content);
                        pages.add(page);
                        title = "";
                        content = new HashMap<>();
                    }
                    pageLine = i;
                    url = lines.get(i).substring(6);
                } else if (i == pageLine + 1) {
                    title = lines.get(i);
                } else if (i > pageLine + 1) {
                    double occurrencesNum = 1;
                    String word = lines.get(i).toLowerCase();
                    if (content.containsKey(word)) {
                        occurrencesNum = content.get(word) + 1;
                    }
                    content.put(word, occurrencesNum);
                    allWords.add(word);
                }
            }
            if (title != "" && !content.isEmpty()) {
                Page page = new Page(url, title, content);
                pages.add(page);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createdInvertedIndex() {
        invertedIndex = new HashMap<>();
        for (String word : allWords) {
            Map<Page, Double> indexPages = new HashMap<>();
            for (Page page : pages) {
                if (page.getContent().containsKey(word)) {
                    indexPages.put(page, page.getContent().get(word));
                }
                invertedIndex.put(word, indexPages);
            }
        }
    }

    public Map<String, Map<Page, Double>> getInvertedIndex() {
        return invertedIndex;
    }
}
