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
}
