package natan.inthemoon.pojos;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public final class MoonMap {

    private final int dimension;
    private final List<PointDescription> pointList;
}
