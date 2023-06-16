package erwan.ulrich.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Node {
    private int level;
    private Node parent;
    private List<Node> listChildren;
    private String string;
    private String parentString;
    private int length;
}
