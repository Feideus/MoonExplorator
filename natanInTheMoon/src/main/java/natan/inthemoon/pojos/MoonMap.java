package natan.inthemoon.pojos;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Representation of the moon as a 2D map.
 * Dimension is the length of one border (thus, the map is square)
 *
 * @author Erwan Ulrich
 * */

@Builder
@Getter
public final class MoonMap {

    private final int dimension;
    private final List<WeightedPointDescription> pointList;
}
