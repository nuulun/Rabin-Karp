import java.util.ArrayList;
import java.util.List;

public class RabinKarp {
    private static final long PRIME = 101;
    private static final long BASE = 256;

    public List<Integer> search(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();

        if (text == null || pattern == null || pattern.length() > text.length()) {
            return matches;
        }

        int n = text.length();
        int m = pattern.length();

        long patternHash = 0;
        long textHash = 0;
        long h = 1;

        // Calculate h = BASE^(m-1) % PRIME
        for (int i = 0; i < m - 1; i++) {
            h = (h * BASE) % PRIME;
        }

        // Calculate initial hash values
        for (int i = 0; i < m; i++) {
            patternHash = (BASE * patternHash + pattern.charAt(i)) % PRIME;
            textHash = (BASE * textHash + text.charAt(i)) % PRIME;
        }

        // Slide pattern over text
        for (int i = 0; i <= n - m; i++) {
            // Check if hash values match
            if (patternHash == textHash) {
                // Verify character by character
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    matches.add(i);
                }
            }

            // Calculate hash for next window
            if (i < n - m) {
                textHash = (BASE * (textHash - text.charAt(i) * h) + text.charAt(i + m)) % PRIME;

                if (textHash < 0) {
                    textHash = textHash + PRIME;
                }
            }
        }

        return matches;
    }
}