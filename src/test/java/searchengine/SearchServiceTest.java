package searchengine;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class SearchServiceTest {
    private SearchService systemUnderTest;

    @BeforeAll
    void setUp() {
        systemUnderTest = new SearchService();
    }

    @Test
    void searchTest_returnList() {
        // we have to refactor the data structure, so wait.

    }

}
