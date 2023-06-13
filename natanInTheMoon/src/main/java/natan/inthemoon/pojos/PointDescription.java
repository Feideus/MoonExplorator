package natan.inthemoon.pojos;

import lombok.Builder;

@Builder
public final class PointDescription {
    private final int x;
    private final int y;
    private final boolean obstacle;
}
