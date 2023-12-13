package searchengine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FileManager {
    private List<Page> pages;
    private Map<String, Map<Page, Integer>> invertedIndex;

    public FileManager(String filename) throws IOException {
        readFile(filename);
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

    private void readFile(String filename) throws IOException {
        try {
            pages = new ArrayList<>();
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
            in.close();
            String url = "";
            String title = "";
            Map<String, Integer> content = new HashMap<>();

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
                    Integer occurrencesNum = 1;
                    String word = lines.get(i).toLowerCase();
                    if (content.containsKey(word)) {
                        occurrencesNum += content.get(word);
                    }
                    content.put(word, occurrencesNum);
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
        for (Page page : pages) {
            Map<String, Integer> content = page.getContent();
            for (Entry<String, Integer> entry : content.entrySet()) {
                String word = entry.getKey();
                Integer occurrenceNum = entry.getValue();
                Map<Page, Integer> indexPage = new HashMap<>();
                if (invertedIndex.containsKey(word)) {
                    indexPage = invertedIndex.get(word);
                }
                indexPage.put(page, occurrenceNum);
                invertedIndex.put(word, indexPage);
            }
        }
    }

    public Map<String, Map<Page, Integer>> getInvertedIndex() {
        return invertedIndex;
    }

    public int getQuantityOfPages() {
        return pages.size();
    }
}
