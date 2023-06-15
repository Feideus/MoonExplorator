package erwan.ulrich.abstraction;

import java.util.List;

public interface AbstractInputManagerService {

    List<String> parseInput(String inputAsString) throws Exception;

    void validateInput(final String inputAsString) throws Exception;
}
