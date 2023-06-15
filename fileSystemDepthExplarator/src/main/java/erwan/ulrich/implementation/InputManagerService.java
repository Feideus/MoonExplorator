package erwan.ulrich.implementation;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import erwan.ulrich.abstraction.AbstractInputManagerService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class InputManagerService implements AbstractInputManagerService {


    @Override
    public List<String> parseInput(final String inputAsString) throws Exception {
        validateInput(inputAsString);
        int maxDepth = computeMaxDepth(inputAsString);

        Pattern pattern;
        Matcher matcher;

        for (int i = 1; i < maxDepth; i++) {
            String levelRegex = "(?<!\\t)(\\t){" + i + "}(?!\\t)";
            pattern = Pattern.compile(levelRegex);
            matcher = pattern.matcher(inputAsString);
            var res = matcher.results();
        }

        return Arrays.asList(inputAsString);
    }

    @Override
    public void validateInput(final String inputAsString) throws Exception {
        if (inputAsString == null || inputAsString.isBlank() || inputAsString.isEmpty()) {
            throw new Exception("Invalid input");
        }

        // regex checks if string has 2 consecutive backslashes
        Pattern pattern = Pattern.compile("\\\\");
        Matcher matcher = pattern.matcher(inputAsString);

        if (matcher.matches()) {
            throw new Exception("Invalid input");
        }
    }

    private int computeMaxDepth(final String inputAsString) {
        if (inputAsString == null || inputAsString.isEmpty()) {
            return 0;
        }

        Pattern pattern = Pattern.compile("\t+");
        Matcher matcher = pattern.matcher(inputAsString);

        int maxDepth = 0;
        while (matcher.find()) {
            String match = matcher.group();
            int numTabs = match.length();
            if (numTabs > maxDepth) {
                maxDepth = numTabs;
            }
        }

        return maxDepth;
    }
}
