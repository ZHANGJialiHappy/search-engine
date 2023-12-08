package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RankingTest {
    private Ranking systemUnderTest;
    private Set<Page> unionPages;
    private Page page1;
    private Page page2;
    private List<String[]> searchWords1;
    private List<String[]> searchWords2;
    private List<String[]> searchWords3;
    private Map<String, Map<Page, Integer>> invertedIndex;

    @BeforeEach
    void setUp() {
        setUpSearchWord1();
        setUpSearchWord2();
        setUpSearchWord3();
        systemUnderTest = new Ranking();
        setUpUnionPages();
        setUpInvertedIndex();
    }

    private void setUpUnionPages() {
        unionPages = new HashSet<>();
        Map<String, Integer> content1 = new HashMap<>();
        content1.put("word1", 2);
        content1.put("word2", 3);
        content1.put("word3", 3);
        Map<String, Integer> content2 = new HashMap<>();
        content2.put("word1", 1);
        content2.put("word2", 1);
        page1 = new Page("http://page1.com", "title1", content1);
        page2 = new Page("http://page2.com", "title2", content2);
        unionPages.add(page1);
        unionPages.add(page2);
    }

    private void setUpSearchWord1() {
        searchWords1 = new ArrayList<>();
        searchWords1.add(new String[] { "word1" });
    }

    private void setUpSearchWord2() {
        searchWords2 = new ArrayList<>();
        searchWords2.add(new String[] { "word1", "word2" });
    }

    private void setUpSearchWord3() {
        searchWords3 = new ArrayList<>();
        searchWords3.add(new String[] { "word1" });
        searchWords3.add(new String[] { "word2" });

    }

    private void setUpInvertedIndex() {
        Map<Page, Integer> indexPageWord1 = new HashMap<>();
        indexPageWord1.put(page1, 2);
        indexPageWord1.put(page2, 1);
        Map<Page, Integer> indexPageWord2 = new HashMap<>();
        indexPageWord2.put(page1, 3);
        indexPageWord2.put(page2, 1);
        Map<Page, Integer> indexPageWord3 = new HashMap<>();
        indexPageWord3.put(page1, 3);
        invertedIndex = new HashMap<>();
        invertedIndex.put("word1", indexPageWord1);
        invertedIndex.put("word2", indexPageWord2);
        invertedIndex.put("word3", indexPageWord3);
    }

    @Test
    void rankPages_searchWord1_returnPages() {
        List<Page> actual = systemUnderTest.rankPages(unionPages, searchWords1, 2, invertedIndex);
        List<Page> expected = new ArrayList<>();
        if (systemUnderTest.getIsFrequencyInverse()) {
            expected = actual;
        } else {
            expected.add(page2);
            expected.add(page1);
        }
        assertEquals(expected, actual);
    }

    @Test
    void rankPages_searchWord2_returnPages() {
        List<Page> actual = systemUnderTest.rankPages(unionPages, searchWords2, 2, invertedIndex);
        List<Page> expected = new ArrayList<>();
        if (systemUnderTest.getIsFrequencyInverse()) {
            expected = actual;
        } else {
            expected.add(page2);
            expected.add(page1);
        }
        assertEquals(expected, actual);
    }

    @Test
    void rankPages_searchWord3_returnPages() {
        List<Page> actual = systemUnderTest.rankPages(unionPages, searchWords3, 2, invertedIndex);
        List<Page> expected = new ArrayList<>();
        if (systemUnderTest.getIsFrequencyInverse()) {
            expected = actual;
        } else {
            expected.add(page2);
            expected.add(page1);
        }
        assertEquals(expected, actual);
    }

}