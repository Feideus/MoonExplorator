package natan.inthemoon.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class PointDescription {
    private final int x;
    private final int y;
    private final boolean obstacle;
}
