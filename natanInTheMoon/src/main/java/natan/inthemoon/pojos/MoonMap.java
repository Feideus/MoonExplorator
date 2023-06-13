package natan.inthemoon.pojos;

import lombok.Builder;

import java.util.List;

@Builder
public final class MoonMap {

    private final int dimension;
    private final List<PointDescription> pointList;
}
