package erwan.ulrich.abstraction;

import erwan.ulrich.pojos.Node;

import java.util.List;

public interface AbstractInputManagerService {

    List<Node> parseInput(String inputAsString) throws Exception;

    void validateInput(final String inputAsString) throws Exception;
}
