package erwan.ulrich.abstraction;

import java.util.List;

public interface AbstractFileSystemExploratorService {
    int computeDepth(List<String> parsedInput);

    int exploreFileSystemDepth(final String inputAsString) throws Exception;
}
