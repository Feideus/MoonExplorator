package erwan.ulrich.pojos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Node {
    private int level;
    private Node parent;
    private int length;
}
