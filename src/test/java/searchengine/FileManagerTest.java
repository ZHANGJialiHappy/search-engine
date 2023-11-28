package searchengine;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class FileManagerTest {
    private FileManager systemUnderTest;
    private static final String TEST_FILE_PATH = "config.txt";

    @BeforeAll
    void setUp() throws IOException {
        systemUnderTest = new FileManager();
    }

    @Test
    void getFile_noFile_returnEmptyBytes() {
        String invalidFilePath = "nonexistentfile.txt";
        byte[] result = systemUnderTest.getFile(invalidFilePath);
        assertArrayEquals(new byte[0], result, "Invalid file path should return empty bytes");
    }

    @Test
    void getFile_validFileName_returnFileBytes() throws IOException {
        byte[] expectedBytes = Files.readAllBytes(Paths.get(TEST_FILE_PATH));
        byte[] actualBytes = systemUnderTest.getFile(TEST_FILE_PATH);
        assertArrayEquals(expectedBytes, actualBytes, "File content should match");
    }

    @Test
    void getFile_withNullPath_returnEmptyBytes() {
        byte[] result = systemUnderTest.getFile(null);
        assertArrayEquals(new byte[0], result, "");
    }
}
