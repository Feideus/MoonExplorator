package erwan.ulrich.abstraction;

import erwan.ulrich.pojos.Node;

import java.util.List;

public interface AbstractFileSystemExploratorService {
    String drawPonderedTree(String inputAsString) throws Exception;

    void computeMaxPathLength(List<Node> nodeList) throws Exception;

    int exploreFileSystemDepth(final String inputAsString) throws Exception;
}
