package natan.inthemoon.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.beans.ImmutableBean;

/**
 * Description of a position on the map
 *
 * @author Erwan Ulrich
 * */

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PointDescription {
    private final int x;
    private final int y;
    private final boolean obstacle;

    public int leftIndex(int dimension){
        return (x - 1 + dimension) % dimension;
    }
    public int rightIndex(int dimension){
        return (x + 1 ) % dimension;
    }
    public int upIndex(int dimension){
        return (y - 1 + dimension) % dimension;
    }
    public int downIndex(int dimension){
        return (y + 1) % dimension;
    }
}
