package erwan.ulrich.implementation;

import erwan.ulrich.abstraction.AbstractInputManagerService;
import erwan.ulrich.pojos.Node;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class InputManagerService implements AbstractInputManagerService {

    @Override
    public List<Node> parseInput(String inputAsString) throws Exception {
        validateInput(inputAsString);
        int maxDepth = computeMaxDepth(inputAsString);

        inputAsString = inputAsString.replaceAll("\\n", "");

        List<String> splittedBits2 = Arrays.stream(inputAsString.split("\\t")).collect(Collectors.toList());

        List<Node> nodeList = new ArrayList<>();

        Node root = Node
                .builder()
                .level(0)
                .length(splittedBits2.get(0).length())
                .string(splittedBits2.get(0))
                .parent(null)
                .build();
        nodeList.add(root);

        splittedBits2.remove(0);

        int depthCurrentNode = 1;

        for (int i = 0; i < splittedBits2.size(); i++) {
            String currentString = splittedBits2.get(i);
            if (currentString.isBlank() || currentString.isEmpty()) {
                depthCurrentNode = depthCurrentNode + 1;
            } else {
                String parentString = lastNodeAtLevelBeforeIndex(depthCurrentNode, i, splittedBits2);
                Node newNode = Node
                        .builder()
                        .level(depthCurrentNode)
                        .string(currentString)
                        .length(currentString.length())
                        .listChildren(new ArrayList<>())
                        .parentString(parentString != null ? parentString : root.getString())
                        .build();

                nodeList.add(newNode);
                depthCurrentNode = 1;
            }
        }

        for (Node node : nodeList) {
            // exclude root
            if (node.getLevel() != 0) {
                node.setParent(findParentFromString(node, nodeList));
            }
        }

        for (Node node : nodeList) {
            // exclude root
            if (node.getLevel() != maxDepth) {
                node.setListChildren(findChildrenFromNode(node, nodeList));
            }
        }

        return nodeList;
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

    private Node findParentFromString(final Node node, final List<Node> nodeList) throws Exception {
        return nodeList
                .stream()
                .filter(node1 -> node1.getString().equals(node.getString()))
                .findFirst()
                .orElseThrow(() -> new Exception("No parent found"));
    }

    private List<Node> findChildrenFromNode(final Node node, final List<Node> nodeList) {
        return nodeList
                .stream()
                .filter(node1 -> (node1.getParentString() != null
                        && node1.getParentString().equals(node.getString())))
                .collect(Collectors.toList());
    }

    public String lastNodeAtLevelBeforeIndex(int level, int index, List<String> inputList) {
        List<String> localList = inputList.subList(0, index);
        String parentNodeAsString = null;
        int currentLevel = 1;

        for (String localString : localList) {
            if (localString.isBlank() || localString.isEmpty()) {
                currentLevel = currentLevel + 1;
            } else {
                if (currentLevel == level - 1 && isDirectory(localString)) {
                    parentNodeAsString = localString;
                }
                currentLevel = 1;
            }
        }
        return parentNodeAsString;
    }

    private boolean isDirectory(String string) {
        return !string.contains(".");
    }
}
