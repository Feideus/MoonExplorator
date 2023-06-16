package erwan.ulrich.implementation;

import erwan.ulrich.abstraction.AbstractFileSystemExploratorService;
import erwan.ulrich.abstraction.AbstractInputManagerService;
import erwan.ulrich.pojos.Node;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class FileSystemExploratorService implements AbstractFileSystemExploratorService {

    private final AbstractInputManagerService inputManagementService;

    private int currentMaxLength = 0;

    public FileSystemExploratorService(AbstractInputManagerService inputManagementService) {
        this.inputManagementService = inputManagementService;
    }

    @Override
    public int exploreFileSystemDepth(String inputAsString) throws Exception {
        inputManagementService.validateInput(inputAsString);

        List<Node> inputs = inputManagementService.parseInput(inputAsString);
        computeMaxPathLength(inputs);

        int temporaryCurrentMax = this.currentMaxLength;
        currentMaxLength = 0;
        return temporaryCurrentMax;
    }

    @Override
    public String drawPonderedTree(String inputAsString) throws Exception {
        inputManagementService.validateInput(inputAsString);
        StringBuilder stringBuilder = new StringBuilder();

        List<Node> inputs = inputManagementService.parseInput(inputAsString);

        return drawPonderedTree(inputs.get(0), " ", stringBuilder).toString();
    }


    @Override
    public void computeMaxPathLength(List<Node> nodeList) throws Exception {
        if (CollectionUtils.isEmpty(nodeList)) {
            throw new Exception("Node list is empty");
        }
        depthFirstSearchAlgorithm(nodeList.get(0), 0);
    }

    private StringBuilder drawPonderedTree(final Node node, final String prefix, StringBuilder stringBuilder) {

        stringBuilder.append(prefix)
                .append(node.getString())
                .append(" (")
                .append(node.getLength())
                .append(")");

        List<Node> children = node.getListChildren();
        if (children != null) {
            for (Node child : children) {
                stringBuilder.append(prefix).append("\n");
                drawPonderedTree(child, prefix + "    ", stringBuilder);
            }
        }
        return stringBuilder;
    }

    private void depthFirstSearchAlgorithm(Node node, int currentLength) {
        if (node == null) {
            return;
        }

        currentLength += node.getLength();

        if (currentLength > this.currentMaxLength) {
            this.currentMaxLength = currentLength;
        }

        for (Node child : node.getListChildren()) {
            depthFirstSearchAlgorithm(child, currentLength);
        }
    }
}
