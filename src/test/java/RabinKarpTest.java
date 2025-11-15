import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RabinKarpTest {
    private RabinKarp algorithm;
    private static FileWriter outputWriter;

    static {
        try {
            outputWriter = new FileWriter("output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp() {
        algorithm = new RabinKarp();
    }

    @Test
    public void testCase1() {
        TestCase tc = readTestCase(0);

        long startTime = System.nanoTime();
        List<Integer> result = algorithm.search(tc.text, tc.pattern);
        long endTime = System.nanoTime();

        assertEquals(3, result.size());
        assertEquals(List.of(0, 4, 8), result);

        saveOutput(1, tc.text, tc.pattern, result, endTime - startTime);
    }

    @Test
    public void testCase2() {
        TestCase tc = readTestCase(1);

        long startTime = System.nanoTime();
        List<Integer> result = algorithm.search(tc.text, tc.pattern);
        long endTime = System.nanoTime();

        assertEquals(1, result.size());
        assertEquals(List.of(15), result);

        saveOutput(2, tc.text, tc.pattern, result, endTime - startTime);
    }

    @Test
    public void testCase3() {
        TestCase tc = readTestCase(2);

        long startTime = System.nanoTime();
        List<Integer> result = algorithm.search(tc.text, tc.pattern);
        long endTime = System.nanoTime();

        assertEquals(9, result.size());
        assertEquals(List.of(2, 7, 12, 17, 22, 27, 32, 37, 42), result);

        saveOutput(3, tc.text, tc.pattern, result, endTime - startTime);
    }

    @Test
    public void testNoMatch() {
        String text = "hello world";
        String pattern = "xyz";
        List<Integer> result = algorithm.search(text, pattern);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSingleCharacter() {
        String text = "aaaaa";
        String pattern = "a";
        List<Integer> result = algorithm.search(text, pattern);
        assertEquals(5, result.size());
    }

    @AfterAll
    public static void printComplexity() {
        try {
            outputWriter.write("\n========================================\n");
            outputWriter.write("ALGORITHM COMPLEXITY ANALYSIS\n");
            outputWriter.write("========================================\n");
            outputWriter.write("Time Complexity: O(n + m) average case\n");
            outputWriter.write("Space Complexity: O(1) auxiliary\n");
            outputWriter.write("Where n = text length, m = pattern length\n");
            outputWriter.write("========================================\n");
            outputWriter.close();

            System.out.println("\n========================================");
            System.out.println("ALGORITHM COMPLEXITY ANALYSIS");
            System.out.println("========================================");
            System.out.println("Time Complexity: O(n + m) average case");
            System.out.println("Space Complexity: O(1) auxiliary");
            System.out.println("Where n = text length, m = pattern length");
            System.out.println("========================================\n");
            System.out.println("Output saved to output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TestCase readTestCase(int index) {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            List<TestCase> testCases = new ArrayList<>();
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Text:")) {
                    String text = line.substring(5);
                    String patternLine = br.readLine();
                    if (patternLine != null && patternLine.startsWith("Pattern:")) {
                        String pattern = patternLine.substring(8);
                        testCases.add(new TestCase(text, pattern));
                    }
                }
            }

            if (index < testCases.size()) {
                return testCases.get(index);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TestCase("", "");
    }

    private void saveOutput(int testNum, String text, String pattern,
                            List<Integer> positions, long time) {
        try {
            String output = "=== Test Case " + testNum + " ===\n" +
                    "Input Text: \"" + text + "\"\n" +
                    "Search Pattern: \"" + pattern + "\"\n" +
                    "Found at positions: " + positions + "\n" +
                    "Total matches: " + positions.size() + "\n" +
                    "Time elapsed: " + time + " ns\n\n";

            outputWriter.write(output);
            outputWriter.flush();
            System.out.print(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class TestCase {
        String text;
        String pattern;

        TestCase(String text, String pattern) {
            this.text = text;
            this.pattern = pattern;
        }
    }
}