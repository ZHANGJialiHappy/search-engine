package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class SearchServiceTest {
    private SearchService systemUnderTest;
    private Map<String, Map<Page, Integer>> invertedIndex;
    private String searchTerm;

    @BeforeAll
    void setUp() {
        systemUnderTest = new SearchService();
        setUpInvertedIndex();
        searchTerm = "word1 word2 or word3";
    }

    private void setUpInvertedIndex() {
        invertedIndex = new HashMap<>();
        Map<String, Integer> content1 = new HashMap<>();
        content1.put("word1", 2);
        content1.put("word2", 3);
        content1.put("word3", 3);
        Map<String, Integer> content2 = new HashMap<>();
        content2.put("word1", 1);
        content2.put("word2", 1);
        Page page1 = new Page("http://page1.com", "title1", content1);
        Page page2 = new Page("http://page2.com", "title2", content2);
        Map<Page, Integer> indexPageWord1 = new HashMap<>();
        Map<Page, Integer> indexPageWord2 = new HashMap<>();
        Map<Page, Integer> indexPageWord3 = new HashMap<>();
        indexPageWord1.put(page1, 2);
        indexPageWord1.put(page2, 1);
        indexPageWord2.put(page1, 3);
        indexPageWord2.put(page2, 1);
        indexPageWord3.put(page1, 3);
        invertedIndex.put("word1", indexPageWord1);
        invertedIndex.put("word2", indexPageWord2);
        invertedIndex.put("word3", indexPageWord3);
    }

    @Test
    void searchTest_returnList() {
        List<String> expected = new ArrayList<>();
        expected.add("{\"url\": \"http://page1.com\", \"title\": \"title1\"}");
        expected.add("{\"url\": \"http://page2.com\", \"title\": \"title2\"}");
        List<String> actual = new ArrayList<>(systemUnderTest.handleRequest(searchTerm, invertedIndex));
        assertEquals(expected, actual);
    }

}
